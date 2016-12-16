package com.leoni.viewModel.vm;

import com.leoni.data.manager.vm.VmClipManager;
import com.leoni.data.manager.vm.VmVarianteManager;
import com.leoni.data.models.vm.VmClip;
import com.leoni.data.models.vm.VmVariante;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Vlayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 19.8.2015
 * Time: 8:51
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EditVariantClipsVM {

    @WireVariable
    private VmClipManager vmClipManager;

    @WireVariable
    private VmVarianteManager vmVarianteManager;

    private List<VmClip> vmClipList;
    private List<Combobox> comboboxList;
    private VmClipListRenderer vmClipListRenderer;
    private VmVariante myVariant;


    @AfterCompose
    public void doAfterCompose( @BindingParam("vlayout") Vlayout vlayout,
                                @ContextParam(ContextType.VIEW) Component view,
                                @ExecutionArgParam("myVariant") VmVariante myObject) throws Exception {

        myVariant = myObject;
        vmClipList = vmClipManager.getAll();
        comboboxList = new ArrayList<>();
        vmClipListRenderer = new VmClipListRenderer();
        for (VmClip item : myVariant.getClipSet())
        {
            Set<VmClip> selectedClipToSet = new HashSet();
            selectedClipToSet.add(item);
            Combobox combobox = new Combobox();
            combobox.setModel(new ListModelList<VmClip>(vmClipList));
            ListModelList lml = (ListModelList)combobox.getModel();
            Set selectedSet = new HashSet();
            for (Object vmClip : lml){
                        if (((VmClip) vmClip).getId().equals(item.getId())){
                            selectedSet.add(vmClip);
                        }
            }
            //selectedSet.add((VmClip) lml.get(0));
            lml.setSelection(selectedSet);
            combobox.setItemRenderer(vmClipListRenderer);
            vlayout.appendChild(combobox);
            comboboxList.add(combobox);
        }

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
    public void saveVariant(//@BindingParam("vlayout") Vlayout vlayout,
                            @ContextParam(ContextType.VIEW) Component view) {
        Set<VmClip> selectedClipSet =  new HashSet<>();
        for (Combobox item : comboboxList){
            Set<VmClip> vmClip = ((ListModelList) item.getModel()).getSelection();
            selectedClipSet.addAll(vmClip);
        }
        myVariant.setClipSet(selectedClipSet);
        vmVarianteManager.saveOrUpdate(myVariant);
        view.detach();
    }
}
