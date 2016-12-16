package com.leoni.viewModel;

import com.leoni.data.manager.*;
import com.leoni.data.models.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 19.12.2013
 * Time: 8:48
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CopyModulVM {

    @WireVariable
    private ModulsManager modulsManager;

    @WireVariable
    private SchrittModulRm9X1WrmManager schrittManager;

    @WireVariable
    private SicherungenRelais9X1WrmManager sicherungenRelais9X1WrmManager;

    @WireVariable
    private FoamManager foamManager;

    @WireVariable
    private ModulClipListWrmManager modulClipListWrmManager;

    @WireVariable
    private VsModulyWrmManager vsModulyWrmManager;

    @WireVariable
    private GrundVarianteModuleManager grundVarianteModuleManager;

    @WireVariable
    private VariantManager variantManager;



    private String sachNrBest;
    private String sachNrLieferant;
    private Moduls myModul;
    private int oldModulId;
    private List<SicherungenRelais9X1Wrm> sicherungenList;
    private List<Foam> foamList;
    private List<SchrittModulRm9X1Wrm> schrittList;
    private List<ModulClipListWrm> modulClipList;
    private List<VsModulyWrm> vsModulList;
    private List<GrundVarianteModule> grundVarianteModuleList;
    private List<Variant> modulInVariantList = new ArrayList<>();
    private List<Variant> selectedVariantsToCopy = new ArrayList<>();
    private boolean sicherungenTab=true;
    private boolean schrittTab=true;
    private boolean modulClipTab=true;
    private boolean vsModulTab=true;
    private boolean foamTab=true;
    private boolean modulesTab=true;
    private boolean grundVarianteTab=true;
    private String user;
    private Map<Integer,Set<Integer>> modulsMap = new HashMap<Integer, Set<Integer>>();


    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("myModul") Moduls myObject) throws Exception {
        myModul=myObject;
        oldModulId=myModul.getId();
        sachNrBest=myModul.getSachNrBest();
        sachNrLieferant=myModul.getSachNrLieferant().trim();
        List<Variant> allVriants = variantManager.getAll();

        for (Variant tempVariant : allVriants) {
             Set<Moduls> tempModulsSet = tempVariant.getModulsSet();
            boolean isInselectedVriants = false;
            for (Moduls tempModul : tempModulsSet) {
                if (!isInselectedVriants&&tempModul.getSachNrLieferant().trim().contains(sachNrLieferant)){modulInVariantList.add(tempVariant);
                    isInselectedVriants = true;
                }
            }
        }

        if (!modulInVariantList.isEmpty()){
            Map<String, List<Variant>> arguments2 = new HashMap<>();
            arguments2.put("myVariantList", modulInVariantList);
            Window window2 = (Window) Executions.createComponents(
                    "modulsEditor/copyVariants.zul", view, arguments2);
            window2.doModal();
        }
       user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getSachNrBest()
    {
        return sachNrBest;
    }

    public void setSachNrBest(String sachNrBest)
    {
        this.sachNrBest = sachNrBest;
    }

    public String getSachNrLieferant()
    {
        return sachNrLieferant;
    }

    public void setSachNrLieferant(String sachNrLieferant)
    {
        this.sachNrLieferant = sachNrLieferant;
    }

    public boolean isFoamTab() {
        return foamTab;
    }

    public void setFoamTab(boolean foamTab) {
        this.foamTab = foamTab;
    }

    public boolean isVsModulTab() {
        return vsModulTab;
    }

    public void setVsModulTab(boolean vsModulTab) {
        this.vsModulTab = vsModulTab;
    }

    public boolean isSchrittTab() {
        return schrittTab;
    }

    public void setSchrittTab(boolean schrittTab) {
        this.schrittTab = schrittTab;
    }

    public boolean isModulClipTab() {
        return modulClipTab;
    }

    public void setModulClipTab(boolean modulClipTab) {
        this.modulClipTab = modulClipTab;
    }

    public boolean isSicherungenTab() {
        return sicherungenTab;
    }

    public void setSicherungenTab(boolean sicherungenTab) {
        this.sicherungenTab = sicherungenTab;
    }

    public boolean isModulesTab() {
        return modulesTab;
    }

    public void setModulesTab(boolean modulesTab) {
        this.modulesTab = modulesTab;
    }

    public boolean isGrundVarianteTab() {
        return grundVarianteTab;
    }

    public void setGrundVarianteTab(boolean grundVarianteTab) {
        this.grundVarianteTab = grundVarianteTab;
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {
        if((!modulesTab&&!modulsManager.findBySachNrLieferant(sachNrLieferant).isEmpty())||modulsManager.findBySachNrLieferant(sachNrLieferant).isEmpty()){
        sicherungenList = sicherungenRelais9X1WrmManager.getObjectsByModulsId(myModul.getId());
        foamList = foamManager.getObjectsByLiefNr(myModul.getSachNrLieferant());
        schrittList = schrittManager.getObjectsByModulsId(myModul.getId());
        modulClipList = modulClipListWrmManager.getObjectsByModulsId(myModul.getId());
        vsModulList = vsModulyWrmManager.getObjectsByModulsId(myModul.getId());
        grundVarianteModuleList = grundVarianteModuleManager.getObjectsByModulsId(myModul.getId());
        Moduls newModul;
        if(!modulesTab){newModul = modulsManager.findBySachNrLieferant(sachNrLieferant).get(0);}
        else newModul = modulsManager.addCopiedModul(myModul,sachNrBest,sachNrLieferant,user);
            if(sicherungenTab){
            for(SicherungenRelais9X1Wrm item : sicherungenList ){
                item.setModuls(newModul);
                item.setId(null);
                item.setChangedDate(new Date());
                item.setChangedBy(user);
                System.out.println(sicherungenRelais9X1WrmManager.create(item).getId());
            }
            }
            if(schrittTab){
        for(SchrittModulRm9X1Wrm item : schrittList ){
                item.setModuls(newModul);
                item.setId(null);
            item.setChangedDate(new Date());
            item.setChangedBy(user);
            System.out.println(schrittManager.create(item).getId());
            }
            }
            if(modulClipTab){
        for(ModulClipListWrm item : modulClipList ){
                item.setModuls(newModul);
                item.setId(null);
            item.setChangedDate(new Date());
            item.setChangedBy(user);
            System.out.println(modulClipListWrmManager.create(item).getId());
            }
            }
            if(vsModulTab){
        for(VsModulyWrm item : vsModulList ){
                item.setModuls(newModul);
                item.setId(null);
            item.setChangedDate(new Date());
            item.setChangedBy(user);
            System.out.println(vsModulyWrmManager.create(item).getId());
            }
            }
            if(foamTab){
        for(Foam item : foamList ){
                item.setSachNrLief(newModul.getSachNrLieferant());
                item.setSachNrBest(newModul.getSachNrBest());
                //item.setOid(null);
            item.setChangedDate(new Date());
            item.setChangedBy(user);
            System.out.println(foamManager.create(item));
            }
            }
            if(grundVarianteTab){
                System.out.println("list"+grundVarianteModuleList);
                for(GrundVarianteModule item : grundVarianteModuleList ){
                    item.setModuls(newModul);
                    item.setId(null);
                    System.out.println(grundVarianteModuleManager.create(item).getId());
                }
            }
            if(!selectedVariantsToCopy.isEmpty()){
                for(Variant item : selectedVariantsToCopy){
                   if(item.getXmlModuls()!=null&&!item.getXmlModuls().trim().equals(""))
                   {Set<Moduls> modulsSet = item.getModulsSet();
                   modulsSet.add(newModul);
                   item.setModulsSet(modulsSet);
                   modulsMap =  XMLBuilder.buildMapFromXML(item.getXmlModuls());
                   Map<Integer, Set<Moduls>> newModulsMap = new HashMap<>();
                   for (Map.Entry<Integer, Set<Integer>> entry : modulsMap.entrySet())
                       {   Set<Integer> modulsOr = entry.getValue();
                           Set<Moduls> newModulsOr = new HashSet<>();
                           for (Integer modulId : modulsOr) {
                              newModulsOr.add(modulsManager.findById(modulId).get(0));
                              if (modulId==oldModulId){newModulsOr.add(newModul);}
                           }
                           newModulsMap.put(entry.getKey(),newModulsOr);
                       }
                   String xmlModuls =  XMLBuilder.buildXML(newModulsMap);
                   item.setXmlModuls(xmlModuls);
                   variantManager.saveEditedVariant(item,user);}
                   else{Messagebox.show("Nenasiel sa XML subor v databaze, modul sa nenakopiroval do varant!", "Error", Messagebox.OK, Messagebox.ERROR);}
                }
            }
            BindUtils.postGlobalCommand(null, null, "refresh", null);
            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
            view.detach();
        }
        else{ Messagebox.show("SachNrLief uz existuje!", "Error", Messagebox.OK, Messagebox.ERROR);}


    }

    @Command
    public void setVariantsToCopy(@ContextParam(ContextType.VIEW) Component view,
                                  @BindingParam("selectedVariants")  List<Variant> selectedVariants) {
        selectedVariantsToCopy = selectedVariants;
    }
}
