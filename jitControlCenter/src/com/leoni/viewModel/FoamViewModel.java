package com.leoni.viewModel;

import com.leoni.data.manager.BandManager;
import com.leoni.data.manager.FoamManager;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.models.Foam;
import com.leoni.data.models.Harness;
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
import org.zkoss.zul.*;

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
 * Date: 16.1.2014
 * Time: 8:31
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FoamViewModel {
    @WireVariable
    private FoamManager foamManager;

    @WireVariable
    private ModulsManager modulsManager;


    @Wire("#foamGrid")
    private Grid foamGrid;
    private List<Foam> foamList = new ArrayList<Foam>();
    private Foam selectedFoam;
    private Moduls moduls;
    private String sachNrBestSearch;
    private String sachNrLieferantSearch;
    private Moduls sachNrBestModul;
    private Moduls sachNrLiefModul;
    private double t1=0;
    private double t2=0;
    private double t3=0;
    private double t4=0;
    private double t5=0;
    private String kabelsatzKz;
    private static final String MODULS_LIEF = "^[0-9]{8}[A-Z]$";
    private static final String MODULS_BEST = "\\s";
    private Foam foamToDelete;
    private List<Moduls> modulsList;
    private List<Moduls> modulsList2;
    private String user;
    //ListModel modulsModel;
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        foamList=foamManager.getAll();
       // modulsList = modulsManager.getAll();
        modulsList2 = modulsManager.getAll();
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //modulsModel= new SimpleListModel(modulsManager.getAll());
        //System.out.println(modulsModel.);
    }

    @Command
    @NotifyChange ({"foamList"})
    public void search()
    {
        if ((sachNrLieferantSearch==null||sachNrLieferantSearch.trim().equals(""))&&
                (sachNrBestSearch==null||sachNrBestSearch.trim().equals("")))
        {foamList=foamManager.getAll();}
        else {
            if(sachNrBestSearch!=null&&!sachNrBestSearch.trim().equals(""))  foamList = foamManager.findBySachNrBest(sachNrBestSearch);
            if(sachNrLieferantSearch!=null&&!sachNrLieferantSearch.trim().equals(""))  foamList = foamManager.findBySachNrLieferant(sachNrLieferantSearch);}
    }

    @Command
    @NotifyChange ({"foamList","sachNrLieferantSearch","sachNrBestSearch"})
    public void cancelSearch()
    {   foamList = foamManager.getAll();
        sachNrLieferantSearch = "";
        sachNrBestSearch = "";
    }

    @Command
    @NotifyChange ({"foamList"})
    public void searchBySachNrBest()
    {
        if (sachNrBestSearch.equals("")){foamList=foamManager.getAll();}
        else foamList = foamManager.findBySachNrBest(sachNrBestSearch);
    }

    @Command
    @NotifyChange ({"foamList"})
    public void searchBySachNrLieferant()
    {
        if (sachNrLieferantSearch.equals("")){foamList=foamManager.getAll();}
        else foamList =foamManager.findBySachNrLieferant(sachNrLieferantSearch);
    }

    @Command
    @NotifyChange ({"foamList"})
    public void deleteFoam(@BindingParam("foam") Foam myFoam)
    {
        foamToDelete=myFoam;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            foamManager.delete(foamToDelete);
                            foamList=foamManager.getAll();
                            BindUtils.postNotifyChange(null, null, FoamViewModel.this, "foamList");
                        }
                    }
                }
        );


    }

    @Command
    @NotifyChange({"selectedFoam"})
    public void saveFoam(@ContextParam(ContextType.VIEW) Component comp,
                            @BindingParam("selectedData")Foam editedFoam) {
        foamManager.saveEditedFoam(editedFoam,user);
        foamList=foamManager.getAll();
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"foamList","sachNrBestModul","sachNrLiefModul","t1","t2","t3","t4","t5","kabelsatzKz","modulsList","modulsList2"})
    public void generateNewFoam() {
        //sachNrLiefModul=sachNrBestModul;
        if(sachNrBestModul!=null&&sachNrLiefModul!=null){

        if(!modulsManager.findBySachNrLieferant(sachNrLiefModul.getSachNrLieferant()).isEmpty()){
            if(sachNrLiefModul.getSachNrBest().equals(sachNrBestModul.getSachNrBest())) {
                //if (t1!=null&&t2!=null&&t3!=null&&t4!=null&&t5!=null){
                foamManager.addNewFoam(sachNrLiefModul.getSachNrLieferant(),sachNrLiefModul.getSachNrBest(),t1,t2,t3,t4,t5,kabelsatzKz,user );
                foamList=foamManager.getAll();

                sachNrLiefModul=null;
                sachNrBestModul=null;

                sachNrLiefModul=null;
                sachNrBestModul=null;
                t1=0;
                t2=0;
                t3=0;
                t4=0;
                t5=0;
                kabelsatzKz=null;

                Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
               // }
               // else{ Messagebox.show("Hodnoty t1 - t5 nemozu byt prazdne!", "Error", Messagebox.OK, Messagebox.ERROR);}
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
        if(sachNrLiefModul!=null)
            sachNrBestModul = modulsManager.findBySachNrLieferant(sachNrLiefModul.getSachNrLieferant()).get(0);
        //else modulsList = modulsManager.getAll();
    }

    @Command
    public void exportToExcel() throws Exception{

        File xslFile = foamManager.exportToFile(foamList);
        byte[] buffer = new byte[(int) xslFile.length()];
        FileInputStream fs = new FileInputStream(xslFile);
        fs.read(buffer);
        fs.close();
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        AMedia amedia = new AMedia("FoamReport.csv", "csv", "application/file", is);
        Filedownload.save(amedia);
    }

    public List<Foam> getFoamList() {
        return foamList;
    }

    public void setFoamList(List<Foam> foamList) {
        this.foamList = foamList;
    }

    public Foam getSelectedFoam() {
        return selectedFoam;
    }

    public void setSelectedFoam(Foam selectedFoam) {
        this.selectedFoam = selectedFoam;
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

    public Moduls getSachNrLiefModul() {
        return sachNrLiefModul;
    }

    public void setSachNrLiefModul(Moduls sachNrLiefModul) {
        this.sachNrLiefModul = sachNrLiefModul;
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

    public double getT1() {
        return t1;
    }

    public void setT1(double t1) {
        this.t1 = t1;
    }

    public double getT2() {
        return t2;
    }

    public void setT2(double t2) {
        this.t2 = t2;
    }

    public double getT3() {
        return t3;
    }

    public void setT3(double t3) {
        this.t3 = t3;
    }

    public double getT4() {
        return t4;
    }

    public void setT4(double t4) {
        this.t4 = t4;
    }

    public double getT5() {
        return t5;
    }

    public void setT5(double t5) {
        this.t5 = t5;
    }

    public String getKabelsatzKz() {
        return kabelsatzKz;
    }

    public void setKabelsatzKz(String kabelsatzKz) {
        this.kabelsatzKz = kabelsatzKz;
    }

    /*public ListModel getModulsModel() {
        return modulsModel;
    }

    public void setModulsModel(ListModel modulsModel) {
        this.modulsModel = modulsModel;
    } */
}
