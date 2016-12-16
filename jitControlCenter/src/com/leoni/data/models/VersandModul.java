package com.leoni.data.models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 2.3.2015
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "versand_export_items")
public class VersandModul {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "export_id", referencedColumnName = "id")
    private VersandExport versandExport;
    @Column (name = "sach_nr_best")
    private String sachNrBest;
    @Column (name = "sach_nr_lief")
    private String sachNrLieferant;
    @Column (name = "auftrag_nr")
    private Integer auftragNr;
    @Column (name = "pieces")
    private Integer pieces;
    @Column (name = "count")
    private Integer count;
    @Column (name = "time")
    private String time;
    @Column (name = "ladung_pcs")
    private String ladungPcs;
    @Column (name = "ladung_name")
    private String ladungName;
    @Column (name = "palette_pcs")
    private String palettePcs;
    @Column (name = "palette_name")
    private String paletteName;
    @Column (name = "deckel_pcs")
    private String deckelPcs;
    @Column (name = "deckel_name")
    private String deckelName;
    @Column (name = "mj")
    private Integer mj;
    @Column (name = "persnr")
    private String persnr;
    @Column (name = "scan")
    private Boolean scan;
    @Column (name = "status")
    private Integer status;

    public String getSachNrBest() {
        return sachNrBest;
    }

    public void setSachNrBest(String sachNrBest) {
        this.sachNrBest = sachNrBest;
    }

    public String getSachNrLieferant() {
        return sachNrLieferant;
    }

    public void setSachNrLieferant(String sachNrLieferant) {
        this.sachNrLieferant = sachNrLieferant;
    }

    public Integer getAuftragNr() {
        return auftragNr;
    }

    public void setAuftragNr(Integer auftragNr) {
        this.auftragNr = auftragNr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLadungName() {
        return ladungName;
    }

    public void setLadungName(String ladungName) {
        this.ladungName = ladungName;
    }

    public String getPaletteName() {
        return paletteName;
    }

    public void setPaletteName(String paletteName) {
        this.paletteName = paletteName;
    }

    public String getDeckelName() {
        return deckelName;
    }

    public void setDeckelName(String deckelName) {
        this.deckelName = deckelName;
    }

    public Integer getMj() {
        return mj;
    }

    public void setMj(Integer mj) {
        this.mj = mj;
    }

    public VersandExport getVersandExport() {
        return versandExport;
    }

    public void setVersandExport(VersandExport versandExport) {
        this.versandExport = versandExport;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLadungPcs() {
        return ladungPcs;
    }

    public void setLadungPcs(String ladungPcs) {
        this.ladungPcs = ladungPcs;
    }

    public String getPalettePcs() {
        return palettePcs;
    }

    public void setPalettePcs(String palettePcs) {
        this.palettePcs = palettePcs;
    }

    public String getDeckelPcs() {
        return deckelPcs;
    }

    public void setDeckelPcs(String deckelPcs) {
        this.deckelPcs = deckelPcs;
    }

    public String getPersnr() {
        return persnr;
    }

    public void setPersnr(String persnr) {
        this.persnr = persnr;
    }

    public Boolean getScan() {
        return scan;
    }

    public void setScan(Boolean scan) {
        this.scan = scan;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
