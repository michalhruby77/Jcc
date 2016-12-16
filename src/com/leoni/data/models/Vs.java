package com.leoni.data.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 16.12.2014
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "vs", schema = "public", catalog = "")
@Entity
public class Vs implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column (name = "pocet")
    private Integer pocet;
    @Column (name = "nazov")
    private String nazov;
    /*@Column (name = "strana")
    private String strana; */
    @Column (name = "changed_by")
    private String changedBy;
    @Column (name = "changed_date")
    private Date changedDate;
    @OneToOne
    @JoinColumn(name = "id_farby")
    private VsFarby vsFarby;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /*public Integer getPocet() {
        return pocet;
    }

    public void setPocet(Integer pocet) {
        this.pocet = pocet;
    } */

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

   /* public String getStrana() {
        return strana;
    }

    public void setStrana(String strana) {
        this.strana = strana;
    }         */

    public VsFarby getVsFarby() {
        return vsFarby;
    }

    public void setVsFarby(VsFarby vsFarby) {
        this.vsFarby = vsFarby;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public Date getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(Date changedDate) {
        this.changedDate = changedDate;
    }

    public Integer getPocet() {
        return pocet;
    }

    public void setPocet(Integer pocet) {
        this.pocet = pocet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vs)) return false;

        Vs vs = (Vs) o;

        if (changedBy != null ? !changedBy.equals(vs.changedBy) : vs.changedBy != null) return false;
        if (changedDate != null ? !changedDate.equals(vs.changedDate) : vs.changedDate != null) return false;
        if (id != null ? !id.equals(vs.id) : vs.id != null) return false;
        if (nazov != null ? !nazov.equals(vs.nazov) : vs.nazov != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (nazov != null ? nazov.hashCode() : 0);
        result = 31 * result + (changedBy != null ? changedBy.hashCode() : 0);
        result = 31 * result + (changedDate != null ? changedDate.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        Vs vs = (Vs) o;
        return this.getNazov().compareTo(vs.getNazov());}

}
