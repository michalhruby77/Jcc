package com.leoni.data.models.nonDBmodels;

/**
 * Created by hrmi1005 on 16. 12. 2015.
 */
public class HarnessCheckObj {

    String prodNr;
    String ksKz;
    String date;
    String time;
    String program;
    String programDesc;
    String status;

    public HarnessCheckObj(String prodNr, String ksKz, String date, String time, String program, String programDesc, String status) {
        this.prodNr = prodNr;
        this.ksKz = ksKz;
        this.date = date;
        this.time = time;
        this.program = program;
        this.programDesc = programDesc;
        this.status = status;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgramDesc() {
        return programDesc;
    }

    public void setProgramDesc(String programDesc) {
        this.programDesc = programDesc;
    }
}
