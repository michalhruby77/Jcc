package com.leoni.viewModel;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 25.8.2015
 * Time: 6:51
 * To change this template use File | Settings | File Templates.
 */

import com.leoni.data.manager.GroupDeriveManager;
import com.leoni.data.manager.Lpab64Manager;
import com.leoni.data.manager.dab.PrnrManager;
import com.leoni.data.manager.oldJIT.JitAbruManager;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.ProdGroup;
import com.leoni.data.models.nonDBmodels.ReportResultObj;
import com.leoni.data.models.nonDBmodels.ReportTableRecord;
import org.apache.commons.io.IOUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 19.11.2014
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ReportDateVM {

    @WireVariable
    private JitAbruManager jitAbruManager;

    @WireVariable
    private PrnrManager prnrManager;

    @WireVariable
    private Lpab64Manager lpab64Manager;

    @WireVariable
    private GroupDeriveManager groupDeriveManager;


    private Date date;
    private String dateString;



    @Init
    public void init(){
        date = new Date();
    }

    @Command
    @NotifyChange({"date"})
    public void submit()
    {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        dateString = df.format(date);
        List<String> prodNrList = jitAbruManager.getProdNrList(dateString);
        if(!prodNrList.isEmpty()){
        List<ReportResultObj> reportResultList = prnrManager.getPrnrByProdNrList(prodNrList);
        if(!reportResultList.isEmpty()){
        Set<ReportTableRecord> reportTableRecordSet = new HashSet<>();
        String stainSubStr = "";
        for (ReportResultObj item : reportResultList){
            if(item.getStatusIn() != null){
            stainSubStr = item.getStatusIn().substring(8,12);}
            String stainString = "";
            if (stainSubStr.startsWith("0")||stainSubStr.startsWith("10")||stainSubStr.startsWith("11")||
                    stainSubStr.startsWith("12")||stainSubStr.startsWith("13")){
                stainString = item.getStatusIn().substring(0,8) + "/" + "r";
            }

            else{
                stainString = item.getStatusIn().substring(0,8) + "/" + "p";
            }
            ReportTableRecord reportTableRecord = new ReportTableRecord(dateString,stainString,item.getCustomer(),item.getProdDate());
            if (!reportTableRecordSet.contains(reportTableRecord)){
                List<String> prodNrListTemp = new ArrayList<>();
                prodNrListTemp.add(item.getProdNr());
                reportTableRecord.setProdNrList(prodNrListTemp);
                reportTableRecordSet.add(reportTableRecord);
            }
            else{
                for (ReportTableRecord reportTableRecord2: reportTableRecordSet){
                    if(stainString.trim().equals(reportTableRecord2.getStaIn().trim()) &&
                       item.getCustomer().trim().equals(reportTableRecord2.getCustomerNr().trim()) &&
                       item.getProdDate().trim().equals(reportTableRecord2.getProdDate().trim())){
                       reportTableRecord2.getProdNrList().add(item.getProdNr());
                    }
                }
            }
        }
            for (ReportTableRecord record : reportTableRecordSet){
                int flCounter = 0;
                int ll991Counter = 0;
                int rl991Counter = 0;
                int ll981Counter = 0;
                int rl981Counter = 0;
                int cupCounter = 0;
                for  (String prodNr : record.getProdNrList()){
                      List<Lpab64> modulsList = lpab64Manager.findGrundByProdnrAndKabelsatz(prodNr.trim(), "F");
                      if (!modulsList.isEmpty()){
                           String sachNrBest = modulsList.get(0).getSachNrBest().trim();
                           List<ProdGroup> prodGroupList = groupDeriveManager.deriveProdGroup(sachNrBest);
                           if (!prodGroupList.isEmpty()){
                                ProdGroup prodGroup = prodGroupList.get(0);
                                /*if (prodGroup.getProdGroup().trim().equals()) flCounter++;
                                if () ll991Counter++;
                                if () rl991Counter++;
                                if () ll981Counter++;
                                if () rl981Counter++;
                                if () cupCounter++;*/
                           }
                           else Messagebox.show("Nenasiela sa grupa v prodgroup pre " + prodNr + "!", "Chyba!", Messagebox.OK, Messagebox.ERROR);
                      }
                      else Messagebox.show("Nenasiel sa grund v lpab64 pre " + prodNr + "!", "Chyba!", Messagebox.OK, Messagebox.ERROR);
                }
                record.setNrOfFL(flCounter);
                record.setNrOf991LL(ll991Counter);
                record.setNrOf991RL(rl991Counter);
                record.setNrOf981LL(ll981Counter);
                record.setNrOf981RL(rl981Counter);
                record.setNrOfCUP(cupCounter);


                System.out.println(record.getStaIn() + " " + record.getCustomerNr() + " " + record.getProdDate() +
                /*record.getProdNrList()+*/ " " + record.getNrOfFL()+ " " + record.getNrOf991LL()+ " " + record.getNrOf981LL()
                        + " " + record.getNrOf991RL()+ " " + record.getNrOf981RL()+ " " + record.getNrOfCUP());
            }
         }
         else Messagebox.show("Ziadny zaznam v tabulke prnr!", "Chyba!", Messagebox.OK, Messagebox.ERROR);
        }
        else Messagebox.show("Ziadny zaznam v tabulke JitAbru!", "Chyba!", Messagebox.OK, Messagebox.ERROR);

        date = new Date();
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

