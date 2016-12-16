package com.leoni.viewModel;

import com.leoni.data.manager.VsFarbyManager;
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

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 16.12.2014
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VsFarbyVM {

    @WireVariable
    private VsFarbyManager vsFarbyManager;

    private List<VsFarby> vsFarbyList = new ArrayList<VsFarby>();
    private String user;
    private VsFarby selectedVsFarba;
    private VsFarby vsFarbaToDelete;
    private String popis;
    private AImage selectedImage;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        vsFarbyList = vsFarbyManager.getAll();
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
    @NotifyChange({"vsFarbyList","popis"})
    public void generateNewVsFarba() {

        if(popis!=null&&selectedImage!=null){

            vsFarbyManager.addVsFarba(popis,selectedImage,user);
                    Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
            popis = null;
        }
        else{ Messagebox.show("Nezadali ste id, popis alebo obrazok!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    @NotifyChange ({"vsFarbyList"})
    public void deleteVsFarba(@BindingParam("vsFarba") VsFarby myVsFarba)
    {
        vsFarbaToDelete = myVsFarba;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            vsFarbyManager.delete(vsFarbaToDelete);
                            vsFarbyList=vsFarbyManager.getAll();
                            BindUtils.postNotifyChange(null, null, VsFarbyVM.this, "vsFarbyList");
                        }
                    }
                }
        );
    }

    @Command
    public void changePicture(@BindingParam("event") UploadEvent event,@BindingParam("vsFarba") VsFarby myVsFarba)
    {
        Media media = event.getMedia();
        if (media instanceof Image) {
            AImage img = (AImage) media;
            if (img.getHeight() <= 65 && img.getHeight() <= 65) {
                myVsFarba.setObrazok(img.getByteData());
                vsFarbyManager.saveEditedVsFarba(myVsFarba,user);
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
    @NotifyChange({"selectedVsFarba"})
    public void saveVsFarba(@ContextParam(ContextType.VIEW) Component comp,
                            @BindingParam("selectedData")VsFarby editedVsFarba) {
        vsFarbyManager.saveEditedVsFarba(editedVsFarba,user);
        vsFarbyList = vsFarbyManager.getAll();
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    public List<VsFarby> getVsFarbyList() {
        return vsFarbyList;
    }

    public void setVsFarbyList(List<VsFarby> vsFarbyList) {
        this.vsFarbyList = vsFarbyList;
    }

    public VsFarby getSelectedVsFarba() {
        return selectedVsFarba;
    }

    public void setSelectedVsFarba(VsFarby selectedVsFarba) {
        this.selectedVsFarba = selectedVsFarba;
    }



    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }
}
