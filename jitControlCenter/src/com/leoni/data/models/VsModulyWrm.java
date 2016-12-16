package com.leoni.data.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.1.2014
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "vs_moduly_wrm", schema = "public", catalog = "")
@Entity
public class VsModulyWrm implements Comparable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "moduls_id")
    private Moduls moduls;

    @Column (name = "vodic")
    private String vodic;

    @Column (name = "prierez")
    private Double prierez;
    @Column (name = "popis")
    private String popis;
    @OneToOne
    @JoinColumn(name = "id_vs")
    private Vs vs;
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

    public Moduls getModuls() {
        return moduls;
    }

    public void setModuls(Moduls moduls) {
        this.moduls = moduls;
    }

    public String getVodic() {
        return vodic;
    }

    public void setVodic(String vodic) {
        this.vodic = vodic;
    }

    public Double getPrierez() {
        return prierez;
    }

    public void setPrierez(Double prierez) {
        this.prierez = prierez;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
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

    public Vs getVs() {
        return vs;
    }

    public void setVs(Vs vs) {
        this.vs = vs;
    }

    public int compareTo(Object o) {
        VsModulyWrm vs = (VsModulyWrm) o;
        if(this.getPopis() == null){
            if(vs.getPopis() == null)
                return 0; //equal
            else
                return 1;} // null is before other strings
        else // this.member != null
        {if(vs.getPopis() == null)
            return -1;  // all other strings are after null
        else
            return this.getPopis().compareTo(vs.getPopis());}
    }
}
