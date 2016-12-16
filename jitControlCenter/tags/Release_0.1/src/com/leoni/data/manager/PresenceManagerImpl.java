package com.leoni.data.manager;

import com.leoni.data.criterion.*;
import com.leoni.data.models.Presence;
import com.leoni.jcc.presence.JodaUtils;
import org.joda.time.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 4.2.2013
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */

@Service ("presenceManager")
public class PresenceManagerImpl extends GenericManagerImpl<Presence> implements PresenceManager
    {
    private static List<String> shiftNames = Arrays.asList("", "MODRA", "ORANZOVA", "ZELENA", "ZLTA");
    private static Map<String, String> shiftColors = new HashMap<String, String>()
    {{
    put("MODRA", "#0099FF");
    put("ZELENA", "#00FF00");
    put("ORANZOVA", "#FF9900");
    put("ZLTA", "#FFFF00");
    }};

    private static List<String> bandNames = Arrays.asList("F991LL", "F981LL", "F9X1RL", "Zadna Oblast");

    public List<Presence> loadByBandAndInterval(String band, Interval interval)
        {
        CriteriaAppender[] criteriaAppenders = {new Equal(Presence.COLUMN_BAND_NAME, band),
                new Between(Presence.COLUMN_SHIFT_BEGIN, interval.getStart().toDate(), interval.getStart().toDate())};
        return find(Arrays.asList(criteriaAppenders));
        }

    /**
     * Load presence entity by date. If presence isn't find for specific date, default values are loaded.
     *
     * @param interval Specify requested day.
     * @return List of Presence entities.
     */
    public List<Presence> loadByInterval(Interval interval)
        {
        return loadByInterval(interval, true);
        }

    /**
     * Load presence entity by interval. If presence isn't find for specific interval, default values are loaded.
     * Default values are saved in database with Presence.COLUMN_SHIFT_BEGIN = (Date: 1970-01-01, Time: set).
     *
     * @param interval  Specify requested interval.
     * @param defIfNone true - Load default,if day hasn't entity.
     * @return List of Presence entities.
     */
    public List<Presence> loadByInterval(Interval interval, boolean defIfNone)
        {
        Between btw = new Between(Presence.COLUMN_SHIFT_BEGIN, interval.getStart().toDate(), interval.getEnd().toDate());
        List<Presence> lp = find(btw);
        if (lp.size() == 0 && defIfNone)
            {
            Interval i = new Interval(new DateTime(0), Period.days(1));
            btw.setLoValue(i.getStart().toDate());
            btw.setHiValue(i.getEnd().toDate());
            lp = find(btw);
            MutableDateTime mdt = new MutableDateTime(interval.getStart());
            List<Presence> tmp = new ArrayList<Presence>();
            for (Presence p : lp)
                {
                Presence tmpPresence = new Presence(p);
                tmpPresence.setId(null);
                mdt.setTime(new DateTime(tmpPresence.getShiftBegin()));
                tmpPresence.setShiftBegin(new Timestamp(mdt.getMillis()));
                mdt.setTime(new DateTime(tmpPresence.getShiftEnd()));
                tmpPresence.setShiftEnd(new Timestamp(mdt.getMillis()));
                tmp.add(tmpPresence);
                }
            return tmp;
            }
        return lp;
        }

    /**
     * Load default Presence list for requested bandName
     *
     * @param bandName Band name for load default values.
     * @return Default values for requested bandName
     */
    public List<Presence> loadDefault(String bandName)
        {
        Interval interval = new Interval(new DateTime(0), Period.days(1));
        Between btw = new Between(Presence.COLUMN_SHIFT_BEGIN, interval.getStart().toDate(), interval.getEnd().toDate());
        Equal equal = new Equal(Presence.COLUMN_BAND_NAME, bandName);
        return find(Arrays.asList(btw, equal));
        }

    public List<String> getShiftNames()
        {
        return shiftNames;
        }

    public String getShifColor(String shiftName)
        {
        return shiftColors.get(shiftName);
        }

    /**
     * Check overlap this presence in database.
     *
     * @param presence Presence for check in database.
     * @return True if overlap in database. Otherwise false.
     */
    public boolean isOverlap(Presence presence)
        {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal(Presence.COLUMN_BAND_NAME, presence.getBandName()));

        Interval presenceInterval = JodaUtils.createInterval(presence.getShiftBegin(), presence.getShiftEnd());
        caList.add(new Or(new Between(Presence.COLUMN_SHIFT_BEGIN, presenceInterval.getStart().toDate(), presenceInterval.getEnd().toDate()),
                new Between(Presence.COLUMN_SHIFT_END, presenceInterval.getStart().toDate(), presenceInterval.getEnd().toDate())));

        if (presence.getId() != null)
            {
            caList.add(new NotEqual(Presence.COLUMN_ID, presence.getId()));
            }

        List<Presence> presences = find(caList);

        for (Presence p : presences)
            {
            if (JodaUtils.isOverlap(presenceInterval, JodaUtils.createInterval(p.getShiftBegin(), p.getShiftEnd())))
                {
                return true;
                }
            }
        return false;
        }

    public List<String> getBandNames()
        {
        return bandNames;
        }

    }
