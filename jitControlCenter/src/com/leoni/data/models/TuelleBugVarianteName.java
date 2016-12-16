package com.leoni.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 21.7.2014
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "tuelle_bug_variante_name", schema = "public", catalog = "")
@Entity
public class TuelleBugVarianteName {
    @Column(name = "variante_name")
    private String varianteName;
    @Column (name = "beschreibung")
    private String beschreibung;
    @Id
    @Column (name = "sum_wertigkeit")
    private Integer sumWertigkeit;
    @Column (name = "variante_gruppe")
    private String varianteGruppe;
    @Column (name = "variante_type")
    private String varianteType;
    @Column (name = "variante_ausf")
    private String varianteAusf;

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

    public String getVarianteGruppe() {
        return varianteGruppe;
    }

    public void setVarianteGruppe(String varianteGruppe) {
        this.varianteGruppe = varianteGruppe;
    }

    public String getVarianteType() {
        return varianteType;
    }

    public void setVarianteType(String varianteType) {
        this.varianteType = varianteType;
    }

    public String getVarianteAusf() {
        return varianteAusf;
    }

    public void setVarianteAusf(String varianteAusf) {
        this.varianteAusf = varianteAusf;
    }
}
