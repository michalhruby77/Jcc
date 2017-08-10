package com.leoni.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "stoffs", schema = "public")
@Entity
public class Stoff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column (name = "obrazok")
    private byte[] obrazok;
    @Column (name = "nazov")
    private String nazov;
    @Column (name = "stoff_nr")
    private String stoffNr;
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

    public byte[] getObrazok() {
        return obrazok;
    }

    public void setObrazok(byte[] obrazok) {
        this.obrazok = obrazok;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getStoffNr() {
        return stoffNr;
    }

    public void setStoffNr(String stoffNr) {
        this.stoffNr = stoffNr;
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
