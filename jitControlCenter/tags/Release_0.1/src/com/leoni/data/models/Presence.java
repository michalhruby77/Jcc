package com.leoni.data.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 4.2.2013
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table (name = "presence")
public class Presence
    {
    public  enum EnumTest {TEST1, TEST2, TEST3, TEST4}
    public static final String COLUMN_ID = "id";
//    public static final String COLUMN_SHIFT_DATE = "shiftDate";
    public static final String COLUMN_SHIFT_BEGIN = "shiftBegin";
    public static final String COLUMN_SHIFT_END = "shiftEnd";
    public static final String COLUMN_SHIFT_NAME = "shiftName";
    public static final String COLUMN_BAND_NAME = "bandName";
    public static final String COLUMN_LAST_CHANGE = "lastChange";
    public static final String COLUMN_PLAN = "plan";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
//    @Column (name = "shift_date")
//    private Date shiftDate;
    @Column (name = "shift_begin")
    private Timestamp shiftBegin;
    @Column (name = "shift_end")
    private Timestamp shiftEnd;
    @Column (name = "shift_name")
    private String shiftName;
    @Column (name = "band_name")
    private String bandName;
    @Column (name = "last_change")
    private Timestamp lastChange;
    @Column (name = "plan")
    private String plan;

    public Presence()
        {
        }

    public Presence(Presence presence)
        {
        this.id = presence.id;
        this.shiftBegin = presence.shiftBegin;
        this.shiftEnd = presence.shiftEnd;
        this.shiftName = presence.shiftName;
        this.bandName = presence.bandName;
        this.lastChange = presence.lastChange;
        this.plan = presence.plan;
        }

    public Integer getId()
        {
        return id;
        }

    public void setId(Integer id)
        {
        this.id = id;
        }

    public String getShiftName()
        {
        return shiftName;
        }

    public void setShiftName(String shiftName)
        {
        this.shiftName = shiftName;
        }

    public String getBandName()
        {
        return bandName;
        }

    public void setBandName(String bandName)
        {
        this.bandName = bandName;
        }

    public Timestamp getShiftBegin()
        {
        return shiftBegin;
        }

    public void setShiftBegin(Timestamp shiftBegin)
        {
        this.shiftBegin = shiftBegin;
        }

    public Timestamp getShiftEnd()
        {
        return shiftEnd;
        }

    public void setShiftEnd(Timestamp shiftEnd)
        {
        this.shiftEnd = shiftEnd;
        }

    public Timestamp getLastChange()
        {
        return lastChange;
        }

    public void setLastChange(Timestamp lastChange)
        {
        this.lastChange = lastChange;
        }

    public String getPlan()
        {
        return plan;
        }

    public void setPlan(String plan)
        {
        this.plan = plan;
        }

    @Override
    public boolean equals(Object o)
        {
        if (this == o)
            {
            return true;
            }
        if (o == null || getClass() != o.getClass())
            {
            return false;
            }

        Presence presence = (Presence) o;

        if (!bandName.equals(presence.bandName))
            {
            return false;
            }
        if (!id.equals(presence.id))
            {
            return false;
            }
        if (!lastChange.equals(presence.lastChange))
            {
            return false;
            }
        if (!shiftBegin.equals(presence.shiftBegin))
            {
            return false;
            }
//        if (!shiftDate.equals(presence.shiftDate))
//            {
//            return false;
//            }
        if (!shiftEnd.equals(presence.shiftEnd))
            {
            return false;
            }
        if (!shiftName.equals(presence.shiftName))
            {
            return false;
            }

        return true;
        }

    @Override
    public int hashCode()
        {
        int result = id.hashCode();
//        result = 31 * result + shiftDate.hashCode();
        result = 31 * result + shiftBegin.hashCode();
        result = 31 * result + shiftEnd.hashCode();
        result = 31 * result + shiftName.hashCode();
        result = 31 * result + bandName.hashCode();
        result = 31 * result + lastChange.hashCode();
        return result;
        }
    }
