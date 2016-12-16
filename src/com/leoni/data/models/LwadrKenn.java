package com.leoni.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 19.9.2014
 * Time: 7:41
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "lwadrkenn")
public class LwadrKenn {

    @Id
    @Column(name = "kennung")
    private String kennung;
    @Column (name = "kabelsatz_kz")
    private String kabelsatzKz;

    public String getKennung() {
        return kennung;
    }

    public void setKennung(String kennung) {
        this.kennung = kennung;
    }

    public String getKabelsatzKz() {
        return kabelsatzKz;
    }

    public void setKabelsatzKz(String kabelsatzKz) {
        this.kabelsatzKz = kabelsatzKz;
    }
}
