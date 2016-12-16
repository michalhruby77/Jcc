package com.leoni.viewModel;

import com.leoni.data.manager.BoardTypeManager;
import com.leoni.data.manager.ExcelDocumentManager;
import com.leoni.data.manager.Lpab62Manager;
import com.leoni.data.manager.Lpab64Manager;
import com.leoni.data.manager.dab.PrnrManager;
import com.leoni.data.models.BoardType;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.dab.Prnr;
import com.leoni.data.models.nonDBmodels.PrnrLpab64Obj;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hrmi1005 on 5. 1. 2016.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HarnessInContainerVM {

    @WireVariable
    BoardTypeManager boardTypeManager;

    @WireVariable
    PrnrManager prnrManager;

    @WireVariable
    Lpab64Manager lpab64Manager;

    @WireVariable
    Lpab62Manager lpab62Manager;

    @WireVariable
    ExcelDocumentManager excelDocumentManager;


    @Wire
    Rows rows;

    String modul;
    Set<String> containerNameSet;
    List<PrnrLpab64Obj> prnrLpab64ObjList;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view)
    {
        containerNameSet = new HashSet<>();
        Selectors.wireComponents(view, this, false);
        for (BoardType bt : boardTypeManager.getAll()){
            Checkbox cb = new Checkbox();
            cb.addEventListener("onCheck",new EventListener() {
                public void onEvent(Event evt) {
                  if (cb.isChecked()) {containerNameSet.add(bt.getName().trim());}
                    else{containerNameSet.remove(bt.getName().trim());}
                }});
            Label label = new Label(bt.getName().trim());
            Row row = new Row();
            row.appendChild(label);
            row.appendChild(cb);
            rows.appendChild(row);
        }
    }

    @Command
    public void submit() {
           if (modul != null && !modul.trim().equals("")){
               prnrLpab64ObjList = prnrManager.setContainer(lpab62Manager.getPrnrLpab64Obj(modul.trim()), containerNameSet);
               exportToExcel(prnrLpab64ObjList);
           }
           else{
               List<Prnr> prnrList = prnrManager.getPrnr(containerNameSet);
               prnrLpab64ObjList = new ArrayList<>();
               for (Prnr prnr : prnrList){
                   PrnrLpab64Obj prnrLpab64Obj = new PrnrLpab64Obj(prnr.getProdNr(), prnr.getKabelsatzKz(),
                           prnr.getBoardType(),prnr.getProdDate().toString());
                   prnrLpab64ObjList.add(prnrLpab64Obj);
               }

               List<Lpab64>  lpab64List;

               for (PrnrLpab64Obj prnrLpab64Obj : prnrLpab64ObjList){
                    lpab64List = lpab64Manager.findByProdnrAndKabelsatz(prnrLpab64Obj.getPrnr(),prnrLpab64Obj.getKsKz());
                    prnrLpab64Obj.setModulList(lpab64List);
               }

               exportToExcel(prnrLpab64ObjList);
           }
    }

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public void exportToExcel(List<PrnrLpab64Obj> prnrLpab64ObjList){

        File xslFile = excelDocumentManager.exportPrnrToFile(prnrLpab64ObjList);
        byte[] buffer = new byte[(int) xslFile.length()];
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(xslFile);

            fs.read(buffer);
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        AMedia amedia = new AMedia("Containers.csv", "csv", "application/file", is);
        Filedownload.save(amedia);
    }
}