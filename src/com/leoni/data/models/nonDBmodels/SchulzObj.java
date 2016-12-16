package com.leoni.data.models.nonDBmodels;

import com.leoni.data.models.Lpab64;
import com.leoni.data.models.Moduls;

import java.util.List;

/**
 * Created by hrmi1005 on 9. 12. 2015.
 */
public class SchulzObj {
    private String prodNr;
    private String ksKz;
    private String boardType;
    private String ausf;
    private String reihenf;
    private String kdnr;
    private Integer liefdkd;
    private String prgrp;
    private List<Lpab64> modulList;

    public SchulzObj(String prodNr, String ksKz, String boardType, String ausf, String reihenf, String kdnr, Integer liefdkd, String prgrp) {
        this.prodNr = prodNr;
        this.ksKz = ksKz;
        this.boardType = boardType;
        this.ausf = ausf;
        this.reihenf = reihenf;
        this.kdnr = kdnr;
        this.liefdkd = liefdkd;
        this.prgrp = prgrp;
    }

    public String getProdNr() {
        return prodNr;
    }

    public void setProdNr(String prodNr) {
        this.prodNr = prodNr;
    }

    public String getKsKz() {
        return ksKz;
    }

    public void setKsKz(String ksKz) {
        this.ksKz = ksKz;
    }

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    public String getAusf() {
        return ausf;
    }

    public void setAusf(String ausf) {
        this.ausf = ausf;
    }

    public String getReihenf() {
        return reihenf;
    }

    public void setReihenf(String reihenf) {
        this.reihenf = reihenf;
    }

    public String getKdnr() {
        return kdnr;
    }

    public void setKdnr(String kdnr) {
        this.kdnr = kdnr;
    }

    public Integer getLiefdkd() {
        return liefdkd;
    }

    public void setLiefdkd(Integer liefdkd) {
        this.liefdkd = liefdkd;
    }

    public List<Lpab64> getModulList() {
        return modulList;
    }

    public void setModulList(List<Lpab64> modulList) {
        this.modulList = modulList;
    }

    public String getPrgrp() {
        return prgrp;
    }

    public void setPrgrp(String prgrp) {
        this.prgrp = prgrp;
    }
}
