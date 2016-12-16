package com.leoni.data.models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 16.1.2015
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "nacharbeit", schema = "public", catalog = "")
@Entity
@IdClass(NacharbeitId.class)
public class Nacharbeit {
    @javax.persistence.Column(name = "mode")
    private String mode;
    @Id
    @javax.persistence.Column(name = "prod_nr")
    private String prodNr;
    @Id
    @javax.persistence.Column(name = "kabelsatz_kz")
    private String kabelsatzKz;
    @Id
    @javax.persistence.Column(name = "prod_gruppe")
    private String prodGruppe;
    @Id
    @javax.persistence.Column(name = "ausfuhrung")
    private String ausfuhrung;
    @javax.persistence.Column(name = "logdate")
    @Basic
    private String logdate;
    @Id
    @javax.persistence.Column(name = "logtime")
    @Basic
    private String logtime;
    @javax.persistence.Column(name = "sta_del")
    @Basic
    private String staDel;
    @javax.persistence.Column(name = "table_id")
    @Basic
    private String tableId;
    @javax.persistence.Column(name = "tester_id")
    @Basic
    private String testerId;
    @javax.persistence.Column(name = "vs_vodic_1")
    @Basic
    private String vsVodic1;
    @javax.persistence.Column(name = "vs_vodic_2")
    @Basic
    private String vsVodic2;
    @javax.persistence.Column(name = "fehl_kod_1")
    @Basic
    private String fehlKod1;
    @javax.persistence.Column(name = "fehl_kod_2")
    @Basic
    private String fehlKod2;
    @javax.persistence.Column(name = "fehl_kod_3")
    @Basic
    private String fehlKod3;
    @javax.persistence.Column(name = "fehl_kod_4")
    @Basic
    private String fehlKod4;
    @javax.persistence.Column(name = "fehl_kod_5")
    @Basic
    private String fehlKod5;
    @javax.persistence.Column(name = "fehl_kod_6")
    @Basic
    private String fehlKod6;
    @javax.persistence.Column(name = "fehl_kod_7")
    @Basic
    private String fehlKod7;
    @javax.persistence.Column(name = "fehl_kod_8")
    @Basic
    private String fehlKod8;
    @javax.persistence.Column(name = "fehl_kod_9")
    @Basic
    private String fehlKod9;
    @javax.persistence.Column(name = "fehl_kod_10")
    @Basic
    private String fehlKod10;
    @javax.persistence.Column(name = "fehler_text_1")
    @Basic
    private String fehlerText1;
    @javax.persistence.Column(name = "fehler_text_2")
    @Basic
    private String fehlerText2;
    @javax.persistence.Column(name = "fehler_text_3")
    @Basic
    private String fehlerText3;
    @javax.persistence.Column(name = "fehler_text_4")
    @Basic
    private String fehlerText4;
    @javax.persistence.Column(name = "fehler_text_5")
    @Basic
    private String fehlerText5;
    @javax.persistence.Column(name = "fehler_text_6")
    @Basic
    private String fehlerText6;
    @javax.persistence.Column(name = "fehler_text_7")
    @Basic
    private String fehlerText7;
    @javax.persistence.Column(name = "fehler_text_8")
    @Basic
    private String fehlerText8;
    @javax.persistence.Column(name = "fehler_text_9")
    @Basic
    private String fehlerText9;
    @javax.persistence.Column(name = "fehler_text_10")
    @Basic
    private String fehlerText10;
    @javax.persistence.Column(name = "fehl_time_1")
    @Basic
    private String fehlTime1;
    @javax.persistence.Column(name = "fehl_time_2")
    @Basic
    private String fehlTime2;
    @javax.persistence.Column(name = "fehl_time_3")
    @Basic
    private String fehlTime3;
    @javax.persistence.Column(name = "fehl_time_4")
    @Basic
    private String fehlTime4;
    @javax.persistence.Column(name = "fehl_time_5")
    @Basic
    private String fehlTime5;
    @javax.persistence.Column(name = "fehl_time_6")
    @Basic
    private String fehlTime6;
    @javax.persistence.Column(name = "fehl_time_7")
    @Basic
    private String fehlTime7;
    @javax.persistence.Column(name = "fehl_time_8")
    @Basic
    private String fehlTime8;
    @javax.persistence.Column(name = "fehl_time_9")
    @Basic
    private String fehlTime9;
    @javax.persistence.Column(name = "fehl_time_10")
    @Basic
    private String fehlTime10;
    /*
    private String fehlerTime;
    private String fm1;
    private String fm2;
    private String fm3;
    private String fm4;
    private String fm5;
    private String fm6;
    private String fm7;
    private String fm8;
    private String fm9;
    private String fm10;
    private String prodNrDesc;
    private String staSchaumen;
    private String staSchraub;
    private String zmena;
    private String vsVodic1;
    private String vsVodic2;
    private String vsVodic3;
    private String vsVodic4;
    private String vsVodic5;
    private String vsVodic6;
    private String vsVodic7;
    private String vsVodic8;
    private String vsVodic9;
    private String vsVodic10;  */


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getProdNr() {
        return prodNr;
    }

