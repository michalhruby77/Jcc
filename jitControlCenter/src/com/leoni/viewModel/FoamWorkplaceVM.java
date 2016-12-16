package com.leoni.viewModel;

import com.leoni.data.manager.FoamWorkplaceManager;
import com.leoni.data.manager.FoamWorkplaceModulsManager;
import com.leoni.data.models.FoamWorkplace;
import com.leoni.data.models.FoamWorkplaceModuls;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.10.2014
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FoamWorkplaceVM {
    @WireVariable
    private FoamWorkplaceModulsManager foamWorkplaceModulsManager;

    @WireVariable
    private FoamWorkplaceManager foamWorkplaceManager;

    private List<FoamWorkplace> foamWorkplaceList;
    private FoamWorkplace selectedFoamWorkplace;
    private FoamWorkplace foamWorkplaceDelete;
    private Integer workplace;
    private String form;
    private String template;
    private String workplaceName;
    private String description;
    List<Textbox> textboxList = new ArrayList<Textbox>();


    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view/*,@ContextParam(ContextType.BINDER) Binder _binder*/)
    {
        foamWorkplaceList = foamWorkplaceManager.getAll();

    }

    @Command
    @NotifyChange({"foamWorkplaceList"})
    public void deleteFoamWorkplace(@BindingParam("foamWorkplace") FoamWorkplace myFoamWorkplace)
    {
        foamWorkplaceDelete=myFoamWorkplace;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            foamWorkplaceManager.delete(foamWorkplaceDelete);
                            foamWorkplaceList = foamWorkplaceManager.getAll();
                            BindUtils.postNotifyChange(null, null, FoamWorkplaceVM.this, "foamWorkplaceList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange({"selectedFoamWorkplace"})
    public void saveFoamWorkplace(@ContextParam(ContextType.VIEW) Component comp,
                            @BindingParam("selectedData")FoamWorkplace editedFoamWorkplace) {
        foamWorkplaceManager.saveOrUpdate(editedFoamWorkplace);
        foamWorkplaceList = foamWorkplaceManager.getAll();
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"foamWorkplaceList"})
    public void editModuls(@BindingParam("foamWorkplace") FoamWorkplace foamWorkplace){
//        XMLBuilder.buildMapFromXML(myVariant.getXmlModuls());
          showModal(foamWorkplace);
    }

    public void showModal(FoamWorkplace foamWorkplace) {
        Map<String, FoamWorkplace> arguments = new HashMap<String, FoamWorkplace>();
        arguments.put("myFoamWorkplace", foamWorkplace);
        Window window = (Window) Executions.createComponents(
                "foamWorkplace/editModuls.zul", null, arguments);
        window.doModal();
    }

    @Command
    public void addTextBoxVert(@BindingParam("vlayout") Vlayout vlayout) {
        //textboxList = new ArrayList<Textbox>();
        if(textboxList.isEmpty()||!textboxList.get(textboxList.size()-1).getValue().trim().equals(""))
        {
            Textbox myTextbox = new Textbox();
            vlayout.appendChild(myTextbox);
            textboxList.add(myTextbox);
        }
        else{ Messagebox.show("Vyplnte predosly modul!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    @NotifyChange({"foamWorkplaceList","workplace","form","template","workplaceName","description"})
    public void submit(@BindingParam("vlayout") Vlayout vlayout) {
        if(foamWorkplaceManager.findWorkplace(workplace).isEmpty()){
        List<String> prefixList = new ArrayList<String>();
        for(Textbox item: textboxList){
            if(!item.getValue().trim().equals("")){
                prefixList.add(item.getValue().trim());
            }
        }
        foamWorkplaceManager.add(workplace,form,template,workplaceName,description,prefixList);
        foamWorkplaceList = foamWorkplaceManager.getAll();
        workplace = null;
        form = null;
        template = null;
        workplaceName = null;
        description = null;
            Components.removeAllChildren(vlayout);
        textboxList = new ArrayList<Textbox>();
            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
        }
        else{
            Messagebox.show("Pracovisko uz existuje!", "Error", Messagebox.OK, Messagebox.ERROR);
        }

    }

    @GlobalCommand
    @NotifyChange({"foamWorkplaceList"})
    public void refreshFoamWorkplace(){
        foamWorkplaceList = foamWorkplaceManager.getAll();
    }

    public List<FoamWorkplace> getFoamWorkplaceList() {
        return foamWorkplaceList;
    }

    public void setFoamWorkplaceList(List<FoamWorkplace> foamWorkplaceList) {
        this.foamWorkplaceList = foamWorkplaceList;
    }

    public FoamWorkplace getSelectedFoamWorkplace() {
        return selectedFoamWorkplace;
    }

    public void setSelectedFoamWorkplace(FoamWorkplace selectedFoamWorkplace) {
        this.selectedFoamWorkplace = selectedFoamWorkplace;
    }

    public Integer getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Integer workplace) {
        this.workplace = workplace;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getWorkplaceName() {
        return workplaceName;
    }

    public void setWorkplaceName(String workplaceName) {
        this.workplaceName = workplaceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
