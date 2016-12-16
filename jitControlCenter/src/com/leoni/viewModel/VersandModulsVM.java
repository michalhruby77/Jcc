package com.leoni.viewModel;

import com.itextpdf.text.ListItem;
import com.leoni.data.manager.VersandModulManager;
import com.leoni.data.models.VersandExport;
import com.leoni.data.models.VersandModul;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2015
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VersandModulsVM {

    @WireVariable
    private VersandModulManager versandModulManager;


    @Wire
    Listbox modulListbox;

    private VersandExport versandExport;
    private List<VersandModul> versandModulList;
    private String modulScan;
    private String greenStyle = "background-color: #99FF66";
    private String previousStyle = null;
    private int previousIndex = 0;
    private VersandModul selectedModul;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("myExport") VersandExport myObject) throws Exception {

        Selectors.wireComponents(view, this, false);
        versandExport = myObject;
        versandModulList = versandExport.getModulsList();
    }

    @Command
    @NotifyChange({"modulScan", "selectedModul","versandModulList"})
    public void scan(){
        System.out.println(modulScan);
        String[] words = modulScan.split("#");
        if(selectedModul!=null){
            if(getActiveModul(versandExport)==null||getActiveModul(versandExport).getId()==selectedModul.getId())
            {System.out.println("tu som");
            if(!selectedModul.getScan()){
                if(modulScan.startsWith("persnr")){
                    System.out.println("tu som1");
                    selectedModul.setPersnr(modulScan.trim());
                    selectedModul.setStatus(40);
                    versandModulManager.saveOrUpdate(selectedModul);
                    if(isExportFinished(versandExport)){
                        Messagebox.show("Export hotovy!", "Hotovo!", Messagebox.OK, Messagebox.INFORMATION);}
                }
            }
            if(selectedModul.getScan()){
              if(selectedModul.getStatus()!=40){
                System.out.println("tu som2");
                String selectedSachNrBestNoDots = selectedModul.getSachNrBest().trim().replace(".","");
                String modulScanEdited = modulScan.substring(modulScan.indexOf("#")+1, modulScan.indexOf("_"));
                /*if (selectedSachNrBestNoDots.matches(modulScanEdited.substring(0,modulScanEdited.length()-1)+"[0-9]?"+modulScanEdited.substring(modulScanEdited.length()-1))){
                    System.out.println("pasujeeeeeeeeeeeeeeeeeeeeeeeeee");
                } */
                  System.out.println("takto to vyzera " + selectedSachNrBestNoDots + " " + modulScanEdited.substring(0,modulScanEdited.length()-1)+"[0-9]?"+modulScanEdited.substring(modulScanEdited.length()-1));
                //if(selectedModul.getSachNrLieferant().trim().equals(modulScan.trim())){
                if (selectedSachNrBestNoDots.matches(modulScanEdited.substring(0,modulScanEdited.length()-1)+"[0-9]?"+modulScanEdited.substring(modulScanEdited.length()-1))){
                   System.out.println("tu som3");
                   selectedModul.setStatus(20);
                   int count = selectedModul.getCount();
                   count++;
                   selectedModul.setCount(count);
                    if(count==selectedModul.getPieces()){

                        Map<String, VersandModul> arguments = new HashMap<String, VersandModul>();
                        arguments.put("myModul", selectedModul);
                        Window window = (Window) Executions.createComponents(
                                "versand/persnrScreen.zul", null, arguments);
                        window.doModal();

                    }
                   versandModulManager.saveOrUpdate(selectedModul);
                    if(count==selectedModul.getPieces()){
                        if(isExportFinished(versandExport)){
                        Messagebox.show("Export hotovy!", "Hotovo!", Messagebox.OK, Messagebox.INFORMATION);}
                    }
                }
               if(modulScan.startsWith("persnr")){
                   selectedModul.setStatus(30);
                   selectedModul.setPersnr(modulScan);
                   versandModulManager.saveOrUpdate(selectedModul);
               }
             }
             if(selectedModul.getStatus()==40&&modulScan.startsWith("persnr")&&
                     (selectedModul.getPersnr()==null||selectedModul.getPersnr().trim().equals(""))){
                 selectedModul.setPersnr(modulScan);
                 versandModulManager.saveOrUpdate(selectedModul);
                 Messagebox.show("Podpisane!", "Podpisane!", Messagebox.OK, Messagebox.INFORMATION);
             }
            }
        }
            else{
                Messagebox.show("Dokoncite skenovanie predosleho modulu.", "Error", Messagebox.OK, Messagebox.ERROR);}
        }


        versandModulList = versandExport.getModulsList();
        modulScan = "";

    }

    @GlobalCommand
    @NotifyChange({"versandModulList"})
    public void refreshVersandExport(){
                            versandModulList = versandModulManager.getAllVersandModuls(versandExport);
                            BindUtils.postNotifyChange(null, null, VersandModulsVM.this, "versandModulList");
    }

    VersandModul getActiveModul(VersandExport versandExport){
        for(VersandModul item : versandExport.getModulsList()){
            if (item.getStatus()==20){return item;}
        }
        return null;
    }

    boolean isExportFinished(VersandExport versandExport){
        boolean isFinished = true;
        for(VersandModul item : versandExport.getModulsList()){
            if(item.getStatus()!=40) isFinished = false;
        }
        return isFinished;
    }


    public List<VersandModul> getVersandModulList() {
        return versandModulList;
    }

    public void setVersandModulList(List<VersandModul> versandModulList) {
        this.versandModulList = versandModulList;
    }

    public String getModulScan() {
        return modulScan;
    }

    public void setModulScan(String modulScan) {
        this.modulScan = modulScan;
    }

    public VersandModul getSelectedModul() {
        return selectedModul;
    }

    public void setSelectedModul(VersandModul selectedModul) {
        this.selectedModul = selectedModul;
    }
}
