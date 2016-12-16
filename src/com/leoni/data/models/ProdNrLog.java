package com.leoni.data.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 6.6.2014
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "prodnrlog", schema = "public", catalog = "")
@Entity
@IdClass(ProdNrLogId.class)
public class ProdNrLog implements Serializable{
    @Id
    @Column(name = "prod_nr")
    private String prodNr;
    @Id
    @Column (name = "kabelsatz_kz")
    private String kabelsatzKz;
    @Column(name = "logdate")
    private String logdate;
    @Id
    @Column(name = "logtime")
    private String logtimer;
    @Column(name = "table_id")
    private String tableId;
    @Column(name = "tester_id")
    private String testerId;
    @Column(name = "mode")
    private String mode;
    @Column(name = "logtext")
    private String logtext;

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

    public String getLogdate() {
        return logdate;
    }

    public void setLogdate(String logdate) {
        this.logdate = logdate;
    }

    public String getLogtimer() {
        return logtimer;
    }

    public void setLogtimer(String logtimer) {
        this.logtimer = logtimer;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTesterId() {
        return testerId;
    }

    public void setTesterId(String testerId) {
        this.testerId = testerId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getLogtext() {
        return logtext;
    }

    public void setLogtext(String logtext) {
        this.logtext = logtext;
    }
}
