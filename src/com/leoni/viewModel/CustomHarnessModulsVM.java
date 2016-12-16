package com.leoni.viewModel;

import com.leoni.data.manager.Lpab64Manager;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.models.Lpab62;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.Moduls;
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
 * Date: 18.9.2014
 * Time: 8:33
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CustomHarnessModulsVM {

    @WireVariable
    private Lpab64Manager lpab64Manager;

    @WireVariable
    private ModulsManager modulsManager;

    private Lpab62 myHarness;
    private String prodNr;
    private String kabelsatzKz;
    private List<Lpab64> modulsList;
    private Lpab64 modulToDelete;
    private String sachNrBest;
    //private String sachNrLieferant;
    private List<Moduls> sachNrLieferantList;
    private Moduls selectedModul;


    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("myHarness") Lpab62 myObject) throws Exception {
        myHarness = myObject;
        prodNr = myHarness.getProdNr().trim();
        kabelsatzKz = myHarness.getKabelsatzKz();
        modulsList = lpab64Manager.findByProdnrAndKabelsatz(prodNr,kabelsatzKz);
    }

    @Command
    @NotifyChange ({"modulsList"})
    public void deleteModul(@BindingParam("modul") Lpab64 myModul)
    {
        modulToDelete = myModul;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            lpab64Manager.delete(modulToDelete);
                            //schrittList=schrittManager.getAll();
                            modulsList = lpab64Manager.findByProdnrAndKabelsatz(prodNr,kabelsatzKz);
                            BindUtils.postNotifyChange(null, null, CustomHarnessModulsVM.this, "modulsList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange ({"modulsList","sachNrBest","sachNrLieferantList"})
    public void addModul()
    {
    if(selectedModul!=null){
        if(selectedModul.getKabelsatzKz().trim().equals(myHarness.getKabelsatzKz().trim())){
            if(!modulIsInModulList(selectedModul.getSachNrBest().trim())){
                lpab64Manager.addModul(selectedModul.getSachNrBest().trim(),selectedModul.getSachNrLieferant().trim(),
                        prodNr,selectedModul.isGrund(),selectedModul.getKabelsatzKz().trim());
                sachNrBest = null;
                sachNrLieferantList = null;
                Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
            }
            Messagebox.show("Modul sa uz nachadza v modulovej skladbe!", "Error", Messagebox.OK, Messagebox.ERROR);
        }
        Messagebox.show("Nezhoduje sa typ!", "Error", Messagebox.OK, Messagebox.ERROR);
       }
    else{Messagebox.show("Nie je vybraty modul!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    @NotifyChange({"sachNrLieferantList"})
    public void updateSachNrLieferantList(){
        if(sachNrBest!=null)
            sachNrLieferantList = modulsManager.findBySachNrBest(sachNrBest);

    }

    private boolean modulIsInModulList(String sachNrBest){
        for (Lpab64 item : modulsList){
            if (sachNrBest.equals(item.getSachNrBest().trim())){return true;}
        }
        return false;
    }

    public List<Lpab64> getModulsList() {
        return modulsList;
    }

    public void setModulsList(List<Lpab64> modulsList) {
        this.modulsList = modulsList;
    }

    public List<Moduls> getSachNrLieferantList() {
        return sachNrLieferantList;
    }

    public void setSachNrLieferantList(List<Moduls> sachNrLieferantList) {
        this.sachNrLieferantList = sachNrLieferantList;
    }

    public Moduls getSelectedModul() {
        return selectedModul;
    }

    public void setSelectedModul(Moduls selectedModul) {
        this.selectedModul = selectedModul;
    }

    public String getSachNrBest() {
        return sachNrBest;
    }

    public void setSachNrBest(String sachNrBest) {
        this.sachNrBest = sachNrBest;
    }
}
