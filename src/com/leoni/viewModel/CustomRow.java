package com.leoni.viewModel;

import com.leoni.data.manager.ModulsManager;
import com.leoni.data.models.Moduls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 25.8.2014
 * Time: 10:13
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CustomRow extends Row{

    @Autowired
    @WireVariable
    ModulsManager modulsManager;


    private Textbox textboxSach;
    private Button buttonOK,buttonCancel;
    private Combobox combobox;
    private Label kabelSatz;
    private String modulSachNr;
    private Moduls selectedModul = null;

    CustomRow(){
        setAlign("center");
        Selectors.wireVariables(this, this, Selectors.newVariableResolvers(getClass(), null));
        textboxSach = new Textbox();
        buttonOK =  new Button("OK");
        buttonCancel = new Button("Zmazat");
        textboxSach.setWidth("90%");
        kabelSatz = new Label();
        combobox = new Combobox();
        combobox.setDisabled(true);
        combobox.setWidth("80%");
        buttonOK.addEventListener("onClick",new EventListener() {
            public void onEvent(Event evt) {
                if(!textboxSach.isReadonly()&&textboxSach.getValue()!=null&&!modulsManager.findBySachNrBest(textboxSach.getValue().trim()).isEmpty())
                    createLiefCombo();
                if(textboxSach.isReadonly()&&combobox!=null)submitSelectedModul();


            }});
        buttonCancel.addEventListener("onClick",new EventListener() {
            public void onEvent(Event evt) {
                if(!combobox.isDisabled()&&textboxSach.isReadonly()){
                    textboxSach.setReadonly(false);
                    textboxSach.setValue("");
                    combobox.setModel(null);
                    combobox.setValue(null);
                    combobox.setDisabled(true);
                    kabelSatz.setValue(null);
                }
                if(combobox.isDisabled()&&combobox.getModel()!=null){combobox.setDisabled(false);
                                                                     selectedModul = null;
                                                                    }


            }});
        appendChild(textboxSach);
        appendChild(combobox);
        appendChild(kabelSatz);
        appendChild(buttonOK);
        appendChild(buttonCancel);

    //981.612.230.55 981.612.101.80
    }

   public Moduls getSelectedModul(){
        return selectedModul;
    }
    public void createLiefCombo(){
        textboxSach.setReadonly(true);
        combobox.setDisabled(false);
        combobox.setReadonly(true);
        modulSachNr = textboxSach.getValue().trim();
        List<Moduls> modulsListForSachNr = modulsManager.findBySachNrBest(modulSachNr);
        combobox.setModel(new ListModelList<Moduls>(modulsListForSachNr));
        if(!modulsListForSachNr.isEmpty()){
            kabelSatz.setValue(modulsListForSachNr.get(0).getKabelsatzKz().trim());
            Set<Moduls> selectedModulToSet = new HashSet();
            selectedModulToSet.add(getHighestIndex(modulsListForSachNr));
            ListModelList lml = (ListModelList)combobox.getModel();
            lml.setSelection(selectedModulToSet);
        }
        else{setStyle("background-color: #ff6666");}
        MyRenderer myRenderer = new MyRenderer();
        combobox.setItemRenderer(myRenderer);
    }

    public void submitSelectedModul(){
        ListModelList lml = (ListModelList)combobox.getModel();
        Set<Moduls> modulsSet = lml.getSelection();
            for(Moduls tempModul : modulsSet){
                selectedModul = tempModul;
            }
        combobox.setDisabled(true);
        //System.out.println("druhy");
        //System.out.println(selectedModul.getSachNrLieferant());
    }

    public void setSachNrBest(String sachNrBest){
        textboxSach.setValue(sachNrBest);
        modulSachNr = sachNrBest;
    }

    private Moduls getHighestIndex(List<Moduls> modulsList){
        Moduls highestModul = modulsList.get(0);
        for(Moduls item : modulsList){
            System.out.println(item.getSachNrLieferant());
            System.out.println(item.getSachNrLieferant().compareTo(highestModul.getSachNrLieferant()));
            if (item.getSachNrLieferant().compareTo(highestModul.getSachNrLieferant())>0){
                highestModul = item;
            }
        }
        System.out.println(highestModul.getSachNrLieferant());
    return highestModul;
    }


}
