package com.leoni.data.models.nonDBmodels;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 25.8.2015
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */
public class ReportTableRecord {
    String date;
    String staIn;
    String customerNr;
    String prodDate;
    List<String> prodNrList;
    Integer nrOfFL;
    Integer nrOf991LL;
    Integer nrOf991RL;
    Integer nrOf981LL;
    Integer nrOf981RL;
    Integer nrOfCUP;

    public ReportTableRecord(String date, String staIn, String customerNr, String prodDate) {
        this.date = date;
        this.staIn = staIn;
        this.customerNr = customerNr;
        this.prodDate = prodDate;
        //this.prodNrList = prodNrList;
    }

    public List<String> getProdNrList() {
        return prodNrList;
    }

    public void setProdNrList(List<String> prodNrList) {
        this.prodNrList = prodNrList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStaIn() {
        return staIn;
    }

    public void setStaIn(String staIn) {
        this.staIn = staIn;
    }

    public String getCustomerNr() {
        return customerNr;
    }

    public void setCustomerNr(String customerNr) {
        this.customerNr = customerNr;
    }

    public String getProdDate() {
        return prodDate;
    }

    public void setProdDate(String prodDate) {
        this.prodDate = prodDate;
    }

    public Integer getNrOfFL() {
        return nrOfFL;
    }

    public void setNrOfFL(Integer nrOfFL) {
        this.nrOfFL = nrOfFL;
    }

    public Integer getNrOf991LL() {
        return nrOf991LL;
    }

    public void setNrOf991LL(Integer nrOf991LL) {
        this.nrOf991LL = nrOf991LL;
    }

    public Integer getNrOf991RL() {
        return nrOf991RL;
    }

    public void setNrOf991RL(Integer nrOf991RL) {
        this.nrOf991RL = nrOf991RL;
    }

    public Integer getNrOf981LL() {
        return nrOf981LL;
    }

    public void setNrOf981LL(Integer nrOf981LL) {
        this.nrOf981LL = nrOf981LL;
    }

    public Integer getNrOf981RL() {
        return nrOf981RL;
    }

    public void setNrOf981RL(Integer nrOf981RL) {
        this.nrOf981RL = nrOf981RL;
    }

    public Integer getNrOfCUP() {
        return nrOfCUP;
    }

    public void setNrOfCUP(Integer nrOfCUP) {
        this.nrOfCUP = nrOfCUP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportTableRecord)) return false;

        ReportTableRecord that = (ReportTableRecord) o;

        if (!customerNr.equals(that.customerNr)) return false;
        if (!date.equals(that.date)) return false;
        if (!prodDate.equals(that.prodDate)) return false;
        if (!staIn.equals(that.staIn)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + staIn.hashCode();
        result = 31 * result + customerNr.hashCode();
        result = 31 * result + prodDate.hashCode();
        return result;
    }
}
