package com.leoni.data.models.dab;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 25.8.2015
 * Time: 8:07
 * To change this template use File | Settings | File Templates.
 */
@Entity
@IdClass(PrnrId.class)
@Table(name = "prnr")
public class Prnr {

    @Id
    @Column(name = "prnr")
    private String prodNr;
    @Id
    @Column (name = "kskz")
    private String kabelsatzKz;

    @Column (name = "kdnr")
    private String customerNr;

    @Column (name = "stain")
    private String statusIn;

    @Column (name = "staprint")
    private String statusPrint;

    @Column (name = "staband")
    private String statusBand;

    @Column (name = "liefdkd")
    private Integer prodDate;

    @Column (name = "difficulty")
    private Integer difficulty;

    @Column (name = "brettid")
    private String brettId;

    @Column (name = "prgrp")
    private String prodGroup;

    @Column (name = "bretttype")
    private String boardType;

    @Column (name = "ausf")
    private String ausfuehrung;

    @Column (name = "reihfnr")
    private String reihfNr;

    @Column (name = "lock")
    private String lock;

    @Column (name = "sta_montage_liste")
    private String staMontageListe;

    public String getProdNr() {
        return prodNr;
    }

    public void setProdNr(String prodNr) {
        this.prodNr = prodNr;
    }

    public String getKabelsatzKz() {
        return kabelsatzKz;
    }

    public void setKabelsatzKz(String kabelsatzKz) {
        this.kabelsatzKz = kabelsatzKz;
    }

    public String getCustomerNr() {
        return customerNr;
    }

    public void setCustomerNr(String customerNr) {
        this.customerNr = customerNr;
    }

    public String getStatusIn() {
        return statusIn;
    }

    public void setStatusIn(String statusIn) {
        this.statusIn = statusIn;
    }

    public Integer getProdDate() {
        return prodDate;
    }

    public void setProdDate(Integer prodDate) {
        this.prodDate = prodDate;
    }

    public String getStatusPrint() {
        return statusPrint;
    }

    public void setStatusPrint(String statusPrint) {
        this.statusPrint = statusPrint;
    }

    public String getStatusBand() {
        return statusBand;
    }

    public void setStatusBand(String statusBand) {
        this.statusBand = statusBand;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getBrettId() {
        return brettId;
    }

    public void setBrettId(String brettId) {
        this.brettId = brettId;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public String getProdGroup() {
        return prodGroup;
    }

    public void setProdGroup(String prodGroup) {
        this.prodGroup = prodGroup;
    }

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    public String getAusfuehrung() {
        return ausfuehrung;
    }

    public void setAusfuehrung(String ausfuehrung) {
        this.ausfuehrung = ausfuehrung;
    }

    public String getReihfNr() {
        return reihfNr;
    }

    public void setReihfNr(String reihfNr) {
        this.reihfNr = reihfNr;
    }

    public String getStaMontageListe() {
        return staMontageListe;
    }

    public void setStaMontageListe(String staMontageListe) {
        this.staMontageListe = staMontageListe;
    }
}
