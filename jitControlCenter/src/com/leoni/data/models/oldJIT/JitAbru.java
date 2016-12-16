package com.leoni.data.models.oldJIT;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.6.2014
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
@Entity
@IdClass(JitAbruId.class)
@Table(name = "Jitabru")
public class JitAbru {
    @Id
    @Column (name = "dfue_nr_ein")
    private String dfueNrEin;
    @Column (name = "dfue_datum_ein")
    private String dfueDatumEin;
    @Id
    @Column (name = "kd_nr_dfue")
    private String kdNrDfue;
    @Id
    @Column (name = "prod_nr")
    private String prodNr;
    @Id
    @Column (name = "pab_daten_4916")
    private String pabDaten;
    @Column (name = "pab_satzart")
    private String pabSatzart;
    @Column (name = "prod_merkmal")
    private String prodMerkmal;


    public String getDfueNrEin() {
        return dfueNrEin;
    }

    public void setDfueNrEin(String dfueNrEin) {
        this.dfueNrEin = dfueNrEin;
    }

    public String getDfueDatumEin() {
        return dfueDatumEin;
    }

    public void setDfueDatumEin(String dfueDatumEin) {
        this.dfueDatumEin = dfueDatumEin;
    }

    public String getKdNrDfue() {
        return kdNrDfue;
    }

    public void setKdNrDfue(String kdNrDfue) {
        this.kdNrDfue = kdNrDfue;
    }

    public String getProdNr() {
        return prodNr;
    }

    public void setProdNr(String prodNr) {
        this.prodNr = prodNr;
    }

    public String getPabDaten() {
        return pabDaten;
    }

    public void setPabDaten(String pabDaten) {
        this.pabDaten = pabDaten;
    }

    public String getPabSatzart() {
        return pabSatzart;
    }

    public void setPabSatzart(String pabSatzart) {
        this.pabSatzart = pabSatzart;
    }

    public String getProdMerkmal() {
        return prodMerkmal;
    }

    public void setProdMerkmal(String prodMerkmal) {
        this.prodMerkmal = prodMerkmal;
    }
}
