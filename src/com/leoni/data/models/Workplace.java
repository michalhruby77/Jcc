package com.leoni.data.models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2014
 * Time: 9:07
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "workplace", schema = "public", catalog = "")
@Entity
public class Workplace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;

    @Column (name = "name")
    private String name;
    @Column (name = "band_name")
    private String bandName;
    @Column (name = "step")
    private Integer step;
    @Column (name = "side")
    private String side;
    @Column (name = "name_alias")
    private String alias;
    @Column (name = "operation")
    private String operation;
    @OneToOne
    @JoinColumn(name = "band_id")
    private BandType band;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public BandType getBand() {
        return band;
    }

    public void setBand(BandType band) {
        this.band = band;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