    public void setProdNr(String prodNr) {
        this.prodNr = prodNr;
    }

    //@Basic
    public String getKabelsatzKz() {
        return kabelsatzKz;
    }

    public void setKabelsatzKz(String kabelsatzKz) {
        this.kabelsatzKz = kabelsatzKz;
    }


    public String getProdGruppe() {
        return prodGruppe;
    }

    public void setProdGruppe(String prodGruppe) {
        this.prodGruppe = prodGruppe;
    }


    public String getAusfuhrung() {
        return ausfuhrung;
    }

    public void setAusfuhrung(String ausfuhrung) {
        this.ausfuhrung = ausfuhrung;
    }

    public String getLogdate() {
        return logdate;
    }

    public void setLogdate(String logdate) {
        this.logdate = logdate;
    }

    public String getLogtime() {
        return logtime;
    }

    public void setLogtime(String logtime) {
        this.logtime = logtime;
    }


    public String getStaDel() {
        return staDel;
    }

    public void setStaDel(String staDel) {
        this.staDel = staDel;
    }


    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTesterId() {
        return testerId;
    }

    public void setTesterId(String testerId) {
        this.testerId = testerId;
    }

    /*
    @javax.persistence.Column(name = "fehler_time")
    @Basic
    public String getFehlerTime() {
        return fehlerTime;
    }

    public void setFehlerTime(String fehlerTime) {
        this.fehlerTime = fehlerTime;
    }

    @javax.persistence.Column(name = "fm_1")
    @Basic
    public String getFm1() {
        return fm1;
    }

    public void setFm1(String fm1) {
        this.fm1 = fm1;
    }

    @javax.persistence.Column(name = "fm_2")
    @Basic
    public String getFm2() {
        return fm2;
    }

    public void setFm2(String fm2) {
        this.fm2 = fm2;
    }

    @javax.persistence.Column(name = "fm_3")
    @Basic
    public String getFm3() {
        return fm3;
    }

    public void setFm3(String fm3) {
        this.fm3 = fm3;
    }

    @javax.persistence.Column(name = "fm_4")
    @Basic
    public String getFm4() {
        return fm4;
    }

    public void setFm4(String fm4) {
        this.fm4 = fm4;
    }

    @javax.persistence.Column(name = "fm_5")
    @Basic
    public String getFm5() {
        return fm5;
    }

    public void setFm5(String fm5) {
        this.fm5 = fm5;
    }

    @javax.persistence.Column(name = "fm_6")
    @Basic
    public String getFm6() {
        return fm6;
    }

    public void setFm6(String fm6) {
        this.fm6 = fm6;
    }

    @javax.persistence.Column(name = "fm_7")
    @Basic
    public String getFm7() {
        return fm7;
    }

    public void setFm7(String fm7) {
        this.fm7 = fm7;
    }

    @javax.persistence.Column(name = "fm_8")
    @Basic
    public String getFm8() {
        return fm8;
    }

    public void setFm8(String fm8) {
        this.fm8 = fm8;
    }

    @javax.persistence.Column(name = "fm_9")
    @Basic
    public String getFm9() {
        return fm9;
    }

    public void setFm9(String fm9) {
        this.fm9 = fm9;
    }

    @javax.persistence.Column(name = "fm_10")
    @Basic
    public String getFm10() {
        return fm10;
    }

    public void setFm10(String fm10) {
        this.fm10 = fm10;
    }

    @javax.persistence.Column(name = "prod_nr_desc")
    @Basic
    public String getProdNrDesc() {
        return prodNrDesc;
    }

    public void setProdNrDesc(String prodNrDesc) {
        this.prodNrDesc = prodNrDesc;
    }

    @javax.persistence.Column(name = "sta_schaumen")
    @Basic
    public String getStaSchaumen() {
        return staSchaumen;
    }

    public void setStaSchaumen(String staSchaumen) {
        this.staSchaumen = staSchaumen;
    }

    @javax.persistence.Column(name = "sta_schraub")
    @Basic
    public String getStaSchraub() {
        return staSchraub;
    }

    public void setStaSchraub(String staSchraub) {
        this.staSchraub = staSchraub;
    }

    @javax.persistence.Column(name = "zmena")
    @Basic
    public String getZmena() {
        return zmena;
    }

    public void setZmena(String zmena) {
        this.zmena = zmena;
    }

    @javax.persistence.Column(name = "vs_vodic_1")
    @Basic
    public String getVsVodic1() {
        return vsVodic1;
    }

    public void setVsVodic1(String vsVodic1) {
        this.vsVodic1 = vsVodic1;
    }

    @javax.persistence.Column(name = "vs_vodic_2")
    @Basic
    public String getVsVodic2() {
        return vsVodic2;
    }

    public void setVsVodic2(String vsVodic2) {
        this.vsVodic2 = vsVodic2;
    }

    @javax.persistence.Column(name = "vs_vodic_3")
    @Basic
    public String getVsVodic3() {
        return vsVodic3;
    }

    public void setVsVodic3(String vsVodic3) {
        this.vsVodic3 = vsVodic3;
    }

    @javax.persistence.Column(name = "vs_vodic_4")
    @Basic
    public String getVsVodic4() {
        return vsVodic4;
    }

    public void setVsVodic4(String vsVodic4) {
        this.vsVodic4 = vsVodic4;
    }

    @javax.persistence.Column(name = "vs_vodic_5")
    @Basic
    public String getVsVodic5() {
        return vsVodic5;
    }

    public void setVsVodic5(String vsVodic5) {
        this.vsVodic5 = vsVodic5;
    }

    @javax.persistence.Column(name = "vs_vodic_6")
    @Basic
    public String getVsVodic6() {
        return vsVodic6;
    }

    public void setVsVodic6(String vsVodic6) {
        this.vsVodic6 = vsVodic6;
    }

    @javax.persistence.Column(name = "vs_vodic_7")
    @Basic
    public String getVsVodic7() {
        return vsVodic7;
    }

    public void setVsVodic7(String vsVodic7) {
        this.vsVodic7 = vsVodic7;
    }

    @javax.persistence.Column(name = "vs_vodic_8")
    @Basic
    public String getVsVodic8() {
        return vsVodic8;
    }

    public void setVsVodic8(String vsVodic8) {
        this.vsVodic8 = vsVodic8;
    }

    @javax.persistence.Column(name = "vs_vodic_9")
    @Basic
    public String getVsVodic9() {
        return vsVodic9;
    }

    public void setVsVodic9(String vsVodic9) {
        this.vsVodic9 = vsVodic9;
    }

    @javax.persistence.Column(name = "vs_vodic_10")
    @Basic
    public String getVsVodic10() {
        return vsVodic10;
    }

    public void setVsVodic10(String vsVodic10) {
        this.vsVodic10 = vsVodic10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nacharbeit that = (Nacharbeit) o;

        if (ausfuhrung != null ? !ausfuhrung.equals(that.ausfuhrung) : that.ausfuhrung != null) return false;
        if (fehlKod1 != null ? !fehlKod1.equals(that.fehlKod1) : that.fehlKod1 != null) return false;
        if (fehlKod10 != null ? !fehlKod10.equals(that.fehlKod10) : that.fehlKod10 != null) return false;
        if (fehlKod2 != null ? !fehlKod2.equals(that.fehlKod2) : that.fehlKod2 != null) return false;
        if (fehlKod3 != null ? !fehlKod3.equals(that.fehlKod3) : that.fehlKod3 != null) return false;
        if (fehlKod4 != null ? !fehlKod4.equals(that.fehlKod4) : that.fehlKod4 != null) return false;
        if (fehlKod5 != null ? !fehlKod5.equals(that.fehlKod5) : that.fehlKod5 != null) return false;
        if (fehlKod6 != null ? !fehlKod6.equals(that.fehlKod6) : that.fehlKod6 != null) return false;
        if (fehlKod7 != null ? !fehlKod7.equals(that.fehlKod7) : that.fehlKod7 != null) return false;
        if (fehlKod8 != null ? !fehlKod8.equals(that.fehlKod8) : that.fehlKod8 != null) return false;
        if (fehlKod9 != null ? !fehlKod9.equals(that.fehlKod9) : that.fehlKod9 != null) return false;
        if (fehlTime1 != null ? !fehlTime1.equals(that.fehlTime1) : that.fehlTime1 != null) return false;
        if (fehlTime10 != null ? !fehlTime10.equals(that.fehlTime10) : that.fehlTime10 != null) return false;
        if (fehlTime2 != null ? !fehlTime2.equals(that.fehlTime2) : that.fehlTime2 != null) return false;
        if (fehlTime3 != null ? !fehlTime3.equals(that.fehlTime3) : that.fehlTime3 != null) return false;
        if (fehlTime4 != null ? !fehlTime4.equals(that.fehlTime4) : that.fehlTime4 != null) return false;
        if (fehlTime5 != null ? !fehlTime5.equals(that.fehlTime5) : that.fehlTime5 != null) return false;
        if (fehlTime6 != null ? !fehlTime6.equals(that.fehlTime6) : that.fehlTime6 != null) return false;
        if (fehlTime7 != null ? !fehlTime7.equals(that.fehlTime7) : that.fehlTime7 != null) return false;
        if (fehlTime8 != null ? !fehlTime8.equals(that.fehlTime8) : that.fehlTime8 != null) return false;
        if (fehlTime9 != null ? !fehlTime9.equals(that.fehlTime9) : that.fehlTime9 != null) return false;
        if (fehlerText1 != null ? !fehlerText1.equals(that.fehlerText1) : that.fehlerText1 != null) return false;
        if (fehlerText10 != null ? !fehlerText10.equals(that.fehlerText10) : that.fehlerText10 != null) return false;
        if (fehlerText2 != null ? !fehlerText2.equals(that.fehlerText2) : that.fehlerText2 != null) return false;
        if (fehlerText3 != null ? !fehlerText3.equals(that.fehlerText3) : that.fehlerText3 != null) return false;
        if (fehlerText4 != null ? !fehlerText4.equals(that.fehlerText4) : that.fehlerText4 != null) return false;
        if (fehlerText5 != null ? !fehlerText5.equals(that.fehlerText5) : that.fehlerText5 != null) return false;
        if (fehlerText6 != null ? !fehlerText6.equals(that.fehlerText6) : that.fehlerText6 != null) return false;
        if (fehlerText7 != null ? !fehlerText7.equals(that.fehlerText7) : that.fehlerText7 != null) return false;
        if (fehlerText8 != null ? !fehlerText8.equals(that.fehlerText8) : that.fehlerText8 != null) return false;
        if (fehlerText9 != null ? !fehlerText9.equals(that.fehlerText9) : that.fehlerText9 != null) return false;
        if (fehlerTime != null ? !fehlerTime.equals(that.fehlerTime) : that.fehlerTime != null) return false;
        if (fm1 != null ? !fm1.equals(that.fm1) : that.fm1 != null) return false;
        if (fm10 != null ? !fm10.equals(that.fm10) : that.fm10 != null) return false;
        if (fm2 != null ? !fm2.equals(that.fm2) : that.fm2 != null) return false;
        if (fm3 != null ? !fm3.equals(that.fm3) : that.fm3 != null) return false;
        if (fm4 != null ? !fm4.equals(that.fm4) : that.fm4 != null) return false;
        if (fm5 != null ? !fm5.equals(that.fm5) : that.fm5 != null) return false;
        if (fm6 != null ? !fm6.equals(that.fm6) : that.fm6 != null) return false;
        if (fm7 != null ? !fm7.equals(that.fm7) : that.fm7 != null) return false;
        if (fm8 != null ? !fm8.equals(that.fm8) : that.fm8 != null) return false;
        if (fm9 != null ? !fm9.equals(that.fm9) : that.fm9 != null) return false;
        if (kabelsatzKz != null ? !kabelsatzKz.equals(that.kabelsatzKz) : that.kabelsatzKz != null) return false;
        if (logdate != null ? !logdate.equals(that.logdate) : that.logdate != null) return false;
        if (logtime != null ? !logtime.equals(that.logtime) : that.logtime != null) return false;
        if (mode != null ? !mode.equals(that.mode) : that.mode != null) return false;
        if (prodGruppe != null ? !prodGruppe.equals(that.prodGruppe) : that.prodGruppe != null) return false;
        if (prodNr != null ? !prodNr.equals(that.prodNr) : that.prodNr != null) return false;
        if (prodNrDesc != null ? !prodNrDesc.equals(that.prodNrDesc) : that.prodNrDesc != null) return false;
        if (staDel != null ? !staDel.equals(that.staDel) : that.staDel != null) return false;
        if (staSchaumen != null ? !staSchaumen.equals(that.staSchaumen) : that.staSchaumen != null) return false;
        if (staSchraub != null ? !staSchraub.equals(that.staSchraub) : that.staSchraub != null) return false;
        if (tableId != null ? !tableId.equals(that.tableId) : that.tableId != null) return false;
        if (testerId != null ? !testerId.equals(that.testerId) : that.testerId != null) return false;
        if (vsVodic1 != null ? !vsVodic1.equals(that.vsVodic1) : that.vsVodic1 != null) return false;
        if (vsVodic10 != null ? !vsVodic10.equals(that.vsVodic10) : that.vsVodic10 != null) return false;
        if (vsVodic2 != null ? !vsVodic2.equals(that.vsVodic2) : that.vsVodic2 != null) return false;
        if (vsVodic3 != null ? !vsVodic3.equals(that.vsVodic3) : that.vsVodic3 != null) return false;
        if (vsVodic4 != null ? !vsVodic4.equals(that.vsVodic4) : that.vsVodic4 != null) return false;
        if (vsVodic5 != null ? !vsVodic5.equals(that.vsVodic5) : that.vsVodic5 != null) return false;
        if (vsVodic6 != null ? !vsVodic6.equals(that.vsVodic6) : that.vsVodic6 != null) return false;
        if (vsVodic7 != null ? !vsVodic7.equals(that.vsVodic7) : that.vsVodic7 != null) return false;
        if (vsVodic8 != null ? !vsVodic8.equals(that.vsVodic8) : that.vsVodic8 != null) return false;
        if (vsVodic9 != null ? !vsVodic9.equals(that.vsVodic9) : that.vsVodic9 != null) return false;
        if (zmena != null ? !zmena.equals(that.zmena) : that.zmena != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mode != null ? mode.hashCode() : 0;
        result = 31 * result + (prodNr != null ? prodNr.hashCode() : 0);
        result = 31 * result + (kabelsatzKz != null ? kabelsatzKz.hashCode() : 0);
        result = 31 * result + (prodGruppe != null ? prodGruppe.hashCode() : 0);
        result = 31 * result + (ausfuhrung != null ? ausfuhrung.hashCode() : 0);
        result = 31 * result + (logdate != null ? logdate.hashCode() : 0);
        result = 31 * result + (logtime != null ? logtime.hashCode() : 0);
        result = 31 * result + (staDel != null ? staDel.hashCode() : 0);
        result = 31 * result + (tableId != null ? tableId.hashCode() : 0);
        result = 31 * result + (testerId != null ? testerId.hashCode() : 0);
        result = 31 * result + (fehlKod1 != null ? fehlKod1.hashCode() : 0);
        result = 31 * result + (fehlKod2 != null ? fehlKod2.hashCode() : 0);
        result = 31 * result + (fehlKod3 != null ? fehlKod3.hashCode() : 0);
        result = 31 * result + (fehlKod4 != null ? fehlKod4.hashCode() : 0);
        result = 31 * result + (fehlKod5 != null ? fehlKod5.hashCode() : 0);
        result = 31 * result + (fehlKod6 != null ? fehlKod6.hashCode() : 0);
        result = 31 * result + (fehlKod7 != null ? fehlKod7.hashCode() : 0);
        result = 31 * result + (fehlKod8 != null ? fehlKod8.hashCode() : 0);
        result = 31 * result + (fehlKod9 != null ? fehlKod9.hashCode() : 0);
        result = 31 * result + (fehlKod10 != null ? fehlKod10.hashCode() : 0);
        result = 31 * result + (fehlerText1 != null ? fehlerText1.hashCode() : 0);
        result = 31 * result + (fehlerText2 != null ? fehlerText2.hashCode() : 0);
        result = 31 * result + (fehlerText3 != null ? fehlerText3.hashCode() : 0);
        result = 31 * result + (fehlerText4 != null ? fehlerText4.hashCode() : 0);
        result = 31 * result + (fehlerText5 != null ? fehlerText5.hashCode() : 0);
        result = 31 * result + (fehlerText6 != null ? fehlerText6.hashCode() : 0);
        result = 31 * result + (fehlerText7 != null ? fehlerText7.hashCode() : 0);
        result = 31 * result + (fehlerText8 != null ? fehlerText8.hashCode() : 0);
        result = 31 * result + (fehlerText9 != null ? fehlerText9.hashCode() : 0);
        result = 31 * result + (fehlerText10 != null ? fehlerText10.hashCode() : 0);
        result = 31 * result + (fehlTime1 != null ? fehlTime1.hashCode() : 0);
        result = 31 * result + (fehlTime2 != null ? fehlTime2.hashCode() : 0);
        result = 31 * result + (fehlTime3 != null ? fehlTime3.hashCode() : 0);
        result = 31 * result + (fehlTime4 != null ? fehlTime4.hashCode() : 0);
        result = 31 * result + (fehlTime5 != null ? fehlTime5.hashCode() : 0);
        result = 31 * result + (fehlTime6 != null ? fehlTime6.hashCode() : 0);
        result = 31 * result + (fehlTime7 != null ? fehlTime7.hashCode() : 0);
        result = 31 * result + (fehlTime8 != null ? fehlTime8.hashCode() : 0);
        result = 31 * result + (fehlTime9 != null ? fehlTime9.hashCode() : 0);
        result = 31 * result + (fehlTime10 != null ? fehlTime10.hashCode() : 0);
        result = 31 * result + (fehlerTime != null ? fehlerTime.hashCode() : 0);
        result = 31 * result + (fm1 != null ? fm1.hashCode() : 0);
        result = 31 * result + (fm2 != null ? fm2.hashCode() : 0);
        result = 31 * result + (fm3 != null ? fm3.hashCode() : 0);
        result = 31 * result + (fm4 != null ? fm4.hashCode() : 0);
        result = 31 * result + (fm5 != null ? fm5.hashCode() : 0);
        result = 31 * result + (fm6 != null ? fm6.hashCode() : 0);
        result = 31 * result + (fm7 != null ? fm7.hashCode() : 0);
        result = 31 * result + (fm8 != null ? fm8.hashCode() : 0);
        result = 31 * result + (fm9 != null ? fm9.hashCode() : 0);
        result = 31 * result + (fm10 != null ? fm10.hashCode() : 0);
        result = 31 * result + (prodNrDesc != null ? prodNrDesc.hashCode() : 0);
        result = 31 * result + (staSchaumen != null ? staSchaumen.hashCode() : 0);
        result = 31 * result + (staSchraub != null ? staSchraub.hashCode() : 0);
        result = 31 * result + (zmena != null ? zmena.hashCode() : 0);
        result = 31 * result + (vsVodic1 != null ? vsVodic1.hashCode() : 0);
        result = 31 * result + (vsVodic2 != null ? vsVodic2.hashCode() : 0);
        result = 31 * result + (vsVodic3 != null ? vsVodic3.hashCode() : 0);
        result = 31 * result + (vsVodic4 != null ? vsVodic4.hashCode() : 0);
        result = 31 * result + (vsVodic5 != null ? vsVodic5.hashCode() : 0);
        result = 31 * result + (vsVodic6 != null ? vsVodic6.hashCode() : 0);
        result = 31 * result + (vsVodic7 != null ? vsVodic7.hashCode() : 0);
        result = 31 * result + (vsVodic8 != null ? vsVodic8.hashCode() : 0);
        result = 31 * result + (vsVodic9 != null ? vsVodic9.hashCode() : 0);
        result = 31 * result + (vsVodic10 != null ? vsVodic10.hashCode() : 0);
        return result;
    }         */

