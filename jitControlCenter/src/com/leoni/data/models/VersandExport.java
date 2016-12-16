package com.leoni.data.models;

import com.leoni.viewModel.VersandModulsVM;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 3.3.2015
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "versand_export")
public class VersandExport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Column (name = "date")
    private Date date;
    @OneToMany(orphanRemoval=true,cascade = CascadeType.ALL, mappedBy = "versandExport",fetch = FetchType.EAGER)
    private List<VersandModul> modulsList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<VersandModul> getModulsList() {
        return modulsList;
    }

    public void setModulsList(List<VersandModul> modulsList) {
        this.modulsList = modulsList;
    }
}
