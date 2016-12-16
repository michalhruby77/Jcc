package com.leoni.viewModel;

import com.leoni.data.manager.*;
import com.leoni.data.manager.dab.PrnrManager;
import com.leoni.data.manager.oldJIT.Jitpab62Manager;
import com.leoni.data.manager.oldJIT.Jitpab64Manager;
import com.leoni.data.models.*;
import com.leoni.data.models.dab.Prnr;
import com.leoni.data.models.nonDBmodels.HarnessCheckObj;
import com.leoni.data.models.nonDBmodels.SchulzObj;
import com.leoni.data.models.oldJIT.Jitpab64;
import com.leoni.data.models.oldJIT.TempProdNr;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.11.2015
 * Time: 6:59
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HarnessCheckReportVM {

    @WireVariable
    private ProdNrLogManager prodNrLogManager;

    @WireVariable
    private WorkplaceManager workplaceManager;

    @WireVariable
    private Jitpab62Manager jitpab62Manager;

    @WireVariable
    private PrnrManager prnrManager;

    @WireVariable
    private Lpab64Manager lpab64Manager;

    @WireVariable
    private Lpab62Manager lpab62Manager;

    @WireVariable
    private Jitpab64Manager jitpab64Manager;

    @WireVariable
    private GroupDeriveManager groupDeriveManager;

    @WireVariable
    private NacharbeitManager nacharbeitManager;

    @WireVariable
    private SchrittModulRm9X1WrmManager schrittManager;


    @Wire
    Radiogroup rg;

    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;
    private Workplace selectedWorkplace;
    private List<Workplace> workplaceList;
    private List<HarnessCheckObj> harnessCheckList;
    private Integer countFlAll;
    private Integer countFlSperre;
    private Integer countSeriaAll;
    private Integer countSeriaSperre;
    private String ksKz;

    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, false);
        workplaceList = workplaceManager.findStatusNotNull();
        //System.out.print(jitpab62Manager.test("20160101","20160229","9P3.974.00"));
        /*
        File file;
        try {
            file = new File("C:/test/report.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Prod. Nr.; Prod. Date.;Folge;Customer;Moduls \n");
            for (TempProdNr item : jitpab62Manager.test("20160101","20160229","9P3.974.000")){
                bw.write(item.getProdNr()+";"+item.getLiefDatum()+";"+
                        item.getFolge()+";"+item.getCust()+";"
                );
                for (String modul : item.getModulList()){
                    bw.write(modul + ";");
                }
                bw.write(";\n");
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
       */
       /*
        List<Prnr> prnrList = prnrManager.getPrnrList("F");
        List<SchulzObj> schulzObjList = new ArrayList<>();
        for (Prnr prnr : prnrList){
            SchulzObj so = new SchulzObj(prnr.getProdNr(), prnr.getKabelsatzKz(), prnr.getBoardType(),prnr.getAusfuehrung(),
                    prnr.getReihfNr(),prnr.getCustomerNr(),prnr.getProdDate(), prnr.getProdGroup());
            schulzObjList.add(so);
        }
        for (SchulzObj so: schulzObjList){
            List<Lpab64> lpab64List = lpab64Manager.findByProdnrAndKabelsatz(so.getProdNr(),"F");
            if (lpab64List.isEmpty()){
                lpab64List = jitpab64Manager.getJitpab64(so.getProdNr());
            }
            so.setModulList(lpab64List);
        }
        File file;
        try {
            file = new File("C:/test/report.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Prod. Nr.; Ks Kz;Ausf;Prod group;Board;Kunde;Lieferant;Reihenf;Sach nr best;Sach nr lief;Assembly level;Workplace \n");
            for (SchulzObj item : schulzObjList){

                for (Lpab64 modul : item.getModulList()){

                    List<SchrittModulRm9X1Wrm> schrittModulRm9X1WrmList = schrittManager.findBySachNrLieferant(modul.getSachNrLieferant());
                    String levelToWrite = null;
                    String workplace = null;
                    for (SchrittModulRm9X1Wrm schritt : schrittModulRm9X1WrmList){
                        bw.write(item.getProdNr()+";"+item.getKsKz()+";"+
                                item.getAusf()+";"+item.getPrgrp()+ ";"+item.getBoardType()+";"+
                                item.getKdnr()+";"+item.getLiefdkd()+";"+item.getReihenf()+";"
                        );
                        bw.write(modul.getSachNrBest().trim() + ";" + modul.getSachNrLieferant().trim()+";");
                        levelToWrite = schritt.getAssLevelSeq().toString();
                        workplace = schritt.getSchritt().trim() + schritt.getSeite().trim();
                        bw.write(levelToWrite+ ";"+workplace +  ";");
                        bw.write(";\n");
                    }

                }

            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

       /*List<Prnr> prnrList = prnrManager.getPrnrList("F");
       List<SchulzObj> schulzObjList = new ArrayList<>();
        for (Prnr prnr : prnrList){
            SchulzObj so = new SchulzObj(prnr.getProdNr(), prnr.getKabelsatzKz(), prnr.getBoardType(),prnr.getAusfuehrung(),
                    prnr.getReihfNr(),prnr.getCustomerNr(),prnr.getProdDate());
            schulzObjList.add(so);
        }
        for (SchulzObj so: schulzObjList){
            List<Lpab64> lpab64List = lpab64Manager.findByProdnrAndKabelsatz(so.getProdNr(),"F");
            so.setModulList(lpab64List);
        }*/


    }

    @Command
    @NotifyChange({"countFlAll","countFlSperre","countSeriaAll","countSeriaSperre","harnessCheckList"})
    public void submit()
    {   if(timeFrom!=null&&timeTo!=null&& dateFrom!=null && dateTo !=null && selectedWorkplace != null){
        String status = selectedWorkplace.getOperation().trim();
        ksKz = selectedWorkplace.getAlias().trim();
        List<Lpab62> lpab62List;
        List<Nacharbeit> nacharbeitList;
        harnessCheckList = new ArrayList<>();
        countFlAll = 0;
        countFlSperre = 0;
        countSeriaAll = 0;
        countSeriaSperre = 0;
        String statusWithCapLetter = status.substring(0,1).toUpperCase() + status.substring(1).trim();
        Method method =  null;
        try {
            method = Lpab62.class.getMethod("get" + statusWithCapLetter);
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
        }
        if (rg.getSelectedIndex() == 0){
            nacharbeitList = nacharbeitManager.findByDateStatusAndSperre(dateFrom,dateTo,timeFrom,timeTo,ksKz);
            System.out.println("najdenych zaznamov nacharbeit: "+nacharbeitList.size());
            for (Nacharbeit na : nacharbeitList){
                String program = null;
                String programDesc = null;
                List<Lpab64> lpab64List = lpab64Manager.findGrundByProdnrAndKabelsatz(na.getProdNr(), na.getKabelsatzKz());
                if (!lpab64List.isEmpty()){
                    List<ProdGroup> prodGroupList = groupDeriveManager.deriveProdGroup(lpab64List.get(0).getSachNrBest().trim());
                    if (!prodGroupList.isEmpty()) {program = prodGroupList.get(0).getProgramMark();
                                                   programDesc = prodGroupList.get(0).getProgramDesc();
                    }
                }
                if(program != null && program.equals("G")) countSeriaSperre++;
                if(program != null && program.equals("H")) countFlSperre++;
                HarnessCheckObj harnessCheckObj = new HarnessCheckObj(na.getProdNr(), na.getKabelsatzKz(),
                        na.getLogdate(),na.getLogtime(),program,programDesc,na.getMode());
                harnessCheckList.add(harnessCheckObj);

            }
        }
        else if (rg.getSelectedIndex() == 1){
            lpab62List = lpab62Manager.findHarness(status, dateFrom+timeFrom, dateTo+timeTo,ksKz);
            for (Lpab62 lpab62 : lpab62List){
                String statusLpab62 = null;
                String program = null;
                String programDesc = null;
                List<Lpab64> lpab64List = lpab64Manager.findGrundByProdnrAndKabelsatz(lpab62.getProdNr(), lpab62.getKabelsatzKz());
                if (!lpab64List.isEmpty()){
                    List<ProdGroup> prodGroupList = groupDeriveManager.deriveProdGroup(lpab64List.get(0).getSachNrBest().trim());
                    if (!prodGroupList.isEmpty()) {program = prodGroupList.get(0).getProgramMark();
                        programDesc = prodGroupList.get(0).getProgramDesc();
                    }
                }
                try {
                    statusLpab62 = (String) method.invoke(lpab62);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if(program != null && program.equals("G")) countSeriaAll++;
                if(program != null && program.equals("H")) countFlAll++;
                HarnessCheckObj harnessCheckObj = new HarnessCheckObj(lpab62.getProdNr(), lpab62.getKabelsatzKz(),
                        statusLpab62.substring(0,8),statusLpab62.substring(8),program,programDesc,"OK");
                harnessCheckList.add(harnessCheckObj);
            }

            nacharbeitList = nacharbeitManager.findByDateStatusAndSperre(dateFrom,dateTo,timeFrom,timeTo,ksKz);
            for (Nacharbeit na : nacharbeitList){
                String program = null;
                String programDesc = "";
                List<Lpab64> lpab64List = lpab64Manager.findGrundByProdnrAndKabelsatz(na.getProdNr(), na.getKabelsatzKz());
                if (!lpab64List.isEmpty()){
                    List<ProdGroup> prodGroupList = groupDeriveManager.deriveProdGroup(lpab64List.get(0).getSachNrBest().trim());
                    if (!prodGroupList.isEmpty()) {program = prodGroupList.get(0).getProgramMark();
                        programDesc = prodGroupList.get(0).getProgramDesc();}
                }
                if(program != null && program.equals("G")) countSeriaSperre++;
                if(program != null && program.equals("H")) countFlSperre++;
                HarnessCheckObj harnessCheckObj = new HarnessCheckObj(na.getProdNr(), na.getKabelsatzKz(),
                        na.getLogdate(),na.getLogtime(),program,programDesc,na.getMode());
                harnessCheckList.add(harnessCheckObj);

            }
        }

        else if (rg.getSelectedIndex() == 2){
             lpab62List = lpab62Manager.findHarness(status, dateFrom+timeFrom, dateTo+timeTo,ksKz);
            System.out.print(lpab62List.size());
            for (Lpab62 lpab62 : lpab62List){
                 String statusLpab62 = null;
                 String program = null;
                 String programDesc = null;
                 List<Lpab64> lpab64List = lpab64Manager.findGrundByProdnrAndKabelsatz(lpab62.getProdNr(), lpab62.getKabelsatzKz());
                 if (!lpab64List.isEmpty()){
                    List<ProdGroup> prodGroupList = groupDeriveManager.deriveProdGroup(lpab64List.get(0).getSachNrBest().trim());
                    if (!prodGroupList.isEmpty()) {program = prodGroupList.get(0).getProgramMark();
                        programDesc = prodGroupList.get(0).getProgramDesc();
                    }
                 }
                 try {
                     statusLpab62 = (String) method.invoke(lpab62);
                 } catch (IllegalAccessException e) {
                     e.printStackTrace();
                 } catch (InvocationTargetException e) {
                     e.printStackTrace();
                 }
                 if(program != null && program.equals("G")) countSeriaAll++;
                 if(program != null && program.equals("H")) countFlAll++;
                 HarnessCheckObj harnessCheckObj = new HarnessCheckObj(lpab62.getProdNr(), lpab62.getKabelsatzKz(),
                         statusLpab62.substring(0,8),statusLpab62.substring(8),program,programDesc,"OK");
                 harnessCheckList.add(harnessCheckObj);
             }
        }

    }
    else{ Messagebox.show("Zle vyplnene udaje!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    public void exportToExcel(){

        File xslFile = prodNrLogManager.exportToFile(harnessCheckList);
        byte[] buffer = new byte[(int) xslFile.length()];
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(xslFile);

        fs.read(buffer);
        fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        AMedia amedia = new AMedia("HarnessCheckReport.csv", "csv", "application/file", is);
        Filedownload.save(amedia);
    }

    @Command
    @NotifyChange({"timeFrom","timeTo","dateFrom","dateTo","selectedWorkplace","harnessCheckList"})
    public void reset(){
        dateFrom = "";
        dateTo = "";
        timeFrom = "";
        timeTo = "";
        selectedWorkplace = null;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

  /*public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }      */

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public Workplace getSelectedWorkplace() {
        return selectedWorkplace;
    }

    public void setSelectedWorkplace(Workplace selectedWorkplace) {
        this.selectedWorkplace = selectedWorkplace;
    }

    public List<Workplace> getWorkplaceList() {
        return workplaceList;
    }

    public void setWorkplaceList(List<Workplace> workplaceList) {
        this.workplaceList = workplaceList;
    }

    public List<HarnessCheckObj> getHarnessCheckList() {
        return harnessCheckList;
    }

    public void setHarnessCheckList(List<HarnessCheckObj> harnessCheckList) {
        this.harnessCheckList = harnessCheckList;
    }

    public Integer getCountSeriaSperre() {
        return countSeriaSperre;
    }

    public void setCountSeriaSperre(Integer countSeriaSperre) {
        this.countSeriaSperre = countSeriaSperre;
    }

    public Integer getCountSeriaAll() {
        return countSeriaAll;
    }

    public void setCountSeriaAll(Integer countSeriaAll) {
        this.countSeriaAll = countSeriaAll;
    }

    public Integer getCountFlSperre() {
        return countFlSperre;
    }

    public void setCountFlSperre(Integer countFlSperre) {
        this.countFlSperre = countFlSperre;
    }

    public Integer getCountFlAll() {
        return countFlAll;
    }

    public void setCountFlAll(Integer countFlAll) {
        this.countFlAll = countFlAll;
    }
}

