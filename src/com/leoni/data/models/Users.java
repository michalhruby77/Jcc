package com.leoni.data.models;

import javax.persistence.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 29.5.2014
 * Time: 8:17
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", unique = true)
    private Integer id;
    @Column (name = "username")
    private String username;
    @Column (name = "password")
    private String password;
    @Column (name = "name")
    private String name;
    /*@OneToMany (fetch = FetchType.EAGER)
    @JoinColumn(name="username", referencedColumnName="username")
    private Set<UserRole> userRoleSet = new HashSet<UserRole>();*/

    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "user")
    //private Set<UserRole> userRoleSet;

    @ManyToMany(fetch = FetchType.EAGER,cascade =  CascadeType.MERGE)
    @OrderBy("id ASC")
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id") ,
            inverseJoinColumns =  @JoinColumn(name = "role_id") )
    private SortedSet<Roles> userRoles = new TreeSet<Roles>();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SortedSet<Roles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(SortedSet<Roles> userRoles) {
        this.userRoles = userRoles;
    }
}
