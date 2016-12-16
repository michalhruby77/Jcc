package com.leoni.viewModel;

import com.leoni.data.manager.SettingsStorageManager;
import com.leoni.data.manager.oldJIT.JitAbruManager;
import com.leoni.data.models.SettingsStorage;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 15.10.2015
 * Time: 10:30
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AbrufControlVM {


    @WireVariable
    JitAbruManager jitAbruManager;

    @WireVariable
    SettingsStorageManager settingsStorageManager;

    @Wire
    Rows rows;

    @Wire
    Rows rows2;

    private List<SettingsStorage> settingsStorageList;
    private List<Textbox> prodnrList;
    private List<Label> infoAbrufList;
    private List<Textbox> descList;
    private List<Row> rowList;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);

        prodnrList = new ArrayList<>();
        infoAbrufList = new ArrayList<>();
        descList = new ArrayList<>();
        rowList = new ArrayList<>();
        settingsStorageList = settingsStorageManager.findAbrufControl();
        int counter = 0;
        for (SettingsStorage ss : settingsStorageList){

            Row row = new Row();
            Textbox tb1 = new Textbox();
            tb1.setWidth("100%");
            tb1.setValue(ss.getValue());
            Label label = new Label();
            Textbox tb2 = new Textbox();
            tb2.setWidth("100%");
            tb2.setValue(ss.getDescription());

            row.appendChild(tb1);
            row.appendChild(label);
            row.appendChild(tb2);

            if (counter < 15) rows.appendChild(row);
            else rows2.appendChild(row);

            prodnrList.add(tb1);
            infoAbrufList.add(label);
            descList.add(tb2);
            rowList.add(row);
            counter++;
        }
    }

    @Command
    public void submit(){


        settingsStorageManager.insertAbrufControl(prodnrList,descList);

        for (int i = 0; i < prodnrList.size(); i++){
            String  jitAbruNr = jitAbruManager.controlAbruf(prodnrList.get(i).getValue());
            if (jitAbruNr != null )  {infoAbrufList.get(i).setValue("Nachadza sa v abrufe c. " + jitAbruNr);
                                      rowList.get(i).setStyle("background-color: #99FF66");
            }
            else infoAbrufList.get(i).setValue("Nenachadza sa v abrufe");
        }
    }


}
