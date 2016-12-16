package com.leoni.data.models;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 18.8.2014
 * Time: 8:48
 * To change this template use File | Settings | File Templates.
 */

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Column (name = "name")
    private String name;
    @Column (name = "number")
    private Integer number;

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
