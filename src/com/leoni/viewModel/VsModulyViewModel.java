package com.leoni.viewModel;

import com.leoni.data.manager.ModulsManager;
import com.leoni.data.manager.VsManager;
import com.leoni.data.manager.VsModulyWrmManager;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.Vs;
import com.leoni.data.models.VsModulyWrm;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.BindUtils;
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
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 15.1.2014
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VsModulyViewModel {
    @WireVariable
    private VsModulyWrmManager vsModulyWrmManager;

    @WireVariable
    private ModulsManager modulsManager;

    @WireVariable
    private VsManager vsManager;

    @Wire("#vsModulGrid")
    private Grid vsModulGrid;
    private List<VsModulyWrm> vsModulList = new ArrayList<VsModulyWrm>();
    private List<Vs> vsList;
    private VsModulyWrm selectedVsModul;
    private Vs selectedVs;
    private Vs selectedVsSave;
    private String id="";
    private Moduls moduls;
    private String sachNrBestSearch="";
    private String sachNrLieferantSearch="";
    private String prodGruppeSearch="";
    private String ausfuehrungSearch="";
    private String vsSearch="";
    private String vodicSearch="";
    private Moduls sachNrBestModul;
    private Moduls sachNrLieferantModul;
    private String vodic;
    private Double prierez;
    private String popis;
    //private Integer id_vs;
    private static final String MODULS_LIEF = "^[0-9]{8}[A-Z]$";
    private static final String MODULS_BEST = "\\s";
    private VsModulyWrm vsModulToDelete;
    private List<Moduls> modulsList;
    private List<Moduls> modulsList2;
    private String user;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        vsModulList=vsModulyWrmManager.getAll();
        Collections.sort(vsModulList);
        modulsList2 = modulsManager.getAll();
        vsList = vsManager.getAll();
        Collections.sort(vsList);
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Command
    @NotifyChange ({"vsModulList","selectedVsModul"})
    public void search()
    {    if (id.equals("")&&sachNrLieferantSearch.equals("")&&sachNrBestSearch.equals("")&&ausfuehrungSearch.equals("")&&prodGruppeSearch.equals("")&&vsSearch.equals("")&&vodicSearch.equals("")){
        vsModulList=vsModulyWrmManager.getAll();
        Collections.sort(vsModulList);
    }
    else {vsModulList = vsModulyWrmManager.SearchVsmodul(id,sachNrLieferantSearch,sachNrBestSearch,ausfuehrungSearch,prodGruppeSearch,vsSearch, vodicSearch);
        selectedVsModul = null;
        Collections.sort(vsModulList);
        }
    }

    @Command
    @NotifyChange ({"vsModulList","id","sachNrLieferantSearch","sachNrBestSearch","ausfuehrungSearch","prodGruppeSearch",
            "popisSearch","vodicSearch","selectedVsModul"})
    public void cancelSearch()
    {   vsModulList = vsModulyWrmManager.getAll();
        id = "";
        sachNrLieferantSearch = "";
        sachNrBestSearch = "";
        ausfuehrungSearch = "";
        prodGruppeSearch = "";
        vsSearch = "";
        vodicSearch = "";
        selectedVsModul = null;
    }

    @Command
    @NotifyChange({"vsModulList"})
    public void searchById()
    {
        if (id.equals("")){vsModulList=vsModulyWrmManager.getAll();}
        else vsModulList = vsModulyWrmManager.findById(Integer.parseInt(id));
    };



    @Command
    @NotifyChange ({"vsModulList"})
    public void searchBySachNrBest()
    {
        if (sachNrBestSearch.equals("")){vsModulList=vsModulyWrmManager.getAll();}
        else vsModulList = vsModulyWrmManager.findBySachNrBest(sachNrBestSearch);
    }

    @Command
    @NotifyChange ({"vsModulList"})
    public void searchBySachNrLieferant()
    {
        if (sachNrLieferantSearch.equals("")){vsModulList=vsModulyWrmManager.getAll();}
        else vsModulList =vsModulyWrmManager.findBySachNrLieferant(sachNrLieferantSearch);
    }

    @Command
    @NotifyChange ({"vsModulList"})
        public void deleteVsModul(@BindingParam("vsModul") VsModulyWrm myVsModul)
        {
        vsModulToDelete=myVsModul;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                           vsModulyWrmManager.delete(vsModulToDelete);
                            //vsModulList=vsModulyWrmManager.getAll();
                            search();
                            BindUtils.postNotifyChange(null, null, VsModulyViewModel.this, "vsModulList");
                        }
                    }
                }
        );


    }

    @Command
    @NotifyChange({"selectedVsModul"})
    public void saveVsModul(@ContextParam(ContextType.VIEW) Component comp,
                              @BindingParam("selectedData")VsModulyWrm editedVsModul) {
        String tempPopis = editedVsModul.getPopis();
        String id_vsString="";
        if(tempPopis!=null&&tempPopis.startsWith("Y")){
            id_vsString="10"+tempPopis.substring(1,4);
           // editedVsModul.setId_vs(Integer.valueOf(id_vsString));
        }
        if(tempPopis!=null&&tempPopis.startsWith("YW")){
            id_vsString="20"+tempPopis.substring(2,5);

        }
        if(tempPopis!=null) editedVsModul.setVs(selectedVsSave);
        vsModulyWrmManager.saveEditedVsModul(editedVsModul,user);
        vsModulList=vsModulyWrmManager.getAll();
        selectedVsModul = null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"vsModulList","sachNrBestModul","sachNrLieferantModul","vodic","prierez","popis","selectedVs"})
    public void generateNewVsModul() {
        if(sachNrBestModul!=null&&sachNrLieferantModul!=null){

        if(!modulsManager.findBySachNrLieferant(sachNrLieferantModul.getSachNrLieferant()).isEmpty()){
            if(sachNrLieferantModul.getSachNrBest().equals(sachNrBestModul.getSachNrBest())) {
                String id_vsString="";
                if(popis!=null&&popis.startsWith("Y")){
                    id_vsString="10"+popis.substring(1,4);
                }
                if(popis!=null&&popis.startsWith("YW")){
                    id_vsString="20"+popis.substring(2,5);
                }
                //if(popis!=null){id_vs= Integer.valueOf(id_vsString);}
                vsModulyWrmManager.addNewVsModul(sachNrLieferantModul,vodic,prierez,popis,selectedVs,user);
                vsModulList=vsModulyWrmManager.getAll();
                sachNrLieferantModul=null;
                sachNrBestModul=null;
                sachNrLieferantModul=null;
                sachNrBestModul=null;
                vodic=null;
                prierez=null;
                popis=null;
                selectedVs = null;
                //id_vs=null;
                Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
        }
        else{Messagebox.show("SachNrLief a SachNrBest sa nezhoduju!", "Error", Messagebox.OK, Messagebox.ERROR);}
        }
        else{ Messagebox.show("Modul neexistuje", "Error", Messagebox.OK, Messagebox.ERROR);}

    }
        else{ Messagebox.show("Nezadali ste sch nr. alebo lief nr.!", "Error", Messagebox.OK, Messagebox.ERROR);}
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

        File xslFile = vsModulyWrmManager.exportToFile(vsModulList);
        byte[] buffer = new byte[(int) xslFile.length()];
        FileInputStream fs = new FileInputStream(xslFile);
        fs.read(buffer);
        fs.close();
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        AMedia amedia = new AMedia("VsModulyReport.csv", "csv", "application/file", is);
        Filedownload.save(amedia);
    }

    @Command
    public void importFromExcel(){
        Window window = (Window) Executions.createComponents(
                "vsModulsEditor/addVsModuls.zul", null, null);
        window.doModal();
    }


    public List<VsModulyWrm> getVsModulList() {
        return vsModulList;
    }

    public void setVsModulList(List<VsModulyWrm> vsModulList) {
        this.vsModulList = vsModulList;
    }

    public VsModulyWrm getSelectedVsModul() {
        return selectedVsModul;
    }

    public void setSelectedVsModul(VsModulyWrm selectedVsModul) {
        this.selectedVsModul = selectedVsModul;
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

    public List<Moduls> getModulsList2() {
        return modulsList2;
    }

    public void setModulsList2(List<Moduls> modulsList2) {
        this.modulsList2 = modulsList2;
    }

    public List<Moduls> getModulsList() {
        return modulsList;
    }

    public void setModulsList(List<Moduls> modulsList) {
        this.modulsList = modulsList;
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

    public String getVodic() {
        return vodic;
    }

    public void setVodic(String vodic) {
        this.vodic = vodic;
    }

    public Double getPrierez() {
        return prierez;
    }

    public void setPrierez(Double prierez) {
        this.prierez = prierez;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    /*public Integer getId_vs() {
        return id_vs;
    }

    public void setId_vs(Integer id_vs) {
        this.id_vs = id_vs;
    } */

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

    public String getVsSearch() {
        return vsSearch;
    }

    public void setVsSearch(String vsSearch) {
        this.vsSearch = vsSearch;
    }

    public String getVodicSearch() {
        return vodicSearch;
    }

    public void setVodicSearch(String vodicSearch) {
        this.vodicSearch = vodicSearch;
    }

    public List<Vs> getVsList() {
        return vsList;
    }

    public void setVsList(List<Vs> vsList) {
        this.vsList = vsList;
    }

    public Vs getSelectedVs() {
        return selectedVs;
    }

    public void setSelectedVs(Vs selectedVs) {
        this.selectedVs = selectedVs;
    }

    public Vs getSelectedVsSave() {
        return selectedVsSave;
    }

    public void setSelectedVsSave(Vs selectedVsSave) {
        this.selectedVsSave = selectedVsSave;
    }
}
