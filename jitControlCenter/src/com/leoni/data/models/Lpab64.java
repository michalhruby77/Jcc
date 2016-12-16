package com.leoni.data.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.6.2014
 * Time: 8:09
 * To change this template use File | Settings | File Templates.
 */
@Entity
@IdClass(Lpab64Id.class)
@Table(name = "lpab64")
public class Lpab64 implements Serializable{
    @Id
    @Column(name = "prod_nr")
    private String prodNr;
    @Id
    @Column (name = "kabelsatz_kz")
    private String kabelsatzKz;
    @Column (name = "grundmodul_kz")
    private String grundmodul_kz;
    @Column (name = "beipack_kz")
    private String beipackKz;
    @Column (name = "sach_nr_best")
    private String sachNrBest;
    @Id
    @Column (name = "sach_nr_lieferant")
    private String sachNrLieferant;

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

    public String getGrundmodul_kz() {
        return grundmodul_kz;
    }

    public void setGrundmodul_kz(String grundmodul_kz) {
        this.grundmodul_kz = grundmodul_kz;
    }

    public String getBeipackKz() {
        return beipackKz;
    }

    public void setBeipackKz(String beipackKz) {
        this.beipackKz = beipackKz;
    }

    public String getSachNrBest() {
        return sachNrBest;
    }

    public void setSachNrBest(String sachNrBest) {
        this.sachNrBest = sachNrBest;
    }

    public String getSachNrLieferant() {
        return sachNrLieferant;
    }

    public void setSachNrLieferant(String sachNrLieferant) {
        this.sachNrLieferant = sachNrLieferant;
    }
}
