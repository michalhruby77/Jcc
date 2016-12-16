package com.leoni.data.models;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.6.2014
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public class CriticalNewModul {
    private String sachNrBest;
    private String kundenNr;
    private String prNr;
    private String liefDatum;
    private String dfueNrEin;
    private String existsTsji;
    private String existsAuft;
    private String note;

    public String getSachNrBest() {
        return sachNrBest;
    }

    public void setSachNrBest(String sachNrBest) {
        this.sachNrBest = sachNrBest;
    }

    public String getKundenNr() {
        return kundenNr;
    }

    public void setKundenNr(String kundenNr) {
        this.kundenNr = kundenNr;
    }

    public String getPrNr() {
        return prNr;
    }

    public void setPrNr(String prNr) {
        this.prNr = prNr;
    }

    public String getLiefDatum() {
        return liefDatum;
    }

    public void setLiefDatum(String liefDatum) {
        this.liefDatum = liefDatum;
    }

    public String getExistsTsji() {
        return existsTsji;
    }

    public void setExistsTsji(String existsTsji) {
        this.existsTsji = existsTsji;
    }

    public String getExistsAuft() {
        return existsAuft;
    }

    public void setExistsAuft(String existsAuft) {
        this.existsAuft = existsAuft;
    }

    public String getDfueNrEin() {
        return dfueNrEin;
    }

    public void setDfueNrEin(String dfueNrEin) {
        this.dfueNrEin = dfueNrEin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
