package com.leoni.jcc.modulsEditor;

import com.leoni.data.manager.ModulsManager;
import com.leoni.data.models.Moduls;
import com.leoni.jcc.configuration.Constants;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 11.12.2012
 * Time: 8:43
 * To change this template use File | Settings | File Templates.
 */

@VariableResolver (org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ModulsEditWindow extends SelectorComposer<Window>
    {
    @WireVariable
    private ModulsManager modulsManager;

    private Moduls editedModuls;
    @Wire
    Window modulsEditorWindow;


    @Init
    public void doInit(@ExecutionArgParam ("moduls") Moduls edited, @ContextParam (ContextType.VIEW) Component view) throws Exception
        {
        Selectors.wireComponents(view, this, false);
        editedModuls = edited;
        }

    @Command
    public void submit()
        {
        if (editedModuls.getCreateTime() == null)
            {
            editedModuls.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            }
        if (editedModuls.getCreatePerson() == null)
            {
            editedModuls.setCreatePerson("jit");
            }
        modulsManager.saveOrUpdate(editedModuls);
        modulsEditorWindow.detach();
        }

    @Command
    public void cancel()
        {
        modulsEditorWindow.detach();
        }

    public Moduls getEditedModuls()
        {
        return editedModuls;
        }

    public void setEditedModuls(Moduls editedModuls)
        {
        this.editedModuls = editedModuls;
        }

    public String getSachNrBestRegexp()
        {
        return Constants.SACH_NR_BEST_REGEXP;
        }

    public String getSachNrLieferantRegexp()
        {
        return Constants.SACH_NR_LIEFERANT_REGEXP;
        }

    }
