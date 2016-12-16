package com.leoni.data.models.oldJIT;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 5.6.2014
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "Jitpab62")
@IdClass(Jitpab62Id.class)
public class Jitpab62 {
    @Id
    @Column (name = "prod_nr")
    private String prodNr;
    @Id
    @Column (name = "kabelsatz_kz")
    private String kabelsatzKz;
    @Column (name = "kunden_nr")
    private Integer kundenNr;
    @Column (name = "sta_uebernahme")
    private String ueber;
    @Column (name = "ausfuehrung")
    private String ausfuehrung;
    @Column (name = "liefer_datum")
    private Integer lieferDatum;
    @Column (name = "ablade_stelle")
    private String abladeStelle;



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

    public String getUeber() {
        return ueber;
    }

    public void setUeber(String ueber) {
        this.ueber = ueber;
    }

    public String getAusfuehrung() {
        return ausfuehrung;
    }

    public void setAusfuehrung(String ausfuehrung) {
        this.ausfuehrung = ausfuehrung;
    }

    public Integer getLieferDatum() {
        return lieferDatum;
    }

    public void setLieferDatum(Integer lieferDatum) {
        this.lieferDatum = lieferDatum;
    }

    public String getAbladeStelle() {
        return abladeStelle;
    }

    public void setAbladeStelle(String abladeStelle) {
        this.abladeStelle = abladeStelle;
    }

    public Integer getKundenNr() {
        return kundenNr;
    }

    public void setKundenNr(Integer kundenNr) {
        this.kundenNr = kundenNr;
    }
}
