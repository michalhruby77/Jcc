package com.leoni.viewModel;

import com.leoni.data.models.VersandExport;
import com.leoni.data.models.VersandModul;
import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 16.3.2015
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */
public class VersandExportRenderer implements ListitemRenderer {
    @Override
    public void render(Listitem listitem, Object o, int i) throws Exception {
        VersandExport ve = (VersandExport) o;
        boolean isFinished = true;
        for(VersandModul item : ve.getModulsList()){
            if (item.getStatus()!=40){isFinished = false;}
        }
        listitem.setValue(ve);
        if (isFinished) {listitem.setStyle("background-color: #99FF66");}
        SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyyy, HH:mm");
        new Listcell(dt.format(ve.getDate())).setParent(listitem);
        Button btnOpen = new Button();
        btnOpen.setImage("images/open.png");
        btnOpen.setMold("trendy");
        //btnOpen.setAction("openExport");
        btnOpen.addEventListener("onClick",new EventListener() {
            public void onEvent(Event evt) {
                Map<String, Object> arguments = new HashMap<String, Object>();
                arguments.put("export", ve);
                BindUtils.postGlobalCommand(null, null, "openExportVersand", arguments);
            }});
        Listcell lcOpen = new Listcell();
        lcOpen.appendChild(btnOpen);
        lcOpen.setParent(listitem);
        Button btnPrint = new Button();
        btnPrint.setImage("images/print.png");
        btnPrint.setMold("trendy");
        //btnPrint.setAction("printExport");
        btnPrint.addEventListener("onClick",new EventListener() {
            public void onEvent(Event evt) {
                Map<String, Object> arguments = new HashMap<String, Object>();
                arguments.put("export", ve);
                BindUtils.postGlobalCommand(null, null, "printExportVersand", arguments);
            }});
        Listcell lcPrint = new Listcell();
        lcPrint.appendChild(btnPrint);
        lcPrint.setParent(listitem);
        Button btnDelete = new Button();
        btnDelete.setImage("images/delete.png");
        btnDelete.setMold("trendy");
        //btnDelete.setAction("deleteExport");
        btnDelete.addEventListener("onClick",new EventListener() {
            public void onEvent(Event evt) {
                Map<String, Object> arguments = new HashMap<String, Object>();
                arguments.put("export", ve);
                BindUtils.postGlobalCommand(null, null, "deleteExportVersand", arguments);
            }});
        Listcell lcDelete = new Listcell();
        lcDelete.appendChild(btnDelete);
        lcDelete.setParent(listitem);
    }
}
