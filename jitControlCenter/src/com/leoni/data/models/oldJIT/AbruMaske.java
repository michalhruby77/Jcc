package com.leoni.data.models.oldJIT;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 5.6.2014
 * Time: 8:58
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ABRU_MASKE")
public class AbruMaske {

    @Column (name = "kz")
    private String kz;
    @Column (name = "kd_nr")
    private String kdNr;
    @Id
    @Column (name = "uebt_nr_neu")
    private String uebtNrNeu;
    @Column (name = "status")
    private Integer status;


    public String getKz() {
        return kz;
    }

    public void setKz(String kz) {
        this.kz = kz;
    }

    public String getKdNr() {
        return kdNr;
    }

    public void setKdNr(String kdNr) {
        this.kdNr = kdNr;
    }

    public String getUebtNrNeu() {
        return uebtNrNeu;
    }

    public void setUebtNrNeu(String uebtNrNeu) {
        this.uebtNrNeu = uebtNrNeu;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
