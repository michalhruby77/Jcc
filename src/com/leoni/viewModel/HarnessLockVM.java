package com.leoni.viewModel;

import com.leoni.data.manager.BoardTypeManager;
import com.leoni.data.manager.ExcelDocumentManager;
import com.leoni.data.manager.Lpab62Manager;
import com.leoni.data.manager.Lpab64Manager;
import com.leoni.data.manager.dab.PrnrManager;
import com.leoni.data.models.BoardType;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.dab.Prnr;
import com.leoni.data.models.nonDBmodels.PrnrLpab64Obj;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hrmi1005 on 8. 1. 2016.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HarnessLockVM {

    @WireVariable
    BoardTypeManager boardTypeManager;

    @WireVariable
    PrnrManager prnrManager;

    @WireVariable
    Lpab64Manager lpab64Manager;

    @WireVariable
    Lpab62Manager lpab62Manager;

    @WireVariable
    ExcelDocumentManager excelDocumentManager;


    @Wire
    Rows rows;

    String modul;
    Set<String> containerNameSet;
    List<PrnrLpab64Obj> prnrLpab64ObjList;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view)
    {
        containerNameSet = new HashSet<>();
        Selectors.wireComponents(view, this, false);
        for (BoardType bt : boardTypeManager.getAll()){
            Checkbox cb = new Checkbox();
            cb.addEventListener("onCheck",new EventListener() {
                public void onEvent(Event evt) {
                    if (cb.isChecked()) {containerNameSet.add(bt.getName().trim());}
                    else{containerNameSet.remove(bt.getName().trim());}
                }});
            Label label = new Label(bt.getName().trim());
            Row row = new Row();
            row.appendChild(label);
            row.appendChild(cb);
            rows.appendChild(row);
        }
    }

    @Command
    public void submit() {
        if (modul != null && !modul.trim().equals("")){
            int count = prnrManager.lockHarness(lpab62Manager.getPrnrWoLpab64Obj(modul.trim()),containerNameSet);
            Messagebox.show("Bolo zablokovanych " + count + " kabloviek \n Spustite reinit a reload na DaB!", "Hotovo.",
                    Messagebox.OK, null);
        }
        else{
            Messagebox.show("Vyplnte modul!", "Chyba.", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }
}
