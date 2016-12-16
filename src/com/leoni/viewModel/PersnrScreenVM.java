package com.leoni.viewModel;

import com.leoni.data.manager.VersandModulManager;
import com.leoni.data.models.VersandModul;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 23.3.2015
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PersnrScreenVM {

    @WireVariable
    private VersandModulManager versandModulManager;

    private VersandModul selectedModul;
    private String persnr = "";


    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("myModul") VersandModul myObject){
        selectedModul = myObject;
    }

    @Command
    public void save(@ContextParam(ContextType.VIEW) Component view)
    {
        if(persnr.trim().startsWith("persnr")){
            selectedModul.setStatus(40);
            selectedModul.setPersnr(persnr);
            versandModulManager.saveOrUpdate(selectedModul);
            BindUtils.postGlobalCommand(null, null, "refreshVersandExport", null);
            Messagebox.show("Podpisany!", "Podpisany.", Messagebox.OK, null);
            view.detach();}
        else{ Messagebox.show("zle naskenovane os. cislo!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    public String getPersnr() {
        return persnr;
    }

    public void setPersnr(String persnr) {
        this.persnr = persnr;
    }
}