package com.leoni.jcc.presence;

import com.leoni.data.manager.PresenceManager;
import com.leoni.data.models.Presence;
import com.leoni.jcc.presence.xml.Workplace;
import com.leoni.jcc.presence.xml.Workplaces;
import com.leoni.jcc.presence.xml.XmlHandler;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 21.2.2013
 * Time: 7:34
 * To change this template use File | Settings | File Templates.
 */

@VariableResolver (org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ShiftCopyWindowVM
    {
    @Wire ("window")
    Window window;
    @WireVariable
    PresenceManager presenceManager;

    Presence editedPresence;

    /**
     * ****************************************************************
     * ZK UI handle
     * *****************************************************************
     */

    @AfterCompose
    public void doInit(@ExecutionArgParam ("presence") Presence presence, @ContextParam (ContextType.VIEW) Component view)
        {
        Selectors.wireComponents(view, this, false);
        editedPresence = presence;
        }

    @Command
    public void close()
        {
        window.detach();
        }

    @Command
    public void save()
        {
        if (!presenceManager.isOverlap(editedPresence))
            {
            List<Presence> defaultPresenceList = presenceManager.loadDefault(editedPresence.getBandName());
            Workplaces defaultWorkplaces = XmlHandler.unmarshall(defaultPresenceList.get(0).getPlan());
            Workplaces copyWorkplaces = XmlHandler.unmarshall(editedPresence.getPlan());
            Workplaces newWorkplaces = new Workplaces();

            for (Workplace defWp : defaultWorkplaces.getWorkplaces())
                {
                boolean find = false;
                for (Workplace cpWp : copyWorkplaces.getWorkplaces())
                    {
                    if (defWp.getId().equals(cpWp.getId()))
                        {
                        newWorkplaces.getWorkplaces().add(new Workplace(cpWp));
                        find = true;
                        break;
                        }
                    }
                if (!find)
                    {
                    newWorkplaces.getWorkplaces().add(new Workplace(defWp));
                    }
                }
            if ("Zadna Oblast".equals(editedPresence.getBandName()))
                {
                editedPresence.setBandName("ZADOBL");
                }
            editedPresence.setPlan(XmlHandler.marshall(newWorkplaces));
            presenceManager.saveOrUpdate(editedPresence);
            BindUtils.postGlobalCommand(null, null, "reloadPresenceDayList", null);
            BindUtils.postGlobalCommand(null, null, "refreshShiftTimeIntervalRadiogroup", null);
            window.detach();
            }
        else
            {
            Messagebox.show("Nastavenie smeny nie je platne. \nV zadanom case pracuje uz ina smena.");
            }
        }

    /**
     * ****************************************************************
     * GETTERS AND SETTERS
     * *****************************************************************
     */

    public PresenceManager getPresenceManager()
        {
        return presenceManager;
        }

    public void setPresenceManager(PresenceManager presenceManager)
        {
        this.presenceManager = presenceManager;
        }

    public Presence getEditedPresence()
        {
        return editedPresence;
        }

    public void setEditedPresence(Presence editedPresence)
        {
        this.editedPresence = editedPresence;
        }

    public String getBandName()
        {
        return editedPresence.getBandName();
        }

    public void setBandName(String bandName)
        {
        this.editedPresence.setBandName(bandName);
        }

    public String getShiftName()
        {
        return editedPresence.getShiftName();
        }

    public void setShiftName(String shiftName)
        {
        editedPresence.setShiftName(shiftName);
        }

    public Date getShiftBeginDate()
        {
        return new Date(editedPresence.getShiftBegin().getTime());
        }

    @NotifyChange ("shiftEndDate")
    public void setShiftBeginDate(Date shiftBeginDate)
        {
        MutableDateTime mdt = new MutableDateTime(editedPresence.getShiftBegin());
        mdt.setDate(new DateTime(shiftBeginDate));
        Period period = new Period(editedPresence.getShiftBegin().getTime(), editedPresence.getShiftEnd().getTime());
        editedPresence.getShiftBegin().setTime(mdt.getMillis());
//        mdt.setTime(new DateTime(editedPresence.getShiftEnd()));
        mdt.add(period);
        editedPresence.getShiftEnd().setTime(mdt.getMillis());
        }

    public Date getShiftBeginTime()
        {
        return new Date(editedPresence.getShiftBegin().getTime());
        }

    public void setShiftBeginTime(Date shiftBeginTime)
        {
        MutableDateTime mdt = new MutableDateTime(editedPresence.getShiftBegin());
        mdt.setTime(new DateTime(shiftBeginTime));
        editedPresence.getShiftBegin().setTime(mdt.getMillis());
        }

    public Date getShiftEndDate()
        {
        return new Date(editedPresence.getShiftEnd().getTime());
        }

    public void setShiftEndDate(Date shiftEndDate)
        {
        MutableDateTime mdt = new MutableDateTime(editedPresence.getShiftEnd());
        mdt.setDate(new DateTime(shiftEndDate));
        editedPresence.getShiftEnd().setTime(mdt.getMillis());
        }

    public Date getShiftEndTime()
        {
        return new Date(editedPresence.getShiftEnd().getTime());
        }

    public void setShiftEndTime(Date shiftEndTime)
        {
        MutableDateTime mdt = new MutableDateTime(editedPresence.getShiftEnd());
        mdt.setTime(new DateTime(shiftEndTime));
        editedPresence.getShiftEnd().setTime(mdt.getMillis());
        }
    }
