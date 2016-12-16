package com.leoni.viewModel;

import com.leoni.data.manager.LwadrKennManager;
import com.leoni.data.manager.NacharbeitManager;
import com.leoni.data.models.LwadrKenn;
import com.leoni.data.models.Nacharbeit;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 19.1.2015
 * Time: 8:56
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class NacharbeitViewVM {

    @WireVariable
    private LwadrKennManager lwadrKennManager;

    @WireVariable
    private NacharbeitManager nacharbeitManager;


    private String prodNr;
    private String selectedKs;
    private Set<String> kabelsatzList;
    private String result;
    private String selectedKs2;
    private String dateFrom;
    private String dateTo;
    private boolean generateProdNrVis = true;
    private boolean generateDateVis = false;
    private List<String> modusList;
    private String selectedModus;
    private List<Nacharbeit> nacharbeitList;

    @AfterCompose
    public void doAfterCompose()
    {
        kabelsatzList = new HashSet<String>();
        for (LwadrKenn item : lwadrKennManager.getAll()){
            kabelsatzList.add(item.getKabelsatzKz().trim());
        }
        modusList = new ArrayList<>();
        modusList.add("nacharbeit");
        modusList.add("sperre");
        modusList.add("*");
    }

    @Command
    @NotifyChange({"kabelsatzList","generateProdNrVis","generateDateVis"})
    public void generateProdNr(){
        generateProdNrVis = true;
        generateDateVis = false;
    }

    @Command
    @NotifyChange({"generateProdNrVis","generateDateVis"})
    public void generateDate(){
        generateProdNrVis = false;
        generateDateVis = true;
    }

    @Command
    @NotifyChange({"nacharbeitList","groupList","kabelsatzList","prodDateList","generateProdNrVis","generateBatchVis",
            "prodNr", "selectedKs", "selectedKs2", "selectedCustomer", "selectedGroup", "selectedProdDate", "result"})
    public void submit(){
        if(generateProdNrVis){
           nacharbeitList = nacharbeitManager.findByProdNrAndKs(prodNr,selectedKs,selectedModus);
        }
        if(!generateProdNrVis){
            nacharbeitList = nacharbeitManager.findByDate(dateFrom, dateTo, selectedKs2, selectedModus);
        }
    }

    @Command
    public void exportToExcel() throws Exception{

        File xslFile = nacharbeitManager.exportToFile(nacharbeitList);
        byte[] buffer = new byte[(int) xslFile.length()];
        FileInputStream fs = new FileInputStream(xslFile);
        fs.read(buffer);
        fs.close();
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        AMedia amedia = new AMedia("Nacharbeit Report.csv", "csv", "application/file", is);
        Filedownload.save(amedia);
    }

   /* @Command
    public void test() {
        List<Nacharbeit> nacharbeits = nacharbeitManager.getAll610();
        for (Nacharbeit nacharbeit : nacharbeits){
            if(nacharbeit.getVsVodic1()!=null)
            {String number = nacharbeit.getVsVodic1().replaceAll("\\D+", "");
            if (!number.trim().isEmpty()){
            TestTab testTab = testManager.test(number);
            Double strToAdd = null;
            if(testTab!=null)strToAdd = testTab.getStripping1();
            String newString = nacharbeit.getFehlerText1();
            if(strToAdd!=null){
            newString = newString + " Odiz. " + strToAdd + " mm";
            nacharbeit.setFehlerText1(newString);}
                if(nacharbeit.getVsVodic2()!=null)
                {String number2 = nacharbeit.getVsVodic2().replaceAll("\\D+", "");
                if (!number2.trim().isEmpty()){
                    TestTab testTab2 = testManager.test(number2);
                    Double strToAdd2 = null;
                    if(testTab2!=null)strToAdd2 = testTab2.getStripping1();
                    //Double strToadd2 = testManager.test(number2).getStripping1();
                    String newString2 = nacharbeit.getFehlerText2();
                    if(strToAdd2!=null){
                    newString2 = newString2 + " Odiz. " + strToAdd2  + " mm";
                    nacharbeit.setFehlerText2(newString2);}}}
                nacharbeitManager.saveOrUpdate(nacharbeit);} }
        }
    }       */

    public String getProdNr() {
        return prodNr;
    }

    public void setProdNr(String prodNr) {
        this.prodNr = prodNr;
    }

    public String getSelectedKs() {
        return selectedKs;
    }

    public void setSelectedKs(String selectedKs) {
        this.selectedKs = selectedKs;
    }

    public Set<String> getKabelsatzList() {
        return kabelsatzList;
    }

    public void setKabelsatzList(Set<String> kabelsatzList) {
        this.kabelsatzList = kabelsatzList;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSelectedKs2() {
        return selectedKs2;
    }

    public void setSelectedKs2(String selectedKs2) {
        this.selectedKs2 = selectedKs2;
    }

    public String getDateFrom() {
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
    }

    public boolean isGenerateProdNrVis() {
        return generateProdNrVis;
    }

    public void setGenerateProdNrVis(boolean generateProdNrVis) {
        this.generateProdNrVis = generateProdNrVis;
    }

    public boolean isGenerateDateVis() {
        return generateDateVis;
    }

    public void setGenerateDateVis(boolean generateDateVis) {
        this.generateDateVis = generateDateVis;
    }

    public List<String> getModusList() {
        return modusList;
    }

    public void setModusList(List<String> modusList) {
        this.modusList = modusList;
    }

    public String getSelectedModus() {
        return selectedModus;
    }

    public void setSelectedModus(String selectedModus) {
        this.selectedModus = selectedModus;
    }

    public List<Nacharbeit> getNacharbeitList() {
        return nacharbeitList;
    }

    public void setNacharbeitList(List<Nacharbeit> nacharbeitList) {
        this.nacharbeitList = nacharbeitList;
    }
}
