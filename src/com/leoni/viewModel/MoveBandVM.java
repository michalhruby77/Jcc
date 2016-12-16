package com.leoni.viewModel;

import com.leoni.data.manager.BandManager;
import com.leoni.data.models.Harness;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.10.2015
 * Time: 9:14
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MoveBandVM {

    @WireVariable
    private BandManager bandManager;

    private Harness thisHarness;
    private Harness selectedHarness;
    private List<Harness> harnessList;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("harness") Harness harness) throws Exception {
          thisHarness = harness;
          harnessList = bandManager.findEmptyByBrettType(thisHarness.getBretttype().trim());
          Collections.sort(harnessList, Comparator.comparing(s -> s.getBrettid()));
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view){
                if (selectedHarness.getProd_nr() == null || selectedHarness.getProd_nr().trim().equals("")){
                    selectedHarness.setProd_nr(thisHarness.getProd_nr());
                    selectedHarness.setKabelsatz_kz(thisHarness.getKabelsatz_kz());
                    selectedHarness.setSide_a_step(thisHarness.getSide_a_step());
                    selectedHarness.setSide_b_step(thisHarness.getSide_b_step());
                    selectedHarness.setBusytime(thisHarness.getBusytime());
                    thisHarness.setProd_nr(null);
                    thisHarness.setKabelsatz_kz(null);
                    thisHarness.setSide_a_step(0);
                    thisHarness.setSide_b_step(0);
                    thisHarness.setBusytime(null);
                    bandManager.saveOrUpdate(selectedHarness);
                    bandManager.saveOrUpdate(thisHarness);
                    BindUtils.postGlobalCommand(null, null, "refreshBand", null);
                    view.detach();
                    Messagebox.show("Presunute!", "Presunute.", Messagebox.OK, null);
                }
                else{
                    Messagebox.show("Doska je obsadena!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    public Harness getSelectedHarness() {
        return selectedHarness;
    }

    public void setSelectedHarness(Harness selectedHarness) {
        this.selectedHarness = selectedHarness;
    }

    public List<Harness> getHarnessList() {
        return harnessList;
    }

    public void setHarnessList(List<Harness> harnessList) {
        harnessList = harnessList;
    }
}
