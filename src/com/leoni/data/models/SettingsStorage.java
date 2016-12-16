package com.leoni.data.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 15.10.2015
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "settings_storage")
public class SettingsStorage implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", unique = true)
    private Integer id;
    @Column (name = "parameter")
    private String parameter;
    @Column (name = "value")
    private String value;
    @Column (name = "change_date")
    private Date changeDate;
    @Column (name = "description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int compareTo(Object o) {
        SettingsStorage ss = (SettingsStorage) o ;
        return this.getId().compareTo(ss.getId());  //To change body of implemented methods use File | Settings | File Templates.
    }
}
