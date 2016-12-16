package com.leoni.viewModel;

import com.leoni.data.manager.ColorManager;
import com.leoni.data.manager.NewModulesManager;
import com.leoni.data.models.Color;
import com.leoni.data.models.NewModules;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2014
 * Time: 9:46
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class NewModulesVM {
    @WireVariable
    private NewModulesManager newModulesManager;

    private List<NewModules> nmList = new ArrayList<NewModules>();
    private NewModules selectedNewModule;
    private String sachNrBest;
    private String notiz;
    private NewModules nmToDelete;
    private String sachNrBestSearch = "";
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        nmList = newModulesManager.getAll();
    }

    @Command
    @NotifyChange({"nmList"})
    public void deleteNewModule(@BindingParam("newModule") NewModules newModule)
    {
        nmToDelete=newModule;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            //System.out.println(nmToDelete.getSachNrBest());
                            newModulesManager.delete(nmToDelete);
                            nmList = newModulesManager.getAll();
                            BindUtils.postNotifyChange(null, null, NewModulesVM.this, "nmList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange({"selectedNewModule"})
    public void saveNewModule(@ContextParam(ContextType.VIEW) Component comp,
                          @BindingParam("selectedData")NewModules editedNewModule) {
        newModulesManager.saveNewModule(editedNewModule);
        nmList=newModulesManager.getAll();
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"nmList","sachNrBest","notiz"})
    public void generateNewModule() {
        newModulesManager.addNewModule(sachNrBest,notiz);
        nmList = newModulesManager.getAll();
        sachNrBest=null;
        notiz=null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange ({"nmList"})
    public void search()
    {    if (sachNrBestSearch.equals("")){
            nmList=newModulesManager.getAll();}
         else nmList = newModulesManager.findBySachNrBest(sachNrBestSearch);
    }

    public NewModules getNmToDelete() {
        return nmToDelete;
    }

    public void setNmToDelete(NewModules nmToDelete) {
        this.nmToDelete = nmToDelete;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    public String getSachNrBest() {
        return sachNrBest;
    }

    public void setSachNrBest(String sachNrBest) {
        this.sachNrBest = sachNrBest;
    }

    public NewModules getSelectedNewModule() {
        return selectedNewModule;
    }

    public void setSelectedNewModule(NewModules selectedNewModule) {
        this.selectedNewModule = selectedNewModule;
    }

    public List<NewModules> getNmList() {
        return nmList;
    }

    public void setNmList(List<NewModules> nmList) {
        this.nmList = nmList;
    }

    public String getSachNrBestSearch() {
        return sachNrBestSearch;
    }

    public void setSachNrBestSearch(String sachNrBestSearch) {
        this.sachNrBestSearch = sachNrBestSearch;
    }
}