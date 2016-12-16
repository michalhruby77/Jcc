package com.leoni.viewModel;

import com.leoni.data.manager.ModulsAliasManager;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.ModulsAlias;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.9.2015
 * Time: 9:56
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EditAliasVM {


    @WireVariable
    private ModulsManager modulsManager;

    @WireVariable
    private ModulsAliasManager modulsAliasManager;

    private List<Textbox> textboxList = new ArrayList<Textbox>();
    private Moduls myModul;

    @AfterCompose
    public void doAfterCompose( @BindingParam("vlayout") Vlayout vlayout,
                                @ContextParam(ContextType.VIEW) Component view,
                                @ExecutionArgParam("myModul") Moduls moduls) throws Exception {
        myModul = moduls;
        for(ModulsAlias item : myModul.getAliasList()){
            Textbox myTextbox = new Textbox();
            myTextbox.setValue(item.getSachNrLieferant().trim());
            vlayout.appendChild(myTextbox);
            textboxList.add(myTextbox);
        }
    }

    @Command
    public void addTextBoxVert(@BindingParam("vlayout") Vlayout vlayout) {
        if(!textboxList.isEmpty()/*&&!textboxList.get(textboxList.size()-1).getValue().trim().equals("")*/)
        {
            String modulStr = textboxList.get(textboxList.size()-1).getValue().trim();
            if(!modulsManager.findBySachNrLieferant(modulStr).isEmpty()){
            Textbox myTextbox = new Textbox();
            vlayout.appendChild(myTextbox);
            textboxList.add(myTextbox);}
            else{ Messagebox.show("Predosly modul nenajdeny!", "Error", Messagebox.OK, Messagebox.ERROR);}
        }
        else{
            Textbox myTextbox = new Textbox();
            vlayout.appendChild(myTextbox);
            textboxList.add(myTextbox);
        }

    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {
        List<String> aliasListStr = new ArrayList<String>();
        boolean allAliasesOk = true;
        for(Textbox item: textboxList){
            if(!item.getValue().trim().equals("")){
                String modulStr = item.getValue().trim();
                if(!modulsManager.findBySachNrLieferant(modulStr).isEmpty()) aliasListStr.add(modulStr);
                else{
                if (allAliasesOk) Messagebox.show("Modul neexistuje: "+modulStr, "Error", Messagebox.OK, Messagebox.ERROR);
                allAliasesOk = false;
                }
            }
        }
        if (allAliasesOk){
            List<ModulsAlias> aliasList = new ArrayList<>();
            for (String item : aliasListStr){
                ModulsAlias modulsAlias = modulsAliasManager.findBySachNrLieferant(item.trim()).get(0);
                aliasList.add(modulsAlias);
            }
            myModul.setAliasList(aliasList);
            modulsManager.saveOrUpdate(myModul);
            BindUtils.postGlobalCommand(null, null, "refresh", null);
            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
            view.detach();
        }
    }
}
