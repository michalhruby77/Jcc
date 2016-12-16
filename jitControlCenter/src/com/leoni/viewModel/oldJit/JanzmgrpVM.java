package com.leoni.viewModel.oldJit;

import com.leoni.data.manager.oldJIT.Jitpab62Manager;
import com.leoni.data.manager.oldJIT.Jitpab64Manager;
import com.leoni.data.models.nonDBmodels.JanzmgrpObj;
import com.leoni.data.models.oldJIT.Jitpab62;
import com.leoni.data.models.oldJIT.Jitpab64;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 25.11.2014
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class JanzmgrpVM {

    @WireVariable
    private Jitpab62Manager jitpab62Manager;

    @WireVariable
    private Jitpab64Manager jitpab64Manager;

    String selectedKunde = "*";
    String dateFromString;
    String dateToString;
    private Date dateFrom = new Date();
    private Date dateTo = new Date();


    @Command
    @NotifyChange({"selectedKunde","dateTo","dateFrom"})
    public void submit(@BindingParam("rows") Rows rows)
    {
        Components.removeAllChildren(rows);
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        dateFromString = df.format(dateFrom);
        dateToString = df.format(dateTo);
        /*List<Jitpab62> jitpab62List = jitpab62Manager.getJanzmgrp1(selectedKunde, dateFromString, dateToString);
        List<Jitpab64> jitpab64List = new ArrayList<>();
        Set<String> grpSet = new HashSet<>();
        Set<JanzmgrpObj> janzmgrpObjSet = new HashSet<>();
        Set<String> prodnr62Set = new HashSet<>();
        for(Jitpab62 item : jitpab62List){
            prodnr62Set.add(item.getProdNr());
        }
        jitpab64List = jitpab64Manager.getJanzmgrp2(prodnr62Set);
        for(Jitpab64 item : jitpab64List){
            if (item.getGrundmodulKz().trim().equals("J")){
                JanzmgrpObj janzmgrpObj = new JanzmgrpObj();
                janzmgrpObj.setKsKz(item.getKabelsatzKz());
                janzmgrpObj.setGroup(item.getSachNrBest().substring(0,3));
                for (Jitpab62 item2 : jitpab62List){
                     if (item.getProdNr().trim().equals(item2.getProdNr().trim())&&item.getKabelsatzKz().trim().equals(item2.getKabelsatzKz().trim())){
                         janzmgrpObj.setAusfuehrung(item2.getAusfuehrung());
                     }
                }
                if (janzmgrpObj.getAusfuehrung()!=null)janzmgrpObjSet.add(janzmgrpObj);}
        }
        for (JanzmgrpObj item : janzmgrpObjSet){
            System.out.println(item.getGroup() + " " + item.getKsKz() + " " +  item.getAusfuehrung());
        } */
        SortedSet<JanzmgrpObj> janzmgrpObjSet = jitpab62Manager.selecteInnerJoinJitpab62Jitpab64(selectedKunde, dateFromString, dateToString);//new TreeSet<>();
        /*List list = jitpab62Manager.selecteInnerJoinJitpab62Jitpab64(selectedKunde, dateFromString, dateToString);
        Iterator it = list.iterator();
        while(it.hasNext()){

            Object o[]=(Object[])it.next();
            System.out.println(o[0] + " " + o[1]+"++++++++++++++++++++++++++++++++++++++++++++++++ "+o[3]+" "+o[5]);
            JanzmgrpObj janzmgrpObj = new JanzmgrpObj();
            String prodNr = ((String)o[0]).trim();
            janzmgrpObj.setKsKz((String)o[1]);
            janzmgrpObj.setAusfuehrung((String)o[3]);
            janzmgrpObj.setGroup(((String) o[5]).trim().substring(0, 3));
            if(!janzmgrpObjSet.contains(janzmgrpObj)){
                //System.out.println("neobsahuje");
                Set<String> prodNrSet = new HashSet<>();
                prodNrSet.add(prodNr);
                janzmgrpObj.setProdNrSet(prodNrSet);
                janzmgrpObjSet.add(janzmgrpObj);

            }
            else{
                //System.out.println("obsahuje");
                Iterator itSet = janzmgrpObjSet.iterator();
                while(itSet.hasNext())
                    {
                        JanzmgrpObj  janzmgrpObj1 = (JanzmgrpObj) itSet.next();
                        if (janzmgrpObj1.getGroup().trim().equals(janzmgrpObj.getGroup().trim())&&
                            janzmgrpObj1.getAusfuehrung().trim().equals(janzmgrpObj.getAusfuehrung().trim())&&
                            janzmgrpObj1.getKsKz().trim().equals(janzmgrpObj.getKsKz().trim())){
                            Set prodNrSet = janzmgrpObj1.getProdNrSet();
                            prodNrSet.add(prodNr) ;
                            janzmgrpObj1.setProdNrSet(prodNrSet);
                            System.out.println("pridavam prod cislo do existuje: "+prodNr+ " do " + janzmgrpObj1.getGroup()+ " "+janzmgrpObj1.getKsKz() + " " + janzmgrpObj1.getAusfuehrung());
                        }
                    }
                }
            } */
        SortedSet<JanzmgrpObj> janzmgrpObjectSetTempC = new TreeSet();
        for (JanzmgrpObj item : janzmgrpObjSet){
            JanzmgrpObj janzmgrpObj = new JanzmgrpObj();
            janzmgrpObj.setKsKz("C");
            janzmgrpObj.setProdNrSet(item.getProdNrSet());
            janzmgrpObj.setGroup(item.getGroup());
            janzmgrpObj.setAusfuehrung(item.getAusfuehrung());
            janzmgrpObjectSetTempC.add(janzmgrpObj);

        }

        SortedSet<JanzmgrpObj> janzmgrpObjectSetTempT = new TreeSet();
        for (JanzmgrpObj item : janzmgrpObjSet){
            JanzmgrpObj janzmgrpObj = new JanzmgrpObj();
            janzmgrpObj.setKsKz("T");
            Set prodNrSet = new HashSet();
            prodNrSet.addAll(item.getProdNrSet());
            janzmgrpObj.setProdNrSet(prodNrSet);
            janzmgrpObj.setGroup(item.getGroup());
            janzmgrpObj.setAusfuehrung("XL");
            if(!janzmgrpObjectSetTempT.contains(janzmgrpObj)){
                janzmgrpObjectSetTempT.add(janzmgrpObj);
            }
            else{
                Iterator itSet = janzmgrpObjectSetTempT.iterator();
                while(itSet.hasNext())
                {
                    JanzmgrpObj  janzmgrpObj1 = (JanzmgrpObj) itSet.next();
                    if (janzmgrpObj1.getGroup().trim().equals(janzmgrpObj.getGroup().trim())&&
                        janzmgrpObj1.getKsKz().trim().equals(janzmgrpObj.getKsKz().trim())){
                        Set prodNrSet1 = new HashSet();
                        prodNrSet1.addAll(item.getProdNrSet());
                        janzmgrpObj1.getProdNrSet().addAll(prodNrSet);
                    }
                }
            }
        }

        SortedSet<JanzmgrpObj> janzmgrpObjectSetTempU = new TreeSet();
        for (JanzmgrpObj item : janzmgrpObjSet){
            JanzmgrpObj janzmgrpObj = new JanzmgrpObj();
            janzmgrpObj.setKsKz("U");
            Set prodNrSet = new HashSet();
            prodNrSet.addAll(item.getProdNrSet());
            janzmgrpObj.setProdNrSet(prodNrSet);
            janzmgrpObj.setGroup(item.getGroup());
            janzmgrpObj.setAusfuehrung("XL");
            if(!janzmgrpObjectSetTempU.contains(janzmgrpObj)){
                janzmgrpObjectSetTempU.add(janzmgrpObj);
            }
            else{
                Iterator itSet = janzmgrpObjectSetTempU.iterator();
                while(itSet.hasNext())
                {
                    JanzmgrpObj  janzmgrpObj1 = (JanzmgrpObj) itSet.next();
                    if (janzmgrpObj1.getGroup().trim().equals(janzmgrpObj.getGroup().trim())&&
                            janzmgrpObj1.getKsKz().trim().equals(janzmgrpObj.getKsKz().trim())){
                        Set prodNrSet1 = new HashSet();
                        prodNrSet1.addAll(item.getProdNrSet());
                        janzmgrpObj1.getProdNrSet().addAll(prodNrSet);
                    }
                }
            }
        }

        JanzmgrpObj janzmgrpObjE = new JanzmgrpObj();
        JanzmgrpObj janzmgrpObjR = new JanzmgrpObj();

        if(!selectedKunde.equals("323357")){
        janzmgrpObjE.setAusfuehrung("XL");
        janzmgrpObjE.setKsKz("E");
        janzmgrpObjE.setGroup("991");
        janzmgrpObjE.setCount(jitpab62Manager.selecteInnerJoinJitpab62Jitpab64E(selectedKunde, dateFromString, dateToString));

        janzmgrpObjR.setAusfuehrung("XL");
        janzmgrpObjR.setKsKz("R");
        janzmgrpObjR.setGroup("991");
        janzmgrpObjR.setCount(jitpab62Manager.selecteInnerJoinJitpab62Jitpab64R(selectedKunde, dateFromString, dateToString));}



        janzmgrpObjSet.addAll(janzmgrpObjectSetTempC);
        janzmgrpObjSet.addAll(janzmgrpObjectSetTempT);
        janzmgrpObjSet.addAll(janzmgrpObjectSetTempU);


        for (JanzmgrpObj item : janzmgrpObjSet){
            jitpab64Manager.getJanzmgrpCount(item);
        }

        if(!selectedKunde.equals("323357")){
        janzmgrpObjSet.add(janzmgrpObjE);
        janzmgrpObjSet.add(janzmgrpObjR);}

        for (JanzmgrpObj item : janzmgrpObjSet){
            Row row = new Row();
            Label label1 = new Label(item.getGroup());
            Label label2 = new Label(item.getKsKz());
            Label label3 = new Label(item.getAusfuehrung());
            Label label4 = new Label(String.valueOf(item.getCount()));
            row.appendChild(label1);
            row.appendChild(label2);
            row.appendChild(label3);
            row.appendChild(label4);
            rows.appendChild(row);
            //System.out.println(item.getGroup() + " " + item.getKsKz() + " " +  item.getAusfuehrung() + " " + item.getCount() );
        }
    }

    public String getSelectedKunde() {
        return selectedKunde;
    }

    public void setSelectedKunde(String selectedKunde) {
        this.selectedKunde = selectedKunde;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
