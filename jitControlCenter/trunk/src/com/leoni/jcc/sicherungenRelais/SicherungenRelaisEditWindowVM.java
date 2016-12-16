package com.leoni.jcc.sicherungenRelais;

import com.leoni.data.manager.ModulsManager;
import com.leoni.data.manager.SicherungenRelais9X1WrmManager;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.SicherungenRelais9X1Wrm;
import com.leoni.jcc.component.ModulAutoCompleteModel;
import com.leoni.jcc.configuration.Constants;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 23.1.2013
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */

@VariableResolver (org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SicherungenRelaisEditWindowVM
    {
    private SicherungenRelais9X1Wrm editedSicherungenRelais;
    private ListModelList<String> boxList = new ListModelList<String>();
    private ListModelList<String> platzList = new ListModelList<String>();

    @WireVariable ("modulAutoCompleteModel")
    private ModulAutoCompleteModel sachNrBestAutoCompleteModel;
    @WireVariable ("modulAutoCompleteModel")
    private ModulAutoCompleteModel sachNrLieferantAutoCompleteModel;

    @WireVariable
    private SicherungenRelais9X1WrmManager sicherungenRelais9X1WrmManager;
    @WireVariable
    private ModulsManager modulsManager;

    @Wire
    private Window sichrungenRelaisEditWindow;
    @Wire
    Combobox sicherungSachNrBest;
    @Wire
    Combobox sicherungSachNrLieferant;

    @Init
    public void doInit(@ExecutionArgParam ("sicherungenRelais9X1Wrm") SicherungenRelais9X1Wrm edited, @ContextParam (ContextType.VIEW) Component view) throws Exception
        {
        Selectors.wireComponents(view, this, false);
        sachNrBestAutoCompleteModel.setColumn(Moduls.COLUMN_SACH_NR_BEST);
        sachNrLieferantAutoCompleteModel.setColumn(Moduls.COLUMN_SACH_NR_LIEFERANT);
        editedSicherungenRelais = edited;
        boxList.addAll(sicherungenRelais9X1WrmManager.getBoxList());
        if (editedSicherungenRelais.getBox() != null && !"".equals(editedSicherungenRelais.getBox()))
            {
            platzList.addAll(sicherungenRelais9X1WrmManager.getPlatzList(editedSicherungenRelais.getBox()));
            }
        }

    @Command ("boxSelectionChanged")
    public void doBoxSelectionChanged(@BindingParam ("selectedValue") String selectedBox)
        {
        platzList.clear();
        if (selectedBox != null && !"".equals(selectedBox))
            {
            platzList.addAll(sicherungenRelais9X1WrmManager.getPlatzList(selectedBox));
            }
        }

    @Command("submit")
    public void doSubmit(@BindingParam ("sachNrBest") String sachNrBest, @BindingParam ("sachNrLieferant") String sachNrlieferant)
        {
        editedSicherungenRelais.setModuls(modulsManager.findBySachNrBestAndSachNrLieferant(sachNrBest, sachNrlieferant).get(0));
        sicherungenRelais9X1WrmManager.saveOrUpdate(editedSicherungenRelais);
        sichrungenRelaisEditWindow.detach();
        }

    @Command("cancel")
    public void doCancel()
        {
        editedSicherungenRelais = null;
        sichrungenRelaisEditWindow.detach();
        }

    @Command("sachNrBestSelected")
    public void doSachNrBestSelected(@BindingParam("sachNrBest") String sachNrBest)
        {
        List<Moduls> modulsList = modulsManager.findBySachNrBest(sachNrBest);
        if (modulsList.size() == 0)
            {
            sicherungSachNrLieferant.setValue("");
            }
        else if (modulsList.size() == 1)
            {
            sicherungSachNrLieferant.setValue(modulsList.get(0).getSachNrLieferant());
            }
        else if (modulsList.size() > 1)
            {
            String sachNrLieferant = modulsList.get(0).getSachNrLieferant();
            sicherungSachNrLieferant.setValue(sachNrLieferant.substring(0, sachNrLieferant.length() - 2));
            sicherungSachNrLieferant.open();
            }

        sachNrLieferantAutoCompleteModel.reload();
        }

    @Command("sachNrLieferantSelected")
        public void doSachNrLieferantSelected(@BindingParam("sachNrLieferant")String sachNrLieferant)
            {
            List<Moduls> modulsList = modulsManager.findBySachNrLieferant(sachNrLieferant);
            if (modulsList.size() > 0)
                {
                sicherungSachNrBest.setValue(modulsList.get(0).getSachNrBest());
                }
            sachNrBestAutoCompleteModel.reload();
            }

    public SicherungenRelais9X1Wrm getEditedSicherungenRelais()
        {
        return editedSicherungenRelais;
        }

    public void setEditedSicherungenRelais(SicherungenRelais9X1Wrm editedSicherungenRelais)
        {
        this.editedSicherungenRelais = editedSicherungenRelais;
        }

    public String getSachNrBestRegexp()
        {
        return Constants.SACH_NR_BEST_REGEXP;
        }

    public String getSachNrLieferantRegexp()
        {
        return Constants.SACH_NR_LIEFERANT_REGEXP;
        }

    public ListModelList getPlatzList()
        {
        return platzList;
        }

    public ListModelList<String> getBoxList()
        {
        return boxList;
        }

    public ModulAutoCompleteModel getSachNrBestAutoCompleteModel()
        {
        return sachNrBestAutoCompleteModel;
        }

    public void setSachNrBestAutoCompleteModel(ModulAutoCompleteModel sachNrBestAutoCompleteModel)
        {
        this.sachNrBestAutoCompleteModel = sachNrBestAutoCompleteModel;
        }

    public ModulAutoCompleteModel getSachNrLieferantAutoCompleteModel()
        {
        return sachNrLieferantAutoCompleteModel;
        }

    public void setSachNrLieferantAutoCompleteModel(ModulAutoCompleteModel sachNrLieferantAutoCompleteModel)
        {
        this.sachNrLieferantAutoCompleteModel = sachNrLieferantAutoCompleteModel;
        }


    }
