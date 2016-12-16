package com.leoni.data.manager;

import com.leoni.data.models.Presence;
import org.joda.time.Interval;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 4.2.2013
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
public interface PresenceManager extends GenericManager<Presence>
    {
    public List<Presence> loadByBandAndInterval(String band, Interval interval);
    public List<Presence> loadByInterval(Interval interval);
    public List<Presence> loadByInterval(Interval interval, boolean defIfNone);
    public List<Presence> loadDefault (String band);
    public List<String> getShiftNames();
    public List<String> getBandNames();
    public String getShifColor(String shiftName);
    public boolean isOverlap (Presence presence);
    }
