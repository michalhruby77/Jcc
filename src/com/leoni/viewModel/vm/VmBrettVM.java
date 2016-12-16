package com.leoni.viewModel.vm;

import com.leoni.data.manager.ColorManager;
import com.leoni.data.manager.vm.VmBrettManager;
import com.leoni.data.manager.vm.VmStelleManager;
import com.leoni.data.manager.vm.VmVarianteManager;
import com.leoni.data.models.vm.VmBrett;
import com.leoni.data.models.vm.VmStelle;
import com.leoni.data.models.vm.VmVariante;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 10.8.2015
 * Time: 8:05
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VmBrettVM {

    @WireVariable
    private VmStelleManager vmStelleManager;

    @WireVariable
    private VmBrettManager vmBrettManager;

    @WireVariable
    private VmVarianteManager vmVarianteManager;


    private String name;
    private VmStelle selectedStelle;
    private VmStelle selectedStelleSave;
    private VmBrett selectedBrett;
    private VmBrett brettToDelete;
    private List<VmStelle> stelleList;
    private List<VmVariante> variantList;
    private List<VmBrett> brettList;
    private List<Combobox> comboboxList;
    private VmVariantListRenderer vmVariantListRenderer;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        stelleList = vmStelleManager.getAll();
        brettList = vmBrettManager.getAll();
        variantList = vmVarianteManager.getAll();
        vmVariantListRenderer = new VmVariantListRenderer();
        comboboxList = new ArrayList<>();
    }

    @Command
    @NotifyChange({"brettList"})
    public void deleteBrett(@BindingParam("brett") VmBrett myBrett)
    {
        brettToDelete=myBrett;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            vmBrettManager.delete(brettToDelete);
                            brettList = vmBrettManager.getAll();
                            BindUtils.postNotifyChange(null, null, VmBrettVM.this, "brettList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange({"selectedBrett"})
    public void saveBrett(@ContextParam(ContextType.VIEW) Component comp,
                          @BindingParam("selectedData")VmBrett editedBrett) {
        vmBrettManager.saveOrUpdate(editedBrett);
        brettList=vmBrettManager.getAll();
        selectedBrett = null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"brettList","name", "selectedStelle"})
    public void generateNew(@BindingParam("vlayout") Vlayout vlayout) {
        if (name != null)
        {Set<VmVariante> selectedVariantSet =  new HashSet<>();
        for (Combobox item : comboboxList){
            Set<VmVariante> vmVariantSet = ((ListModelList) item.getModel()).getSelection();
            selectedVariantSet.addAll(vmVariantSet);
        }
        VmBrett brett = new VmBrett();
        brett.setName(name);
        brett.setVmStelle(selectedStelle);
        brett.setVarianteSet(selectedVariantSet);
        vmBrettManager.create(brett);
        brettList = vmBrettManager.getAll();
        Components.removeAllChildren(vlayout);
        comboboxList.clear();
        name=null;
        selectedStelle=null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);}
        else{
            Messagebox.show("Vyplnte nazov!", "Chyba.", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    public void addCombobox(@ContextParam(ContextType.VIEW) Component view,@BindingParam("vlayout") Vlayout vlayout) {
        Combobox combobox = new Combobox();
        combobox.setModel(new ListModelList<VmVariante>(variantList));
        combobox.setItemRenderer(vmVariantListRenderer);
        vlayout.appendChild(combobox);
        comboboxList.add(combobox);
    }

    @Command
    public void editVariants(@BindingParam("brett") VmBrett myBrett,@ContextParam(ContextType.VIEW) Component comp){
        Map<String, VmBrett> arguments = new HashMap<String, VmBrett>();
        arguments.put("myBrett", myBrett);
        Window window = (Window) Executions.createComponents("vm/editBrett.zul", comp, arguments);
        window.doModal();
    }

    @Command
    public void copyBoard(@BindingParam("brett") VmBrett myBrett,@ContextParam(ContextType.VIEW) Component comp){
        Map<String, VmBrett> arguments = new HashMap<String, VmBrett>();
        arguments.put("myBrett", myBrett);
        Window window = (Window) Executions.createComponents("vm/copyBrett.zul", comp, arguments);
        window.doModal();
    }

    @Command
    public void showVariant(@BindingParam("brett") VmBrett myBrett,@ContextParam(ContextType.VIEW) Component comp) {
        Map<String, Set<VmVariante>> arguments = new HashMap<>();
        arguments.put("variantsSet", myBrett.getVarianteSet());
        Window window = (Window) Executions.createComponents("vm/showVariants.zul", comp, arguments);
        window.doPopup();
    }

    @GlobalCommand
    @NotifyChange({"brettList"})
    public void refresh(){
        brettList = vmBrettManager.getAll();
    }

    public List<VmBrett> getBrettList() {
        return brettList;
    }

    public void setBrettList(List<VmBrett> brettList) {
        this.brettList = brettList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VmStelle getSelectedStelle() {
        return selectedStelle;
    }

    public void setSelectedStelle(VmStelle selectedStelle) {
        this.selectedStelle = selectedStelle;
    }


    public VmBrett getBrettToDelete() {
        return brettToDelete;
    }

    public void setBrettToDelete(VmBrett brettToDelete) {
        this.brettToDelete = brettToDelete;
    }

    public List<VmStelle> getStelleList() {
        return stelleList;
    }

    public void setStelleList(List<VmStelle> stelleList) {
        this.stelleList = stelleList;
    }

    public VmStelle getSelectedStelleSave() {
        return selectedStelleSave;
    }

    public void setSelectedStelleSave(VmStelle selectedStelleSave) {
        this.selectedStelleSave = selectedStelleSave;
    }

    public VmBrett getSelectedBrett() {
        return selectedBrett;
    }

    public void setSelectedBrett(VmBrett selectedBrett) {
        this.selectedBrett = selectedBrett;
    }
}
