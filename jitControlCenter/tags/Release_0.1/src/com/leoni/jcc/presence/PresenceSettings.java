package com.leoni.jcc.presence;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 6.2.2013
 * Time: 8:22
 * To change this template use File | Settings | File Templates.
 */
public class PresenceSettings
    {
    private MutableDateTime begin;
    private MutableDateTime end;

    private String shiftName;

    public PresenceSettings(String shiftName, MutableDateTime begin, MutableDateTime end)
        {
        this.begin = begin;
        this.end = end;
        this.shiftName = shiftName;
        }

    public PresenceSettings(PresenceSettings ps)
        {
        this.begin = ps.begin;
        this.end = ps.end;
        this.shiftName = ps.shiftName;
        }

    public MutableDateTime getBegin()
        {
        return begin;
        }

    public void setBegin(MutableDateTime begin)
        {
        this.begin = begin;
        }

    public MutableDateTime getEnd()
        {
        return end;
        }

    public void setEnd(MutableDateTime end)
        {
        this.end = end;
        }

    public Date getBeginDate()
        {
        return begin.toDate();
        }

    public void setBeginDate(Date begin)
        {
        this.begin.setDate(new DateTime(begin));
        }

    public Date getBeginTime()
        {
        return begin.toDate();
        }

    public void setBeginTime(Date begin)
        {
        this.begin.setTime(new DateTime(begin));
        }

    public Date getEndDate()
        {
        return end.toDate();
        }

    public void setEndDate(Date end)
        {
        this.end.setDate(new DateTime(end));
        }

    public Date getEndTime()
        {
        return end.toDate();
        }

    public void setEndTime(Date end)
        {
        this.end.setTime(new DateTime(end));
        }

    public String getShiftName()
        {
        return shiftName;
        }

    public void setShiftName(String shiftName)
        {
        this.shiftName = shiftName;
        }
    }
