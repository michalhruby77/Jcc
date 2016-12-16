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
                                @BindingParam("vlayout2") Vlayout vlayout2,
                                @BindingParam("vlayout3") Vlayout vlayout3,
                                @BindingParam("vlayout4") Vlayout vlayout4,
                                @BindingParam("vlayout5") Vlayout vlayout5,
                                @BindingParam("vlayout6") Vlayout vlayout6,
                                @ContextParam(ContextType.VIEW) Component view,
                                @ExecutionArgParam("myFoamWorkplace") FoamWorkplace foamWorkplace) throws Exception {
            myFoamWorkplace = foamWorkplace;
            foamWorkplaceModulsList = myFoamWorkplace.getModulsList();
            for(FoamWorkplaceModuls item : foamWorkplaceModulsList){
                Textbox myTextbox = new Textbox();
                myTextbox.setValue(item.getProdNrPrefix().trim());
                if (foamWorkplaceModulsList.indexOf(item)>=0 && foamWorkplaceModulsList.indexOf(item)<=9)
                vlayout.appendChild(myTextbox);
                if (foamWorkplaceModulsList.indexOf(item)>=10 && foamWorkplaceModulsList.indexOf(item)<=19)
                    vlayout2.appendChild(myTextbox);
                if (foamWorkplaceModulsList.indexOf(item)>=20 && foamWorkplaceModulsList.indexOf(item)<=29)
                    vlayout3.appendChild(myTextbox);
                if (foamWorkplaceModulsList.indexOf(item)>=30 && foamWorkplaceModulsList.indexOf(item)<=39)
                    vlayout4.appendChild(myTextbox);
                if (foamWorkplaceModulsList.indexOf(item)>=40 && foamWorkplaceModulsList.indexOf(item)<=49)
                    vlayout5.appendChild(myTextbox);
                if (foamWorkplaceModulsList.indexOf(item)>=50 && foamWorkplaceModulsList.indexOf(item)<=59)
                    vlayout6.appendChild(myTextbox);

                textboxList.add(myTextbox);
            }
    }

    @Command
    public void addTextBoxVert(@BindingParam("vlayout") Vlayout vlayout,
                               @BindingParam("vlayout2") Vlayout vlayout2,
                               @BindingParam("vlayout3") Vlayout vlayout3,
                               @BindingParam("vlayout4") Vlayout vlayout4,
                               @BindingParam("vlayout5") Vlayout vlayout5,
                               @BindingParam("vlayout6") Vlayout vlayout6) {
        if(!textboxList.isEmpty()&&!textboxList.get(textboxList.size()-1).getValue().trim().equals(""))
        {
            Textbox myTextbox = new Textbox();

            if (textboxList.size()>=0 && textboxList.size()<=9)
                vlayout.appendChild(myTextbox);
            if (textboxList.size()>=10 && textboxList.size()<=19)
                vlayout2.appendChild(myTextbox);
            if (textboxList.size()>=20 && textboxList.size()<=29)
                vlayout3.appendChild(myTextbox);
            if (textboxList.size()>=30 && textboxList.size()<=39)
                vlayout4.appendChild(myTextbox);
            if (textboxList.size()>=40 && textboxList.size()<=49)
                vlayout5.appendChild(myTextbox);
            if (textboxList.size()>=50 && textboxList.size()<=59)
                vlayout6.appendChild(myTextbox);
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
