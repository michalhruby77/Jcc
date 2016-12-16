package com.leoni.viewModel;

import com.leoni.data.manager.ProdNrLogManager;
import com.leoni.data.models.ProdNrLog;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 21.10.2015
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HarnessLogVM {

    @WireVariable
    private ProdNrLogManager prodNrLogManager;

    @Wire
    Listbox listbox;

    private List<ProdNrLog> prodNrLogList;



    @AfterCompose
    public void doAfterCompose( @ContextParam(ContextType.VIEW) Component view,
                                @ExecutionArgParam("prodNr") String prodNr,
                                @ExecutionArgParam("ksKz") String ksKz) throws Exception {
        Selectors.wireComponents(view, this, false);
        prodNrLogList =  prodNrLogManager.getProdNrLogProdNrAndKsKz(prodNr, ksKz);
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {
        Set<Listitem> li =  listbox.getSelectedItems();
        if(!li.isEmpty())
        {
            for (Listitem item : li){
                ProdNrLog logToDel = item.getValue();
                prodNrLogManager.delete(logToDel);
            }
        }
        Messagebox.show("Hotovo! \n Spustite reinit a reload na DAB ", "Hotovo.", Messagebox.OK, null);
        view.detach();
    }

    public List<ProdNrLog> getProdNrLogList() {
        return prodNrLogList;
    }

    public void setProdNrLogList(List<ProdNrLog> prodNrLogList) {
        this.prodNrLogList = prodNrLogList;
    }
}
