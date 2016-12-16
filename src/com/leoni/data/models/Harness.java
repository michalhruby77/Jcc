package com.leoni.data.models;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 11.12.2013
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "band")
@IdClass(HarnessId.class)
public class Harness implements Comparable, Serializable {
    //@Id
    //@GeneratedValue (strategy = GenerationType.IDENTITY)
    //@Column (name = "oid")
    //private Integer id;
    @Id
    @Column (name = "bretttype")
    private String bretttype;
    @Id
    @Column (name = "brettid")
    private String brettid;
    @Column (name = "busytime")
    private Date busytime;
    @Column (name = "lock")
    private Boolean lock;
    @Column (name = "prod_nr")
    private String prod_nr;
    @Column (name = "kabelsatz_kz")
    private String kabelsatz_kz;
    @Column (name = "modulen")
    private String modulen;
    @Column (name = "side_b_step")
    private Integer side_b_step;
    @Column (name = "side_a_step")
    private Integer side_a_step;
    @Column (name = "band_name")
    private String band_name;
    @Column (name = "truck_name")
    private String truck_name;

    public String getBretttype() {
        return bretttype;
    }

    public void setBretttype(String bretttype) {
        this.bretttype = bretttype;
    }
    public String getBrettid() {
        return brettid;
    }

    public void setBrettid(String brettid) {
        this.brettid = brettid;
    }
    public Date getBusytime() {
        return busytime;
    }

    public void setBusytime(Date busytime) {
        this.busytime = busytime;
    }
    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }
    public String getProd_nr() {
        return prod_nr;
    }

    public void setProd_nr(String prod_nr) {
        this.prod_nr = prod_nr;
    }
    public String getKabelsatz_kz() {
        return kabelsatz_kz;
    }

    public void setKabelsatz_kz(String kabelsatz_kz) {
        this.kabelsatz_kz = kabelsatz_kz;
    }
    public String getModulen() {
        return modulen;
    }

    public void setModulen(String modulen) {
        this.modulen = modulen;
    }
    public String getBand_name() {
        return band_name;
    }

    public void setBand_name(String band_name) {
        this.band_name = band_name;
    }
    public String getTruck_name() {
        return truck_name;
    }

    public void setTruck_name(String truck_name) {
        this.truck_name = truck_name;
    }
    public Integer getSide_b_step() {
        return side_b_step;
    }

    public void setSide_b_step(Integer side_b_step) {
        this.side_b_step = side_b_step;
    }
    public Integer getSide_a_step() {
        return side_a_step;
    }

    public void setSide_a_step(Integer side_a_step) {
        this.side_a_step = side_a_step;
    }

   /* public int compareTo(Object o) {
        Harness harness = (Harness) o;
        if (this.getBusytime()!=null&&harness.getBusytime()!=null)
        {

        if (this.getBusytime().
                compareTo(harness.getBusytime())<0 ) return -1;
        else return 1;
        }
      return 5;
    }*/

    public int compareTo(Object o) {
        Harness harness = (Harness) o;
        if(this.getBusytime() == null){
        if(harness.getBusytime() == null)
            return 0; //equal
        else
            return 1;} // null is before other strings
        else // this.member != null
        {if(harness.getBusytime() == null)
            return -1;  // all other strings are after null
        else
            return this.getBusytime().compareTo(harness.getBusytime());}
    }


}

