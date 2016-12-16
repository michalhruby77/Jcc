package com.leoni.viewModel;

import com.leoni.data.manager.ColorManager;
import com.leoni.data.manager.Lpab62Manager;
import com.leoni.data.manager.ProdNrLogManager;
import com.leoni.data.models.Lpab62;
import com.leoni.data.models.ProdNrLog;
import com.leoni.data.models.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.4.2014
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Lpab62ViewModel {

    @WireVariable
    private Lpab62Manager lpab62Manager;

    @WireVariable
    private ProdNrLogManager prodNrLogManager;

    private String prodNr = null;
    private String kabelsatzKz = null;
    private String user;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Command
    @NotifyChange({"prodNr","kabelsatzKz"})
    public void deleteEsdStatus()
    {
        Messagebox.show("Ste si isti?", "Vymazat?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            if(!lpab62Manager.findByProdnrAndKabelsatz(prodNr.trim(),kabelsatzKz).isEmpty()){
                                Lpab62 harnessToDeleteEsdStatus = lpab62Manager.findByProdnrAndKabelsatz(prodNr,kabelsatzKz).get(0);
                                if(harnessToDeleteEsdStatus.getStaEsdSchraub()==null||harnessToDeleteEsdStatus.getStaEsdSchraub().trim().equals("")){
                                    if(harnessToDeleteEsdStatus.getStaEsdSchraubBegin()!=null&&!harnessToDeleteEsdStatus.getStaEsdSchraubBegin().trim().equals("")){
                                    lpab62Manager.deleteBegin(harnessToDeleteEsdStatus);
                                    prodNrLogManager.saveProdNrLog(prodNr,kabelsatzKz,"F9X1XL_SCREW_E",user,"SCREW ESD DELBEGIN","SCREW ESD BEGIN DELETED");
                                    Messagebox.show("Status zmazany!", "Ulozene.", Messagebox.OK, null);
                                    }else{Messagebox.show("Kablovka este nebola nacitana na tomto pracovisku!", "Error", Messagebox.OK, Messagebox.ERROR);}
                            }
                                else{Messagebox.show("Kablovka uz ma ukoncene srobovanie, status mozu zmenit iba na opravnom mieste!", "Error", Messagebox.OK, Messagebox.ERROR);}
                            }
                            else {Messagebox.show("Kablovka sa nenasla!", "Error", Messagebox.OK, Messagebox.ERROR);}
                        }
                        prodNr = null;
                        kabelsatzKz = "F";
                        BindUtils.postNotifyChange(null, null, Lpab62ViewModel.this, "prodNr");
                        BindUtils.postNotifyChange(null, null, Lpab62ViewModel.this, "kabelsatzKz");
                    }
                }
        );
    }

    public String getProdNr() {
        return prodNr;
    }

    public void setProdNr(String prodNr) {
        this.prodNr = prodNr;
    }

    public String getKabelsatzKz() {
        return kabelsatzKz;
    }

    public void setKabelsatzKz(String kabelsatzKz) {
        this.kabelsatzKz = kabelsatzKz;
    }
}
