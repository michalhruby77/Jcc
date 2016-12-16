package com.leoni.data.models;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.10.2014
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "foam_workplace")
@Entity
public class FoamWorkplace {
    @Id
    @Column (name = "workplace")
    private Integer workplace;
    @Column (name = "form")
    private String form;
    @Column (name = "template")
    private String template;
    @Column (name = "wp_name")
    private String workplaceName;
    @Column (name = "description")
    private String description;
    @OneToMany(orphanRemoval=true,cascade = CascadeType.ALL, mappedBy = "foamWorkplace",fetch = FetchType.EAGER)
    private List<FoamWorkplaceModuls> modulsList;

    public Integer getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Integer workplace) {
        this.workplace = workplace;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<FoamWorkplaceModuls> getModulsList() {
        return modulsList;
    }

    public void setModulsList(List<FoamWorkplaceModuls> modulsList) {
        this.modulsList = modulsList;
    }

    public String getWorkplaceName() {
        return workplaceName;
    }

    public void setWorkplaceName(String workplaceName) {
        this.workplaceName = workplaceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
