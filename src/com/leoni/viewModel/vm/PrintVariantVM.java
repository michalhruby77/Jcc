package com.leoni.viewModel.vm;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 2.10.2015
 * Time: 10:11
 * To change this template use File | Settings | File Templates.
 */

import com.leoni.data.manager.PrintingManager;
import com.leoni.data.manager.UsersManager;
import com.leoni.data.models.Users;
import com.leoni.data.models.vm.VmVariante;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

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
    private UsersManager usersManager;


    private VmVariante myVariant;
    private Integer nrOfPieces;
    private String user;
    private List<String> printerList;
    private String selectedPrinter;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("myVariant") VmVariante myObject) throws Exception {
        myVariant = myObject;
        nrOfPieces = 1;
        printerList = printingManager.getPrinterNames();
        user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {
        Users users = usersManager.getUser(user);
        printingManager.printVmVariantLabel(myVariant, users, selectedPrinter.toLowerCase());
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
