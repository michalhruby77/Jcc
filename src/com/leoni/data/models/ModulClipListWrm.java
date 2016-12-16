package com.leoni.data.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.1.2014
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "modul_clip_list_wrm", schema = "public", catalog = "")
@Entity
public class ModulClipListWrm implements Comparable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "moduls_id")
    private Moduls moduls;
    @Column (name = "kod_clip")
    private String codeClip;
    @Column (name = "is_brett")
    private Boolean isBrett;
    @Column (name = "note")
    private String note;
    @Column (name = "condition_erz_nr")
    private String conditionErzNr;
    @Column (name = "changed_by")
    private String changedBy;
    @Column (name = "changed_date")
    private Date changedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Moduls getModuls() {
        return moduls;
    }

    public void setModuls(Moduls moduls) {
        this.moduls = moduls;
    }

    public String getCodeClip() {
        return codeClip;
    }

    public void setCodeClip(String codeClip) {
        this.codeClip = codeClip;
    }

    public Boolean getIsBrett() {
        return isBrett;
    }

    public void setIsBrett(Boolean isBrett) {
        this.isBrett = isBrett;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getConditionErzNr() {
        return conditionErzNr;
    }

    public void setConditionErzNr(String conditionErzNr) {
        this.conditionErzNr = conditionErzNr;
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
        ModulClipListWrm modulClipListWrm = (ModulClipListWrm) o;
        if(this.getCodeClip() == null){
            if(modulClipListWrm.getCodeClip() == null)
                return 0; //equal
            else
                return 1;} // null is before other strings
        else // this.member != null
        {if(modulClipListWrm.getCodeClip() == null)
            return -1;  // all other strings are after null
        else
            return this.getCodeClip().compareTo(modulClipListWrm.getCodeClip());}
    }
}
