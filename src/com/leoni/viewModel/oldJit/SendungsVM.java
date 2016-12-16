package com.leoni.viewModel.oldJit;

import com.leoni.data.manager.oldJIT.LieferTableManager;
import com.leoni.data.models.oldJIT.LieferTable;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.10.2014
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SendungsVM {

    @WireVariable
    private LieferTableManager lieferTableManager;

    Integer lieferscheinNr;
    LieferTable sendung;

    @Command
    @NotifyChange({"sendung","lieferscheinNr"})
    public void submit(){
        if (lieferscheinNr!=null){
            sendung = lieferTableManager.searchSendung(String.valueOf(lieferscheinNr));
            lieferscheinNr = null;
        }
        else{
            Messagebox.show("Nenasiel sa ziadny zaznam!", "Chyba", Messagebox.OK, Messagebox.ERROR);}
    }

    public Integer getLieferscheinNr() {
        return lieferscheinNr;
    }

    public void setLieferscheinNr(Integer lieferscheinNr) {
        this.lieferscheinNr = lieferscheinNr;
    }

    public LieferTable getSendung() {
        return sendung;
    }

    public void setSendung(LieferTable sendung) {
        this.sendung = sendung;
    }
}
