package com.leoni.viewModel;

import com.leoni.data.manager.ModulsManager;
import com.leoni.data.manager.SicherungenRelais9X1WrmManager;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.SicherungenRelais9X1Wrm;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 31.10.2014
 * Time: 7:39
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CopyRelayVM {

    @WireVariable
    private SicherungenRelais9X1WrmManager sicherungenRelais9X1WrmManager;

    @WireVariable
    private ModulsManager modulsManager;

    private String sachNrBest;
    private String sachNrLieferant;
    private SicherungenRelais9X1Wrm myRelay;
    private String user;
    private String wert;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("myRelay") SicherungenRelais9X1Wrm myObject) throws Exception {
        myRelay=myObject;
        sachNrBest=myRelay.getModuls().getSachNrBest();
        sachNrLieferant=myRelay.getModuls().getSachNrLieferant();
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        wert = myRelay.getWert();
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {
        if(sachNrLieferant!=null&&!modulsManager.findBySachNrLieferant(sachNrLieferant.trim()).isEmpty()){
        Moduls newModul = modulsManager.findBySachNrLieferant(sachNrLieferant.trim()).get(0);
        if(wert!=null&&sachNrBest!=null&&isInteger(wert)&&sachNrBest.trim().equals(newModul.getSachNrBest().trim())){
        myRelay.setWert(wert.trim());
        myRelay.setModuls(newModul);
        myRelay.setId(null);
        sicherungenRelais9X1WrmManager.saveOrUpdate(myRelay);
        BindUtils.postGlobalCommand(null, null, "refresh", null);
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
        view.detach();}
            else{
            Messagebox.show("Vyplnte spravne sach nr best a sach nr lief!", "Error", Messagebox.OK, Messagebox.ERROR);}
        }
        else{Messagebox.show("Modul neexistuje!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public String getSachNrBest() {
        return sachNrBest;
    }

    public void setSachNrBest(String sachNrBest) {
        this.sachNrBest = sachNrBest;
    }

    public String getSachNrLieferant() {
        return sachNrLieferant;
    }

    public void setSachNrLieferant(String sachNrLieferant) {
        this.sachNrLieferant = sachNrLieferant;
    }

    public SicherungenRelais9X1Wrm getMyRelay() {
        return myRelay;
    }

    public void setMyRelay(SicherungenRelais9X1Wrm myRelay) {
        this.myRelay = myRelay;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWert() {
        return wert;
    }

    public void setWert(String wert) {
        this.wert = wert;
    }
}
