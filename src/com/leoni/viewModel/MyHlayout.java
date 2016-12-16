package com.leoni.viewModel;


import com.leoni.data.manager.ModulsManager;
//import com.leoni.data.manager.ModulsManagerImpl;
import com.leoni.data.manager.ModulsManagerImpl;
import com.leoni.data.models.Moduls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 20.3.2014
 * Time: 12:22
 * To change this template use File | Settings | File Templates.
 */

public class MyHlayout extends Hlayout {

    @WireVariable
    private ModulsManager modulsManager;

    private List<Textbox> tbList = new ArrayList<Textbox>();
    private Button button = new Button(" + ");
    private Hlayout hlayout = new Hlayout();
    private Textbox firstTb;

    MyHlayout() {
        Selectors.wireVariables(this, this, Selectors.newVariableResolvers(getClass(), null));
        firstTb = new Textbox();
        firstTb.setWidth("80px");
        tbList.add(firstTb);
        hlayout.appendChild(firstTb);
        hlayout.appendChild(new Label(" / "));
        button.setMold("trendy");
        button.addEventListener("onClick",new EventListener() {
            public void onEvent(Event evt) {
                if(!modulsManager.findBySachNrLieferant(tbList.get(tbList.size()-1).getValue()).isEmpty()){
                Textbox tempTb = new Textbox();
                tempTb.setWidth("80px");
                tbList.add(tempTb);
                hlayout.appendChild(tempTb);
                hlayout.appendChild(new Label(" / "));}
            }});
        this.appendChild(hlayout);
        this.appendChild(button);
    }


    public List<String> getSelectedModuls(){
        List<String> selectedModuls = new ArrayList<String>();
        for ( Textbox item : tbList){
            if(!modulsManager.findBySachNrLieferant(item.getValue()).isEmpty()) {selectedModuls.add(item.getValue());}
        }
        return selectedModuls;
    }




    public void addTextboxSet(Set<Moduls> selectedModuls){
        boolean isFirst = true;
        for (Moduls item:selectedModuls){
                if(isFirst){
                    firstTb.setValue(item.getSachNrLieferant());
                    isFirst = false;
                }
                else{
                Textbox tempTb = new Textbox();
                tempTb.setWidth("80px");
                tempTb.setValue(item.getSachNrLieferant());
                tbList.add(tempTb);
                hlayout.appendChild(tempTb);
                hlayout.appendChild(new Label(" / "));}

        }
    }

    public boolean hasAtLeast1SelectedModul(){
        boolean hasSelectedModul = false;
        for (Textbox item : tbList){
            if (item!=null && item.getValue()!=null && !modulsManager.findBySachNrLieferant(item.getValue()).isEmpty()){
               hasSelectedModul = true;
            }
        }
        return hasSelectedModul;
    }


}
