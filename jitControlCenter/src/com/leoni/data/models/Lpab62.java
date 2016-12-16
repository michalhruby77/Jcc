package com.leoni.data.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.4.2014
 * Time: 9:40
 * To change this template use File | Settings | File Templates.
 */
@Entity
@IdClass(Lpab62Id.class)
@Table(name = "lpab62")
public class Lpab62 implements Serializable{
        @Id
        @Column (name = "prod_nr")
        private String prodNr;
        @Id
        @Column (name = "kabelsatz_kz")
        private String kabelsatzKz;
        @Column (name = "kunden_nr")
        private String kundenNr;
        @Column (name = "prod_gruppe")
        private String prodgruppe;
        @Column (name = "ausfuehrung")
        private String ausfuehrung;
        @Column (name = "liefer_datum")
        private Integer lieferDatum;
        @Column (name = "sta_uebernahme")
        private String staUebernahme;
        @Column (name = "sta_montageliste")
        private String staMontageliste;
    @Column (name = "sta_pruef_elektr")
    private String staPruefElektr;
    @Column (name = "sta_pruef_relais")
    private String staPruefRelais;
    @Column (name = "sta_sicherung")
    private String staSicherung;
    @Column (name = "sta_clip")
    private String staClip;
    @Column (name = "sta_tuelle")
    private String staTuelle;
    @Column (name = "sta_wa")
    private String staWa;
    @Column (name = "sta_dfue_export")
    private String staDfueExport;
    @Column (name = "sta_archiv")
    private String staArchiv;
    @Column (name = "sta_schraub")
    private String staSchraub;
    @Column (name = "sta_schaumen")
    private String staSchaumen;
    @Column (name = "sta_tmplager")
    private String staTmplager;
    @Column (name = "sta_loetung")
    private String staLoetung;
    @Column (name = "sta_band_einlauf")
    private String staBandEinlauf;
    @Column (name = "sta_band_auslauf")
    private String staBandAuslauf;
    @Column (name = "sta_esd_schraub")
    private String staEsdSchraub;
    @Column (name = "sta_esd_schraub_begin")
    private String staEsdSchraubBegin;
    @Column (name = "sta_clip_zones")
    private String staClipZones;
    @Column (name = "prod_reihenf_nr")
    private String prodReihenfNr;
    @Column (name = "sta_tuelle_t1")
    private boolean staTuelleT1;
    @Column (name = "sta_tuelle_t2")
    private boolean staTuelleT2;
    @Column (name = "sta_tuelle_t3")
    private boolean staTuelleT3;
    @Column (name = "sta_tuelle_t4")
    private boolean staTuelleT4;
    @Column (name = "sta_tuelle_t5")
    private boolean staTuelleT5;
    @Column (name = "sta_antenna_check")
    private String staAntenna;
    @Column (name = "order_source")
    private String orderSource;

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

    public String getKundenNr() {
        return kundenNr;
    }

    public void setKundenNr(String kundenNr) {
        this.kundenNr = kundenNr;
    }

    public String getProdgruppe() {
        return prodgruppe;
    }

