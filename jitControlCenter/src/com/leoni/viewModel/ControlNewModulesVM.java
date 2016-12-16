package com.leoni.viewModel;

import com.leoni.data.manager.NewModulesManager;
import com.leoni.data.manager.oldJIT.AbruMaskeManager;
import com.leoni.data.manager.oldJIT.JitAbruManager;
import com.leoni.data.manager.oldJIT.JitAuftManager;
import com.leoni.data.manager.oldJIT.JitTsjiManager;
import com.leoni.data.models.CriticalNewModul;
import com.leoni.data.models.NewModules;
import org.hibernate.SessionFactory;
//import org.hibernate.classic.Session;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.6.2014
 * Time: 10:11
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ControlNewModulesVM {

    @WireVariable
    private AbruMaskeManager abruMaskeManager;
    @WireVariable
    private NewModulesManager newModulesManager;

    @WireVariable
    private JitAbruManager jitAbruManager;

    @WireVariable
    private JitAuftManager jitAuftManager;

    @WireVariable
    private JitTsjiManager jitTsjiManager;

    //@WireVariable
    //private SessionFactory sessionFactoryOld;

    private List<NewModules> newModulesList;
    private List<String> uebtNrsList;
    private List<CriticalNewModul> criticalNewModulList;
    private boolean specifyDfueNr = false;
    private Integer dfueNr;


    @Command
    @NotifyChange({"criticalNewModulList","specifyDfueNr","dfueNr"})
    public void submit(){
        String modulsInJitAuft = "";
        String modulsInJitTsji = "";
        criticalNewModulList = new ArrayList<CriticalNewModul>();
        newModulesList = newModulesManager.getAll();
        //System.out.println("nove moduly     "+newModulesList);
        if(!specifyDfueNr) uebtNrsList = abruMaskeManager.getUebtNrsStatus012();
        if (!specifyDfueNr){
            for (String uebtNr : uebtNrsList){
                Map<String,Set<String>> modulsInProdNr = jitAbruManager.getModulesInCarMap(uebtNr);
                for (NewModules item : newModulesList){
                   List<CriticalNewModul> cnmTempList = jitAbruManager.checkForModules(item.getSachNrBest(), modulsInProdNr, uebtNr);
                    for(CriticalNewModul cnm : cnmTempList){
                        cnm.setExistsAuft(jitAuftManager.existsSachNrBestInAuft(cnm.getSachNrBest(),cnm.getKundenNr()));
                        cnm.setExistsTsji(jitTsjiManager.existsSachNrBestInTsji(cnm.getSachNrBest()));
                    }
                    criticalNewModulList.addAll(cnmTempList);
                }
                modulsInProdNr.clear();
            }
        }
        else{
            Map<String,Set<String>> modulsInProdNr = jitAbruManager.getModulesInCarMap(String.valueOf(dfueNr));
            for (NewModules item : newModulesList){
                List<CriticalNewModul> cnmTempList = jitAbruManager.checkForModules(item.getSachNrBest(),modulsInProdNr, String.valueOf(dfueNr));
                for(CriticalNewModul cnm : cnmTempList){
                    cnm.setExistsAuft(jitAuftManager.existsSachNrBestInAuft(cnm.getSachNrBest(),cnm.getKundenNr()));
                    cnm.setExistsTsji(jitTsjiManager.existsSachNrBestInTsji(cnm.getSachNrBest()));
                }
                criticalNewModulList.addAll(cnmTempList);
            }
            modulsInProdNr.clear();
        }


        if(criticalNewModulList.isEmpty()){
            Messagebox.show("Neboli najdene ziadne kriticke moduly.", "OK", Messagebox.OK, Messagebox.INFORMATION);}
        specifyDfueNr = false;
        dfueNr = null;
    }

    public List<CriticalNewModul> getCriticalNewModulList() {
        return criticalNewModulList;
    }

    public void setCriticalNewModulList(List<CriticalNewModul> criticalNewModulList) {
        this.criticalNewModulList = criticalNewModulList;
    }

    public boolean isSpecifyDfueNr() {
        return specifyDfueNr;
    }

    public void setSpecifyDfueNr(boolean specifyDfueNr) {
        this.specifyDfueNr = specifyDfueNr;
    }

    public Integer getDfueNr() {
        return dfueNr;
    }

    public void setDfueNr(Integer dfueNr) {
        this.dfueNr = dfueNr;
    }
}