    public String getFehlKod1() {
        return fehlKod1;
    }

    public void setFehlKod1(String fehlKod1) {
        this.fehlKod1 = fehlKod1;
    }

    public String getFehlKod2() {
        return fehlKod2;
    }

    public void setFehlKod2(String fehlKod2) {
        this.fehlKod2 = fehlKod2;
    }

    public String getFehlKod3() {
        return fehlKod3;
    }

    public void setFehlKod3(String fehlKod3) {
        this.fehlKod3 = fehlKod3;
    }

    public String getFehlKod4() {
        return fehlKod4;
    }

    public void setFehlKod4(String fehlKod4) {
        this.fehlKod4 = fehlKod4;
    }

    public String getFehlKod5() {
        return fehlKod5;
    }

    public void setFehlKod5(String fehlKod5) {
        this.fehlKod5 = fehlKod5;
    }

    public String getFehlKod6() {
        return fehlKod6;
    }

    public void setFehlKod6(String fehlKod6) {
        this.fehlKod6 = fehlKod6;
    }

    public String getFehlKod7() {
        return fehlKod7;
    }

    public void setFehlKod7(String fehlKod7) {
        this.fehlKod7 = fehlKod7;
    }

    public String getFehlKod8() {
        return fehlKod8;
    }

    public void setFehlKod8(String fehlKod8) {
        this.fehlKod8 = fehlKod8;
    }

