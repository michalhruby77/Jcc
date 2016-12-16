package com.leoni.data.models.nonDBmodels;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 25.8.2015
 * Time: 10:19
 * To change this template use File | Settings | File Templates.
 */
public class ReportResultObj {

    String prodNr;
    String customer;
    String prodDate;
    String statusIn;

    public ReportResultObj(String prodNr, String customer, String prodDate, String statusIn) {
        this.prodNr = prodNr;
        this.customer = customer;
        this.prodDate = prodDate;
        this.statusIn = statusIn;
    }

    public String getProdNr() {
        return prodNr;
    }

    public void setProdNr(String prodNr) {
        this.prodNr = prodNr;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getProdDate() {
        return prodDate;
    }

    public void setProdDate(String prodDate) {
        this.prodDate = prodDate;
    }

    public String getStatusIn() {
        return statusIn;
    }

    public void setStatusIn(String statusIn) {
        this.statusIn = statusIn;
    }
}
