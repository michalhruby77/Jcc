package com.leoni.viewModel.vm;

import com.leoni.data.manager.vm.VmBrettManager;
import com.leoni.data.manager.vm.VmClipManager;
import com.leoni.data.manager.vm.VmSwitchManager;
import com.leoni.data.models.vm.VmBrett;
import com.leoni.data.models.vm.VmClip;
import com.leoni.data.models.vm.VmSwitch;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.9.2015
 * Time: 13:15
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VmSwitchVM {

    @WireVariable
    private VmClipManager vmClipManager;

    @WireVariable
    private VmBrettManager vmBrettManager;

    @WireVariable
    private VmSwitchManager vmSwitchManager;


    private String name;
    private String address;
    private Boolean status;
    private VmBrett selectedBrett;
    private VmBrett selectedBrettSave;
    private VmClip selectedClip;
    private VmClip selectedClipSave;
    private VmSwitch selectedSwitch;
    private VmSwitch switchToDelete;
    private List<VmClip> clipList;
    private List<VmBrett> brettList;
    private List<VmSwitch> switchList;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        switchList = vmSwitchManager.getAll();
        brettList = vmBrettManager.getAll();
        clipList = vmClipManager.getAll();
        status = true;
    }

    @Command
    @NotifyChange({"switchList"})
    public void deleteSwitch(@BindingParam("switch") VmSwitch mySwitch)
    {
        switchToDelete = mySwitch;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            vmSwitchManager.delete(switchToDelete);
                            switchList = vmSwitchManager.getAll();
                            BindUtils.postNotifyChange(null, null, VmSwitchVM.this, "switchList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange({"selectedSwitch"})
    public void saveSwitch(@ContextParam(ContextType.VIEW) Component comp,
                          @BindingParam("selectedData")VmSwitch editedSwitch) {
        vmSwitchManager.saveOrUpdate(editedSwitch);
        switchList = vmSwitchManager.getAll();
        selectedSwitch = null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"switchList","name","address", "status","selectedBrett","selectedClip"})
    public void generateNew() {
        if (name != null && address != null && name.length() < 11 && address.length() < 10 ){
        try
            {VmSwitch vmSwitch = new VmSwitch();
        vmSwitch.setName(name);
        vmSwitch.setAddress(address);
        vmSwitch.setStatus(status);
        vmSwitch.setVmBrett(selectedBrett);
        vmSwitch.setVmClip(selectedClip);
        vmSwitchManager.create(vmSwitch);
        switchList = vmSwitchManager.getAll();
        name = null;
        address = null;
        status = true;
        selectedBrett = null;
        selectedClip = null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);}
        catch(DataAccessException e){
            Messagebox.show("Chyba pri vkladani zaznamu. \n Adresa uz existuje?", "Error", Messagebox.OK, Messagebox.ERROR);
        }
        }
        else {Messagebox.show("Zla dlzka nazvu alebo adresy", "Error", Messagebox.OK, Messagebox.ERROR);}
    }



    public List<VmSwitch> getSwitchList() {
        return switchList;
    }

    public void setSwitchList(List<VmSwitch> switchList) {
        this.switchList = switchList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public VmBrett getSelectedBrett() {
        return selectedBrett;
    }

    public void setSelectedBrett(VmBrett selectedBrett) {
        this.selectedBrett = selectedBrett;
    }

    public VmBrett getSelectedBrettSave() {
        return selectedBrettSave;
    }

    public void setSelectedBrettSave(VmBrett selectedBrettSave) {
        this.selectedBrettSave = selectedBrettSave;
    }

    public VmClip getSelectedClip() {
        return selectedClip;
    }

    public void setSelectedClip(VmClip selectedClip) {
        this.selectedClip = selectedClip;
    }

    public VmClip getSelectedClipSave() {
        return selectedClipSave;
    }

    public void setSelectedClipSave(VmClip selectedClipSave) {
        this.selectedClipSave = selectedClipSave;
    }

    public VmSwitch getSelectedSwitch() {
        return selectedSwitch;
    }

    public void setSelectedSwitch(VmSwitch selectedSwitch) {
        this.selectedSwitch = selectedSwitch;
    }

    public VmSwitch getSwitchToDelete() {
        return switchToDelete;
    }

    public void setSwitchToDelete(VmSwitch switchToDelete) {
        this.switchToDelete = switchToDelete;
    }

    public List<VmClip> getClipList() {
        return clipList;
    }

    public void setClipList(List<VmClip> clipList) {
        this.clipList = clipList;
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
}
