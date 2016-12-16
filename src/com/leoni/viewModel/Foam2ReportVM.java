package com.leoni.viewModel;

import com.leoni.data.manager.ProdNrLogManager;
import com.leoni.data.models.ProdNrLog;
import com.leoni.data.models.nonDBmodels.FoamReportObj;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.proxy.MapProxy;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hrmi1005 on 25. 4. 2016.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Foam2ReportVM {


    @WireVariable
    private ProdNrLogManager prodNrLogManager;

    private Date date = new Date();
    private String result;
    private String dateString;
    private Map<String, List<String>> testerProdnrs;
    private  List<FoamReportObj> foamReportObjList;


    @Init
    public void init() {

    }

    @Command
    @NotifyChange({"result", "timeFrom", "timeTo", "dateString", "foamReportObjList"})
    public void submit() {
           DateFormat df = new SimpleDateFormat("yyyyMMdd");
           dateString =  df.format(date);
            Map<String, Set<String>> testerProdnrs = new HashMap<>();
           List<ProdNrLog> prodNrLogList = prodNrLogManager.getProdNrLogByDate(dateString);

        for (ProdNrLog prodNrLog : prodNrLogList){
                if (testerProdnrs.containsKey(prodNrLog.getTesterId().trim())){
                    Set<String> prodnrSet = testerProdnrs.get(prodNrLog.getTesterId().trim());
                    prodnrSet.add(prodNrLog.getProdNr().trim());
                    testerProdnrs.put(prodNrLog.getTesterId().trim(),prodnrSet);
                }
                else {
                    Set<String> prodnrSet = new HashSet<>();
                    prodnrSet.add(prodNrLog.getProdNr().trim());
                    testerProdnrs.put(prodNrLog.getTesterId().trim(),prodnrSet);
                }
           }
        System.out.println(testerProdnrs);
        foamReportObjList = new ArrayList<>();
        for (String key : testerProdnrs.keySet()) {
            FoamReportObj foamReportObj= new FoamReportObj();
            foamReportObj.setTesterId(key);
            foamReportObj.setCount(testerProdnrs.get(key).size());
            foamReportObjList.add(foamReportObj);
            System.out.println("Key = " + key);

        }
        }

    public List<FoamReportObj> getFoamReportObjList() {
        return foamReportObjList;
    }

    public void setFoamReportObjList(List<FoamReportObj> foamReportObjList) {
        this.foamReportObjList = foamReportObjList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}