    public String getFehlKod9() {
        return fehlKod9;
    }

    public void setFehlKod9(String fehlKod9) {
        this.fehlKod9 = fehlKod9;
    }

    public String getFehlKod10() {
        return fehlKod10;
    }

    public void setFehlKod10(String fehlKod10) {
        this.fehlKod10 = fehlKod10;
    }

    public String getFehlerText1() {
        return fehlerText1;
    }

    public void setFehlerText1(String fehlerText1) {
        this.fehlerText1 = fehlerText1;
    }

    public String getFehlerText2() {
        return fehlerText2;
    }

    public void setFehlerText2(String fehlerText2) {
        this.fehlerText2 = fehlerText2;
    }

    public String getFehlerText3() {
        return fehlerText3;
    }

    public void setFehlerText3(String fehlerText3) {
        this.fehlerText3 = fehlerText3;
    }

    public String getFehlerText4() {
        return fehlerText4;
    }

    public void setFehlerText4(String fehlerText4) {
        this.fehlerText4 = fehlerText4;
    }

    public String getFehlerText5() {
        return fehlerText5;
    }

    public void setFehlerText5(String fehlerText5) {
        this.fehlerText5 = fehlerText5;
    }

    public String getFehlerText6() {
        return fehlerText6;
    }

    public void setFehlerText6(String fehlerText6) {
        this.fehlerText6 = fehlerText6;
    }

