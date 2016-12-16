package com.leoni.data.models.oldJIT;

import com.leoni.data.models.Lpab64Id;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 25.11.2014
 * Time: 15:19
 * To change this template use File | Settings | File Templates.
 */
@Entity
@IdClass(Lpab64Id.class)
@Table(name = "Jitpab64")
public class Jitpab64 {

    @Id
    @Column (name = "prod_nr")
    private String prodNr;
    @Id
    @Column (name = "kabelsatz_kz")
    private String kabelsatzKz;
    @Id
    @Column (name = "sach_nr_best")
    private String sachNrBest;
    @Column (name = "grundmodul_kz")
    private String grundmodulKz;
    @Column (name = "beipack_kz")
    private String beipackKz;
    @Column (name = "abruf_menge")
    private Integer abrufMenge;

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

    public String getSachNrBest() {
        return sachNrBest;
    }

    public void setSachNrBest(String sachNrBest) {
        this.sachNrBest = sachNrBest;
    }

    public String getGrundmodulKz() {
        return grundmodulKz;
    }

    public void setGrundmodulKz(String grundmodulKz) {
        this.grundmodulKz = grundmodulKz;
    }

    public String getBeipackKz() {
        return beipackKz;
    }

    public void setBeipackKz(String beipackKz) {
        this.beipackKz = beipackKz;
    }

    public Integer getAbrufMenge() {
        return abrufMenge;
    }

    public void setAbrufMenge(Integer abrufMenge) {
        this.abrufMenge = abrufMenge;
    }
}
