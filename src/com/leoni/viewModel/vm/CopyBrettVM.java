package com.leoni.viewModel.vm;

import com.leoni.data.manager.vm.VmBrettManager;
import com.leoni.data.manager.vm.VmSwitchManager;
import com.leoni.data.models.vm.VmBrett;
import com.leoni.data.models.vm.VmSwitch;
import com.leoni.data.models.vm.VmVariante;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vlayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hrmi1005 on 19. 5. 2016.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CopyBrettVM {

    @WireVariable
    VmSwitchManager vmSwitchManager;

    @WireVariable
    VmBrettManager vmBrettManager;


    private VmBrett myBrett;
    private List<VmSwitch> vmSwitchList;
    private String name;

    @AfterCompose
    public void doAfterCompose( @BindingParam("vlayout") Vlayout vlayout,
                                @ContextParam(ContextType.VIEW) Component view,
                                @ExecutionArgParam("myBrett") VmBrett vmBrett) throws Exception {

        myBrett = vmBrett;
        vmSwitchList = vmSwitchManager.findByBoard(myBrett);
        for (VmSwitch vmSwitch : vmSwitchList){
         vmSwitch.setAddress("");
        }
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {

        boolean hasAddressLength8 = true;
        boolean addressAlreadyExist = false;
        for (VmSwitch vmSwitch : vmSwitchList){
            if (vmSwitch.getAddress().length() != 8)  hasAddressLength8 = false;
            if (!vmSwitchManager.findByAddress(vmSwitch.getAddress()).isEmpty())  addressAlreadyExist = true;
        }

        if (hasAddressLength8 && name != null && name.length() <= 20){
            if(vmBrettManager.findByName(name).isEmpty() && !addressAlreadyExist){
        VmBrett vmBrett = new VmBrett();
        vmBrett.setName(name);
        vmBrett.setVarianteSet(myBrett.getVarianteSet());
        vmBrett.setVmStelle(myBrett.getVmStelle());
        vmBrettManager.create(vmBrett);
        for (VmSwitch vmSwitch : vmSwitchList){
            vmSwitch.setId(null);
            vmSwitch.setVmBrett(vmBrett);
            vmSwitchManager.create(vmSwitch);
        }
        BindUtils.postGlobalCommand(null, null, "refresh", null);
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
        view.detach();}
            else {Messagebox.show("Nazov dosky alebo adresa uz existuje !", "Chyba!", Messagebox.OK, Messagebox.ERROR);}
        }
        else {Messagebox.show("Nazov dosky alebo adresa ma nespravnu dlzku!", "Chyba!", Messagebox.OK, Messagebox.ERROR);}
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VmSwitch> getVmSwitchList() {
        return vmSwitchList;
    }

    public void setVmSwitchList(List<VmSwitch> vmSwitchList) {
        this.vmSwitchList = vmSwitchList;
    }
}


