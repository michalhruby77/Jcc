package com.leoni.viewModel;

import com.leoni.data.manager.ColorManager;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.manager.VariantManager;
import com.leoni.data.manager.WorkplaceManager;
import com.leoni.data.models.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.bind.impl.BindComboitemRenderer;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zul.*;
//import org.zkoss.zul.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 5.3.2014
 * Time: 7:45
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VariantViewModel{
    @WireVariable
    private VariantManager variantManager;

    @WireVariable
    private WorkplaceManager workplaceManager;

    @WireVariable
    private ColorManager colorManager;

    @WireVariable
    private ModulsManager modulsManager;

    private List<Variant> variantList = new ArrayList<Variant>();

    private List<Color> colorList = new ArrayList<Color>();
    private List<Workplace> workplaceList = new ArrayList<Workplace>();
    private List<String> modulsList;
    private Variant selectedVariant;
    private Color selectedColor;
    private Workplace selectedWorkplace;
    private Color selectedColorSave;
    private Workplace selectedWorkplaceSave;
    private Color color;
    private Workplace workplace;
    private String name;
    private String description;
    private String scanString;
    private Boolean scanRequired = true;
    private String typ;
    //private Integer modulsCount;
    private Variant variantToDelete;
    private String selectedModul11;
    private String sachNrLieferantSearch="";
    private String workplaceSearch="";
    private String typSearch="";
    private String descriptionSearch="";
    private String nameSearch="";
    private String scanStringSearch="";
    private List<String> typList;
    private String user;
    private boolean modul12Vis = false;
    private boolean modul61Vis = false;
    private int counterVert = 2;
    Map<Integer,Set<Moduls>> mp = new HashMap<Integer, Set<Moduls>>();
    List<MyHlayout> myHlayoutList = new ArrayList<MyHlayout>();
    Set<Label> plusSet = new HashSet<Label>();
    private String arg1;
    private String arg2;
    private String arg3;


    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("arg1") String arg1,
                     @ExecutionArgParam("arg2") String arg2, @ExecutionArgParam("arg3") String arg3)
    {
        Selectors.wireComponents(view, this, false);
        variantList = variantManager.getAllByType(arg1,arg2,arg3);
        Collections.sort(variantList);
        colorList = colorManager.getAll();
        workplaceList = workplaceManager.getAll();
        modulsList =  modulsManager.getAllModulsLief();
        typList = variantManager.getAllTypes();
        typList.add("");
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Command
    @NotifyChange({"variantList"})
    public void deleteVariant(@BindingParam("variant") Variant myVariant)
    {
        variantToDelete=myVariant;
        System.out.println(myVariant.getModulsSet());
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            variantManager.delete(variantToDelete);
                            search();
                            BindUtils.postNotifyChange(null, null, VariantViewModel.this, "variantList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange({"variantList","selectedVariant"})
    public void search()
    {if (sachNrLieferantSearch.equals("")/*&&workplaceSearch.equals("")*/&&descriptionSearch.equals("")&&nameSearch.equals("")
            &&scanStringSearch.equals("")/*&&typSearch.equals("")*/){variantList = variantManager.getAllByType(arg1,arg2,arg3);//variantManager.getAll();
        Collections.sort(variantList);
    }
    else {
        variantList = variantManager.findBySachNrLieferant(sachNrLieferantSearch, arg2+arg3, descriptionSearch,nameSearch,scanStringSearch, arg1);
        Collections.sort(variantList);
    }
        selectedVariant = null;
    }

    @Command
    @NotifyChange ({"variantList","workplaceSearch","sachNrLieferantSearch","descriptionSearch","nameSearch",
            "scanStringSearch", "typSearch", "selectedVariant"})
    public void cancelSearch()
    {   variantList = variantManager.getAll();
        workplaceSearch = "";
        sachNrLieferantSearch = "";
        descriptionSearch = "";
        nameSearch = "";
        scanStringSearch = "";
        typSearch = "";
        selectedVariant = null;
    }

    @Command
    @NotifyChange({"variantList"})
    public void addNewVariant(){
//        XMLBuilder.buildMapFromXML(myVariant.getXmlModuls());
        Window window1 = (Window) Executions.createComponents(
                "variantEditor/addVariant.zul", null, null);
        window1.doModal();
    }


    @Command
    @NotifyChange({"variantList"})
    public void editModuls(@BindingParam("variant") Variant myVariant){
//        XMLBuilder.buildMapFromXML(myVariant.getXmlModuls());
        if (myVariant.getXmlModuls()!=null){showModal(myVariant);}
        else {Messagebox.show("Nenasiel sa XML subor v databaze", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    public void showModal(Variant myVariant) {
        Map<String, Variant> arguments = new HashMap<String, Variant>();
        arguments.put("myVariant", myVariant);
        Window window = (Window) Executions.createComponents(
                "variantEditor/editVariant.zul", null, arguments);
        window.doModal();
    }

    @Command
    @NotifyChange({"variantList"})
    public void copyVariant(@BindingParam("variant") Variant myVariant){
        if (myVariant.getXmlModuls()!=null){showModal2(myVariant);}
        else {Messagebox.show("Nenasiel sa XML subor v databaze", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    public void showModal2(Variant myVariant) {
        Map<String, Variant> arguments = new HashMap<String, Variant>();
        arguments.put("myVariant", myVariant);
        Window window2 = (Window) Executions.createComponents(
                "variantEditor/copyVariant.zul",null ,arguments);
        window2.doModal();
    }

    @Command
    @NotifyChange({"selectedVariant"})
    public void saveVariant(@ContextParam(ContextType.VIEW) Component comp,
                              @BindingParam("selectedData")Variant editedVariant) {
       // editedVariant.setWorkplace(selectedWorkplaceSave);
       // editedVariant.setColor(selectedColorSave);
        variantManager.saveEditedVariant(editedVariant, user);
        selectedVariant = null;
        search();
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @GlobalCommand
    @NotifyChange({"variantList"})
    public void refresh(){
        if (sachNrLieferantSearch.equals("")/*&&workplaceSearch.equals("")*/&&descriptionSearch.equals("")&&nameSearch.equals("")
                &&scanStringSearch.equals("")){variantList = variantManager.getAllByType(arg1,arg2,arg3);
            Collections.sort(variantList);
        }
        else {variantList = variantManager.findBySachNrLieferant(sachNrLieferantSearch,arg2+arg3,descriptionSearch,nameSearch,scanStringSearch, arg1);
            Collections.sort(variantList);
        }
    }

    @Command
    @NotifyChange({"variantList","selectedColorSave","selectedWorkplaceSave","name","description","scanString","scanRequired","modulsCount","selectedModul11","modul12Vis","modul61Vis", "typ"})
    public void generateNewVariant(@BindingParam("vlayout") Vlayout vlayout) {
        if(selectedWorkplace!=null&&selectedColor!=null&&!myHlayoutList.isEmpty()){

            Set<Moduls> modulsSet = new HashSet<Moduls>();
            mp = new HashMap<Integer, Set<Moduls>>();
            int counterHlayouts = 0;
            boolean hasWrongModulLiefnr=false;
            String nonexistingModuls = "";
            for (MyHlayout myHlayout : myHlayoutList){
               Set<Moduls> modulsInHlayoutSet = new HashSet<Moduls>();
               for(String itemLief : myHlayout.getSelectedModuls()){
               Moduls selectedModul = null;
               if(!modulsManager.findBySachNrLieferant(itemLief).isEmpty()){selectedModul = modulsManager.findBySachNrLieferant(itemLief).get(0);}
               if(selectedModul != null){modulsSet.add(selectedModul);
                                        modulsInHlayoutSet.add(selectedModul);
               }
               else {hasWrongModulLiefnr = true;
                    nonexistingModuls = nonexistingModuls + itemLief + ", ";
                    }
               }
                mp.put(counterHlayouts,modulsInHlayoutSet);
                counterHlayouts++;
             }
            if(!hasWrongModulLiefnr){
            String xmlModuls = XMLBuilder.buildXML(mp);
            int modCount=0;
            for(MyHlayout myHlayout: myHlayoutList){
                if(myHlayout.hasAtLeast1SelectedModul())
                    modCount++;
            }

            variantManager.addNewVariant(selectedWorkplace,selectedColor,name,description,scanString,scanRequired,modCount,modulsSet,xmlModuls, typ, user);
            search();
            selectedWorkplaceSave=null;
            selectedColorSave=null;
            name=null;
            scanString=null;
            description=null;
            typ = null;
            scanRequired = true;
            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
            modul12Vis = false;
            modul61Vis = false;
            selectedModul11 = null;
            for (MyHlayout item: myHlayoutList){
                item.detach();
            }
            for (Label item: plusSet){
                item.detach();
            }
            myHlayoutList =  new ArrayList<MyHlayout>();
            mp =  new HashMap<Integer, Set<Moduls>>();
            }
            else{Messagebox.show(nonexistingModuls, "Nezalozene moduly", Messagebox.OK, Messagebox.ERROR);}
        } else{Messagebox.show("Chyba workplace, color, description alebo moduly", "Error", Messagebox.OK, Messagebox.ERROR);}

    }





    @Command
    @NotifyChange({"modul21Vis", "modul31Vis", "modul41Vis", "modul51Vis", "modul61Vis"})
    public void addTextBoxVert(@ContextParam(ContextType.VIEW) Component view,@BindingParam("vlayout") Vlayout vlayout) {
            if(myHlayoutList.isEmpty()||!myHlayoutList.get(myHlayoutList.size()-1).getSelectedModuls().isEmpty()){
            MyHlayout myHlayout = new MyHlayout();
            vlayout.appendChild(myHlayout);
            myHlayoutList.add(myHlayout);
            Label tempLabel = new Label(" + ");
            vlayout.appendChild(tempLabel);
            plusSet.add(tempLabel);
            }
            else{ Messagebox.show("Vyplnte predosly modul!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    public void exportToExcel() throws Exception{

        File xslFile = variantManager.exportToFile(variantList);
        byte[] buffer = new byte[(int) xslFile.length()];
        FileInputStream fs = new FileInputStream(xslFile);
        fs.read(buffer);
        fs.close();
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        AMedia amedia = new AMedia("VariantReport.csv", "csv", "application/file", is);
        Filedownload.save(amedia);
    }

    @Command
    public void printVariant(@BindingParam("variant") Variant myVariant){

        Map<String, Variant> arguments = new HashMap<String, Variant>();
        arguments.put("myVariant", myVariant);
        Window window = (Window) Executions.createComponents(
                "variantEditor/printVariant.zul",null ,arguments);
        window.doModal();
    }

    @Command
    public void showModuls(@BindingParam("variant") Variant myVariant,@ContextParam(ContextType.VIEW) Component comp) {
        Map<String, Set<Moduls>> arguments = new HashMap<String, Set<Moduls>>();
        System.out.println(myVariant.getModulsSet());
        arguments.put("modulsSet", myVariant.getModulsSet());
        Window window = (Window)Executions.createComponents("variantEditor/showModuls.zul", comp, arguments);
        window.doPopup();
    }

    public List<Variant> getVariantList() {
        return variantList;
    }

    public void setVariantList(List<Variant> variantList) {
        this.variantList = variantList;
    }

    public List<Color> getColorList() {
        return colorList;
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
    }

    public List<Workplace> getWorkplaceList() {
        return workplaceList;
    }

    public void setWorkplaceList(List<Workplace> workplaceList) {
        this.workplaceList = workplaceList;
    }

    public Variant getSelectedVariant() {
        return selectedVariant;
    }

    public void setSelectedVariant(Variant selectedVariant) {
        this.selectedVariant = selectedVariant;
    }

    public Workplace getSelectedWorkplace() {
        return selectedWorkplace;
    }

    public void setSelectedWorkplace(Workplace selectedWorkplace) {
        this.selectedWorkplace = selectedWorkplace;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScanString() {
        return scanString;
    }

    public void setScanString(String scanString) {
        this.scanString = scanString;
    }

    public Boolean getScanRequired() {
        return scanRequired;
    }

    public void setScanRequired(Boolean scanRequired) {
        this.scanRequired = scanRequired;
    }



    public Variant getVariantToDelete() {
        return variantToDelete;
    }

    public void setVariantToDelete(Variant variantToDelete) {
        this.variantToDelete = variantToDelete;
    }

    public Color getSelectedColorSave() {
        return selectedColorSave;
    }

    public void setSelectedColorSave(Color selectedColorSave) {
        this.selectedColorSave = selectedColorSave;
    }

    public Workplace getSelectedWorkplaceSave() {
        return selectedWorkplaceSave;
    }

    public void setSelectedWorkplaceSave(Workplace selectedWorkplaceSave) {
        this.selectedWorkplaceSave = selectedWorkplaceSave;
    }

    public List<String> getModulsList() {
        return modulsList;
    }

    public void setModulsList(List<String> modulsList) {
        this.modulsList = modulsList;
    }

    public String getSelectedModul11() {
        return selectedModul11;
    }

    public void setSelectedModul11(String selectedModul11) {
        this.selectedModul11 = selectedModul11;
    }

    public boolean isModul12Vis() {
        return modul12Vis;
    }

    public void setModul12Vis(boolean modul12Vis) {
        this.modul12Vis = modul12Vis;
    }

    public boolean isModul61Vis() {
        return modul61Vis;
    }

    public void setModul61Vis(boolean modul61Vis) {
        this.modul61Vis = modul61Vis;
    }

    public String getSachNrLieferantSearch() {
        return sachNrLieferantSearch;
    }

    public void setSachNrLieferantSearch(String sachNrLieferantSearch) {
        this.sachNrLieferantSearch = sachNrLieferantSearch;
    }

    public String getWorkplaceSearch() {
        return workplaceSearch;
    }

    public void setWorkplaceSearch(String workplaceSearch) {
        this.workplaceSearch = workplaceSearch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionSearch() {
        return descriptionSearch;
    }

    public void setDescriptionSearch(String descriptionSearch) {
        this.descriptionSearch = descriptionSearch;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public String getScanStringSearch() {
        return scanStringSearch;
    }

    public void setScanStringSearch(String scanStringSearch) {
        this.scanStringSearch = scanStringSearch;
    }

    public String getTypSearch() {
        return typSearch;
    }

    public void setTypSearch(String typSearch) {
        this.typSearch = typSearch;
    }

    public List<String> getTypList() {
        return typList;
    }

    public void setTypList(List<String> typList) {
        this.typList = typList;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getArg3() {
        return arg3;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }
}
