package com.leoni.viewModel;

import com.leoni.data.manager.ProdNrLogManager;
import com.leoni.data.models.ProdNrLog;
import org.apache.commons.io.IOUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 29.5.2015
 * Time: 8:40
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FoamReportVM {

    @WireVariable
    private ProdNrLogManager prodNrLogManager;

    private Date date= new Date();
    private String timeFrom;
    private String timeTo;
    private String result;
    private String dateString;
    private String persNr;
    private boolean persNrBool;


    @Init
    public void init(){
        persNr = null;
        persNrBool = false;
    }

    @Command
    @NotifyChange({"result","timeFrom","timeTo","dateString","persNr"})
    public void submit()
    {   if(timeFrom!=null&&timeTo!=null){
        //Map<String,Map<String,Set<String>>> pracMap = new HashMap<>();
        Map<String, Set<String>> prodNrMap  = new HashMap<>();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        dateString = df.format(date);
        System.out.println("date = " + dateString + "timefrom= "+timeFrom.trim()+" timeto= "+timeTo.trim()+ "persnr "+persNr);
        List<ProdNrLog> prodNrLogList = prodNrLogManager.getProdNrLogPersNrAndProdNr(dateString,timeFrom,timeTo,persNr);
        for (ProdNrLog prodNrLog : prodNrLogList){

            String testerId = prodNrLog.getTesterId().trim();
            String prodNr = prodNrLog.getProdNr().trim();
            //System.out.println(testerId + " "  + prodNr);
            prodNrMap.put(prodNr,new HashSet<String>());
            /*if (!pracMap.containsKey(testerId)){
                Map<String, Set<String>> prodNrMap = new HashMap<>();
                prodNrMap.put(prodNr, new HashSet<String>());
                pracMap.put(testerId,prodNrMap);
            }
            else{
                Map prodnrMap = pracMap.get(testerId);
                if(!prodnrMap.containsKey(prodNr)){
                   prodnrMap.put(prodNr, new HashSet<String>());
                }
            }   */
        }
        prodNrLogManager.getWorkplace(prodNrMap, dateString, timeFrom,timeTo);
        for (Map.Entry entry : prodNrMap.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

        File xslFile = prodNrLogManager.exportToFile(prodNrMap,persNr);
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
        AMedia amedia = new AMedia(persNr+".csv", "csv", "application/file", is);
        Filedownload.save(amedia);

        timeFrom = "";
        timeTo = "";
        dateString = "";
        persNr = "";
    }
    else{ Messagebox.show("Zle vyplnene udaje!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    @NotifyChange({"persNrBool"})
    public void checkPersnr()
    {
        if (persNrBool) persNrBool = false;
        else  persNrBool = true;
    }


        public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPersNr() {
        return persNr;
    }

    public void setPersNr(String persNr) {
        this.persNr = persNr;
    }

    public boolean isPersNrBool() {
        return persNrBool;
    }

    public void setPersNrBool(boolean persNrBool) {
        this.persNrBool = persNrBool;
    }
}
