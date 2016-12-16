package com.leoni.viewModel;

import com.leoni.data.manager.Lpab62Manager;
import com.leoni.data.manager.Lpab64Manager;
import com.leoni.data.manager.PdfManager;
import com.leoni.data.manager.PrintingManager;
import com.leoni.data.models.Lpab62;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.Moduls;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.9.2014
 * Time: 9:42
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CustomHarnessVM {

    @WireVariable
    private Lpab62Manager lpab62Manager;

    @WireVariable
    private PdfManager pdfManager;

    @WireVariable
    private PrintingManager printingManager;

    @WireVariable
    private Lpab64Manager lpab64Manager;

    private List<Lpab62> harnessList;
    private String prodNrSearch = "";
    private String kabelsatzKzSearch = "";
    private String kundenNrSearch = "";


    @AfterCompose
    public void init()
    {
        harnessList=lpab62Manager.getCustomHarnesses();
    }

    @Command
    @NotifyChange({"harnessList"})
    public void search()
    {    if (prodNrSearch.equals("")&&kabelsatzKzSearch.equals("")&&kundenNrSearch.equals("")){
             harnessList = lpab62Manager.getAll();}
    else harnessList = lpab62Manager.searchCustomHarnesses(prodNrSearch,kabelsatzKzSearch,kundenNrSearch);
    }

    @Command
    @NotifyChange ({"harnessList","prodNrSearch","kabelsatzKzSearch","kundenNrSearch"})
    public void cancelSearch()
    {   harnessList = lpab62Manager.getCustomHarnesses();
        prodNrSearch = "";
        kabelsatzKzSearch = "";
        kundenNrSearch = "";
    }

    @Command
    public void editModuls(@BindingParam("harness") Lpab62 harness)
    {
        Map<String, Lpab62> arguments = new HashMap<String, Lpab62>();
        arguments.put("myHarness", harness);
        Window window = (Window) Executions.createComponents(
                "lpab64/editModuls.zul", null, arguments);
        window.doModal();


    }

    @Command
    public void printMl(@BindingParam("harness") Lpab62 harness)
    {
        List<Lpab64> modulsList = lpab64Manager.findByProdnrAndKabelsatz(harness.getProdNr().trim(),harness.getKabelsatzKz().trim());
        printingManager.printMontageList(pdfManager.createMontageListPdf(harness, modulsList));
    }

    @Command
    public void printEtik(@BindingParam("harness") Lpab62 harness)
    {
        if (harness.getKabelsatzKz().trim().equals("F"))
        {printingManager.printEtiketF(harness);}
        if (harness.getKabelsatzKz().trim().equals("C"))
        {printingManager.printEtiketC(harness);}
    }

    @Command
    public void createCustomHarness(){
        Window window = (Window) Executions.createComponents(
                "lpab62/insertNewHarness.zul",null ,null);
        window.doModal();
    }

    public List<Lpab62> getHarnessList() {
        return harnessList;
    }

    public void setHarnessList(List<Lpab62> harnessList) {
        this.harnessList = harnessList;
    }

    public String getKundenNrSearch() {
        return kundenNrSearch;
    }

    public void setKundenNrSearch(String kundenNrSearch) {
        this.kundenNrSearch = kundenNrSearch;
    }

    public String getKabelsatzKzSearch() {
        return kabelsatzKzSearch;
    }

    public void setKabelsatzKzSearch(String kabelsatzKzSearch) {
        this.kabelsatzKzSearch = kabelsatzKzSearch;
    }

    public String getProdNrSearch() {
        return prodNrSearch;
    }

    public void setProdNrSearch(String prodNrSearch) {
        this.prodNrSearch = prodNrSearch;
    }
}
