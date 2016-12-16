package com.leoni.data.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 21.7.2014
 * Time: 10:44
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "dme_variante_name", schema = "public", catalog = "")
@Entity
public class DmeVarianteName implements Serializable{
    @Column(name = "variante_name")
    private String varianteName;
    @Column (name = "beschreibung")
    private String beschreibung;
    @Id
    @Column (name = "sum_wertigkeit")
    private Integer sumWertigkeit;
    @Lob
    @Column (name = "bild")
    private byte[] bild;

    public String getVarianteName() {
        return varianteName;
    }

    public void setVarianteName(String varianteName) {
        this.varianteName = varianteName;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Integer getSumWertigkeit() {
        return sumWertigkeit;
    }

    public void setSumWertigkeit(Integer sumWertigkeit) {
        this.sumWertigkeit = sumWertigkeit;
    }

    public byte[] getBild() {
        return bild;
    }

    public void setBild(byte[] bild) {
        this.bild = bild;
    }
}
