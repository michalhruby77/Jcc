package com.leoni.data.models;

import javax.persistence.*;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 10.1.2014
 * Time: 8:24
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "foam", schema = "public", catalog = "")
@Entity
public class Foam {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column (name = "oid")
//    private Integer oid;
    @Id
    @Column(name = "sach_nr_lieferant")
    private String sachNrLief;
    @Column (name = "sach_nr_best")
    private String sachNrBest;
    @Column (name = "t1")
    private Double t1;
    @Column (name = "t2")
    private Double t2;
    @Column (name = "t3")
    private Double t3;
    @Column (name = "t4")
    private Double t4;
    @Column (name = "t5")
    private Double t5;
    @Column (name = "kabelsatz_kz")
    private String kabelsatzKz;
    @Column (name = "changed_by")
    private String changedBy;
    @Column (name = "changed_date")
    private Date changedDate;


    public String getSachNrLief() {
        return sachNrLief;
    }

    public void setSachNrLief(String sachNrLief) {
        this.sachNrLief = sachNrLief;
    }

    public String getSachNrBest() {
        return sachNrBest;
    }

    public void setSachNrBest(String sachNrBest) {
        this.sachNrBest = sachNrBest;
    }

    public Double getT1() {
        return t1;
    }

    public void setT1(Double t1) {
        this.t1 = t1;
    }

    public Double getT2() {
        return t2;
    }

    public void setT2(Double t2) {
        this.t2 = t2;
    }

    public Double getT3() {
        return t3;
    }

    public void setT3(Double t3) {
        this.t3 = t3;
    }

    public Double getT4() {
        return t4;
    }

    public void setT4(Double t4) {
        this.t4 = t4;
    }

    public Double getT5() {
        return t5;
    }

    public void setT5(Double t5) {
        this.t5 = t5;
    }

    public String getKabelsatzKz() {
        return kabelsatzKz;
    }

    public void setKabelsatzKz(String kabelsatzKz) {
        this.kabelsatzKz = kabelsatzKz;
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
