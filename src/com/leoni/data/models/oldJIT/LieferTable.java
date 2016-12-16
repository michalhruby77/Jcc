package com.leoni.data.models.oldJIT;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.10.2014
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */
@Entity
@IdClass(LieferTableId.class)
@Table(name = "LIEFER_TABELLE")
public class LieferTable {
    @Id
    @Column (name = "uebt_nr")
    private String uebtNr;
    @Column (name = "uebt_datum")
    private String uebtDatum;
    @Column (name = "dfue_satzart")
    private String dfueSatzart;
    @Id
    @Column (name = "fortlaufende_nr")
    private String fortlaufendeNr;
    @Column (name = "transport_nr")
    private String transportNr;
    @Column (name = "lieferschein_nr")
    private String lieferscheinNr;
    @Column (name = "sta_aktuell")
    private String staAktuell;



    public String getUebtNr() {
        return uebtNr;
    }

    public void setUebtNr(String uebtNr) {
        this.uebtNr = uebtNr;
    }

    public String getUebtDatum() {
        return uebtDatum;
    }

    public void setUebtDatum(String uebtDatum) {
        this.uebtDatum = uebtDatum;
    }

    public String getDfueSatzart() {
        return dfueSatzart;
    }

    public void setDfueSatzart(String dfueSatzart) {
        this.dfueSatzart = dfueSatzart;
    }

    public String getFortlaufendeNr() {
        return fortlaufendeNr;
    }

    public void setFortlaufendeNr(String fortlaufendeNr) {
        this.fortlaufendeNr = fortlaufendeNr;
    }

    public String getTransportNr() {
        return transportNr;
    }

    public void setTransportNr(String transportNr) {
        this.transportNr = transportNr;
    }

    public String getLieferscheinNr() {
        return lieferscheinNr;
    }

    public void setLieferscheinNr(String lieferscheinNr) {
        this.lieferscheinNr = lieferscheinNr;
    }

    public String getStaAktuell() {
        return staAktuell;
    }

    public void setStaAktuell(String staAktuell) {
        this.staAktuell = staAktuell;
    }
}
