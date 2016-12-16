package com.leoni.data.models.nonDBmodels;

import com.leoni.viewModel.oldJit.JanzmgrpVM;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 27.10.2014
 * Time: 8:18
 * To change this template use File | Settings | File Templates.
 */
public class JanzmgrpObj implements Comparable{
 String group;
 String ksKz;
 String ausfuehrung;
 Set<String> prodNrSet;
 int count;

    public String getAusfuehrung() {
        return ausfuehrung;
    }

    public void setAusfuehrung(String ausfuehrung) {
        this.ausfuehrung = ausfuehrung;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getKsKz() {
        return ksKz;
    }

    public void setKsKz(String ksKz) {
        this.ksKz = ksKz;
    }

    public Set<String> getProdNrSet() {
        return prodNrSet;
    }

    public void setProdNrSet(Set<String> prodNrSet) {
        this.prodNrSet = prodNrSet;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        JanzmgrpObj janzmgrpObj = (JanzmgrpObj) o;
        if(this.getGroup()!=null&&this.getKsKz()!=null&&this.getAusfuehrung()!=null&&janzmgrpObj.getGroup()!=null&&janzmgrpObj.getKsKz()!=null&&janzmgrpObj.getAusfuehrung()!=null){
        if((this.getGroup().equals(janzmgrpObj.getGroup()))&&
           (this.getKsKz().equals(janzmgrpObj.getKsKz()))&&
           (this.getAusfuehrung().equals(janzmgrpObj.getAusfuehrung()))){return true;}
        else return false;}
        else return false;
    }

    @Override
    public int hashCode(){
        if(this.getKsKz().trim().equals("F")||this.getKsKz().trim().equals("C"))
        return this.group.hashCode() *
               this.ksKz.hashCode() *
               this.ausfuehrung.hashCode();
        else return this.group.hashCode() *
                    this.ksKz.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        JanzmgrpObj janzmgrpObj = (JanzmgrpObj) o;
        if(!this.getKsKz().equals(janzmgrpObj.getKsKz())) return this.getKsKz().compareTo(janzmgrpObj.getKsKz());
        else {
             if(this.getKsKz().trim().equals("F")||this.getKsKz().trim().equals("C")){
                if(!this.getGroup().equals(janzmgrpObj.getGroup())) return this.getGroup().compareTo(janzmgrpObj.getGroup());
                else{if(!this.getAusfuehrung().equals(janzmgrpObj.getAusfuehrung()))  return this.getAusfuehrung().compareTo(janzmgrpObj.getAusfuehrung());
                 else return 0;
                }
             }
            else{
                 if(!this.getGroup().equals(janzmgrpObj.getGroup())) return this.getGroup().compareTo(janzmgrpObj.getGroup());
                 else return 0;}
        }
    }
}
