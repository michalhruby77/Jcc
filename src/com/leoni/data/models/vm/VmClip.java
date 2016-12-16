package com.leoni.data.models.vm;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 3.8.2015
 * Time: 7:10
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "vm_clip", schema = "public", catalog = "")
@Entity
public class VmClip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clip_id")
    private Integer id;

    @Column (name = "name_clip")
    private String name;

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
}
