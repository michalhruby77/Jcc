package com.leoni.data.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.10.2014
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "foam_workplace_moduls")
@Entity
public class FoamWorkplaceModuls {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "workplace_id", referencedColumnName = "workplace")
    private FoamWorkplace foamWorkplace;
    @Column (name = "prodnr_prefix")
    private String prodNrPrefix;

    public FoamWorkplace getFoamWorkplace() {
        return foamWorkplace;
    }

    public void setFoamWorkplace(FoamWorkplace foamWorkplace) {
        this.foamWorkplace = foamWorkplace;
    }

    public String getProdNrPrefix() {
        return prodNrPrefix;
    }

    public void setProdNrPrefix(String prodNrPrefix) {
        this.prodNrPrefix = prodNrPrefix;
    }
}
