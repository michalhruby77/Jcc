package com.leoni.data.models.vm;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 3.8.2015
 * Time: 7:22
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "vm_brett", schema = "public", catalog = "")
@Entity
public class VmBrett {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brett_id")
    private Integer id;

    @Column (name = "name_brett")
    private String name;

    @OneToOne
    @JoinColumn(name = "stelle_id")
    private VmStelle vmStelle;

    @ManyToMany(fetch = FetchType.EAGER,/* cascade = CascadeType.ALL*/cascade =  CascadeType.MERGE)
    @JoinTable(name = "vm_brett_variante",
            joinColumns = @JoinColumn(name = "brett_id") ,
            inverseJoinColumns =  @JoinColumn(name = "variante_id") )
    private Set<VmVariante> varianteSet = new HashSet<>();

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

    public VmStelle getVmStelle() {
        return vmStelle;
    }

    public void setVmStelle(VmStelle vmStelle) {
        this.vmStelle = vmStelle;
    }

    public Set<VmVariante> getVarianteSet() {
        return varianteSet;
    }

    public void setVarianteSet(Set<VmVariante> varianteSet) {
        this.varianteSet = varianteSet;
    }
}
