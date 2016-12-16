package com.leoni.data.models.oldJIT;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.11.2015
 * Time: 9:30
 * To change this template use File | Settings | File Templates.
 */
public class TempProdNr {
    private String prodNr;
    private String ksKz;
    private String liefDatum;
    private String folge;
    private String cust;
    private List<String> modulList;

    public TempProdNr(String prodNr, String ksKz, String liefDatum, String folge, String cust) {
        this.prodNr = prodNr;
        this.ksKz = ksKz;
        this.folge = folge;
        this.cust = cust;
        this.liefDatum = liefDatum;
    }

    public String getLiefDatum() {
        return liefDatum;
    }

    public void setLiefDatum(String liefDatum) {
        this.liefDatum = liefDatum;
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

    public String getFolge() {
        return folge;
    }

    public void setFolge(String folge) {
        this.folge = folge;
    }

    public String getCust() {
        return cust;
    }

    public void setCust(String cust) {
        this.cust = cust;
    }

    public List<String> getModulList() {
        return modulList;
    }

    public void setModulList(List<String> modulList) {
        this.modulList = modulList;
    }
}