    public void setProdgruppe(String prodgruppe) {
        this.prodgruppe = prodgruppe;
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

    public String getStaUebernahme() {
        return staUebernahme;
    }

    public void setStaUebernahme(String staUebernahme) {
        this.staUebernahme = staUebernahme;
    }

    public String getStaMontageliste() {
        return staMontageliste;
    }

    public void setStaMontageliste(String staMontageliste) {
        this.staMontageliste = staMontageliste;
    }

    public String getStaPruefElektr() {
        return staPruefElektr;
    }

    public void setStaPruefElektr(String staPruefElektr) {
        this.staPruefElektr = staPruefElektr;
    }

    public String getStaPruefRelais() {
        return staPruefRelais;
    }

    public void setStaPruefRelais(String staPruefRelais) {
        this.staPruefRelais = staPruefRelais;
    }

    public String getStaSicherung() {
        return staSicherung;
    }

    public void setStaSicherung(String staSicherung) {
        this.staSicherung = staSicherung;
    }

    public String getStaClip() {
        return staClip;
    }

    public void setStaClip(String staClip) {
        this.staClip = staClip;
    }

    public String getStaTuelle() {
        return staTuelle;
    }

    public void setStaTuelle(String staTuelle) {
        this.staTuelle = staTuelle;
    }

    public String getStaWa() {
        return staWa;
    }

    public void setStaWa(String staWa) {
        this.staWa = staWa;
    }

    public String getStaDfueExport() {
        return staDfueExport;
    }

    public void setStaDfueExport(String staDfueExport) {
        this.staDfueExport = staDfueExport;
    }

    public String getStaArchiv() {
        return staArchiv;
    }

    public void setStaArchiv(String staArchiv) {
        this.staArchiv = staArchiv;
    }

    public String getStaSchraub() {
        return staSchraub;
    }

    public void setStaSchraub(String staSchraub) {
        this.staSchraub = staSchraub;
    }

    public String getStaSchaumen() {
        return staSchaumen;
    }

    public void setStaSchaumen(String staSchaumen) {
        this.staSchaumen = staSchaumen;
    }

    public String getStaTmplager() {
        return staTmplager;
    }

    public void setStaTmplager(String staTmplager) {
        this.staTmplager = staTmplager;
    }

    public String getStaLoetung() {
        return staLoetung;
    }

    public void setStaLoetung(String staLoetung) {
        this.staLoetung = staLoetung;
    }

    public String getStaBandEinlauf() {
        return staBandEinlauf;
    }

    public void setStaBandEinlauf(String staBandEinlauf) {
        this.staBandEinlauf = staBandEinlauf;
    }

    public String getStaBandAuslauf() {
        return staBandAuslauf;
    }

    public void setStaBandAuslauf(String staBandAuslauf) {
        this.staBandAuslauf = staBandAuslauf;
    }

    public String getStaEsdSchraub() {
        return staEsdSchraub;
    }

    public void setStaEsdSchraub(String staEsdSchraub) {
        this.staEsdSchraub = staEsdSchraub;
    }

    public String getStaClipZones() {
        return staClipZones;
    }

    public void setStaClipZones(String staClipZones) {
        this.staClipZones = staClipZones;
    }

    public String getProdReihenfNr() {
        return prodReihenfNr;
    }

    public void setProdReihenfNr(String prodReihenfNr) {
        this.prodReihenfNr = prodReihenfNr;
    }

    public boolean isStaTuelleT1() {
        return staTuelleT1;
    }

    public void setStaTuelleT1(boolean staTuelleT1) {
        this.staTuelleT1 = staTuelleT1;
    }

    public boolean isStaTuelleT2() {
        return staTuelleT2;
    }

    public void setStaTuelleT2(boolean staTuelleT2) {
        this.staTuelleT2 = staTuelleT2;
    }

    public boolean isStaTuelleT3() {
        return staTuelleT3;
    }

    public void setStaTuelleT3(boolean staTuelleT3) {
        this.staTuelleT3 = staTuelleT3;
    }

    public boolean isStaTuelleT4() {
        return staTuelleT4;
    }

    public void setStaTuelleT4(boolean staTuelleT4) {
        this.staTuelleT4 = staTuelleT4;
    }

    public boolean isStaTuelleT5() {
        return staTuelleT5;
    }

    public void setStaTuelleT5(boolean staTuelleT5) {
        this.staTuelleT5 = staTuelleT5;
    }

    public String getStaEsdSchraubBegin() {
        return staEsdSchraubBegin;
    }

    public void setStaEsdSchraubBegin(String staEsdSchraubBegin) {
        this.staEsdSchraubBegin = staEsdSchraubBegin;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getStaAntenna() {
        return staAntenna;
    }

    public void setStaAntenna(String staAntenna) {
        this.staAntenna = staAntenna;
    }
}
