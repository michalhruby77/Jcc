package com.leoni.data.models.nonDBmodels;

import com.leoni.data.models.Lpab64;

import java.util.List;

/**
 * Created by hrmi1005 on 7. 1. 2016.
 */
public class PrnrLpab64Obj {
    private String prnr;
    private String ksKz;
    private String container;
    private String prodDate;
    private List<Lpab64> modulList;

    public PrnrLpab64Obj(String prnr, String ksKz, String container, String prodDate) {
        this.prnr = prnr;
        this.ksKz = ksKz;
        this.container = container;
        this.prodDate = prodDate;
    }

    public String getPrnr() {
        return prnr;
    }

    public void setPrnr(String prnr) {
        this.prnr = prnr;
    }

    public String getKsKz() {
        return ksKz;
    }

    public void setKsKz(String ksKz) {
        this.ksKz = ksKz;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getProdDate() {
        return prodDate;
    }

    public void setProdDate(String prodDate) {
        this.prodDate = prodDate;
    }

    public List<Lpab64> getModulList() {
        return modulList;
    }

    public void setModulList(List<Lpab64> modulList) {
        this.modulList = modulList;
    }
}
