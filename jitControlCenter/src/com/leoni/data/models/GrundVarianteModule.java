package com.leoni.data.models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.4.2014
 * Time: 10:23
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "grund_variante_module_wrm", schema = "public", catalog = "")
@Entity
public class GrundVarianteModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "moduls_id")
    private Moduls moduls;
    @Column (name = "wertigkeit")
    private Integer wertigkeit;
    @Column (name = "wertigkeit_dme_sw")
    private Integer wertigkeitDmeSw;
    @Column (name = "wertigkeit_dme_gr")
    private Integer wertigkeitDmeGr;
    @Column (name = "wertigkeit_tuelle_bug")
    private Integer wertigkeitTuelleBug;
    @Column (name = "note")
    private String note;

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

    public Integer getWertigkeit() {
        return wertigkeit;
    }

    public void setWertigkeit(Integer wertigkeit) {
        this.wertigkeit = wertigkeit;
    }

    public Integer getWertigkeitDmeSw() {
        return wertigkeitDmeSw;
    }

    public void setWertigkeitDmeSw(Integer wertigkeitDmeSw) {
        this.wertigkeitDmeSw = wertigkeitDmeSw;
    }

    public Integer getWertigkeitDmeGr() {
        return wertigkeitDmeGr;
    }

    public void setWertigkeitDmeGr(Integer wertigkeitDmeGr) {
        this.wertigkeitDmeGr = wertigkeitDmeGr;
    }

    public Integer getWertigkeitTuelleBug() {
        return wertigkeitTuelleBug;
    }

    public void setWertigkeitTuelleBug(Integer wertigkeitTuelleBug) {
        this.wertigkeitTuelleBug = wertigkeitTuelleBug;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
