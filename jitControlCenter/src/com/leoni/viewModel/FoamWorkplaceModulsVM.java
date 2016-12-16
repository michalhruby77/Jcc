package com.leoni.viewModel;

import com.leoni.data.manager.FoamWorkplaceManager;
import com.leoni.data.manager.FoamWorkplaceModulsManager;
import com.leoni.data.models.FoamWorkplace;
import com.leoni.data.models.FoamWorkplaceModuls;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 10.10.2014
 * Time: 10:12
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FoamWorkplaceModulsVM {


    @WireVariable
    private FoamWorkplaceManager foamWorkplaceManager;

    @WireVariable
    private FoamWorkplaceModulsManager foamWorkplaceModulsManager;

    private List<FoamWorkplaceModuls> foamWorkplaceModulsList;
    private FoamWorkplace selectedFoamWorkplace;
    private List<Textbox> textboxList = new ArrayList<Textbox>();
    private FoamWorkplace myFoamWorkplace;
    @AfterCompose
    public void doAfterCompose( @BindingParam("vlayout") Vlayout vlayout,
                                @ContextParam(ContextType.VIEW) Component view,
                                @ExecutionArgParam("myFoamWorkplace") FoamWorkplace foamWorkplace) throws Exception {
            myFoamWorkplace = foamWorkplace;
            foamWorkplaceModulsList = myFoamWorkplace.getModulsList();
            for(FoamWorkplaceModuls item : foamWorkplaceModulsList){
                Textbox myTextbox = new Textbox();
                myTextbox.setValue(item.getProdNrPrefix().trim());
                vlayout.appendChild(myTextbox);
                textboxList.add(myTextbox);
            }
    }

    @Command
    public void addTextBoxVert(@BindingParam("vlayout") Vlayout vlayout) {
        if(!textboxList.isEmpty()&&!textboxList.get(textboxList.size()-1).getValue().trim().equals(""))
        {
            Textbox myTextbox = new Textbox();
            vlayout.appendChild(myTextbox);
            textboxList.add(myTextbox);
        }
        else{ Messagebox.show("Vyplnte predosly modul!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {
                List<String> prefixList = new ArrayList<String>();
                for(Textbox item: textboxList){
                   if(!item.getValue().trim().equals("")){
                       prefixList.add(item.getValue().trim());
                   }
                }
        myFoamWorkplace.getModulsList().clear();
        foamWorkplaceManager.saveOrUpdate(myFoamWorkplace) ;
        foamWorkplaceModulsManager.deleteItems(myFoamWorkplace);
        foamWorkplaceModulsManager.addFoamWorkplaceModulsList(myFoamWorkplace,prefixList);

        BindUtils.postGlobalCommand(null, null, "refreshFoamWorkplace", null);
    view.detach();
    }
}
