package com.leoni.data.models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.6.2014
 * Time: 12:39
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "newmodulescheck")
public class NewModules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Column (name = "sach_nr_best")
    private String sachNrBest;
    @Column (name = "notiz")
    private String notiz;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSachNrBest() {
        return sachNrBest;
    }

    public void setSachNrBest(String sachNrBest) {
        this.sachNrBest = sachNrBest;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }
}
