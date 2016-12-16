package com.leoni.viewModel;

import com.leoni.data.manager.*;
import com.leoni.data.models.*;
import com.leoni.jcc.listModel.GenericListModelImpl;
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
import org.zkoss.zul.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 9.11.2012
 * Time: 10:25
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver (org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ModulsEditorViewModel
    {
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
        private VariantManager variantManager;

        @WireVariable
        private ColorManager colorManager;

        @WireVariable
        private PrintingManager printingManager;

        @WireVariable
        private UsersManager usersManager;

        @Wire
        private Image image;

    @Wire ("#modulsGrid")
    private Grid modulsGrid;
    private List<Moduls> modulsList = new ArrayList<Moduls>();
    private Moduls modulsFilter;
    private String id="";
    private String sachNrBestSearch="";
    private String sachNrLieferantSearch="";
    private String prodGruppeSearch="";
    private String ausfuehrungSearch="";
    private String sachNrBest;
    private String sachNrLieferant;
    private GenericListModelImpl myListModel;
    private Moduls selectedModul;
    private String prodGruppe="991";
    private String kabelSatz="F";
    private String ausfuehrung="LL";
    private Integer difficulty = 1;
    private String commentary="";
    private String createPerson="jit";
    private boolean grund=false;
    private boolean blocked=false;
    private boolean modul12Vis = false;
    private List<ModulsAlias> aliasList = new ArrayList<>();
    private String foamForm;
    private String carBody;
    private List<Color> colorList = new ArrayList<>();
    private List<Integer> difficultyList = new ArrayList<>();
    private Color selectedColor;
    private String user;
    private String kanbanSearch;
    private String kanbanInfo;
    private String assTime;
    private Moduls kanbanModul;
        private static final String MODULS_LIEF = "^[0-9]{8}[A-Z]$";
        private static final String MODULS_BEST = "\\s";
    private Moduls modulToDelete;



    @AfterCompose
    public void init(@ContextParam (ContextType.VIEW) Component view)
        {
        Selectors.wireComponents(view, this, false);
        modulsList=modulsManager.getAll();
        colorList = colorManager.getAll();
        difficultyList.add(1);
        difficultyList.add(2);
        //difficultyList.add(3);
        kanbanInfo = "Naskenuj modul";
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        image.setSrc("images/kanban1.png");

        }

    @Command
    @NotifyChange ({"modulsList","selectedModul"})
    public void search()
        {    if (id.equals("")&&sachNrLieferantSearch.equals("")&&sachNrBestSearch.equals("")&&ausfuehrungSearch.equals("")&&prodGruppeSearch.equals("")){
            modulsList=modulsManager.getAll();}
        else modulsList = modulsManager.findByForModuls(id,sachNrLieferantSearch.toUpperCase(),
                    sachNrBestSearch.toUpperCase(),ausfuehrungSearch.toUpperCase(),prodGruppeSearch);
        selectedModul = null;
        }

    @Command
    @NotifyChange ({"modulsList","id","sachNrLieferantSearch","sachNrBestSearch","ausfuehrungSearch","prodGruppeSearch","selectedModul"})
    public void cancelSearch()
        {   modulsList = modulsManager.getAll();
            id = "";
            sachNrLieferantSearch = "";
            sachNrBestSearch = "";
            ausfuehrungSearch = "";
            prodGruppeSearch = "";
            selectedModul = null;
        }

    @Command
    @NotifyChange ({"modulsList"})
        public void searchById()
        {
            if (id.equals("")){modulsList=modulsManager.getAll();}
            else modulsList = modulsManager.findById(Integer.parseInt(id));
        };


    @Command
    @NotifyChange ({"kanbanSearch","kanbanInfo"})
    public void searchKanban()
        {
            if (kanbanSearch != null && kanbanSearch.toUpperCase().startsWith("P") && kanbanSearch.length() == 10){
               if (!modulsManager.findBySachNrLieferant(kanbanSearch.substring(1).toUpperCase()).isEmpty()){
               kanbanModul =  modulsManager.findBySachNrLieferant(kanbanSearch.substring(1).toUpperCase()).get(0);
               kanbanSearch = null;
               image.setSrc("images/kanban2.png");
               kanbanInfo = "Naskenuj pocet pre " + kanbanModul.getSachNrLieferant().trim() + "(" +
                       kanbanModul.getSachNrBest().trim()+ ")" ;
               }
            else {Messagebox.show("Lieferant cislo neexistuje!", "Error", Messagebox.OK, Messagebox.ERROR);}
            }
            else if (kanbanSearch != null && kanbanSearch.toUpperCase().startsWith("Q") && kanbanModul != null){
                int quantity = Integer.parseInt(kanbanSearch.substring(1));
                Users users = usersManager.getUser(user);
                printingManager.printModulLabel(kanbanModul, users, quantity);
                kanbanModul = null;
                kanbanSearch = null;
                image.setSrc("images/kanban1.png");
                kanbanInfo = "Naskenuj modul";
            }
            else{
                kanbanSearch = null;
                Messagebox.show("Zly scan!", "Error", Messagebox.OK, Messagebox.ERROR);
            }
        };

    @Command
    @NotifyChange ({"kanbanSearch","kanbanInfo"})
    public void clearKanban()
         {
           kanbanSearch = null;
           kanbanInfo = "Naskenuj modul";
           image.setSrc("images/kanban1.png");
           kanbanModul = null;
         }



    @Command
    @NotifyChange ({"modulsList"})
    public void searchBySachNrBest()
        {
            if (sachNrBestSearch.equals("")){modulsList=modulsManager.getAll();}
            else modulsList = modulsManager.findBySachNrBest(sachNrBestSearch);
        }

    @Command
    @NotifyChange ({"modulsList"})
        public void searchBySachNrLieferant()
        {
            if (sachNrLieferantSearch.equals("")){modulsList=modulsManager.getAll();}
            else modulsList = modulsManager.findBySachNrLieferant(sachNrLieferantSearch);
        }

    @Command
    @NotifyChange({"modulsList"})
        public void copyModul(@BindingParam("modul") Moduls myModul) {
            showModal(myModul);
            //modulsList=modulsManager.getAll();
            search();
        }
        public void showModal(Moduls myModul) {
            Map<String, Moduls> arguments = new HashMap<String, Moduls>();
            arguments.put("myModul", myModul);
            Window window = (Window) Executions.createComponents(
                    "modulsEditor/copyModul.zul",null ,arguments);
            window.doModal();
        }

    @Command
    @NotifyChange({"modulsList"})
        public void showInfoModul(@BindingParam("modul") Moduls myModul) {
        Map<String, Moduls> arguments = new HashMap<String, Moduls>();
        arguments.put("myModul", myModul);
        Window window = (Window) Executions.createComponents(
                "modulsEditor/showInfoModul.zul",null ,arguments);
        window.doModal();
        search();
        }


    @Command
    @NotifyChange({"modulsList"})
        public void editModul(@BindingParam("modul") Moduls myModul) {
            showModalEdit(myModul);
            modulsList=modulsManager.getAll();
        }
        public void showModalEdit(Moduls myModul) {
            Map<String, Moduls> arguments = new HashMap<String, Moduls>();
            arguments.put("myModul", myModul);
            Window window = (Window) Executions.createComponents(
                    "modulsEditor/editModul.zul",null ,arguments);
            window.doModal();
        }

        @Command
        @NotifyChange({"modulsList","sachNrBest","sachNrLieferant","prodGruppe","kabelSatz","ausfuehrung","grund",
                "blocked","commentary","createPerson", "foamForm", "carBody", "selectedColor","difficulty","assTime"})
        public void generateNewModul() {

            if(sachNrBest!=null&&sachNrLieferant!=null&&selectedColor!=null&&assTime!=null&&
                    isNumeric(assTime)){
                Pattern pattern = Pattern.compile(MODULS_LIEF);
                Pattern pattern2 = Pattern.compile(MODULS_BEST);
                Matcher matcher = pattern.matcher(sachNrLieferant);
                Matcher matcher2 = pattern2.matcher(sachNrBest.trim());
            if (matcher.matches()&&!matcher2.find()){
            if(modulsManager.findBySachNrLieferant(sachNrLieferant).isEmpty()){
            modulsManager.addNewModul(sachNrBest,sachNrLieferant,prodGruppe,kabelSatz,ausfuehrung,grund,blocked,
                    commentary, aliasList, createPerson,user, foamForm, carBody, selectedColor, difficulty,assTime);
            modulsList=modulsManager.getAll();
            sachNrBest=null;
            sachNrLieferant=null;
            selectedColor = null;
            prodGruppe="991";
            kabelSatz="F";
            ausfuehrung="LL";
            grund=false;
            blocked=false;
            commentary="";
            createPerson="jit";
            foamForm = "";
            carBody = "";
            difficulty = 1;
            assTime = null;
            aliasList.clear();
            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
            }
            else{ Messagebox.show("SachNrLief uz existuje!", "Error", Messagebox.OK, Messagebox.ERROR);}
            }
            else{ Messagebox.show("Zle zadane lief nr. alebo best nr.", "Error", Messagebox.OK, Messagebox.ERROR);}
            }
            else{ Messagebox.show("Nezadali ste sch nr. alebo lief nr. alebo farbu alebo cas spracovania!", "Error", Messagebox.OK, Messagebox.ERROR);}

        }

        @Command
        @NotifyChange ({"modulsList"})
        public void deleteModul(@BindingParam("modul") Moduls myModul)
        {
            modulToDelete=myModul;
            Messagebox.show("Are you sure?", "Delete?", Messagebox.YES|Messagebox.NO,
                    Messagebox.QUESTION,
                    new EventListener() {
                        public void onEvent(Event evt) {
                            if ("onYes".equals(evt.getName())) {
                     //if(variantManager.findVariantsWithModul(modulToDelete).isEmpty())
                     {
                    List<SicherungenRelais9X1Wrm>  sicherungenRelaisList =  sicherungenRelais9X1WrmManager.findBySachNrLieferant(modulToDelete.getSachNrLieferant());
                    List<SchrittModulRm9X1Wrm>  schrittList =  schrittManager.findBySachNrLieferant(modulToDelete.getSachNrLieferant());
                    List<VsModulyWrm>  vsModulyList =  vsModulyWrmManager.findBySachNrLieferant(modulToDelete.getSachNrLieferant());
                    List<ModulClipListWrm>  clipList =  modulClipListWrmManager.findBySachNrLieferant(modulToDelete.getSachNrLieferant());
                    List<Foam>  foamList =  foamManager.findBySachNrLieferant(modulToDelete.getSachNrLieferant());
                    List<Variant> variantList = variantManager.findVariantsWithModul(modulToDelete);
                    for (SicherungenRelais9X1Wrm item : sicherungenRelaisList){
                        sicherungenRelais9X1WrmManager.delete(item);
                    }
                    for (SchrittModulRm9X1Wrm item : schrittList){
                                    schrittManager.delete(item);
                    }
                    for (VsModulyWrm item : vsModulyList){
                                    vsModulyWrmManager.delete(item);
                    }
                    for (ModulClipListWrm item : clipList){
                                    modulClipListWrmManager.delete(item);
                    }
                    for (Foam item : foamList){
                                    foamManager.delete(item);
                    }
                    //System.out.println(variantList);
                    for (Variant item : variantList){
                                    boolean modulExist = false;
                                    Set<Moduls> tempModulsSet = item.getModulsSet();
                                    Set<Moduls> newModulsSet = new HashSet<>();
                                    for(Moduls moduls:tempModulsSet){
                                        if (!moduls.getSachNrLieferant().trim().equals(modulToDelete.getSachNrLieferant().trim())) newModulsSet.add(moduls);
                                        else modulExist = true;
                                    }
                                    if(modulExist){
                                    Map<Integer,Set<Integer>> modulsIdMap = XMLBuilder.buildMapFromXML(item.getXmlModuls());

                                        for (Map.Entry<Integer, Set<Integer>> entry : modulsIdMap.entrySet())
                                        {
                                            Iterator<Integer> iter = entry.getValue().iterator();
                                            while (iter.hasNext()) {
                                                Integer id = iter.next();

                                                if (id.intValue()==modulToDelete.getId().intValue())
                                                    iter.remove();
                                            }
                                            /*for (Integer id: entry.getValue()){
                                                if (id.intValue()==modulToDelete.getId().intValue()){entry.getValue().remove(id);}
                                            } */
                                        }
                                        //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+modulsIdMap);
                                        Map<Integer,Set<Moduls>> modulsMap = new HashMap<>();
                                        int mapCounter = 0;
                                        for (Map.Entry<Integer, Set<Integer>> entry : modulsIdMap.entrySet()){
                                           if (!entry.getValue().isEmpty()){
                                               Set<Moduls> modulsSet = new HashSet<>();
                                               for(Integer modulId : entry.getValue()){
                                                   if(!modulsManager.findById(modulId).isEmpty()){
                                                   Moduls modul = modulsManager.findById(modulId).get(0);
                                                   modulsSet.add(modul);}
                                               }
                                               modulsMap.put(mapCounter,modulsSet);
                                               mapCounter++;
                                           }
                                        }
                                    //System.out.println(XMLBuilder.buildXML(modulsMap));
                                    item.setXmlModuls(XMLBuilder.buildXML(modulsMap));
                                    item.setModulsCount(modulsMap.size());
                                    }

                                    item.setModulsSet(newModulsSet);
                                    variantManager.saveOrUpdate(item);
                                }
                    modulsManager.delete(modulToDelete);
                    modulsList=modulsManager.getAll();
                    BindUtils.postNotifyChange(null, null, ModulsEditorViewModel.this, "modulsList");
                                }
                                //else{Messagebox.show("Najskor vymazte modul z variant!", "Error", Messagebox.OK, Messagebox.ERROR);}
                            }
                        }
                    }
            );


        }

        @Command
        public void print(@BindingParam("modul") Moduls myModul){

            Map<String, Moduls> arguments = new HashMap<String, Moduls>();
            arguments.put("myModul", myModul);
            Window window = (Window) Executions.createComponents(
                    "modulsEditor/printModul.zul",null ,arguments);
            window.doModal();
        }

        @Command
        @NotifyChange({"selectedModul"})
        public void saveModul(@ContextParam(ContextType.VIEW) Component comp,
                                 @BindingParam("selectedData") Moduls editedModul) {

            if(selectedModul.getSachNrLieferant().trim().equals(editedModul.getSachNrLieferant().trim())||modulsManager.findBySachNrLieferant(editedModul.getSachNrLieferant()).isEmpty()){

            modulsManager.saveEditedModul(editedModul,user);
            modulsList=modulsManager.getAll();
            selectedModul = null;
            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
            }
            else{ Messagebox.show("SachNrLief uz existuje!", "Error", Messagebox.OK, Messagebox.ERROR);}
            //{jobOrderControllerImpl.setJobOrderNrOfHarnesses(editedJobOrder.getId(),changeSelectedNumber);}

        }

        @Command
        public void showAlias(@BindingParam("modul") Moduls myModul,@ContextParam(ContextType.VIEW) Component comp) {
            Map<String, List<ModulsAlias>> arguments = new HashMap<>();
            //System.out.println(myVariant.getModulsSet());
            arguments.put("aliasList", myModul.getAliasList());
            Window window = (Window)Executions.createComponents("modulsEditor/showAlias.zul", comp, arguments);
            window.doPopup();
        }

        @Command
        public void exportToExcel() throws Exception{

            File xslFile = modulsManager.exportToFile(modulsList);
            byte[] buffer = new byte[(int) xslFile.length()];
            FileInputStream fs = new FileInputStream(xslFile);
            fs.read(buffer);
            fs.close();
            ByteArrayInputStream is = new ByteArrayInputStream(buffer);
            AMedia amedia = new AMedia("ModulsReport.csv", "csv", "application/file", is);
            Filedownload.save(amedia);
        }

        @Command
        @NotifyChange({"modulsList"})
        public void editAlias(@BindingParam("modul") Moduls myModul) {
            showModalAlias(myModul);
            //modulsList=modulsManager.getAll();
            search();
        }
        public void showModalAlias(Moduls myModul) {
            Map<String, Moduls> arguments = new HashMap<String, Moduls>();
            arguments.put("myModul", myModul);
            Window window = (Window) Executions.createComponents(
                    "modulsEditor/editAlias.zul",null ,arguments);
            window.doModal();
        }

        @GlobalCommand
        @NotifyChange({"modulsList"})
        public void refresh(){
            search();
        }

        public static boolean isNumeric(String str)
        {
            //return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
            //return str.matches("^(\\d{1,2}\\.)?\\d{0,3}$");
            return str.matches("^\\d{1,2}(\\.\\d{0,3})?$");
        }
    public List<Moduls> getModulsList()
        {
        return modulsList;
        }

    public void setModulsList(List<Moduls> modulsList)
        {
        this.modulsList = modulsList;
        }

    public ModulsManager getModulsManager()
        {
        return modulsManager;
        }

    public void setModulsManager(ModulsManager modulsManager)
        {
        this.modulsManager = modulsManager;
        }

    public Moduls getSelectedModul()
        {
        return selectedModul;
        }

    public void setSelectedModul(Moduls selectedModul)
        {
        this.selectedModul = selectedModul;
        }
        public String getSachNrBestSearch()
        {
            return sachNrBestSearch;
        }

        public void setSachNrBestSearch(String sachNrBestSearch)
        {
            this.sachNrBestSearch = sachNrBestSearch;
        }

        public String getSachNrLieferantSearch()
        {
            return sachNrLieferantSearch;
        }

        public void setSachNrLieferantSearch(String sachNrLieferantSearch)
        {
            this.sachNrLieferantSearch = sachNrLieferantSearch;
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

    public String getId()
        {
            return id;
        }

    public void setId(String id)
        {
            this.id = id;
        }
        public String getProdGruppe()
        {
            return prodGruppe;
        }

        public void setProdGruppe(String prodGruppe)
        {
            this.prodGruppe = prodGruppe;
        }
        public String getKabelSatz()
        {
            return kabelSatz;
        }

        public void setKabelSatz(String kabelSatz)
        {
            this.kabelSatz = kabelSatz;
        }
        public String getAusfuehrung()
        {
            return ausfuehrung;
        }

        public void setAusfuehrung(String ausfuehrung)
        {
            this.ausfuehrung = ausfuehrung;
        }
        public boolean getGrund()
        {
            return grund;
        }

        public void setGrund(boolean grund)
        {
            this.grund = grund;
        }
        public boolean getBlocked()
        {
            return blocked;
        }

        public void setBlocked(boolean blocked)
        {
            this.blocked = blocked;
        }

        public String getCommentary() {
            return commentary;
        }

        public void setCommentary(String commentary) {
            this.commentary = commentary;
        }

        public String getCreatePerson() {
            return createPerson;
        }

        public void setCreatePerson(String createPerson) {
            this.createPerson = createPerson;
        }

        public boolean isModul12Vis() {
            return modul12Vis;
        }

        public void setModul12Vis(boolean modul12Vis) {
            this.modul12Vis = modul12Vis;
        }

        public String getFoamForm() {
            return foamForm;
        }

        public void setFoamForm(String foamForm) {
            this.foamForm = foamForm;
        }

        public String getCarBody() {
            return carBody;
        }

        public void setCarBody(String carBody) {
            this.carBody = carBody;
        }

        public Color getSelectedColor() {
            return selectedColor;
        }

        public void setSelectedColor(Color selectedColor) {
            this.selectedColor = selectedColor;
        }

        public List<Color> getColorList() {
            return colorList;
        }

        public void setColorList(List<Color> colorList) {
            this.colorList = colorList;
        }

        public Integer getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(Integer difficulty) {
            this.difficulty = difficulty;
        }

        public List<Integer> getDifficultyList() {
            return difficultyList;
        }

        public void setDifficultyList(List<Integer> difficultyList) {
            this.difficultyList = difficultyList;
        }

        public String getKanbanSearch() {
            return kanbanSearch;
        }

        public void setKanbanSearch(String kanbanSearch) {
            this.kanbanSearch = kanbanSearch;
        }

        public String getKanbanInfo() {
            return kanbanInfo;
        }

        public void setKanbanInfo(String kanbanInfo) {
            this.kanbanInfo = kanbanInfo;
        }

        public String getAssTime() {
            return assTime;
        }

        public void setAssTime(String assTime) {
            this.assTime = assTime;
        }


    }
