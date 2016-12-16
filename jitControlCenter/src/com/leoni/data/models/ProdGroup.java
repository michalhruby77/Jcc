package com.leoni.data.models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 5.9.2014
 * Time: 10:21
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "derive_prod_group")
public class ProdGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Column (name = "sach_nr_best")
    private String sachNrBest;
    @Column (name = "prod_group")
    private String prodGroup;
    @Column (name = "kabelsatz_kz")
    private String kabelsatzKz;
    @Column (name = "ausfuehrung")
    private String ausfuehrung;
    @Column (name = "lade_einheit")
    private String ladeEinheit;
    @Column (name = "kunden_nr")
    private String kundenNr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSachNrBest() {
        return sachNrBest;
    }

    public void setSachNrBest(String sachNrBest) {
        this.sachNrBest = sachNrBest;
    }

    public String getProdGroup() {
        return prodGroup;
    }

    public void setProdGroup(String prodGroup) {
        this.prodGroup = prodGroup;
    }

    public String getKabelsatzKz() {
        return kabelsatzKz;
    }

    public void setKabelsatzKz(String kabelsatzKz) {
        this.kabelsatzKz = kabelsatzKz;
    }

    public String getAusfuehrung() {
        return ausfuehrung;
    }

    public void setAusfuehrung(String ausfuehrung) {
        this.ausfuehrung = ausfuehrung;
    }

    public String getLadeEinheit() {
        return ladeEinheit;
    }

    public void setLadeEinheit(String ladeEinheit) {
        this.ladeEinheit = ladeEinheit;
    }

    public String getKundenNr() {
        return kundenNr;
    }

    public void setKundenNr(String kundenNr) {
        this.kundenNr = kundenNr;
    }
}
