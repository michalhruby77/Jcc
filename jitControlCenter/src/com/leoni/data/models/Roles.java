package com.leoni.data.models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 29.5.2014
 * Time: 8:24
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "roles")
public class Roles implements Comparable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    //@ManyToOne
    @Column (name = "role")
    private String role;

    @Column (name = "menu_xml")
    private String menuXml;

    @Column (name = "permissions")
    private String permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMenuXml() {
        return menuXml;
    }

    public void setMenuXml(String menuXml) {
        this.menuXml = menuXml;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Roles)
            return this.id==((Roles) obj).getId();
        else
            return false;
    }

    @Override
    public int compareTo(Object o) {
        if(this.id < ((Roles) o).getId() ) return -1;
        else return 1;
    }


}
