package com.leoni.viewModel;

import com.leoni.data.manager.PdfManager;
import com.leoni.data.manager.PrintingManager;
import com.leoni.data.models.Variant;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.2.2015
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PrintVariantVM {

    @WireVariable
    private PrintingManager printingManager;

    @WireVariable
    private PdfManager pdfManager;

    private Variant myVariant;
    private Integer nrOfPieces;
    private String user;
    private List<String> printerList;
    private String selectedPrinter;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("myVariant") Variant myObject) throws Exception {
                myVariant = myObject;
                nrOfPieces = 1;
                printerList = printingManager.getPrinterNames();
                user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {

        File file = pdfManager.createVariantScan(myVariant.getName(), myVariant.getScanString(), user, nrOfPieces);
              printingManager.printVariantScan(file, selectedPrinter.toLowerCase());
              view.detach();
    }

    public Integer getNrOfPieces() {
        return nrOfPieces;
    }

    public void setNrOfPieces(Integer nrOfPieces) {
        this.nrOfPieces = nrOfPieces;
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
}
