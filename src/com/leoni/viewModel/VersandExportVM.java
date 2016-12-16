package com.leoni.viewModel;

import com.leoni.data.manager.*;
import com.leoni.data.models.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 2.3.2015
 * Time: 14:06
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VersandExportVM {

    @WireVariable
    private ExcelDocumentManager excelDocumentManager;

    @WireVariable
    private VersandExportManager versandExportManager;

    @WireVariable
    private VersandModulManager versandModulManager;

    @WireVariable
    private PrintingManager printingManager;

    @WireVariable
    private ModulsManager modulsManager;

    @WireVariable
    private VsFarbyManager vsFarbyManager;

    @WireVariable
    private VsManager vsManager;

    @WireVariable
    private VsModulyWrmManager vsModulyWrmManager;

    @WireVariable
    private ModulClipListWrmManager modulClipListWrmManager;


    private List<VersandModul> versandModulList;
    private List<VersandExport> versandExportList;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view) throws Exception {
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        versandExportList = versandExportManager.getAll();
        Collections.sort(versandExportList);
        //List<ModulClipListWrm> modulClipListWrms = excelDocumentManager.getModulsClipFromExcel(null);
        //List<VsModulyWrm> modulClipListWrms = excelDocumentManager.getVsModulsFromExcel(null);
        //List<Moduls> modulList = excelDocumentManager.getModulsFromExcel(null);
        /*for(ModulClipListWrm item:modulClipListWrms){
            modulClipListWrmManager.create(item);
        } */
       /*List<ModulClipListWrm> modulClipListWrms = excelDocumentManager.getModulsClipFromExcel(null);
        for(ModulClipListWrm item:modulClipListWrms){
             modulClipListWrmManager.create(item);
        }
        //-----------------------------------ULOZENIE Z EXCELU
        Set<String> colors = excelDocumentManager.getAllColorsFromExcel();
        for(String item:colors){
            String fileName = item.replace("/","-");
            System.out.println("FAJLNEJM "+fileName);
            AImage aImage = new AImage("c://letovanie//"+fileName+".JPG");
            vsFarbyNewManager.addVsFarba(item,aImage,user);
        }
        Map<String,String> vsNames = excelDocumentManager.getAllVsFromExcel();
        Iterator it = vsNames.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            VsFarbyNew vsFarbyNew = vsFarbyNewManager.getVsFarba(pair.getValue().toString());
            vsNewManager.addVs(pair.getKey().toString(),vsFarbyNew,user);
            it.remove();
        }
        List<VsModulyWrmNew> vsModuls = excelDocumentManager.getAllVsModulsFromExcel();
        int counter = 0;
        Set<String> sachnrs = new HashSet<>();
        for (VsModulyWrmNew vsModulyWrmNew:vsModuls){
            Moduls moduls = null;
              if(modulsManager.findBySachNrLieferant(vsModulyWrmNew.getSachNrLief().trim()).isEmpty()){
                  System.out.println("NEZALOZENY MODUL "+vsModulyWrmNew.getSachNrLief());
                  counter++;
                  sachnrs.add(vsModulyWrmNew.getSachNrLief());
              }
              else{moduls = modulsManager.findBySachNrLieferant(vsModulyWrmNew.getSachNrLief().trim()).get(0);}
              List<VsNew> vsNewList = vsNewManager.getVs(vsModulyWrmNew.getPopisTemp().trim());
              VsNew vsNew = null;
              if(!vsNewList.isEmpty()) vsNew = vsNewList.get(0);
              vsModulyWrmNew.setModuls(moduls);
              vsModulyWrmNew.setVsNew(vsNew);
              vsModulyWrmNew.setChangedBy(user);
              vsModulyWrmNew.setChangedDate(new Date());
              vsModulyWrmNewManager.create(vsModulyWrmNew);
        }
        System.out.println("pocet nezalozenych modulov: "+counter);
        System.out.println("pocet nezalozenych modulov: "+sachnrs);*/
        //-------------------------------------ULOZENIE Z DB-----------------------------------------------------------------
        /* List<VsFarby> vsFarbyList = vsFarbyManager.getAll();
        for (VsFarby item:vsFarbyList){
            vsFarbyNewManager.addVsFarba(item.getPopis().trim(),item.getObrazok(),user);
        }

        List<Vs> vsList = vsManager.getAll();

        for (Vs item:vsList){
            VsFarbyNew vsFarbyNew = vsFarbyNewManager.getVsFarba(item.getVsFarby().getPopis().trim());
            vsNewManager.addVs(String.valueOf(item.getId()),vsFarbyNew,user);
        }

        List<VsModulyWrm> vsModulsList = vsModulyWrmManager.getAll();
        Set<String> chzbajuceVska = new HashSet<>();
        for (VsModulyWrm item:vsModulsList){
            Moduls moduls = item.getModuls();
            List<VsNew> vsNewList = vsNewManager.getVs(String.valueOf(item.getId_vs()));
            VsNew vsNew = null;
            if(!vsNewList.isEmpty()) vsNew = vsNewList.get(0);
            else chzbajuceVska.add(String.valueOf(item.getId_vs()));
            VsModulyWrmNew vsModulyWrmNew = new VsModulyWrmNew();
            vsModulyWrmNew.setModuls(moduls);
            vsModulyWrmNew.setVsNew(vsNew);
            vsModulyWrmNew.setVodic(item.getVodic());
            vsModulyWrmNew.setPrierez(item.getPrierez());
            vsModulyWrmNew.setChangedBy(user);
            vsModulyWrmNew.setChangedDate(new Date());
            vsModulyWrmNewManager.create(vsModulyWrmNew);
        }
        System.out.println("Chybajuce vska: " + chzbajuceVska); */
       /* List<VsModulyWrm> vsModul = excelDocumentManager.getVsModulsFromExcel(null);

        for(VsModulyWrm vsModulyWrm : vsModul){
            vsModulyWrmManager.create(vsModulyWrm);
        }*/
    }

    @Command
    @NotifyChange({"versandModulList","versandExportList"})
    public void createExport(@BindingParam("event") UploadEvent event){
           System.out.println("function was called");
           Media media = event.getMedia();
           versandModulList = excelDocumentManager.getVersandModuls(media);
           VersandExport versandExport = new VersandExport();
           versandExport.setDate(new Date());
           versandExport = versandExportManager.create(versandExport);
           versandExport.setModulsList(versandModulList);
           for(VersandModul item : versandModulList){

               item.setVersandExport(versandExport);
               versandModulManager.create(item);
           }
        versandExportList = versandExportManager.getAll();
        Collections.sort(versandExportList);
        Map<String, VersandExport> arguments = new HashMap<String, VersandExport>();
        arguments.put("myExport", versandExport);
        Window window = (Window) Executions.createComponents(
                "versand/versandModuls.zul", null, arguments);
        window.doModal();
    }

    @GlobalCommand
    public void openExportVersand(@BindingParam("export") VersandExport myExport){
        Map<String, VersandExport> arguments = new HashMap<String, VersandExport>();
        arguments.put("myExport", myExport);
        Window window = (Window) Executions.createComponents(
                "versand/versandModuls.zul", null, arguments);
        window.doModal();
    }

    @GlobalCommand
    public void printExportVersand(@BindingParam("export") VersandExport myExport){
        /*List<VersandModul> tempVersandModulList = myExport.getModulsList();
        boolean canPrint = true;
        for (VersandModul item : tempVersandModulList){
            if (item.getStatus()!=40) canPrint = false;
        }  */

        //if (canPrint){

            File xslFile =  excelDocumentManager.printVersandExport(myExport);
            byte[] buffer = new byte[(int) xslFile.length()];
            FileInputStream fs = null;
            try {
                fs = new FileInputStream(xslFile);
                fs.read(buffer);
                fs.close();
                ByteArrayInputStream is = new ByteArrayInputStream(buffer);
                AMedia amedia = new AMedia("export-report.xls", "xls", "application/file", is);
                Filedownload.save(amedia);
            } catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

       // }
       // else {Messagebox.show("Export este nie je dokonceny.", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    @GlobalCommand
    @NotifyChange({"versandExportList"})
    public void deleteExportVersand(@BindingParam("export") VersandExport myExport){
        Messagebox.show("Ste si isti?", "Vymazat?", Messagebox.YES|Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            versandExportManager.delete(myExport);
                            versandExportList = versandExportManager.getAll();
                            BindUtils.postGlobalCommand(null, null, "refresh", null);

                        }
                    }
                }
        );


    }

    @NotifyChange("versandExportList")
    @GlobalCommand
    public void refresh() {
        versandExportList = versandExportManager.getAll();
        Collections.sort(versandExportList);
    }

    public List<VersandModul> getVersandModulList() {
        return versandModulList;
    }

    public void setVersandModulList(List<VersandModul> versandModulList) {
        this.versandModulList = versandModulList;
    }

    public List<VersandExport> getVersandExportList() {
        return versandExportList;
    }

    public void setVersandExportList(List<VersandExport> versandExportList) {
        this.versandExportList = versandExportList;
    }
}
