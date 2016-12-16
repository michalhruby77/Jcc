package com.leoni.viewModel;

import com.leoni.data.manager.*;
import com.leoni.data.models.Customer;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.ProdGroup;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 18.8.2014
 * Time: 8:13
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InsertNewHarnessVM {

    @WireVariable
    private CustomerManager customerManager;

    @WireVariable
    private Lpab62Manager lpab62Manager;

    @WireVariable
    private Lpab64Manager lpab64Manager;

    @WireVariable
    private ExcelDocumentManager excelDocumentManager;

    @WireVariable
    private ModulsManager modulsManager;

    @WireVariable
    private GroupDeriveManager groupDeriveManager;

    @WireVariable
    private PdfManager pdfManager;

    @WireVariable
    private PrintingManager printingManager;

    private List<Customer> customerList;
    private String prodNr;
    private Customer customer;
    private String ausfuehrung = null;
    private String prodGruppe = null;
    private Date lieferDatum;
    private String reihenfNr;
    private List<CustomRow> customRowList = new ArrayList<CustomRow>();
    private Integer modulsF = 0;
    private Integer modulsC = 0;
    private Integer modulsT = 0;
    private Integer modulsU = 0;
    private Integer modulsE = 0;
    private Integer modulsR = 0;
    private Integer grundF = 0;
    private Integer grundC = 0;
    private Integer grundT = 0;
    private Integer grundU = 0;
    private Integer grundE = 0;
    private Integer grundR = 0;
    private Boolean grundFvis = false;
    private Boolean grundCvis = false;
    private Boolean grundTvis = false;
    private Boolean grundUvis = false;
    private Boolean grundEvis = false;
    private Boolean grundRvis = false;
    private List<String> definedOrder = Arrays.asList("F", "C", "T", "U", "E", "R");
    private List<Moduls> modulsList;
    private List<ProdGroup> prodGroupList;
    //----------------------------------
    /*String patternLLF = "9P[39]\\.97[4]\\.(000|177)"; //[0-9]{3}";
    String patternRLF = "9P[40]\\.97[4]\\.000"; //[0-9]{3};
    String patternLLC = "9P[39]\\.97[0]\\.[0-9]{3}";
    String patternRLC = "9P[40]\\.97[0]\\.[0-9]{3}";
    String patternT = "9P[0349]\\.97[1]\\.0(29|30)";
    String patternU = "9P[0349]\\.97[1]\\.6(87|88)";
    String patternE = "9P[0349]\\.97[1]\\.035";
    String patternR = "9P[0349]\\.97[1]\\.036";
    Pattern LLF = Pattern.compile(patternLLF);
    Pattern RLF = Pattern.compile(patternRLF);
    Pattern LLC = Pattern.compile(patternLLC);
    Pattern RLC = Pattern.compile(patternRLC);
    Pattern T = Pattern.compile(patternT);
    Pattern U = Pattern.compile(patternU);
    Pattern E = Pattern.compile(patternE);
    Pattern R = Pattern.compile(patternR);*/
    //--------------------------------
    @AfterCompose
    public void init()
    {
        customerList=customerManager.getAll();
        customer = customerList.get(0);
    }

    @Command
    @NotifyChange({"prodNr","customer","prodGruppe","ausfuehrung","lieferDatum","reihenfNr"})
    public void submit(@ContextParam(ContextType.VIEW)Component view, @BindingParam("rows") Rows rows )
    {
     if (prodNr!=null&&customer!=null&&prodGruppe!=null&&ausfuehrung!=null&&lieferDatum!=null){
     //if(lpab62Manager.findByProdnrAndCustomer(String.valueOf(prodNr), String.valueOf(customer.getNumber())).isEmpty()){
     SimpleDateFormat sdf = new SimpleDateFormat("yyyyyMMdd");
     Integer lieferDatumInt  = Integer.valueOf(sdf.format(lieferDatum));
     String ksToAdd = "";
     if (grundF==1 && lpab62Manager.findByProdnrAndCustomerAndKsKz(String.valueOf(prodNr), String.valueOf(customer.getNumber()),"F").isEmpty()) {int rowsAdded62 = lpab62Manager.addHarnessF(prodNr,customer.getNumber(),prodGruppe,ausfuehrung,lieferDatumInt,reihenfNr);
         ksToAdd = ksToAdd+"F";}
     if (grundC==1 && lpab62Manager.findByProdnrAndCustomerAndKsKz(String.valueOf(prodNr), String.valueOf(customer.getNumber()),"C").isEmpty()) {int rowsAdded62 = lpab62Manager.addHarnessC(prodNr, customer.getNumber(), prodGruppe, ausfuehrung, lieferDatumInt, reihenfNr);
         ksToAdd = ksToAdd+"C";}
     if (grundT==1 && lpab62Manager.findByProdnrAndCustomerAndKsKz(String.valueOf(prodNr), String.valueOf(customer.getNumber()),"T").isEmpty()) {int rowsAdded62 = lpab62Manager.addHarnessT(prodNr, customer.getNumber(), prodGruppe, ausfuehrung, lieferDatumInt, reihenfNr);
         ksToAdd = ksToAdd+"T";}
     if (grundU==1 && lpab62Manager.findByProdnrAndCustomerAndKsKz(String.valueOf(prodNr), String.valueOf(customer.getNumber()),"U").isEmpty()) {int rowsAdded62 = lpab62Manager.addHarnessU(prodNr, customer.getNumber(), prodGruppe, ausfuehrung, lieferDatumInt, reihenfNr);
         ksToAdd = ksToAdd+"U";}
     if (grundE==1 && lpab62Manager.findByProdnrAndCustomerAndKsKz(String.valueOf(prodNr), String.valueOf(customer.getNumber()),"E").isEmpty()) {int rowsAdded62 = lpab62Manager.addHarnessE(prodNr, customer.getNumber(), prodGruppe, ausfuehrung, lieferDatumInt, reihenfNr);
         ksToAdd = ksToAdd+"E";}
     if (grundR==1 && lpab62Manager.findByProdnrAndCustomerAndKsKz(String.valueOf(prodNr), String.valueOf(customer.getNumber()),"R").isEmpty()) {int rowsAdded62 = lpab62Manager.addHarnessR(prodNr, customer.getNumber(), prodGruppe, ausfuehrung, lieferDatumInt, reihenfNr);
         ksToAdd = ksToAdd+"R";}
     int rowsAdded64 = lpab64Manager.addrecords(modulsList,prodNr,ksToAdd);
     //System.out.println("pocet pridanych riadkov do lpab64: " + rowsAdded64);
     prodNr = null;
     customer = customerList.get(0);
     prodGruppe = null;
     ausfuehrung = null;
     lieferDatum = null;
     reihenfNr = null;
     Components.removeAllChildren(rows);
     view.detach();
     /*}
         else{
         Messagebox.show("Produkcne cislo s danym zakaznikom uz existuje!", "Chyba.", Messagebox.OK, Messagebox.ERROR);} */
     }
        else{Messagebox.show("Zle zadane hodnoty!", "Chyba.", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    public void addModul(@BindingParam("rows") Rows rows)
    {
        addRow(rows);
    }

    @Command
    @NotifyChange({"prodNr","prodGruppe","ausfuehrung"})
    public void getModulsFromExcel(@BindingParam("event") UploadEvent event,@BindingParam("rows") Rows rows)
    {
        Media media = event.getMedia();
        Set<String> modulsString = excelDocumentManager.getSachNrBest(media);
        addFilledRows(rows,modulsString);
        prodNr = excelDocumentManager.getProdNr(media);
    }

    @Command
    @NotifyChange({"modulsF","modulsC","modulsT","modulsU","modulsE","modulsR","grundF","grundC","grundT","grundU",
            "grundE","grundR","grundFvis","grundCvis","grundTvis","grundUvis","grundEvis","grundRvis","prodGruppe",
            "ausfuehrung"})
    public void validate(@BindingParam("rows") Rows rows)
    {
        modulsF = 0;
        modulsC = 0;
        modulsT = 0;
        modulsU = 0;
        modulsE = 0;
        modulsR = 0;
        grundF = 0;
        grundC = 0;
        grundT = 0;
        grundU = 0;
        grundE = 0;
        grundR = 0;
        Components.removeAllChildren(rows);
        modulsList = new ArrayList<Moduls>();
        prodGroupList = new ArrayList<ProdGroup>();
        Comparator<CustomRow> comparator = new Comparator<CustomRow>(){

            public int compare(CustomRow o1, CustomRow o2) {
                if(o1.getSelectedModul() == null){
                    if(o2.getSelectedModul() == null)
                        return 0;
                    else
                        return 1;}
                else
                {if(o2.getSelectedModul() == null)
                    return -1;
                else

                return Integer.valueOf(
                        definedOrder.indexOf(o1.getSelectedModul().getKabelsatzKz().trim()))
                        .compareTo(
                                Integer.valueOf(
                                        definedOrder.indexOf(o2.getSelectedModul().getKabelsatzKz().trim()))); } //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        Collections.sort(customRowList,comparator);
        for (CustomRow item : customRowList){
            rows.appendChild(item);
            Moduls selectedModul;
            System.out.println("modul: "+item.getSelectedModul());
            if(item.getSelectedModul()!=null) {
                System.out.println("sach nr.: "+item.getSelectedModul().getSachNrBest()+"lief nr.: "+item.getSelectedModul().getSachNrLieferant());
                selectedModul = item.getSelectedModul();
                modulsList.add(selectedModul);
                if(selectedModul.getKabelsatzKz()!=null&&selectedModul.getKabelsatzKz().equals("F")){
                    modulsF++;
                    /*if (selectedModul.isGrund()){grundF++;
                        prodGruppe = selectedModul.getProdGruppe().trim();
                        ausfuehrung = selectedModul.getAusfuehrung().trim();
                    }*/
                    System.out.println("modul "+selectedModul.getSachNrBest());
                    System.out.println("grp derive "+groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(), customer).size());
                    if(groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(),customer).size()==1){
                        ProdGroup prodGroup = groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(), customer).get(0);
                        if(prodGroup.getKabelsatzKz().trim().equals("F")){grundF++;
                            prodGruppe = prodGroup.getProdGroup().trim();
                            ausfuehrung = prodGroup.getAusfuehrung().trim();
                        }
                    }
                }
                if(selectedModul.getKabelsatzKz()!=null&&selectedModul.getKabelsatzKz().equals("C")){
                    modulsC++;
                    if(groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(),customer).size()==1){
                        ProdGroup prodGroup = groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(), customer).get(0);
                        if(prodGroup.getKabelsatzKz().trim().equals("C")){grundC++;}
                    }
                }
                if(selectedModul.getKabelsatzKz()!=null&&selectedModul.getKabelsatzKz().equals("T")){
                    modulsT++;
                    if(groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(),customer).size()==1){
                        ProdGroup prodGroup = groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(), customer).get(0);
                        if(prodGroup.getKabelsatzKz().trim().equals("T")){grundT++;}
                    }
                }
                if(selectedModul.getKabelsatzKz()!=null&&selectedModul.getKabelsatzKz().equals("U")){
                    modulsU++;
                    if(groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(),customer).size()==1){
                        ProdGroup prodGroup = groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(), customer).get(0);
                        if(prodGroup.getKabelsatzKz().trim().equals("U")){grundU++;}
                    }
                }
                if(selectedModul.getKabelsatzKz()!=null&&selectedModul.getKabelsatzKz().equals("E")){
                    modulsE++;
                    if(groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(),customer).size()==1){
                        ProdGroup prodGroup = groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(), customer).get(0);
                        if(prodGroup.getKabelsatzKz().trim().equals("E")){grundE++;}
                    }
                }
                if(selectedModul.getKabelsatzKz()!=null&&selectedModul.getKabelsatzKz().equals("R")){
                    modulsR++;
                    if(groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(),customer).size()==1){
                        ProdGroup prodGroup = groupDeriveManager.deriveProdGroup(selectedModul.getSachNrBest().trim(), customer).get(0);
                        if(prodGroup.getKabelsatzKz().trim().equals("R")){grundR++;}
                    }
                }
            }
        }
        if(grundF==1)grundFvis=true;
        if(grundC==1)grundCvis=true;
        if(grundT==1)grundTvis=true;
        if(grundU==1)grundUvis=true;
        if(grundE==1)grundEvis=true;
        if(grundR==1)grundRvis=true;
        //if(prodGroupList)

          // System.out.println(pdfManager.createMontageListPdf(lpab62Manager.findByProdnrAndKabelsatz("2050192","F").get(0),modulsList));

    }

    private void addRow(Rows rows){
        CustomRow row = new CustomRow();
        rows.appendChild(row);
        customRowList.add(row);
        if(row.getSelectedModul()!=null&&row.getSelectedModul().isGrund()&&row.getSelectedModul().getKabelsatzKz().trim().equals("F")){
            prodGruppe = row.getSelectedModul().getProdGruppe().trim();
            ausfuehrung = row.getSelectedModul().getAusfuehrung().trim();
        }
    }

    private void addFilledRows(Rows rows, Set<String> modulsList){
        Components.removeAllChildren(rows);
        customRowList.clear();
        for(String modul : modulsList){
        CustomRow row = new CustomRow();
        row.setSachNrBest(modul.trim());
        row.createLiefCombo();
        row.submitSelectedModul();
        rows.appendChild(row);
        customRowList.add(row);
        if(row.getSelectedModul()!=null&&row.getSelectedModul().isGrund()&&row.getSelectedModul().getKabelsatzKz().trim().equals("F")){
           prodGruppe = row.getSelectedModul().getProdGruppe().trim();
           ausfuehrung = row.getSelectedModul().getAusfuehrung().trim();
        }
        }

    }


    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public String getProdNr() {
        return prodNr;
    }

    public void setProdNr(String prodNr) {
        this.prodNr = prodNr;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAusfuehrung() {
        return ausfuehrung;
    }

    public void setAusfuehrung(String ausfuehrung) {
        this.ausfuehrung = ausfuehrung;
    }

    public Date getLieferDatum() {
        return lieferDatum;
    }

    public void setLieferDatum(Date lieferDatum) {
        this.lieferDatum = lieferDatum;
    }

    public String getReihenfNr() {
        return reihenfNr;
    }

    public void setReihenfNr(String reihenfNr) {
        this.reihenfNr = reihenfNr;
    }

    public String getProdGruppe() {
        return prodGruppe;
    }

    public void setProdGruppe(String prodGruppe) {
        this.prodGruppe = prodGruppe;
    }

    public Integer getModulsF() {
        return modulsF;
    }

    public void setModulsF(Integer modulsF) {
        this.modulsF = modulsF;
    }

    public Integer getModulsC() {
        return modulsC;
    }

    public void setModulsC(Integer modulsC) {
        this.modulsC = modulsC;
    }

    public Integer getModulsT() {
        return modulsT;
    }

    public void setModulsT(Integer modulsT) {
        this.modulsT = modulsT;
    }

    public Integer getModulsU() {
        return modulsU;
    }

    public void setModulsU(Integer modulsU) {
        this.modulsU = modulsU;
    }

    public Integer getModulsE() {
        return modulsE;
    }

    public void setModulsE(Integer modulsE) {
        this.modulsE = modulsE;
    }

    public Integer getModulsR() {
        return modulsR;
    }

    public void setModulsR(Integer modulsR) {
        this.modulsR = modulsR;
    }

    public CustomerManager getCustomerManager() {
        return customerManager;
    }

    public void setCustomerManager(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    public Boolean getGrundFvis() {
        return grundFvis;
    }

    public void setGrundFvis(Boolean grundFvis) {
        this.grundFvis = grundFvis;
    }

    public Boolean getGrundCvis() {
        return grundCvis;
    }

    public void setGrundCvis(Boolean grundCvis) {
        this.grundCvis = grundCvis;
    }

    public Boolean getGrundTvis() {
        return grundTvis;
    }

    public void setGrundTvis(Boolean grundTvis) {
        this.grundTvis = grundTvis;
    }

    public Boolean getGrundUvis() {
        return grundUvis;
    }

    public void setGrundUvis(Boolean grundUvis) {
        this.grundUvis = grundUvis;
    }

    public Boolean getGrundEvis() {
        return grundEvis;
    }

    public void setGrundEvis(Boolean grundEvis) {
        this.grundEvis = grundEvis;
    }

    public Boolean getGrundRvis() {
        return grundRvis;
    }

    public void setGrundRvis(Boolean grundRvis) {
        this.grundRvis = grundRvis;
    }

    public Integer getGrundF() {
        return grundF;
    }

    public void setGrundF(Integer grundF) {
        this.grundF = grundF;
    }

    public Integer getGrundC() {
        return grundC;
    }

    public void setGrundC(Integer grundC) {
        this.grundC = grundC;
    }

    public Integer getGrundT() {
        return grundT;
    }

    public void setGrundT(Integer grundT) {
        this.grundT = grundT;
    }

    public Integer getGrundU() {
        return grundU;
    }

    public void setGrundU(Integer grundU) {
        this.grundU = grundU;
    }

    public Integer getGrundE() {
        return grundE;
    }

    public void setGrundE(Integer grundE) {
        this.grundE = grundE;
    }

    public Integer getGrundR() {
        return grundR;
    }

    public void setGrundR(Integer grundR) {
        this.grundR = grundR;
    }
}
