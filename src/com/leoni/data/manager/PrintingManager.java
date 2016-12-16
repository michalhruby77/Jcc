package com.leoni.data.manager;

import com.leoni.data.models.Lpab62;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.Users;
import com.leoni.data.models.vm.VmVariante;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 16.9.2014
 * Time: 8:27
 * To change this template use File | Settings | File Templates.
 */
public interface PrintingManager {
    public void printEtiketF(Lpab62 harness, String printerName);
    public void printEtiketC(Lpab62 harness, String printerName);
    public void printMontageList(File file, String printerName);
    public void printVariantScan(File file, String printerName);
    public void printVmVariantLabel(VmVariante vmVariante, Users user, String printerName);
    public void printModulLabel(Moduls moduls, Users user, Integer nrOfCopies);
    public List<String> getPrinterNames();
}
