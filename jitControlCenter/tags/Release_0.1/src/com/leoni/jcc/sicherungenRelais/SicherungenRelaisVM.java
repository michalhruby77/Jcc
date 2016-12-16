package com.leoni.jcc.sicherungenRelais;

import com.leoni.data.manager.SicherungenRelais9X1WrmManager;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.SicherungenRelais9X1Wrm;
import com.leoni.jcc.listModel.SicherungenRelaisModel;
import org.apache.commons.beanutils.BeanUtils;
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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 18.1.2013
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */

@VariableResolver (org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SicherungenRelaisVM
    {
    private final String filterTextboxesSelector = "auxheader textbox";
    private final String filterListboxesSelector = "auxheader listbox";

    private static final String MSG_SELECT_SICHRUNG = "Najskôr vyber poistku!";

    @WireVariable
    private SicherungenRelais9X1WrmManager sicherungenRelais9X1WrmManager;
    @WireVariable
    private SicherungenRelaisModel sicherungenRelaisListModel;

    @Wire
    private Paging pagingSichRrel;

    private ListModelList platzList = new ListModelList();

    @Wire (filterTextboxesSelector)
    private List<Textbox> filterTextboxes;
    @Wire (filterListboxesSelector)
    private List<Listbox> filterListboxes;

    private SicherungenRelais9X1Wrm selectedSicherungRelais;

    @Init
    public void doInit(@ContextParam (ContextType.VIEW) Component view)
        {
        Selectors.wireComponents(view, this, false);
        sicherungenRelaisListModel.setPaginal(pagingSichRrel);
        }

    @Command ("filterChanged")
    public void doFilterChanged(@BindingParam ("column") String column, @BindingParam ("criterion") String criterion, @BindingParam ("value1") String value)
        {
        sicherungenRelaisListModel.setFilterProperty(column, criterion, value);
        }

    @Command ("boxSelectionChanged")
    public void doBoxSelectionChanged(@BindingParam ("selectedValue") String selectedValue)
        {
        sicherungenRelaisListModel.setFilterProperty("platz", "eq", "", false);
        doFilterChanged("box", "eq", selectedValue);
        platzList.clear();
        if (!"".equals(selectedValue))
            {
            platzList.addAll(sicherungenRelais9X1WrmManager.getPlatzList(selectedValue));
            }
        }

    @Command ("clearFilter")
    public void doClearFilter()
        {
        sicherungenRelaisListModel.clearFilter();
        for (Textbox t : filterTextboxes)
            {
            t.setValue("");
            }
        for (Listbox l : filterListboxes)
            {
            l.setSelectedIndex(0);
            }
        }


    void editSicherungRelais(SicherungenRelais9X1Wrm srw)
        {
        Map<String, Object> am = new HashMap<String, Object>();
        am.put("sicherungenRelais9X1Wrm", srw);
        Window w = (Window) Executions.createComponents("/sicherungenRelais/sicherungenRelaisEditWindow.zul", null, am);
        w.doModal();
        }

    @Command
    public void createSicherungRelais()
        {
            SicherungenRelais9X1Wrm srw = new SicherungenRelais9X1Wrm();
            srw.setModuls(new Moduls());
            srw.setBeschreibung("");
            editSicherungRelais(srw);
        }

    @Command
    public void copySicherungRelais() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
        {
        if (checkSelectedSicherungRelais())
            {
            SicherungenRelais9X1Wrm srw = (SicherungenRelais9X1Wrm) BeanUtils.cloneBean(selectedSicherungRelais);
            srw.setId(null);
            editSicherungRelais(srw);
            }
        }

    @Command
    public void editSicherungRelais()
        {
        if (checkSelectedSicherungRelais())
            {
            editSicherungRelais(selectedSicherungRelais);
            }
        }

    @Command
    public void deleteSicherungRelais()
        {
        if (checkSelectedSicherungRelais())
            {
            Messagebox.show("Naozaj chces zmazat tento modul: " + selectedSicherungRelais.getModuls().getSachNrBest() + ", "
                    + selectedSicherungRelais.getModuls().getSachNrLieferant() + ", "
                    + selectedSicherungRelais.getBox() + ", "
                    + selectedSicherungRelais.getPlatz() + ", "
                    + selectedSicherungRelais.getWert() + "?", "Zmazat modul.", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>()
            {
            public void onEvent(Event event) throws Exception
                {
                if ("onOK".equals(event.getName()))
                    {
                    sicherungenRelais9X1WrmManager.delete(selectedSicherungRelais);
                    Messagebox.show("Vymazane.", "Vymazane.", Messagebox.OK, Messagebox.INFORMATION);
                    doRefrashRelais();
                    }
                }
            });
            }
        }

    @Command ("selectionChanged")
    public void doSelectionChanged(@BindingParam ("sicherungRelais") int selectedIndex)
        {
        if (selectedIndex >= 0)
            {
            selectedSicherungRelais = sicherungenRelaisListModel.getElementAt(selectedIndex);
            }
        }

    @GlobalCommand ("refreshSicherungenRelais")
    public void doRefrashRelais()
        {
        selectedSicherungRelais = null;
        sicherungenRelaisListModel.reload();
        }

    protected boolean checkSelectedSicherungRelais()
        {
        if (selectedSicherungRelais == null)
            {
            Messagebox.show(MSG_SELECT_SICHRUNG, "No selection.", Messagebox.OK, Messagebox.INFORMATION);
            return false;
            }
        return true;
        }

    public SicherungenRelaisModel getSicherungenRelaisListModel()
        {
        return sicherungenRelaisListModel;
        }

    public SicherungenRelais9X1WrmManager getSicherungenRelais9X1WrmManager()
        {
        return sicherungenRelais9X1WrmManager;
        }

    public ListModelList getPlatzList()
        {
        return platzList;
        }
    }
