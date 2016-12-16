package com.leoni.viewModel;

import com.leoni.data.manager.Lpab64Manager;
import com.leoni.data.manager.PdfManager;
import com.leoni.data.manager.PrintingManager;
import com.leoni.data.models.Lpab62;
import com.leoni.data.models.Lpab64;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 18.11.2015
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PrintHarnessEtikVM {
    @WireVariable
    private PrintingManager printingManager;

    @WireVariable
    private Lpab64Manager lpab64Manager;

    @WireVariable
    private PdfManager pdfManager;



    private Lpab62 myHarness;
    private Integer nrOfPieces;
    private List<String> printerList;
    private String selectedPrinter;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("myLpab62") Lpab62 myObject) throws Exception {
        myHarness = myObject;
        nrOfPieces = 1;
        printerList = printingManager.getPrinterNames();
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {
    if (myHarness.getKabelsatzKz().trim().equals("F"))
        {printingManager.printEtiketF(myHarness, selectedPrinter);}
        if (myHarness.getKabelsatzKz().trim().equals("C"))
        {printingManager.printEtiketC(myHarness, selectedPrinter);}
        view.detach();
    }

    public String getSelectedPrinter() {
        return selectedPrinter;
    }

    public void setSelectedPrinter(String selectedPrinter) {
        this.selectedPrinter = selectedPrinter;
    }

    public List<String> getPrinterList() {
        return printerList;
    }

    public void setPrinterList(List<String> printerList) {
        this.printerList = printerList;
    }

    public Integer getNrOfPieces() {
        return nrOfPieces;
    }

    public void setNrOfPieces(Integer nrOfPieces) {
        this.nrOfPieces = nrOfPieces;
    }


}