package com.leoni.viewModel;
import com.leoni.data.manager.ColorManager;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.manager.SchrittModulRm9X1WrmManager;
import com.leoni.data.manager.SicherungenRelais9X1WrmManager;
import com.leoni.data.models.Color;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.SchrittModulRm9X1Wrm;
import com.leoni.data.models.SicherungenRelais9X1Wrm;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class SchrittViewModel {

    @WireVariable
    private SchrittModulRm9X1WrmManager schrittManager;

    @WireVariable
    private ModulsManager modulsManager;

    @WireVariable
    private ColorManager colorManager;

    @Wire("#schrittGrid")
    private Grid schrittGrid;
    private List<SchrittModulRm9X1Wrm> schrittList = new ArrayList<SchrittModulRm9X1Wrm>();
    private SchrittModulRm9X1Wrm selectedSchritt;
    private String id="";
    private Moduls moduls;
    private String sachNrBestSearch="";
    private String sachNrLieferantSearch="";
    private String prodGruppeSearch="";
    private String ausfuehrungSearch="";
    private String bandNameSearch="";
    private String seiteSearch="";
    private String schrittSearch="";
    private Moduls sachNrBestModul;
    private Moduls sachNrLieferantModul;
    private String variante1;
    private String variante2;
    private String variante3;
    private String variante4;
    private String seite;
    private String schritt;
    private String popis;
    private String farba;
    private String isCheck;
    private String bandName;
    private boolean isCheckBool=false;
    private static final String MODULS_LIEF = "^[0-9]{8}[A-Z]$";
    private static final String MODULS_BEST = "\\s";
    private SchrittModulRm9X1Wrm schrittToDelete;
    private String user;
    private List<Moduls> modulsList;
    private List<Moduls> modulsList2;
    private List<String> bandNameList = Arrays.asList(
            "",
            "F991RL",
            "F981LL",
            "F991LL"
            );
    private List<String> seiteList = Arrays.asList(
            "",
            "A",
            "B"
            );
    private List<String> stepList = Arrays.asList(
            "","1","2","3","4","5","6","7","8"
    );
    private List<Color> colorList;


    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        schrittList=schrittManager.getAll();
        colorList = colorManager.getAll();
        //modulsList = modulsManager.getAll();
        modulsList2 = modulsManager.getAll();
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Command
    @NotifyChange ({"schrittList"})
    public void search()
    {    if (id.equals("")&&sachNrLieferantSearch.equals("")&&sachNrBestSearch.equals("")&&ausfuehrungSearch.equals("")&&
            bandNameSearch.equals("")&&seiteSearch.equals("")&&schrittSearch.equals("")&&prodGruppeSearch.equals("")){
        schrittList=schrittManager.getAll();}
    else schrittList = schrittManager.findBy(id,sachNrLieferantSearch,sachNrBestSearch,ausfuehrungSearch,prodGruppeSearch,bandNameSearch,seiteSearch,schrittSearch);
    }

    @Command
    @NotifyChange ({"schrittList","id","sachNrLieferantSearch","sachNrBestSearch","ausfuehrungSearch","prodGruppeSearch","bandNameSearch","seiteSearch","schrittSearch"})
    public void cancelSearch()
    {   schrittList = schrittManager.getAll();
        id = "";
        sachNrLieferantSearch = "";
        sachNrBestSearch = "";
        ausfuehrungSearch = "";
        prodGruppeSearch = "";
        bandNameSearch = "";
        seiteSearch = "";
        schrittSearch = "";
    }

    @Command
    @NotifyChange({"schrittList"})
    public void searchById()
    {
        if (id.equals("")){schrittList=schrittManager.getAll();}
        else schrittList = schrittManager.findById(Integer.parseInt(id));
    };



    @Command
    @NotifyChange ({"schrittList"})
    public void searchBySachNrBest()
    {
        if (sachNrBestSearch.equals("")){schrittList=schrittManager.getAll();}
        else schrittList = schrittManager.findBySachNrBest(sachNrBestSearch);
    }

    @Command
    @NotifyChange ({"schrittList"})
    public void searchBySachNrLieferant()
    {
        if (sachNrLieferantSearch.equals("")){schrittList=schrittManager.getAll();}
        else schrittList = schrittManager.findBySachNrLieferant(sachNrLieferantSearch);
    }

    @Command
    @NotifyChange ({"schrittList"})
    public void deleteSchritt(@BindingParam("schritt") SchrittModulRm9X1Wrm mySchritt)
    {
        schrittToDelete=mySchritt;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            schrittManager.delete(schrittToDelete);
                            //schrittList=schrittManager.getAll();
                            search();
                            BindUtils.postNotifyChange(null, null, SchrittViewModel.this, "schrittList");
                        }
                    }
                }
        );


    }

    @Command
    @NotifyChange({"selectedSchritt"})
    public void saveSchritt(@ContextParam(ContextType.VIEW) Component comp,
                          @BindingParam("selectedData")SchrittModulRm9X1Wrm editedSchritt) {
        schrittManager.saveEditedSchritt(editedSchritt,user);
        schrittList=schrittManager.getAll();
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"schrittList","sachNrBestModul","sachNrLieferantModul","variante1","variante2","variante3","variante4","seite","schritt","popis","popis","farba","isCheck","bandName","isCheckBool"})
    public void generateNewSchritt() {
        if(sachNrBestModul!=null&&sachNrLieferantModul!=null&&seite!=null&&schritt!=null){

         if(!modulsManager.findBySachNrLieferant(sachNrLieferantModul.getSachNrLieferant()).isEmpty()){
             if(sachNrLieferantModul.getSachNrBest().equals(sachNrBestModul.getSachNrBest())) {

             if(isCheckBool==false){isCheck="F";}else {isCheck="T";}
             schrittManager.addNewSchritt(sachNrLieferantModul, variante1, variante2, variante3, variante4, seite, schritt, popis, farba, isCheck, bandName,user);
             schrittList=schrittManager.getAll();
             sachNrLieferantModul=null;
             sachNrBestModul=null;

             sachNrLieferantModul=null;
             sachNrBestModul=null;

             variante1=null;
             variante2=null;
             variante3=null;
             variante4=null;
             seite=null;
                schritt=null;
                popis=null;
                farba=null;
                isCheck=null;
                bandName=null;
                isCheckBool=false;
                Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
             }
             else{Messagebox.show("SachNrLief a SachNrBest sa nezhoduju!", "Error", Messagebox.OK, Messagebox.ERROR);}
        }
        else{ Messagebox.show("Modul neexistuje", "Error", Messagebox.OK, Messagebox.ERROR);}

        }
        else{ Messagebox.show("Nezadali ste sch nr., lief nr.!, stranu alebo schritt", "Error", Messagebox.OK, Messagebox.ERROR);}
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

        File xslFile = schrittManager.exportToFile(schrittList);
        byte[] buffer = new byte[(int) xslFile.length()];
        FileInputStream fs = new FileInputStream(xslFile);
        fs.read(buffer);
        fs.close();
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        AMedia amedia = new AMedia("SichritteReport.csv", "csv", "application/file", is);
        Filedownload.save(amedia);
    }

    public List<SchrittModulRm9X1Wrm> getSchrittList() {
        return schrittList;
    }

    public void setSchrittList(List<SchrittModulRm9X1Wrm> schrittList) {
        this.schrittList = schrittList;
    }

    public SchrittModulRm9X1Wrm getSelectedSchritt() {
        return selectedSchritt;
    }

    public void setSelectedSchritt(SchrittModulRm9X1Wrm selectedSchritt) {
        this.selectedSchritt = selectedSchritt;
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

    public String getSachNrBestSearch() {
        return sachNrBestSearch;
    }

    public void setSachNrBestSearch(String sachNrBestSearch) {
        this.sachNrBestSearch = sachNrBestSearch;
    }

    public String getSachNrLieferantSearch() {
        return sachNrLieferantSearch;
    }

    public void setSachNrLieferantSearch(String sachNrLieferantSearch) {
        this.sachNrLieferantSearch = sachNrLieferantSearch;
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

    public String getVariante1() {
        return variante1;
    }

    public void setVariante1(String variante1) {
        this.variante1 = variante1;
    }

    public String getVariante2() {
        return variante2;
    }

    public void setVariante2(String variante2) {
        this.variante2 = variante2;
    }

    public String getVariante3() {
        return variante3;
    }

    public void setVariante3(String variante3) {
        this.variante3 = variante3;
    }

    public String getVariante4() {
        return variante4;
    }

    public void setVariante4(String variante4) {
        this.variante4 = variante4;
    }

    public String getSeite() {
        return seite;
    }

    public void setSeite(String seite) {
        this.seite = seite;
    }

    public String getSchritt() {
        return schritt;
    }

    public void setSchritt(String schritt) {
        this.schritt = schritt;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public String getFarba() {
        return farba;
    }

    public void setFarba(String farba) {
        this.farba = farba;
    }

    /*public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    } */

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String check) {
        isCheck = check;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public boolean getIsCheckBool() {
        return isCheckBool;
    }

    public void setIsCheckBool(boolean checkBool) {
        isCheckBool = checkBool;
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

    public String getSeiteSearch() {
        return seiteSearch;
    }

    public void setSeiteSearch(String seiteSearch) {
        this.seiteSearch = seiteSearch;
    }

    public String getSchrittSearch() {
        return schrittSearch;
    }

    public void setSchrittSearch(String schrittSearch) {
        this.schrittSearch = schrittSearch;
    }

    public String getBandNameSearch() {
        return bandNameSearch;
    }

    public void setBandNameSearch(String bandNameSearch) {
        this.bandNameSearch = bandNameSearch;
    }

    public List<String> getBandNameList() {
        return bandNameList;
    }

    public void setBandNameList(List<String> bandNameList) {
        this.bandNameList = bandNameList;
    }

    public List<String> getSeiteList() {
        return seiteList;
    }

    public void setSeiteList(List<String> seiteList) {
        this.seiteList = seiteList;
    }

    public List<String> getStepList() {
        return stepList;
    }

    public void setStepList(List<String> stepList) {
        this.stepList = stepList;
    }

    public List<Color> getColorList() {
        return colorList;
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
    }
}