    public String getFehlerText7() {
        return fehlerText7;
    }

    public void setFehlerText7(String fehlerText7) {
        this.fehlerText7 = fehlerText7;
    }

    public String getFehlerText8() {
        return fehlerText8;
    }

    public void setFehlerText8(String fehlerText8) {
        this.fehlerText8 = fehlerText8;
    }

    public String getFehlerText9() {
        return fehlerText9;
    }

    public void setFehlerText9(String fehlerText9) {
        this.fehlerText9 = fehlerText9;
    }

    public String getFehlerText10() {
        return fehlerText10;
    }

    public void setFehlerText10(String fehlerText10) {
        this.fehlerText10 = fehlerText10;
    }

    public String getFehlTime1() {
        return fehlTime1;
    }

    public void setFehlTime1(String fehlTime1) {
        this.fehlTime1 = fehlTime1;
    }

    public String getFehlTime2() {
        return fehlTime2;
    }

    public void setFehlTime2(String fehlTime2) {
        this.fehlTime2 = fehlTime2;
    }

    public String getFehlTime3() {
        return fehlTime3;
    }

    public void setFehlTime3(String fehlTime3) {
        this.fehlTime3 = fehlTime3;
    }

    public String getFehlTime4() {
        return fehlTime4;
    }

    public void setFehlTime4(String fehlTime4) {
        this.fehlTime4 = fehlTime4;
    }

