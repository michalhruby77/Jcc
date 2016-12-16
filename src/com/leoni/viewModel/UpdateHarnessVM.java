package com.leoni.viewModel;

import com.leoni.data.manager.Lpab62Manager;
import com.leoni.data.manager.ProdNrLogManager;
import com.leoni.data.models.Lpab62;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.11.2014
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UpdateHarnessVM {

    @WireVariable
    private Lpab62Manager lpab62Manager;

    @WireVariable
    private ProdNrLogManager prodNrLogManager;

    private Lpab62 harness;
    private String statusValue;
    private String statusName;
    private String note;
    private String user;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("prodNr") String prodNr,
                               @ExecutionArgParam("kabelsatz") String kabelsatz,
                               @ExecutionArgParam("status") String statusName) throws Exception {
        harness = lpab62Manager.findByProdnrAndKabelsatz(prodNr.trim(),kabelsatz.trim()).get(0);
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.statusName = statusName;
        Method method;
        method = harness.getClass().getMethod("get"+statusName);
        if(method.invoke(harness)==null){
            statusValue = "";
        }
        else
        statusValue = String.valueOf(method.invoke(harness));
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {
        if(statusValue.matches("[0-9]+")&&statusValue.length()==12){
        Method method;
            try {
                method = harness.getClass().getMethod("set"+statusName, String.class);
                method.invoke(harness,statusValue);
                if(statusName.equals("StaSicherung")){harness.setStaPruefRelais(statusValue);}
                lpab62Manager.saveOrUpdate(harness);
                String status16characters = statusName.trim().substring(0, Math.min(statusName.trim().length(), 16));
                prodNrLogManager.saveProdNrLog(harness.getProdNr(),harness.getKabelsatzKz(),"jcc",user,status16characters +" SET",statusName.trim() +" SET POZNAMKA: "+note);
                Messagebox.show("Status nastaveny!", "OK", Messagebox.OK, Messagebox.INFORMATION);
                view.detach();
                BindUtils.postGlobalCommand(null, null, "refreshLpab62", null);
            } catch (NoSuchMethodException e) {
                System.out.println("metoda pre dany objekt neexistuje! - "+"set"+statusName);
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            }
        else Messagebox.show("Zly format statusu!", "Chyba", Messagebox.OK, Messagebox.ERROR);
    }


    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
