package com.leoni.jcc.component;

import com.leoni.data.models.Moduls;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 13.12.2012
 * Time: 15:19
 * To change this template use File | Settings | File Templates.
 */

@VariableResolver (org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ModulAutoComplet extends SelectorComposer<Combobox>
    {
    @WireVariable
    ModulAutoCompleteModel modulAutoCompleteModel;
    @Wire ("#combo")
    Combobox combobox;

    @Override
    public void doAfterCompose(Combobox comp) throws Exception
        {
        super.doAfterCompose(comp);
        comp.setModel(modulAutoCompleteModel);
        modulAutoCompleteModel.setColumn(Moduls.COLUMN_SACH_NR_BEST);
        }

    @Listen ("onSelect=#combo")
    public void doChanged()
        {
        System.out.println(((Moduls) combobox.getSelectedItem().getValue()).getSachNrBest());
        }

    @Listen ("onChanging=#combo")
    public void doChanged1()
        {
        System.out.println(((Moduls) combobox.getSelectedItem().getValue()).getSachNrLieferant());
        }
    }
