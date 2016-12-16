package com.leoni.viewModel;

import com.leoni.jcc.listModel.GenericListModelImpl;
import com.leoni.data.models.Moduls;
import org.zkoss.bind.GlobalCommandEvent;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@VariableResolver (org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InfoComposer extends SelectorComposer<Window>
    {
    private final String filterTextboxesSelector = "auxheader textbox";
    private final String filterCheckboxesSelector = "auxheader checkbox";
    private final String CRUDButtonsClick = "toolbar button";

    private Moduls selectedModuls;

    @WireVariable// (rewireOnActivate = true)
    public GenericListModelImpl genericListModel;
    @Wire
    public Listbox grid;
    @Wire
    public Paging paging;

    @Wire (filterTextboxesSelector)
    private List<Textbox> filterTextboxes;
    @Wire (filterCheckboxesSelector)
    private List<Checkbox> filterCheckboxes;

    @Override
    public void doAfterCompose(Window comp) throws Exception
        {
        super.doAfterCompose(comp);
        genericListModel.setPaginal(paging);
        grid.setModel(genericListModel);
        }

    @Listen ("onChange = " + filterTextboxesSelector)
    public void filterTextboxChanged(InputEvent inputEvent)
        {
        Textbox textbox = (Textbox) inputEvent.getTarget();
        if (textbox.hasAttribute("column") && textbox.hasAttribute("criterion"))
            {
            genericListModel.setFilterProperty(textbox.getAttribute("column").toString(), textbox.getAttribute("criterion").toString(), inputEvent.getValue());
            }
        }

    @Listen ("onCheck = " + filterCheckboxesSelector)
    public void filterCheckboxChanged(CheckEvent checkEvent)
        {
        Checkbox checkbox = (Checkbox) checkEvent.getTarget();
        if (checkbox.hasAttribute("column") && checkbox.hasAttribute("criterion"))
            {
            genericListModel.setFilterProperty(checkbox.getAttribute("column").toString(), checkbox.getAttribute("criterion").toString(), Boolean.toString(checkEvent.isChecked()));
            }
        }

    void editModul(Moduls m)
        {
        Map<String,Object> am = new HashMap<String, Object>();
        am.put("moduls", m);
        Window w = (Window) Executions.createComponents("/modulsEditor/modulsEditWindow.zul", null, am);
        w.doModal();
        }

    @Listen("onClick = #clearFilter")
        public void clearFilter()
        {
        genericListModel.clearFilter();
        for(Textbox t : filterTextboxes)
            {
            t.setValue("");
            }
        for(Checkbox ch : filterCheckboxes)
            {
            ch.setChecked(false);
            }
        }

    @Listen ("onClick = #createModulsButton")
    public void createModuls(MouseEvent event)
        {
        editModul(new Moduls());
        }

    @Listen ("onClick = #editModulsButton")
    public void editModuls(MouseEvent event)
        {
        editModul(selectedModuls);
        }

    @Listen ("onClick = #deleteModulsButton")
    public void deleteModuls(MouseEvent event)
        {
        Messagebox.show("Question is pressed. Are you sure?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>()
        {
        public void onEvent(Event event) throws Exception
            {
            if ("onOK".equals(event.getName()))
                {
                alert("Vymazane");
                }
            else if("onCancel".equals(event.getName()))
                {
                alert("Bez zmeny");
                }
            }
        });
        }

    @Listen("onSelect = listbox")
    public void onSelectItem(SelectEvent select)
        {
        selectedModuls = (Moduls) select.getSelectedObjects().iterator().next();
        }


    public void updateShoppingCart(Event evt)
        {
            if(evt instanceof GlobalCommandEvent)
                {
                if("updateShoppingCart".equals(((GlobalCommandEvent)evt).getCommand())){
                    //update shopping cart's items
                }
            }
        }
    }
