package com.leoni.viewModel.vm;

import com.leoni.data.manager.vm.VmClipManager;
import com.leoni.data.models.vm.VmClip;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 18.8.2015
 * Time: 7:17
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VmClipVM {

    @WireVariable
    private VmClipManager vmClipManager;

    private String name;
    private VmClip selectedClip;
    private List<VmClip> clipList;
    private VmClip clipToDelete;

    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        clipList = vmClipManager.getAll();
    }

    @Command
    @NotifyChange({"clipList"})
    public void deleteClip(@BindingParam("clip") VmClip myClip)
    {
        clipToDelete = myClip;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            vmClipManager.delete(clipToDelete);
                            clipList = vmClipManager.getAll();
                            BindUtils.postNotifyChange(null, null, VmClipVM.this, "clipList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange({"selectedClip"})
    public void saveClip(@ContextParam(ContextType.VIEW) Component comp,
                          @BindingParam("selectedData")VmClip editedClip) {
        vmClipManager.saveOrUpdate(editedClip);
        clipList = vmClipManager.getAll();
        selectedClip = null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"clipList","name"})
    public void generateNewClip() {
        if(name != null && name.length()<=10){
        VmClip newClip = new VmClip();
        newClip.setName(name);
        vmClipManager.create(newClip);
        clipList = vmClipManager.getAll();
        name=null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
        }
        else{Messagebox.show("Nazov musi mat max 10 znakov!", "Chyba.", Messagebox.OK, Messagebox.ERROR);}
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VmClip getSelectedClip() {
        return selectedClip;
    }

    public void setSelectedClip(VmClip selectedClip) {
        this.selectedClip = selectedClip;
    }

    public List<VmClip> getClipList() {
        return clipList;
    }

    public void setClipList(List<VmClip> clipList) {
        this.clipList = clipList;
    }

    public VmClip getClipToDelete() {
        return clipToDelete;
    }

    public void setClipToDelete(VmClip clipToDelete) {
        this.clipToDelete = clipToDelete;
    }
}
