package com.leoni.data.models.vm;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 3.8.2015
 * Time: 7:57
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "vm_switch", schema = "public", catalog = "")
@Entity
public class VmSwitch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "switch_id")
    private Integer id;

    @Column (name = "name_switch")
    private String name;

    @Column (name = "address")
    private String address;

    @Column (name = "status")
    private boolean status;

    @OneToOne
    @JoinColumn(name = "brett_id")
    private VmBrett vmBrett;

    @OneToOne
    @JoinColumn(name = "clip_id")
    private VmClip vmClip;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public VmBrett getVmBrett() {
        return vmBrett;
    }

    public void setVmBrett(VmBrett vmBrett) {
        this.vmBrett = vmBrett;
    }

    public VmClip getVmClip() {
        return vmClip;
    }

    public void setVmClip(VmClip vmClip) {
        this.vmClip = vmClip;
    }
}
