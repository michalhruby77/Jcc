package com.leoni.viewModel;

import com.leoni.data.manager.StoffManager;
import com.leoni.data.manager.VsFarbyManager;
import com.leoni.data.models.Stoff;
import com.leoni.data.models.VsFarby;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class StoffEditorVM {

    @WireVariable
    private StoffManager stoffManager;

    private List<Stoff> stoffList = new ArrayList<Stoff>();
    private String user;
    private Stoff selectedStoff;
    private Stoff stoffToDelete;
    private String nazov;
    private String stoffNr;
    private AImage selectedImage;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        stoffList = stoffManager.getAll();
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Command
    public void uploadPicture(@BindingParam("event") UploadEvent event)
    {
        Media media = event.getMedia();
        if (media instanceof Image) {
            AImage img = (AImage) media;
            if (img.getHeight() <= 65 && img.getHeight() <= 65) {
                selectedImage = img;
            }
            else {
                Messagebox.show("Nespravne rozmery, velkost musi byt max 65x65 pixelov.", "Error", Messagebox.OK, Messagebox.ERROR);
            }
        }
        else {
            Messagebox.show("Not an image: " + media, "Error", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange({"stoffList","nazov","stoffNr"})
    public void generateNewStoff() {

        if(stoffNr != null && selectedImage != null && nazov != null){

            stoffManager.addStoff(nazov, stoffNr, selectedImage, user);
            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
            stoffNr = null;
            nazov = null;
            stoffList = stoffManager.getAll();
        }
        else{ Messagebox.show("Nezadali ste nazov, stoff cislo alebo obrazok!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    @NotifyChange ({"stoffList"})
    public void deleteStoff(@BindingParam("stoff") Stoff myStoff)
    {
        stoffToDelete = myStoff;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            stoffManager.delete(stoffToDelete);
                            stoffList = stoffManager.getAll();
                            BindUtils.postNotifyChange(null, null, StoffEditorVM.this, "stoffList");
                        }
                    }
                }
        );
    }

    @Command
    public void changePicture(@BindingParam("event") UploadEvent event,@BindingParam("stoff") Stoff myStoff)
    {
        Media media = event.getMedia();
        if (media instanceof Image) {
            AImage img = (AImage) media;
            if (img.getHeight() <= 65 && img.getHeight() <= 65) {
                myStoff.setObrazok(img.getByteData());
                stoffManager.saveEditedStoff(myStoff,user);
            }
            else {
                Messagebox.show("Nespravne rozmery, velkost musi byt max 65x65 pixelov.", "Error", Messagebox.OK, Messagebox.ERROR);
            }
        }
        else {
            Messagebox.show("Not an image: " + media, "Error", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange({"selectedStoff"})
    public void saveStoff(@ContextParam(ContextType.VIEW) Component comp,
                            @BindingParam("selectedData")Stoff editedStoff) {
        stoffManager.saveEditedStoff(editedStoff, user);
        stoffList = stoffManager.getAll();
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    public List<Stoff> getStoffList() {
        return stoffList;
    }

    public void setStoffList(List<Stoff> stoffList) {
        this.stoffList = stoffList;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getStoffNr() {
        return stoffNr;
    }

    public void setStoffNr(String stoffNr) {
        this.stoffNr = stoffNr;
    }

    public Stoff getSelectedStoff() {
        return selectedStoff;
    }

    public void setSelectedStoff(Stoff selectedStoff) {
        this.selectedStoff = selectedStoff;
    }
}
