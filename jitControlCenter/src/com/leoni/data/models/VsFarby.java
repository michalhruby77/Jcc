package com.leoni.data.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 16.12.2014
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "vs_farby", schema = "public", catalog = "")
@Entity
public class VsFarby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Lob
    @Column (name = "obrazok")
    private byte[] obrazok;
    @Column (name = "popis")
    private String popis;
    @Column (name = "changed_by")
    private String changedBy;
    @Column (name = "changed_date")
    private Date changedDate;

    public byte[] getObrazok() {
        return obrazok;
    }

    public void setObrazok(byte[] obrazok) {
        this.obrazok = obrazok;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
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
}
