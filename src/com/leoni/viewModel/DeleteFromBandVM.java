package com.leoni.viewModel;

import com.leoni.data.manager.BandManager;
import com.leoni.data.manager.Lpab62Manager;
import com.leoni.data.models.Harness;
import com.leoni.data.models.Lpab62;
import org.apache.commons.io.IOUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 26.10.2015
 * Time: 9:41
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DeleteFromBandVM {

    @WireVariable
    private BandManager bandManager;

    @WireVariable
    private Lpab62Manager lpab62Manager;


    private Harness thisHarness;
    private int mode;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("harness") Harness harness) throws Exception {
        thisHarness = harness;
        mode = 0;
    }

    @Command
    public void deleteFromBand(){
        mode = 1;

    }

    @Command
    public void deleteFromBandAndEinlauf(){
        mode = 2;

    }

    @Command
    public void deleteFromBandSetAuslauf(){
        mode = 3;

    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view){
        if (mode == 1){
            thisHarness.setBusytime(null);
            thisHarness.setProd_nr(null);
            thisHarness.setKabelsatz_kz(null);
            thisHarness.setModulen(null);
            thisHarness.setSide_a_step(0);
            thisHarness.setSide_b_step(0);
            bandManager.saveOrUpdate(thisHarness);
            BindUtils.postGlobalCommand(null, null, "refreshBand", null);
            view.detach();
            Messagebox.show("Hotovo! \n Skontrolujte pritomnost ks na monitore.", "Hotovo.", Messagebox.OK, null);
        }
        else if (mode == 2){
            List<Lpab62> lpab62List = lpab62Manager.findByProdnrAndKabelsatz(thisHarness.getProd_nr(), thisHarness.getKabelsatz_kz());
            if (lpab62List.size() == 1){
                Lpab62 lpab62 = lpab62List.get(0);
                lpab62.setStaBandEinlauf(null);
                System.out.println(lpab62.getProdNr());
                thisHarness.setBusytime(null);
                thisHarness.setProd_nr(null);
                thisHarness.setKabelsatz_kz(null);
                thisHarness.setModulen(null);
                thisHarness.setSide_a_step(0);
                thisHarness.setSide_b_step(0);
                lpab62Manager.saveOrUpdate(lpab62);
                bandManager.saveOrUpdate(thisHarness);
                BindUtils.postGlobalCommand(null, null, "refreshBand", null);
                view.detach();
                Messagebox.show("Hotovo! \n Skontrolujte pritomnost ks na monitore.", "Hotovo.", Messagebox.OK, null);
            }
            else{Messagebox.show("Kablovka sa nenasla v lpab62!", "Error", Messagebox.OK, Messagebox.ERROR);}
        }
        else if (mode == 3){
            String result = callAuslaufServlet(thisHarness);
            if (result.contains("<h1>OK</h1>")){
                thisHarness.setBusytime(null);
                thisHarness.setProd_nr(null);
                thisHarness.setKabelsatz_kz(null);
                thisHarness.setModulen(null);
                thisHarness.setSide_a_step(0);
                thisHarness.setSide_b_step(0);
                bandManager.saveOrUpdate(thisHarness);
                BindUtils.postGlobalCommand(null, null, "refreshBand", null);
                view.detach();
                Messagebox.show("Hotovo! \n Skontrolujte pritomnost ks na monitore.", "Hotovo.", Messagebox.OK, null);
            }
            else{Messagebox.show("Nepodarilo sa zhodenie!", "Error", Messagebox.OK, Messagebox.ERROR);}

        }
        else {
            Messagebox.show("Vyberte moznost!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    public String callAuslaufServlet(Harness harness){
        String harnessSuffix = "";
        String result = "";
        if (harness.getKabelsatz_kz() != null && harness.getKabelsatz_kz().trim().equals("F")){
            harnessSuffix = "20S"+harness.getProd_nr().trim();
        }
        if (harness.getKabelsatz_kz() != null && harness.getKabelsatz_kz().trim().equals("C")){
            harnessSuffix = "25S"+harness.getProd_nr().trim();
        }

        try {
            URL url = new URL("http://10.106.160.6:8080/DruckAmBand/servlet/AuslaufServlet?mode=auslauf&typeBarCode="+
                    harnessSuffix
            );
            HttpURLConnection urlConn = null;

            urlConn = (HttpURLConnection) url.openConnection();
            InputStream stream = urlConn.getInputStream();
            result = IOUtils.toString(stream, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
      return result;
    }
}
