package com.leoni.data.models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2014
 * Time: 9:13
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "color", schema = "public", catalog = "")
@Entity
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;

    @Column (name = "name")
    private String name;
    @Column (name = "rgb_value")
    private String rgbValue;

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

    public String getRgbValue() {
        return rgbValue;
    }

    public void setRgbValue(String rgbValue) {
        this.rgbValue = rgbValue;
    }
}
