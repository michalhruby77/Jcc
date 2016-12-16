package com.leoni.data.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 18.1.2013
 * Time: 8:53
 * To change this template use File | Settings | File Templates.
 */
@Table (name = "sicherungenrelais_9x1_wrm", schema = "public", catalog = "")
@Entity
public class SicherungenRelais9X1Wrm implements Comparable
    {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MODULS_ID = "moduls_id";
    public static final String COLUMN_BOX = "box";
    public static final String COLUMN_PLATZ = "platz";
    public static final String COLUMN_WERT = "wert";
    public static final String COLUMN_BESCHREIBUNG = "beschreibung";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = COLUMN_ID)
    private Integer id;
    @OneToOne
    @JoinColumn(name = COLUMN_MODULS_ID)
    private Moduls moduls;
    @Column (name = COLUMN_BOX)
    private String box;
    @Column (name = COLUMN_PLATZ)
    private String platz;
    @Column (name = COLUMN_WERT)
    private String wert;
    @Column (name = COLUMN_BESCHREIBUNG)
    private String beschreibung;
    @Column (name = "changed_by")
    private String changedBy;
    @Column (name = "changed_date")
    private Date changedDate;

    public Integer getId()
        {
        return id;
        }
    public void setId(Integer id)
        {
        this.id = id;
        }


    public Moduls getModuls()
        {
        return moduls;
        }
    public void setModuls(Moduls moduls)
        {
        this.moduls = moduls;
        }


    public String getBox()
        {
        return box;
        }
    public void setBox(String box)
        {
        this.box = box;
        }


    public String getPlatz()
        {
        return platz;
        }
    public void setPlatz(String platz)
        {
        this.platz = platz;
        }


    public String getWert()
        {
        return wert;
        }
    public void setWert(String wert)
        {
        this.wert = wert;
        }


    public String getBeschreibung()
        {
        return beschreibung;
        }
    public void setBeschreibung(String beschreibung)
        {
        this.beschreibung = beschreibung;
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

        SicherungenRelais9X1Wrm that = (SicherungenRelais9X1Wrm) o;

        if (beschreibung != null ? !beschreibung.equals(that.beschreibung) : that.beschreibung != null)
            {
            return false;
            }
        if (box != null ? !box.equals(that.box) : that.box != null)
            {
            return false;
            }
        if (id != null ? !id.equals(that.id) : that.id != null)
            {
            return false;
            }
        if (moduls != null ? !moduls.equals(that.moduls) : that.moduls != null)
            {
            return false;
            }
        if (platz != null ? !platz.equals(that.platz) : that.platz != null)
            {
            return false;
            }
        if (wert != null ? !wert.equals(that.wert) : that.wert != null)
            {
            return false;
            }

        return true;
        }

    @Override
    public int hashCode()
        {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (moduls != null ? moduls.hashCode() : 0);
        result = 31 * result + (box != null ? box.hashCode() : 0);
        result = 31 * result + (platz != null ? platz.hashCode() : 0);
        result = 31 * result + (wert != null ? wert.hashCode() : 0);
        result = 31 * result + (beschreibung != null ? beschreibung.hashCode() : 0);
        return result;
        }

        public String getChangedBy() {
            return changedBy;
        }

        public void setChangedBy(String changedBy) {
            this.changedBy = changedBy;
        }

        public Date getChangedDate() {
            return changedDate;
        }

        public void setChangedDate(Date changedDate) {
            this.changedDate = changedDate;
        }

        public int compareTo(Object o) {
            SicherungenRelais9X1Wrm sr = (SicherungenRelais9X1Wrm) o;
            if(this.getBox() == null){
                if(sr.getBox() == null)
                    return 0; //equal
                else
                    return 1;} // null is before other strings
            else // this.member != null
            {if(sr.getBox() == null)
                return -1;  // all other strings are after null
            else
                return this.getBox().compareTo(sr.getBox());}
        }
    }
