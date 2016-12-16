package com.leoni.data.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 10.1.2014
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "schritt_modul_rm_9x1_wrm", schema = "public", catalog = "")
@Entity
public class SchrittModulRm9X1Wrm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "moduls_id")
    private Moduls moduls;
    @Column (name = "variante1")
    private String variante1;
    @Column (name = "variante2")
    private String variante2;
    @Column (name = "variante3")
    private String variante3;
    @Column (name = "variante4")
    private String variante4;
    @Column (name = "seite")
    private String seite;
    @Column (name = "schritt")
    private String schritt;
    @Column (name = "popis")
    private String popis;
    @Column (name = "id_farba")
    private String farba;
    @Column (name = "is_check")
    private String isCheck;
    @Column (name = "band_name")
    private String bandName;
    @Column (name = "changed_by")
    private String changedBy;
    @Column (name = "changed_date")
    private Date changedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Moduls getModuls() {
        return moduls;
    }

    public void setModuls(Moduls moduls) {
        this.moduls = moduls;
    }

    public String getVariante1() {
        return variante1;
    }

    public void setVariante1(String variante1) {
        this.variante1 = variante1;
    }

    public String getVariante2() {
        return variante2;
    }

    public void setVariante2(String variante2) {
        this.variante2 = variante2;
    }

    public String getVariante3() {
        return variante3;
    }

    public void setVariante3(String variante3) {
        this.variante3 = variante3;
    }

    public String getVariante4() {
        return variante4;
    }

    public void setVariante4(String variante4) {
        this.variante4 = variante4;
    }

    public String getSeite() {
        return seite;
    }

    public void setSeite(String seite) {
        this.seite = seite;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public String getSchritt() {
        return schritt;
    }

    public void setSchritt(String schritt) {
        this.schritt = schritt;
    }

   /* public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }*/

    public String getFarba() {
        return farba;
    }

    public void setFarba(String farba) {
        this.farba = farba;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String check) {
        isCheck = check;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
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
