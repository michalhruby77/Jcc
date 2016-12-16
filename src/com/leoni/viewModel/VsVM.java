package com.leoni.viewModel;

import com.leoni.data.manager.VsFarbyManager;
import com.leoni.data.manager.VsManager;
import com.leoni.data.models.Vs;
import com.leoni.data.models.VsFarby;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vlayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 18.12.2014
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VsVM {

    @WireVariable
    private VsManager vsManager;

    @WireVariable
    private VsFarbyManager vsFarbyManager;

    private List<VsFarby> vsFarbyList;
    private List<Vs> vsList;
    private String user;
    private VsFarby selectedVsFarba;
    private VsFarby selectedVsFarbaSave;
    private Vs vsToDelete;
    private Vs selectedVs;
    private String nazov;
    private Integer pocet;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        vsFarbyList = vsFarbyManager.getAll();
        vsList = vsManager.getAll();
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pocet = 0;
    }

    @Command
    @NotifyChange({"vsList"})
    public void deleteVs(@BindingParam("vs") Vs myVs)
    {
        vsToDelete = myVs;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            vsManager.delete(vsToDelete);
                            vsList = vsManager.getAll();
                            BindUtils.postNotifyChange(null, null, VsVM.this, "vsList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange({"selectedVs"})
    public void saveVs(@ContextParam(ContextType.VIEW) Component comp,
                          @BindingParam("selectedData")Vs editedVs) {
        editedVs.setVsFarby(selectedVsFarbaSave);
        vsManager.saveOrUpdate(editedVs);
        vsList = vsManager.getAll();
        selectedVs = null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"vsList","nazov", "pocet","selectedVsFarba"})
    public void generateNewVs(@BindingParam("vlayout") Vlayout vlayout) {
        if (selectedVsFarba != null)
        {
            Vs vs = new Vs();
            vs.setNazov(nazov);
            vs.setPocet(pocet);
            vs.setVsFarby(selectedVsFarba);
            vs.setChangedBy(user);
            vsManager.create(vs);
            selectedVsFarba =  null;
            nazov = null;
            pocet = 0;
            vsList = vsManager.getAll();
            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);}
        else{
            Messagebox.show("Vyberte farbu!", "Chyba.", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public List<VsFarby> getVsFarbyList() {
        return vsFarbyList;
    }

    public void setVsFarbyList(List<VsFarby> vsFarbyList) {
        this.vsFarbyList = vsFarbyList;
    }

    public List<Vs> getVsList() {
        return vsList;
    }

    public void setVsList(List<Vs> vsList) {
        this.vsList = vsList;
    }

    public VsFarby getSelectedVsFarba() {
        return selectedVsFarba;
    }

    public void setSelectedVsFarba(VsFarby selectedVsFarba) {
        this.selectedVsFarba = selectedVsFarba;
    }

    public Vs getSelectedVs() {
        return selectedVs;
    }

    public void setSelectedVs(Vs selectedVs) {
        this.selectedVs = selectedVs;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public Integer getPocet() {
        return pocet;
    }

    public void setPocet(Integer pocet) {
        this.pocet = pocet;
    }

    public VsFarby getSelectedVsFarbaSave() {
        return selectedVsFarbaSave;
    }

    public void setSelectedVsFarbaSave(VsFarby selectedVsFarbaSave) {
        this.selectedVsFarbaSave = selectedVsFarbaSave;
    }
}
