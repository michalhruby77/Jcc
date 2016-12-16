package com.leoni.data.models.vm;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 3.8.2015
 * Time: 7:17
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "vm_variante", schema = "public", catalog = "")
@Entity
public class VmVariante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variante_id")
    private Integer id;

    @Column (name = "name_variante")
    private String name;

    @Column (name = "erz_nr")
    private String erznr;

    @ManyToMany(fetch = FetchType.EAGER,/* cascade = CascadeType.ALL*/cascade =  CascadeType.MERGE)
    @JoinTable(name = "vm_variante_clips",
            joinColumns = @JoinColumn(name = "variante_id") ,
            inverseJoinColumns =  @JoinColumn(name = "clip_id") )
    private Set<VmClip> clipSet = new HashSet<>();

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

    public String getErznr() {
        return erznr;
    }

    public void setErznr(String erznr) {
        this.erznr = erznr;
    }

    public Set<VmClip> getClipSet() {
        return clipSet;
    }

    public void setClipSet(Set<VmClip> clipSet) {
        this.clipSet = clipSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VmVariante)) return false;

        VmVariante that = (VmVariante) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
