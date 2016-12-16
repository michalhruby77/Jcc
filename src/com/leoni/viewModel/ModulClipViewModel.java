package com.leoni.viewModel;

import com.leoni.data.manager.ModulClipListWrmManager;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.models.ModulClipListWrm;
import com.leoni.data.models.Moduls;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 15.1.2014
 * Time: 8:09
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ModulClipViewModel {

    @WireVariable
    private ModulClipListWrmManager modulClipListWrmManager;

    @WireVariable
    private ModulsManager modulsManager;

    @Wire("#modulClipGrid")
    private Grid modulClipGrid;
    private List<ModulClipListWrm> modulClipList = new ArrayList<ModulClipListWrm>();
    private ModulClipListWrm selectedModulClip;
    private String id="";
    private Moduls moduls;
    private String sachNrBestSearch="";
    private String sachNrLieferantSearch="";
    private String prodGruppeSearch="";
    private String ausfuehrungSearch="";
    private String codeClipSearch="";
    private Moduls sachNrBestModul;
    private Moduls sachNrLieferantModul;
    private String codeClip;
    private boolean isBrett=false;
    private String note;
    private String conditionErzNr;
    private static final String MODULS_LIEF = "^[0-9]{8}[A-Z]$";
    private static final String MODULS_BEST = "\\s";
    private ModulClipListWrm modulClipToDelete;
    private String user;
    //private List<Moduls> modulsList;
    private List<Moduls> modulsList2;

    public ModulClipViewModel() {
    }

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        modulClipList=modulClipListWrmManager.getAll();
        //modulsList = modulsManager.getAll();
        modulsList2 = modulsManager.getAll();
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Command
    @NotifyChange ({"modulClipList","selectedModulClip"})
    public void search()
    {    if (id.equals("")&&sachNrLieferantSearch.equals("")&&sachNrBestSearch.equals("")&&ausfuehrungSearch.equals("")&&prodGruppeSearch.equals("")&&codeClipSearch.equals("")){
        modulClipList=modulClipListWrmManager.getAll();}
    else modulClipList = modulClipListWrmManager.findBy(id,sachNrLieferantSearch,sachNrBestSearch,ausfuehrungSearch,prodGruppeSearch,codeClipSearch);
        selectedModulClip = null;
    }

    @Command
    @NotifyChange ({"modulClipList","id","sachNrLieferantSearch","sachNrBestSearch","ausfuehrungSearch",
            "prodGruppeSearch","codeClipSearch", "selectedModulClip"})
    public void cancelSearch()
    {   modulClipList = modulClipListWrmManager.getAll();
        id = "";
        sachNrLieferantSearch = "";
        sachNrBestSearch = "";
        ausfuehrungSearch = "";
        prodGruppeSearch = "";
        codeClipSearch = "";
        selectedModulClip = null;
    }

    @Command
    @NotifyChange({"modulClipList"})
    public void searchById()
    {
        if (id.equals("")){modulClipList=modulClipListWrmManager.getAll();}
        else modulClipList = modulClipListWrmManager.findById(Integer.parseInt(id));
    };



    @Command
    @NotifyChange ({"modulClipList"})
    public void searchBySachNrBest()
    {
        if (sachNrBestSearch.equals("")){modulClipList=modulClipListWrmManager.getAll();}
        else modulClipList = modulClipListWrmManager.findBySachNrBest(sachNrBestSearch);
    }

    @Command
    @NotifyChange ({"modulClipList"})
    public void searchBySachNrLieferant()
    {
        if (sachNrLieferantSearch.equals("")){modulClipList=modulClipListWrmManager.getAll();}
        else modulClipList = modulClipListWrmManager.findBySachNrLieferant(sachNrLieferantSearch);
    }

    @Command
    @NotifyChange ({"modulClipList"})
    public void deleteModulClip(@BindingParam("modulClip") ModulClipListWrm myModulClip)
    {
        modulClipToDelete=myModulClip;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            modulClipListWrmManager.delete(modulClipToDelete);
                            //modulClipList=modulClipListWrmManager.getAll();
                            search();
                            BindUtils.postNotifyChange(null, null, ModulClipViewModel.this, "modulClipList");
                        }
                    }
                }
        );


    }

    @Command
    @NotifyChange({"selectedModulClip"})
    public void saveModulClip(@ContextParam(ContextType.VIEW) Component comp,
                            @BindingParam("selectedData")ModulClipListWrm editedModulClip) {
        modulClipListWrmManager.saveEditedModulClip(editedModulClip,user);
        modulClipList=modulClipListWrmManager.getAll();
        selectedModulClip = null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"modulClipList","sachNrBestModul","sachNrLieferantModul","codeClip","isBrett","note","conditionErzNr","modulsList","modulsList2"})
    public void generateNewModulClip() {
        if(sachNrBestModul!=null&&sachNrLieferantModul!=null&&codeClip!=null){

        if(!modulsManager.findBySachNrLieferant(sachNrLieferantModul.getSachNrLieferant()).isEmpty()){
            if(sachNrLieferantModul.getSachNrBest().equals(sachNrBestModul.getSachNrBest())) {
                modulClipListWrmManager.addNewModulClip(sachNrLieferantModul,codeClip,isBrett,note,conditionErzNr,user);
                modulClipList=modulClipListWrmManager.getAll();
                sachNrLieferantModul=null;
                sachNrBestModul=null;
                //modulsList = null;
                modulsList2 = null;
                //modulsList = new ArrayList<Moduls>();
                //modulsList2 = new ArrayList<Moduls>();
                //modulsList = modulsManager.getAll();
                modulsList2 = modulsManager.getAll();
                sachNrLieferantModul=null;
                sachNrBestModul=null;
                codeClip=null;
                isBrett=false;
                note=null;
                conditionErzNr=null;
                Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
            }
            else{Messagebox.show("SachNrLief a SachNrBest sa nezhoduju!", "Error", Messagebox.OK, Messagebox.ERROR);}
        }
        else{ Messagebox.show("Modul neexistuje", "Error", Messagebox.OK, Messagebox.ERROR);}

        }
        else{ Messagebox.show("Nezadali ste sch nr., lief nr.! alebo kod klipu!", "Error", Messagebox.OK, Messagebox.ERROR);}
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

        File xslFile = modulClipListWrmManager.exportToFile(modulClipList);
        byte[] buffer = new byte[(int) xslFile.length()];
        FileInputStream fs = new FileInputStream(xslFile);
        fs.read(buffer);
        fs.close();
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        AMedia amedia = new AMedia("ModulClipReport.csv", "csv", "application/file", is);
        Filedownload.save(amedia);
    }

    /*@Command
    @NotifyChange({"modulsList2"})
    public void updateModulsList2(){
        if(sachNrBestModul!=null)
            modulsList2 = modulsManager.findBySachNrBest(sachNrBestModul.getSachNrBest());
        else modulsList = modulsManager.getAll();
    } */

    public List<ModulClipListWrm> getModulClipList() {
        return modulClipList;
    }

    public void setModulClipList(List<ModulClipListWrm> modulClipList) {
        this.modulClipList = modulClipList;
    }

    public ModulClipListWrm getSelectedModulClip() {
        return selectedModulClip;
    }

    public void setSelectedModulClip(ModulClipListWrm selectedModulClip) {
        this.selectedModulClip = selectedModulClip;
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

    /*public List<Moduls> getModulsList() {
        return modulsList;
    }

    public void setModulsList(List<Moduls> modulsList) {
        this.modulsList = modulsList;
    }*/

    public List<Moduls> getModulsList2() {
        return modulsList2;
    }

    public void setModulsList2(List<Moduls> modulsList2) {
        this.modulsList2 = modulsList2;
    }

    public String getCodeClip() {
        return codeClip;
    }

    public void setCodeClip(String codeClip) {
        this.codeClip = codeClip;
    }

    public boolean getIsBrett() {
        return isBrett;
    }

    public void setIsBrett(boolean brett) {
        isBrett = brett;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getConditionErzNr() {
        return conditionErzNr;
    }

    public void setConditionErzNr(String conditionErzNr) {
        this.conditionErzNr = conditionErzNr;
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

    public String getCodeClipSearch() {
        return codeClipSearch;
    }

    public void setCodeClipSearch(String codeClipSearch) {
        this.codeClipSearch = codeClipSearch;
    }
}
