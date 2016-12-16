package com.leoni.viewModel;

import com.leoni.data.manager.UsersManager;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.Users;
import com.leoni.data.models.VersandExport;
import com.leoni.data.models.VersandModul;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 14.10.2015
 * Time: 8:14
 * To change this template use File | Settings | File Templates.
 */

public class ModulsEditRnd implements  ListitemRenderer {


        @Override
        public void render(Listitem listitem, Object o, int i) throws Exception {
            String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Moduls moduls = (Moduls) o;
            Label sachNrBest = new Label(moduls.getSachNrBest());
            Listcell lcSachnrBest = new Listcell();
            lcSachnrBest.appendChild(sachNrBest);
            lcSachnrBest.setParent(listitem);
            Label sachNrLief = new Label(moduls.getSachNrLieferant());
            Listcell lcSachnrLief = new Listcell();
            lcSachnrLief.appendChild(sachNrLief);
            lcSachnrLief.setParent(listitem);
            Label prodGroup = new Label(moduls.getProdGruppe());
            Listcell lcprodGroup = new Listcell();
            lcprodGroup.appendChild(prodGroup);
            lcprodGroup.setParent(listitem);
            Label ausfue = new Label(moduls.getAusfuehrung());
            Listcell lcAusfue = new Listcell();
            lcAusfue.appendChild(ausfue);
            lcAusfue.setParent(listitem);
            Label ksKz = new Label(moduls.getKabelsatzKz());
            Listcell lcKskz = new Listcell();
            lcKskz.appendChild(ksKz);
            lcKskz.setParent(listitem);
            Checkbox grund = new Checkbox();
            grund.setChecked(moduls.getGrund());
            grund.setDisabled(true);
            Listcell lcGrund = new Listcell();
            lcGrund.appendChild(grund);
            lcGrund.setParent(listitem);
            Checkbox block = new Checkbox();
            block.setChecked(moduls.getBlock());
            block.setDisabled(true);
            Listcell lcBlock = new Listcell();
            lcBlock.appendChild(block);
            lcBlock.setParent(listitem);
            Datebox db = new Datebox();
            db.setValue(moduls.getCreateTime());
            db.setFormat("yyyy/MM/dd HH:mm");
            db.setDisabled(true);
            Listcell lcDate = new Listcell();
            lcDate.appendChild(db);
            lcDate.setParent(listitem);
            Label foamForm = new Label(moduls.getFoamForm());
            Listcell lcFoamForm = new Listcell();
            lcFoamForm.appendChild(foamForm);
            lcFoamForm.setParent(listitem);
            Label carBody = new Label(moduls.getCarBody());
            Listcell lcCarBody = new Listcell();
            lcCarBody.appendChild(carBody);
            lcCarBody.setParent(listitem);
            Label colorName = new Label(moduls.getColor().getName());
            Listcell lcColor = new Listcell();
            lcColor.appendChild(colorName);
            lcColor.setParent(listitem);
            Label diff = new Label();
            if (moduls.getDifficulty() != null){
                if(moduls.getDifficulty() == 1) {diff.setValue("Lahka");}
                if(moduls.getDifficulty() == 2) {diff.setValue("Tazka");}
            }
            Listcell lcDiff = new Listcell();
            lcDiff.appendChild(diff);
            lcDiff.setParent(listitem);

            Button btnPrint = new Button();
            btnPrint.setImage("images/print.png");
            btnPrint.setMold("trendy");
            /*btnPrint.addEventListener("onClick",new EventListener() {
                public void onEvent(Event evt) {
                    Map<String, Object> arguments = new HashMap<String, Object>();
                    arguments.put("export", ve);
                    BindUtils.postGlobalCommand(null, null, "openExportVersand", arguments);
                }});*/
            Listcell lcPrint = new Listcell();
            lcPrint.appendChild(btnPrint);
            lcPrint.setParent(listitem);

            Button btnALias = new Button();
            btnALias.setImage("images/ALIAS.png");
            btnALias.setMold("trendy");
            /*btnPrint.addEventListener("onClick",new EventListener() {
                public void onEvent(Event evt) {
                    Map<String, Object> arguments = new HashMap<String, Object>();
                    arguments.put("export", ve);
                    BindUtils.postGlobalCommand(null, null, "openExportVersand", arguments);
                }});*/
            Listcell lcAlias = new Listcell();
            lcAlias.appendChild(btnALias);
            lcAlias.setParent(listitem);

            Button btnCopy = new Button();
            btnCopy.setImage("images/copy.png");
            btnCopy.setMold("trendy");
            /*btnPrint.addEventListener("onClick",new EventListener() {
                public void onEvent(Event evt) {
                    Map<String, Object> arguments = new HashMap<String, Object>();
                    arguments.put("export", ve);
                    BindUtils.postGlobalCommand(null, null, "openExportVersand", arguments);
                }});*/
            Listcell lcCopy = new Listcell();
            lcCopy.appendChild(btnCopy);
            lcCopy.setParent(listitem);

            Button btnDelete = new Button();
            btnDelete.setImage("images/delete.png");
            btnDelete.setMold("trendy");
            /*btnPrint.addEventListener("onClick",new EventListener() {
                public void onEvent(Event evt) {
                    Map<String, Object> arguments = new HashMap<String, Object>();
                    arguments.put("export", ve);
                    BindUtils.postGlobalCommand(null, null, "openExportVersand", arguments);
                }});*/
            Listcell lcDelete = new Listcell();
            lcDelete.appendChild(btnDelete);
            lcDelete.setParent(listitem);
        }

}
