package com.leoni.data.manager;

import com.leoni.data.criterion.Between;
import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.models.ProdNrLog;
import com.leoni.data.models.nonDBmodels.HarnessCheckObj;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 6.6.2014
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */
@Service("prodNrLogManager")
public class ProdNrLogManagerImpl extends GenericManagerImpl<ProdNrLog> implements ProdNrLogManager{

    public ProdNrLog saveProdNrLog(String prodNr, String kabelsatzKz, String tableId, String testerId, String mode, String logText) {
        ProdNrLog prodNrLog = new ProdNrLog();
        prodNrLog.setProdNr(prodNr);
        prodNrLog.setKabelsatzKz(kabelsatzKz);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
        prodNrLog.setLogdate(sdf.format(date));
        prodNrLog.setLogtimer(sdf2.format(date));
        prodNrLog.setTableId(tableId);
        prodNrLog.setTesterId(testerId);
        prodNrLog.setMode(mode);
        prodNrLog.setLogtext(logText);
        return create(prodNrLog);
    }

    @Override
    public List<ProdNrLog> getProdNrLogPersNrAndProdNr(String date, String timeFrom, String timeTo, String persnr) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("logdate", date.trim()));
        caList.add(new Between("logtimer",timeFrom.trim(),timeTo.trim()));
        caList.add(new Equal("tableId", "F9X1_SCHAUM_STA"));
        if(persnr != null){caList.add(new Equal("testerId", persnr.trim()));}
        return find(caList);
    }

    @Override
    public Map<String, Set<String>> getWorkplace(Map<String, Set<String>> prodnrLogMap,String date, String timeFrom, String timeTo) {
        for (Map.Entry entry : prodnrLogMap.entrySet()) {
            String prodnr = (String) entry.getKey();
            List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
            caList.add(new Equal("logdate", date.trim()));
            caList.add(new Between("logtimer",timeFrom.trim(),timeTo.trim()));
            caList.add(new Equal("tableId", "F9X1_SCHAUMEN"));
            caList.add(new Equal("prodNr", prodnr.trim()));
            Set<String> wpSet = new HashSet<>();
            for(ProdNrLog prodNrLog : find(caList)){
                System.out.println(prodNrLog.getTesterId());
                wpSet.add(prodNrLog.getTesterId().trim());
            }
            if(prodnrLogMap.containsKey(prodnr.trim())){
                Set tempSet = (Set) entry.getValue();
                System.out.println("bbbb"+tempSet);
                tempSet.addAll(wpSet);
                prodnrLogMap.put(prodnr.trim(),tempSet);
            }
            else prodnrLogMap.put(prodnr.trim(),wpSet);
        }
        return  prodnrLogMap;
    }

    @Override
    public File exportToFile(Map<String, Set<String>> prodnrLogMap, String persnr,String date, String timeFrom, String timeTo) {
        File file;
        try {
            file = new File("report.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Os. cislo:;"+persnr+"\n");
            bw.write("Datum: "+date+";"+" Od: "+timeFrom+" do: "+timeTo+"\n");
            bw.write("Prod. Nr.; Workplace \n");
            for (Map.Entry entry : prodnrLogMap.entrySet()){
                bw.write(entry.getKey()+";");
                for(String wp : (Set<String>) entry.getValue()){
                    bw.write(wp+";");
                }
                bw.write("\n");
            }
            bw.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @Override
    public List<ProdNrLog> getProdNrLogProdNrAndKsKz(String prodNr, String ksKz) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("prodNr", prodNr));
        caList.add(new Equal("kabelsatzKz", ksKz));
        return find(caList);
    }

    @Override
    public List<ProdNrLog> getProdNrLogForHarness(String timeFrom/*, String dateFrom*/, String timeTo, String date,
                                                  String workplaceName, String workplaceMode) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Between("logtimer", timeFrom,timeTo));
        //caList.add(new Between("logdate", dateFrom, dateTo));
        caList.add(new Equal("logdate", date));
        caList.add(new Equal("tableId", workplaceName.trim()));
        caList.add(new Equal("mode", workplaceMode.trim()));
        return find(caList);
    }

    @Override
    public File exportToFile(List<HarnessCheckObj> harnessCheckObjList) {
        File file;
        try {
            file = new File("report.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Prod. Nr.; Kabelsatz;Date;Time;Program; Status;\n");
            for (HarnessCheckObj item : harnessCheckObjList){
                bw.write(item.getProdNr()+";"+item.getKsKz()+";"+
                        item.getDate()+";"+item.getTime()+";"+item.getProgram()+";"+item.getStatus()+";"
                );

                bw.write(";\n");
            }
            bw.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @Override
    public List<ProdNrLog> getProdNrLogByDate(String dateString) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("logdate", dateString));
        caList.add(new Equal("tableId", "F9X1_SCHAUM_STA"));
        return find(caList);
    }
}
