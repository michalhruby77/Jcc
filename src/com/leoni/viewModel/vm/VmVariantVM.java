package com.leoni.viewModel.vm;

import com.leoni.data.manager.vm.VmClipManager;
import com.leoni.data.manager.vm.VmVarianteManager;
import com.leoni.data.models.vm.VmClip;
import com.leoni.data.models.vm.VmVariante;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 18.8.2015
 * Time: 8:57
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VmVariantVM {

    @WireVariable
    private VmClipManager vmClipManager;

    @WireVariable
    private VmVarianteManager vmVarianteManager;

    private String name;
    private String erzNr;
    private VmVariante selectedVariant;
    private VmVariante variantToDelete;
    private List<VmVariante> variantList;
    private List<VmClip> vmClipList;
    private List<Combobox> comboboxList;
    private VmClipListRenderer vmClipListRenderer;

    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        variantList = vmVarianteManager.getAll();
        comboboxList = new ArrayList<>();
        vmClipList = vmClipManager.getAll() ;
        vmClipListRenderer = new VmClipListRenderer();

    }

    @Command
    @NotifyChange({"variantList"})
    public void deleteVariant(@BindingParam("variant") VmVariante myVariant)
    {
        variantToDelete = myVariant;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            vmVarianteManager.delete(variantToDelete);
                            variantList = vmVarianteManager.getAll();
                            BindUtils.postNotifyChange(null, null, VmVariantVM.this, "variantList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange({"selectedVariant"})
    public void saveVariant(@ContextParam(ContextType.VIEW) Component comp,
                         @BindingParam("selectedData")VmVariante editedVariant) {
        vmVarianteManager.saveOrUpdate(editedVariant);
        variantList = vmVarianteManager.getAll();
        selectedVariant = null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"variantList","name","erzNr"})
    public void generateNewVariant(@ContextParam(ContextType.VIEW) Component view,@BindingParam("vlayout") Vlayout vlayout) {
        if(name != null){
            Set<VmClip> selectedClipSet =  new HashSet<>();
            for (Combobox item : comboboxList){
                Set<VmClip> vmClip = ((ListModelList) item.getModel()).getSelection();
                selectedClipSet.addAll(vmClip);
            }
            VmVariante newVariant = new VmVariante();
            newVariant.setName(name);
            newVariant.setErznr(erzNr);
            newVariant.setClipSet(selectedClipSet);
            vmVarianteManager.create(newVariant);
            variantList = vmVarianteManager.getAll();
            Components.removeAllChildren(vlayout);
            comboboxList.clear();
            name = null;
            erzNr = null;
            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
        }
        else{Messagebox.show("Vyplnte nazov!", "Chyba.", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    public void addCombobox(@ContextParam(ContextType.VIEW) Component view,@BindingParam("vlayout") Vlayout vlayout) {
            Combobox combobox = new Combobox();
            combobox.setModel(new ListModelList<VmClip>(vmClipList));
            combobox.setItemRenderer(vmClipListRenderer);
            vlayout.appendChild(combobox);
            comboboxList.add(combobox);
    }

    @Command
    public void showClips(@BindingParam("variant") VmVariante myVariant,@ContextParam(ContextType.VIEW) Component comp) {
        Map<String, Set<VmClip>> arguments = new HashMap<>();
        arguments.put("clipsSet", myVariant.getClipSet());
        Window window = (Window) Executions.createComponents("vm/showClips.zul", comp, arguments);
        window.doPopup();
    }

    @Command
    @NotifyChange({"variantList"})
    public void editClips(@BindingParam("variant") VmVariante myVariant){
        Map<String, VmVariante> arguments = new HashMap();
        arguments.put("myVariant", myVariant);
        Window window = (Window) Executions.createComponents(
                "vm/editVariantClips.zul", null, arguments);
        window.doModal();
    }

    @Command
    public void print(@BindingParam("variant") VmVariante myVariant){

        Map<String, VmVariante> arguments = new HashMap<String, VmVariante>();
        arguments.put("myVariant", myVariant);
        Window window = (Window) Executions.createComponents(
                "vm/printVariant.zul",null ,arguments);
        window.doModal();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getErzNr() {
        return erzNr;
    }

    public void setErzNr(String erzNr) {
        this.erzNr = erzNr;
    }

    public VmVariante getSelectedVariant() {
        return selectedVariant;
    }

    public void setSelectedVariant(VmVariante selectedVariant) {
        this.selectedVariant = selectedVariant;
    }

    public List<VmVariante> getVariantList() {
        return variantList;
    }

    public void setVariantList(List<VmVariante> variantList) {
        this.variantList = variantList;
    }

    public List<VmClip> getVmClipList() {
        return vmClipList;
    }

    public void setVmClipList(List<VmClip> vmClipList) {
        this.vmClipList = vmClipList;
    }
}
