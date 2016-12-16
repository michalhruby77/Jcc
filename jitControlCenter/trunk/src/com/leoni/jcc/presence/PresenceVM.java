package com.leoni.jcc.presence;

import com.leoni.data.manager.PresenceManager;
import com.leoni.data.models.Presence;
import com.leoni.jcc.presence.xml.Workplace;
import com.leoni.jcc.presence.xml.Workplaces;
import com.leoni.jcc.presence.xml.XmlHandler;
import org.apache.log4j.Logger;
import org.joda.time.*;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 5.2.2013
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */

@VariableResolver (org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PresenceVM
    {
    public static String RADIO_INTERVAL_ATTRIBUTE_NAME = "timeIntervalString";

    @WireVariable
    private PresenceManager presenceManager;
    private List<Presence> presenceDayList = new ArrayList<Presence>();

    // attributes selectable from GUI
    private MutableDateTime selectedDate = new MutableDateTime();
    private String selectedBand = "F991LL";
    private MutableInterval selectedTimeInterval = new MutableInterval(0, 0);

    // attributes affected with selectable attributes
    private PresenceSettings selectedPresenceSettings = new PresenceSettings(selectedBand, new MutableDateTime(selectedTimeInterval.getStart()), new MutableDateTime(selectedTimeInterval.getEnd()));
    private Presence selectedPresence;
    private Workplaces selectedWorkplaces;

    private ListModelList<String> shiftNamesListModel;
    private Boolean editEnabled = true;     // TODO: false - block editing input elements

    @Wire
    private Radiogroup shiftTimeIntervalRadiogroup;
    @Wire
    private Vbox siteAHolder;
    @Wire
    private Vbox siteBHolder;
    Logger log = Logger.getLogger(PresenceVM.class);

    /**
     * ****************************************************************
     * ZK UI handle
     * *****************************************************************
     */

    @Init
    public void doInit(@ContextParam (ContextType.VIEW) Component view)
        {
        Selectors.wireComponents(view, this, false);
        selectedDate.setTime(0);
        selectedDate.add(Period.weeks(2));
        presenceDayList = presenceManager.loadByInterval(new Interval(selectedDate, Period.days(1)));
        Collections.sort(presenceDayList, new PresenceComparatorByBeginTime());
        selectedPresence = presenceDayList.get(0);
        selectedTimeInterval = new MutableInterval(selectedPresence.getShiftBegin().getTime(), selectedPresence.getShiftEnd().getTime());
        selectedPresenceSettings.setShiftName(selectedPresence.getShiftName());
        selectedPresenceSettings.getBegin().setTime(new DateTime(selectedPresence.getShiftBegin()));
        selectedPresenceSettings.getEnd().setTime(new DateTime(selectedPresence.getShiftBegin()));
        selectedPresenceSettings = new PresenceSettings(selectedPresence.getShiftName(), new MutableDateTime(selectedPresence.getShiftBegin()), new MutableDateTime(selectedPresence.getShiftEnd()));
        shiftNamesListModel = new ListModelList<String>(presenceManager.getShiftNames());
        refreshShiftTimeIntervalRadiogroup();
        }

    @Command
    @NotifyChange ("selectedPresenceSettings")
    public void calendarChanged(@BindingParam ("calendar") Calendar calendar)
        {
        Clients.showBusy(siteAHolder, "Execute...");
        selectedDate.setDate(new DateTime(calendar.getValue()));
        selectedDate.setTime(0);
        reloadPresenceDayList();
        refreshShiftTimeIntervalRadiogroup();
        Clients.clearBusy(siteAHolder);
        }

    @Command
    @NotifyChange ("selectedPresenceSettings")
    public void bandSelected(@BindingParam (value = "bandName") String bandName)
        {
        if ("Zadna Oblast".equals(bandName))
            {
            selectedBand = "ZADOBL";
            }
        else
            {
            selectedBand = bandName;
            }
        refreshShiftTimeIntervalRadiogroup();
        }

    @Command
    @NotifyChange ("selectedPresenceSettings")
    public void shiftSelected(@BindingParam (value = "radio") Radio radio)
        {
        selectedTimeInterval = new MutableInterval(radio.getAttribute(RADIO_INTERVAL_ATTRIBUTE_NAME));
        selectionChanged();
        }

    @Command
    public void shiftSave()
        {
        Presence tmpPresence = new Presence(selectedPresence);
        tmpPresence.setShiftBegin(new Timestamp(selectedPresenceSettings.getBegin().getMillis()));
        tmpPresence.setShiftEnd(new Timestamp(selectedPresenceSettings.getEnd().getMillis()));
        tmpPresence.setShiftName(selectedPresenceSettings.getShiftName());
        tmpPresence.setLastChange(new Timestamp(new Date().getTime()));
        tmpPresence.setPlan(XmlHandler.marshall(selectedWorkplaces));
        boolean allOk = true;
        if (tmpPresence.getShiftName() == null || "".equals(tmpPresence.getShiftName()))
            {
            Messagebox.show("Vyber farbu smeny.");
            allOk = false;
            }
        if (presenceManager.isOverlap(tmpPresence))
            {
            Messagebox.show("Nastavenie smeny nie je platne.\nV zadanom case pracuje uz ina smena.");
            log.warn("Nastavenie smeny nie je platne. V zadanom case pracuje uz ina smena."
                    + " bandName: " + tmpPresence.getBandName()
                    + ", startTime: " + tmpPresence.getShiftBegin()
                    + ", endTime: " + tmpPresence.getShiftEnd());
            allOk = false;
            }

        if (allOk)
            {
            presenceManager.saveOrUpdate(tmpPresence);
            reloadPresenceDayList();
            refreshShiftTimeIntervalRadiogroup();
            }
        }

    @Command
    public void shiftCopy()
        {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        Presence copy = new Presence(selectedPresence);
        copy.setId(null);
        paramMap.put("presence", copy);
        Window window = (Window) Executions.createComponents("/presence/shiftCopyWindow.zul", null, paramMap);
        window.doModal();
        }

    @Command
    @NotifyChange ("selectedPresenceSettings")
    public void shiftDelete()
        {
        Messagebox.show("Urcite chces zmazat smenu?\nTento krok nie je mozne vratit spet.", "Zmazat", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener<Event>()
                {
                public void onEvent(Event evt) throws InterruptedException
                    {
                    if (evt.getName().equals("onOK"))
                        {
                        presenceManager.delete(selectedPresence);
                        presenceDayList.removeAll(presenceDayList);
                        Messagebox.show("Smena vymazana! ");
                        refreshShiftTimeIntervalRadiogroup();
                        }
                    }
                });
        }

    @Command
    public void addWorkplace()
        {

        }


    /*******************************************************************
     * BOSSINESS LOGIC
     *******************************************************************/

    /**
     * Method repaint workplaces.
     */
    private void rebuildWorkplaceEditor()
        {
        siteAHolder.getChildren().removeAll(siteAHolder.getChildren());
        siteBHolder.getChildren().removeAll(siteBHolder.getChildren());
        selectedWorkplaces = XmlHandler.unmarshall(selectedPresence.getPlan());
        Workplaces wo = selectedWorkplaces;
        for (Workplace wr : wo.getWorkplaces())
            {
            if (wr.getId().contains("A"))
                {
                siteAHolder.appendChild(ComponentFactory.getWorkplaceGroupbox(wr));
                }
            else
                {
                siteBHolder.appendChild(ComponentFactory.getWorkplaceGroupbox(wr));
                }
            }
        }

    /**
     * Load fresh Presence objects to presenceDayList.
     */
    @GlobalCommand
    public void reloadPresenceDayList()
        {
        selectedDate.setTime(0);
        presenceDayList = presenceManager.loadByInterval(new Interval(selectedDate, Period.days(1)));
        }

    /**
     * Return presence list filtered with bandName.
     *
     * @param bandName Band name to filter.
     * @return presenceDayList filtered from presenceDayList
     */
    public List<Presence> getPresenceList(String bandName)
        {
        if (presenceDayList == null || presenceDayList.size() < 1)
            {
            reloadPresenceDayList();
            }
        List<Presence> pl = new ArrayList<Presence>();
        for (Presence p : presenceDayList)
            {
            if (bandName.equals(p.getBandName()))
                {
                pl.add(p);
                }
            }
        if (pl.size() <= 0)
            {
            List<Presence> tmp = presenceManager.loadDefault(bandName);
            MutableDateTime mdt = new MutableDateTime(selectedDate);
            for (Presence p : tmp)
                {
                Presence tmpPresence = new Presence(p);
                tmpPresence.setId(null);
                mdt.setTime(new DateTime(tmpPresence.getShiftBegin()));
                tmpPresence.setShiftBegin(new Timestamp(mdt.getMillis()));
                mdt.setTime(new DateTime(tmpPresence.getShiftEnd()));
                tmpPresence.setShiftEnd(new Timestamp(mdt.getMillis()));
                pl.add(tmpPresence);
                }
            presenceDayList.addAll(pl);
            }
        return pl;
        }

    /**
     * Method rebuild shift time interval radiogroup and also notify required components for change.
     */
    @GlobalCommand
    public void refreshShiftTimeIntervalRadiogroup()
        {
        int sel = shiftTimeIntervalRadiogroup.getSelectedIndex();
        while (shiftTimeIntervalRadiogroup.getItemCount() > 0)
            {
            shiftTimeIntervalRadiogroup.removeItemAt(0);
            }
        selectedDate.setTime(0);
        List<Presence> pl = getPresenceList(selectedBand);
        Collections.sort(pl, new PresenceComparatorByBeginTime());
        for (Presence p : pl)
            {
            Radio r = ComponentFactory.getShiftTimeIntervalButton(JodaUtils.createInterval(p.getShiftBegin(), p.getShiftEnd()));
            r.setStyle("background-color: " + presenceManager.getShifColor(p.getShiftName()));
            shiftTimeIntervalRadiogroup.appendChild(r);
            }
        if (sel >= shiftTimeIntervalRadiogroup.getItemCount() || sel < 0)
            {
            sel = 0;
            }

        if (pl.size() > 0)
            {
            shiftTimeIntervalRadiogroup.setSelectedIndex(sel);
            selectedTimeInterval = new MutableInterval(shiftTimeIntervalRadiogroup.getSelectedItem().getAttribute(RADIO_INTERVAL_ATTRIBUTE_NAME));
            }
        selectionChanged();
        }

    /**
     * Find Presence in given presenceList.
     *
     * @param bandName
     * @param interval
     * @param presenceList
     * @return
     */
    public Presence findPresence(String bandName, Interval interval, List<Presence> presenceList)
        {
        Interval mi = null;
        if (presenceList != null && presenceList.size() > 0)
            {
            for (Presence presence : presenceList)
                {
                mi = JodaUtils.createInterval(presence.getShiftBegin(), presence.getShiftEnd());
                if (bandName.equals(presence.getBandName()) && interval.equals(mi))
                    {
                    return presence;
                    }
                }
            }
        return null;
        }

    /**
     * Method is responsible for caching loaded Presences and it can every time return right Presence object.
     *
     * @param bandName For which you want Presence object.
     * @param interval For which you want Presence object.
     * @return Right Presence object.
     */
    public Presence getPresence(String bandName, Interval interval)
        {
        Presence p;
        if ((p = findPresence(bandName, interval, presenceDayList)) != null)
            {
            return p;
            }

        MutableDateTime mdt = interval.getStart().toMutableDateTime();
        mdt.setTime(0);
        presenceDayList = presenceManager.loadByInterval(new Interval(mdt, Period.days(1)));
        p = findPresence(bandName, interval, presenceDayList);
        return p;
        }

    /**
     * Refresh all UI components after selection change.
     */

    public void selectionChanged()
        {
        selectedPresence = getPresence(selectedBand, selectedTimeInterval.toInterval());
        selectedPresenceSettings.setShiftName(selectedPresence.getShiftName());
        MutableDateTime mtd = new MutableDateTime(selectedPresence.getShiftBegin().getTime());
        selectedPresenceSettings.setBegin(mtd);
        mtd = new MutableDateTime(selectedPresence.getShiftEnd().getTime());
        selectedPresenceSettings.setEnd(mtd);
        selectedWorkplaces = XmlHandler.unmarshall(selectedPresence.getPlan());
        rebuildWorkplaceEditor();
        }


    /**
     * ****************************************************************
     * GETTERS AND SETTERS
     * *****************************************************************
     */
    public ListModel<String> getShiftNamesListModel()
        {
        return shiftNamesListModel;
        }

    public void setShiftNamesListModel(ListModelList<String> shiftNamesListModel)
        {
        this.shiftNamesListModel = shiftNamesListModel;
        }

    public PresenceSettings getSelectedPresenceSettings()
        {
        return selectedPresenceSettings;
        }

    public void setSelectedPresenceSettings(PresenceSettings selectedPresenceSettings)
        {
        this.selectedPresenceSettings = selectedPresenceSettings;
        }
    }
