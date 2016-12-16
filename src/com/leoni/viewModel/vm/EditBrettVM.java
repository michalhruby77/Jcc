package com.leoni.viewModel.vm;

import com.leoni.data.manager.vm.VmBrettManager;
import com.leoni.data.manager.vm.VmVarianteManager;
import com.leoni.data.models.vm.VmBrett;
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
 * Date: 1.10.2015
 * Time: 8:17
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EditBrettVM {
    @WireVariable
    private VmBrettManager vmBrettManager;

    @WireVariable
    private VmVarianteManager vmVarianteManager;

    private VmBrett myBrett;
    private List<Combobox> cbList;
    private Set<VmVariante> variantSetSelected;
    private List<VmVariante> variantList;
    private VmVariantListRenderer vmVariantListRenderer;

    @AfterCompose
    public void doAfterCompose( @BindingParam("vlayout") Vlayout vlayout,
                                @ContextParam(ContextType.VIEW) Component view,
                                @ExecutionArgParam("myBrett") VmBrett vmBrett) throws Exception {
        cbList = new ArrayList<Combobox>();
        variantSetSelected = new HashSet<>();
        variantList = vmVarianteManager.getAll();
        myBrett = vmBrett;
        vmVariantListRenderer = new VmVariantListRenderer();
        for(VmVariante variante : myBrett.getVarianteSet()){
            Combobox cb = new Combobox();
            cb.setWidth("300px");
            ListModelList<VmVariante> variantLML = new ListModelList<>(variantList);
            cb.setModel(variantLML);
            cb.setItemRenderer(vmVariantListRenderer);
            Set<VmVariante> variantSet = new HashSet<VmVariante>();
            for (VmVariante item : variantList){
                if (variante.equals(item)){variantSet.add(item);}
            }
            variantLML.setSelection(variantSet);
            vlayout.appendChild(cb);
            cbList.add(cb);
        }
    }

    @Command
    public void addCombobox(@ContextParam(ContextType.VIEW) Component view,@BindingParam("vlayout") Vlayout vlayout) {
        Combobox combobox = new Combobox();
        combobox.setWidth("300px");
        combobox.setModel(new ListModelList<VmVariante>(variantList));
        combobox.setItemRenderer(vmVariantListRenderer);
        vlayout.appendChild(combobox);
        cbList.add(combobox);
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {
        for (Combobox item : cbList){
            if (((ListModelList)item.getModel()).getSelection() != null){
                Set<VmVariante> selvariants =  ((ListModelList)item.getModel()).getSelection();
                for (VmVariante variante : selvariants){
                    variantSetSelected.add(variante);
                }
            }
        }
        myBrett.setVarianteSet(variantSetSelected);
        vmBrettManager.saveOrUpdate(myBrett);
        view.detach();
    }
}