    public String getFehlTime5() {
        return fehlTime5;
    }

    public void setFehlTime5(String fehlTime5) {
        this.fehlTime5 = fehlTime5;
    }

    public String getFehlTime6() {
        return fehlTime6;
    }

    public void setFehlTime6(String fehlTime6) {
        this.fehlTime6 = fehlTime6;
    }

    public String getFehlTime7() {
        return fehlTime7;
    }

    public void setFehlTime7(String fehlTime7) {
        this.fehlTime7 = fehlTime7;
    }

    public String getFehlTime8() {
        return fehlTime8;
    }

    public void setFehlTime8(String fehlTime8) {
        this.fehlTime8 = fehlTime8;
    }

    public String getFehlTime9() {
        return fehlTime9;
    }

    public void setFehlTime9(String fehlTime9) {
        this.fehlTime9 = fehlTime9;
    }

    public String getFehlTime10() {
        return fehlTime10;
    }

    public void setFehlTime10(String fehlTime10) {
        this.fehlTime10 = fehlTime10;
    }

    public String getVsVodic1() {
        return vsVodic1;
    }

    public void setVsVodic1(String vsVodic1) {
        this.vsVodic1 = vsVodic1;
    }

    public String getVsVodic2() {
        return vsVodic2;
    }

    public void setVsVodic2(String vsVodic2) {
        this.vsVodic2 = vsVodic2;
    }
}
