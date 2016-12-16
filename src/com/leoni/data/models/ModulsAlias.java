package com.leoni.data.models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.9.2015
 * Time: 7:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "moduls")
public class ModulsAlias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Column (name = "sach_nr_lieferant")
    private String sachNrLieferant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSachNrLieferant() {
        return sachNrLieferant;
    }

    public void setSachNrLieferant(String sachNrLieferant) {
        this.sachNrLieferant = sachNrLieferant;
    }
}

