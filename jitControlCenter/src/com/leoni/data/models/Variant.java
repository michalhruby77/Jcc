package com.leoni.data.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2014
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "variant")
@Entity
public class Variant implements Comparable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", unique = true)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "color_id")
    private Color color;
    @OneToOne
    @JoinColumn(name = "workplace_id")
    private Workplace workplace;
    @Column (name = "name")
    private String name;
    @Column (name = "description")
    private String description;
    @Column (name = "scan_string")
    private String scanString;
    @Column (name = "scan_required")
    private Boolean scanRequired;
    @Column (name = "moduls_count")
    private Integer modulsCount;
    @Column (name = "typ")
    private String typ;
    @Column (name = "xmlmoduls")
    private String xmlModuls;
    @ManyToMany(fetch = FetchType.EAGER,/* cascade = CascadeType.ALL*/cascade =  CascadeType.MERGE)
    @JoinTable(name = "moduls_variant",
            joinColumns = @JoinColumn(name = "variant_id") ,
            inverseJoinColumns =  @JoinColumn(name = "moduls_id") )
    private Set<Moduls> modulsSet = new HashSet<Moduls>();
    @Column (name = "changed_by")
    private String changedBy;
    @Column (name = "changed_date")
    private Date changedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScanString() {
        return scanString;
    }

    public void setScanString(String scanString) {
        this.scanString = scanString;
    }

    public Integer getModulsCount() {
        return modulsCount;
    }

    public void setModulsCount(Integer modulsCount) {
        this.modulsCount = modulsCount;
    }

    public Boolean getScanRequired() {
        return scanRequired;
    }

    public void setScanRequired(Boolean scanRequired) {
        this.scanRequired = scanRequired;
    }


    public Set<Moduls> getModulsSet() {
        return modulsSet;
    }

    public void setModulsSet(Set<Moduls> modulsSet) {
        this.modulsSet = modulsSet;
    }

    public String getXmlModuls() {
        return xmlModuls;
    }

    public void setXmlModuls(String xmlModuls) {
        this.xmlModuls = xmlModuls;
    }

    public Date getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(Date changedDate) {
        this.changedDate = changedDate;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public int compareTo(Object o) {
        Variant variant = (Variant) o;
        if(this.getDescription() == null){
            if(variant.getDescription() == null)
                return 0; //equal
            else
                return 1;} // null is before other strings
        else // this.member != null
        {if(variant.getDescription() == null)
            return -1;  // all other strings are after null
        else
            return this.getDescription().compareTo(variant.getDescription());}
    }

    @Override
    public String toString() {
        return "Variant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
