package com.leoni.data.manager;

import com.leoni.data.models.Lpab62;
import com.leoni.data.models.Lpab64;

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
    public void printEtiketF(Lpab62 harness);
    public void printEtiketC(Lpab62 harness);
    public void printMontageList(File file);
    public void printVariantScan(File file, String printerName);
    public List<String> getPrinterNames();
}
