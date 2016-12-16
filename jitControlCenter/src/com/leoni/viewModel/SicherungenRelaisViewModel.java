package com.leoni.viewModel;


import com.leoni.data.manager.ExcelDocumentManager;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.manager.SicherungenRelais9X1WrmManager;
import com.leoni.data.manager.UsersManager;
import com.leoni.data.manager.oldJIT.GestellManager;

import com.leoni.data.models.Moduls;
import com.leoni.data.models.SicherungenRelais9X1Wrm;
import com.leoni.data.models.Users;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.BindUtils;
//import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.1.2014
 * Time: 13:22
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SicherungenRelaisViewModel{


    @WireVariable
    private SicherungenRelais9X1WrmManager sicherungenRelais9X1WrmManager;
    @WireVariable
    private ModulsManager modulsManager;

    private List<SicherungenRelais9X1Wrm> relaisList = new ArrayList<SicherungenRelais9X1Wrm>();
    private SicherungenRelais9X1Wrm selectedRelay;
    private String id="";
    private Moduls moduls;
    private String sachNrBestSearch="";
    private String sachNrLieferantSearch="";
    private String prodGruppeSearch="";
    private String boxSearch="";
    private String platzSearch="";
    private String wertSearch="";
    private String ausfuehrungSearch="";
    private Moduls sachNrBestModul;
    private Moduls sachNrLieferantModul;
    private String box;
    private String platz;
    private String wert;
    private String beschreibung="";
    private static final String MODULS_LIEF = "^[0-9]{8}[A-Z]$";
    private static final String MODULS_BEST = "\\s";
    private SicherungenRelais9X1Wrm relayToDelete;
    //private Binder binder;
    private List<Moduls> modulsList;
    private List<Moduls> modulsList2;
    private List<String> platzList;
    private List<String> platzListSearch;
    private String user;
    private List<String> boxList = Arrays.asList(
            "relais_treager1_vorne",
            "relais_treager2_hinten",
            "sicherungs_dose_links",
            "sicherungs_dose_rechts");
    private List<String> boxListSearch = Arrays.asList(
            "",
            "relais_treager1_vorne",
            "relais_treager2_hinten",
            "sicherungs_dose_links",
            "sicherungs_dose_rechts");
    private static final Map<String, List<String>> platzMap = new HashMap<String, List<String>>()
    {
        {
            put("relais_treager1_vorne", Arrays.asList( "K1", "K2A", "K2B", "K3", "K4", "K5", "K6", "K7", "K8", "K9"));
            put("relais_treager2_hinten", Arrays.asList( "K1", "K2", "K3", "K4", "K5", "K6A", "K6B", "K7A", "K7B", "K8", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5"));
            put("sicherungs_dose_rechts", Arrays.asList( "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "H1", "H2", "E", "F"));
            put("sicherungs_dose_links", Arrays.asList( "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "H1", "E", "F"));
        }
    };
    private static final Map<String, List<String>> platzMapSearch = new HashMap<String, List<String>>()
    {
        {
            put("relais_treager1_vorne", Arrays.asList("", "K1", "K2A", "K2B", "K3", "K4", "K5", "K6", "K7", "K8", "K9"));
            put("relais_treager2_hinten", Arrays.asList("", "K1", "K2", "K3", "K4", "K5", "K6A", "K6B", "K7A", "K7B", "K8", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5"));
            put("sicherungs_dose_rechts", Arrays.asList("", "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8" , "H1", "H2", "E", "F"));
            put("sicherungs_dose_links", Arrays.asList("", "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "H1", "E", "F"));
        }
    };
    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view/*,@ContextParam(ContextType.BINDER) Binder _binder*/)
    {
        relaisList = sicherungenRelais9X1WrmManager.getAll();
        modulsList = modulsManager.getAll();
        modulsList2 = modulsManager.getAll();
        user = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Command
    @NotifyChange ({"relaisList","platzListSearch"})
    public void search()
    {    if (id.equals("")&&sachNrLieferantSearch.equals("")&&sachNrBestSearch.equals("")&&ausfuehrungSearch.equals("")&&prodGruppeSearch.equals("")&&boxSearch.equals("")&&platzSearch.equals("")&&wertSearch.equals("")){
        relaisList=sicherungenRelais9X1WrmManager.getAll();}
    else {
        platzListSearch = platzMapSearch.get(boxSearch);
        relaisList = sicherungenRelais9X1WrmManager.findBy(id,sachNrLieferantSearch,sachNrBestSearch,ausfuehrungSearch,prodGruppeSearch,boxSearch,platzSearch,wertSearch);}

    }

    @Command
    @NotifyChange ({"relaisList","id","sachNrLieferantSearch","sachNrBestSearch","ausfuehrungSearch","prodGruppeSearch","boxSearch","platzSearch","wertSearch"})
    public void cancelSearch()
    {   relaisList = sicherungenRelais9X1WrmManager.getAll();
        id = "";
        sachNrLieferantSearch = "";
        sachNrBestSearch = "";
        ausfuehrungSearch = "";
        prodGruppeSearch = "";
        boxSearch = "";
        platzSearch = "";
        wertSearch = "";
    }


    @Command
    @NotifyChange({"relaisList"})
    public void searchById()
    {
        if (id.equals("")){relaisList=sicherungenRelais9X1WrmManager.getAll();}
        else relaisList = sicherungenRelais9X1WrmManager.findById(Integer.parseInt(id));
    };



    @Command
    @NotifyChange ({"relaisList"})
    public void searchBySachNrBest()
    {
        if (sachNrBestSearch.equals("")){relaisList=sicherungenRelais9X1WrmManager.getAll();}
        else relaisList = sicherungenRelais9X1WrmManager.findBySachNrBest(sachNrBestSearch);
    }

    @Command
    @NotifyChange ({"relaisList"})
    public void searchBySachNrLieferant()
    {
        if (sachNrLieferantSearch.equals("")){relaisList=sicherungenRelais9X1WrmManager.getAll();}
        else relaisList = sicherungenRelais9X1WrmManager.findBySachNrLieferant(sachNrLieferantSearch);
    }

    @Command
    @NotifyChange ({"relaisList"})
    public void deleteRelay(@BindingParam("relay") SicherungenRelais9X1Wrm myRelay)
    {
        relayToDelete=myRelay;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                        sicherungenRelais9X1WrmManager.delete(relayToDelete);
                        //relaisList=sicherungenRelais9X1WrmManager.getAll();
                        search();
                            BindUtils.postNotifyChange(null, null, SicherungenRelaisViewModel.this, "relaisList");
                        }
                    }
                }
        );
    }








    @Command
    @NotifyChange({"selectedRelay"})
    public void saveRelay(@ContextParam(ContextType.VIEW) Component comp,
                          @BindingParam("selectedData") SicherungenRelais9X1Wrm editedRelay) {
            sicherungenRelais9X1WrmManager.saveEditedRelay(editedRelay,user);
            relaisList=sicherungenRelais9X1WrmManager.getAll();
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
       }

    @Command
    @NotifyChange({"relaisList","sachNrBestModul","sachNrLieferantModul","box","platz","wert","beschreibung"})
    public void generateNewRelay() {
        if(sachNrBestModul!=null&&sachNrLieferantModul!=null&&box!=null&&platz!=null&&wert!=null){

        if(!modulsManager.findBySachNrLieferant(sachNrLieferantModul.getSachNrLieferant()).isEmpty()){
            if(sachNrLieferantModul.getSachNrBest().equals(sachNrBestModul.getSachNrBest())) {
            //Moduls myModul = modulsManager.findBySachNrLieferant(sachNrLieferant).get(0);

            sicherungenRelais9X1WrmManager.addNewRelay(sachNrLieferantModul,box,platz,wert,beschreibung,user);
            relaisList=sicherungenRelais9X1WrmManager.getAll();
            sachNrLieferantModul=null;
            sachNrBestModul=null;
            //updateModulsList();
            //updateModulsList2();
            sachNrLieferantModul=null;
            sachNrBestModul=null;

            box=null;
            platz=null;
            wert=null;
            platzList=null;
            beschreibung="";
                Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
            }
            else{Messagebox.show("SachNrLief a SachNrBest sa nezhoduju!", "Error", Messagebox.OK, Messagebox.ERROR);}
            }
            else{ Messagebox.show("Modul neexistuje", "Error", Messagebox.OK, Messagebox.ERROR);}

        }
        else{ Messagebox.show("Nezadali ste sch nr., lief nr., box, platz alebo wert!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }
    @Command
    @NotifyChange({"platzList","platz"})
    public void updatePlatzList(){
       platzList = platzMap.get(box);
       platz = null;
    }

    @Command
    @NotifyChange({"sachNrBestModul"})
    public void updateSachNrBestModul(){
        if(sachNrLieferantModul!=null)
            sachNrBestModul = modulsManager.findBySachNrLieferant(sachNrLieferantModul.getSachNrLieferant()).get(0);
        //else modulsList = modulsManager.getAll();
    }

    @Command
    public void exportToExcel() throws Exception{

        File xslFile = sicherungenRelais9X1WrmManager.exportToFile(relaisList);
        byte[] buffer = new byte[(int) xslFile.length()];
        FileInputStream fs = new FileInputStream(xslFile);
        fs.read(buffer);
        fs.close();
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        AMedia amedia = new AMedia("SicherungenReport.csv", "csv", "application/file", is);
        Filedownload.save(amedia);
        }

    @Command
    @NotifyChange ({"relaisList"})
    public void copyRelay(@BindingParam("relay") SicherungenRelais9X1Wrm myRelay)
    {
        Map<String, SicherungenRelais9X1Wrm> arguments = new HashMap<String,SicherungenRelais9X1Wrm>();
        arguments.put("myRelay", myRelay);
        Window window = (Window) Executions.createComponents(
                "sicherungenEditor/copyRelay.zul",null ,arguments);
        window.doModal();
    }

    @GlobalCommand
    @NotifyChange({"relaisList"})
    public void refresh(){
        search();
    }

    @Command
    public void addBatch() {
        Window window = (Window) Executions.createComponents(
                "sicherungenEditor/addBatch.zul", null,null);
        window.doModal();
    }

    public List<String> getBoxListSearch() {
        return boxListSearch;
    }

    public void setBoxListSearch(List<String> boxListSearch) {
        this.boxListSearch = boxListSearch;
    }

    public List<SicherungenRelais9X1Wrm> getRelaisList() {
        return relaisList;
    }

    public void setRelaisList(List<SicherungenRelais9X1Wrm> relaisList) {
        this.relaisList = relaisList;
    }
    public SicherungenRelais9X1Wrm getSelectedRelay() {
        return selectedRelay;
    }

    public void setSelectedRelay(SicherungenRelais9X1Wrm selectedRelay) {
        this.selectedRelay = selectedRelay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Moduls getModuls() {
        return moduls;
    }

    public void setModuls(Moduls moduls) {
        this.moduls = moduls;
    }

    public String getSachNrLieferantSearch() {
        return sachNrLieferantSearch;
    }

    public void setSachNrLieferantSearch(String sachNrLieferantSearch) {
        this.sachNrLieferantSearch = sachNrLieferantSearch;
    }

    public String getSachNrBestSearch() {
        return sachNrBestSearch;
    }

    public void setSachNrBestSearch(String sachNrBestSearch) {
        this.sachNrBestSearch = sachNrBestSearch;
    }

    public Moduls getSachNrBestModul() {
        return sachNrBestModul;
    }

    public void setSachNrBestModul(Moduls sachNrBestModul) {
        this.sachNrBestModul = sachNrBestModul;
    }

    public Moduls getSachNrLieferantModul() {
        return sachNrLieferantModul;
    }

    public void setSachNrLieferantModul(Moduls sachNrLieferantModul) {
        this.sachNrLieferantModul = sachNrLieferantModul;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getPlatz() {
        return platz;
    }

    public void setPlatz(String platz) {
        this.platz = platz;
    }

    public String getWert() {
        return wert;
    }

    public void setWert(String wert) {
        this.wert = wert;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getProdGruppeSearch() {
        return prodGruppeSearch;
    }

    public void setProdGruppeSearch(String prodGruppeSearch) {
        this.prodGruppeSearch = prodGruppeSearch;
    }

    public String getAusfuehrungSearch() {
        return ausfuehrungSearch;
    }

    public void setAusfuehrungSearch(String ausfuehrungSearch) {
        this.ausfuehrungSearch = ausfuehrungSearch;
    }

    public String getBoxSearch() {
        return boxSearch;
    }

    public void setBoxSearch(String boxSearch) {
        this.boxSearch = boxSearch;
    }

    public String getPlatzSearch() {
        return platzSearch;
    }

    public void setPlatzSearch(String platzSearch) {
        this.platzSearch = platzSearch;
    }

    public String getWertSearch() {
        return wertSearch;
    }

    public void setWertSearch(String wertSearch) {
        this.wertSearch = wertSearch;
    }

    public List<String> getBoxList() {
        return boxList;
    }

    public void setBoxList(List<String> boxList) {
        this.boxList = boxList;
    }

    public List<String> getPlatzList() {
        return platzList;
    }

    public void setPlatzList(List<String> platzList) {
        this.platzList = platzList;
    }

    public List<String> getPlatzListSearch() {
        return platzListSearch;
    }

    public void setPlatzListSearch(List<String> platzListSearch) {
        this.platzListSearch = platzListSearch;
    }

    public List<Moduls> getModulsList() {
        return modulsList;
    }

    public void setModulsList(List<Moduls> modulsList) {
        this.modulsList = modulsList;
    }

    public List<Moduls> getModulsList2() {
        return modulsList2;
    }

    public void setModulsList2(List<Moduls> modulsList2) {
        this.modulsList2 = modulsList2;
    }
}
