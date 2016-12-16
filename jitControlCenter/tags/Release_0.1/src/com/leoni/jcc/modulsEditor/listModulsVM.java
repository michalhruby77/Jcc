package com.leoni.jcc.modulsEditor;

import com.leoni.data.manager.ModulsManager;
import com.leoni.data.models.Moduls;
import com.leoni.jcc.listModel.ModulsListModel;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@VariableResolver (org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class listModulsVM
    {
    private final String filterTextboxesSelector = "auxheader textbox";
    private final String filterCheckboxesSelector = "auxheader checkbox";

    private Moduls selectedModuls;

    @WireVariable("modulsListModel")
    public ModulsListModel genericListModel;

    @WireVariable
    public ModulsManager modulsManager;

    @Wire
    public Listbox grid;
    @Wire
    public Paging paging;

    @Wire (filterTextboxesSelector)
    private List<Textbox> filterTextboxes;
    @Wire (filterCheckboxesSelector)
    private List<Checkbox> filterCheckboxes;

    @Init
    public void doInit(@ContextParam (ContextType.VIEW) Component view) throws Exception
        {
        Selectors.wireComponents(view, this, false);
        paging.setPageSize(15);
        genericListModel.setPaginal(paging);
        grid.setModel(genericListModel);
        }

    @Command ("filterChanged")
    public void doFilterChanged(@BindingParam ("column") String column, @BindingParam ("criterion") String criterion, @BindingParam ("value1") String value)
        {
        genericListModel.setFilterProperty(column, criterion, value);
        }

    @Command ("clearFilter")
    public void doClearFilter()
        {
        genericListModel.clearFilter();
        for (Textbox t : filterTextboxes)
            {
            t.setValue("");
            }
        for (Checkbox ch : filterCheckboxes)
            {
            ch.setChecked(false);
            }
        }

    void editModul(Moduls m)
        {
        Map<String, Object> am = new HashMap<String, Object>();
        am.put("moduls", m);
        Window w = (Window) Executions.createComponents("/modulsEditor/modulsEditWindow.zul", null, am);
        w.doModal();
        }

    @Command
    public void createModuls()
        {
        Moduls m = new Moduls();
        m.setBlock(false);
        m.setGrund(false);
        editModul(m);
        }

    @Command
    public void copyModuls()
        {
        Moduls m = new Moduls();
        m.setSachNrBest(selectedModuls.getSachNrBest());
        m.setSachNrLieferant(selectedModuls.getSachNrLieferant());
        m.setProdGruppe(selectedModuls.getProdGruppe());
        m.setKabelsatzKz(selectedModuls.getKabelsatzKz());
        m.setAusfuehrung(selectedModuls.getAusfuehrung());
        m.setGrund(selectedModuls.getGrund());
        m.setBlock(selectedModuls.getBlock());
        editModul(m);
        }

    @Command
    public void editModuls()
        {
        editModul(selectedModuls);
        }

    @Command
    public void deleteModuls()
        {
        Messagebox.show("Naozaj chces zmazat tento modul: " + selectedModuls.getSachNrBest() + ", " + selectedModuls.getSachNrLieferant() + "?", "Zmazat modul.", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>()
        {
        public void onEvent(Event event) throws Exception
            {
            if ("onOK".equals(event.getName()))
                {
                modulsManager.delete(selectedModuls);
                Messagebox.show("Vymazane.", "Vymazane.", Messagebox.OK, Messagebox.INFORMATION);
                doRefrashModules();
                }
            }
        });
        }

    @Command ("selectionChanged")
    public void doSelectionChanged(@BindingParam ("modul") int selectedIndex)
        {
        if (selectedIndex >= 0)
            {
            selectedModuls = genericListModel.getElementAt(selectedIndex);
            }
        }

    @GlobalCommand ("refreshModuls")
    public void doRefrashModules()
        {
        genericListModel.reload();
        }

    }
