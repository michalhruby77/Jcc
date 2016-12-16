package com.leoni.data.manager;

import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.leoni.data.models.Lpab62;
import com.leoni.data.models.Lpab64;
import org.springframework.stereotype.Service;

import javax.print.*;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterName;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 16.9.2014
 * Time: 8:28
 * To change this template use File | Settings | File Templates.
 */
@Service("printingManager")
public class PrintingManagerImpl implements PrintingManager {
    public void printEtiketC(Lpab62 harness) {
        String zpl = "^XA^MC^EF^EF^MTT^MMC^MNY~SD20^LH50,40^LT-30~TA00^PRA^PON^LRN^PMN^JZN^PQ1,1,1,Y^FS" +
                "^FO00,05^BY2,2.0^BCR,100,N^FD" +
                "25s" + harness.getProdNr().trim() +
                "^FS" +
                "^FO110,05^ADR,25,10^FDProd.-Nr.:^FS" +
                "^FO110,120^ADR,27,15^FD" +
                harness.getProdNr().trim() +
                "^FS" +
                "^FO545,05^ADR,25,10^FDProd.-Nr.:^FS" +
                "^FO545,120^ADR,27,15^FD" +
                harness.getProdNr().trim() +
                "^FS" +
                "^FO500,05^ADR,27,15^FD" +
                harness.getAusfuehrung().trim() +
                "^FS" +
                "^FO500,100^ADR,27,15^FD" +
                harness.getProdgruppe().trim() +
                "^FS" +
                "^FO500,200^ADR,27,15^FD" +
                harness.getKabelsatzKz().trim() +
                "^FS" +
                "^FO480,05^ADR,24,10^FDFolge-Nr.:^FS" +
                "^FO480,150^ADR,24,10^FD" +
                harness.getProdReihenfNr()+
                "^FS" +
                "^FO435,05^ADR,24,10^FDPr.Dat.:^FS" +
                "^FO435,100^ADR,27,15^FD" +
                harness.getLieferDatum() +
                "^FS" +
                "^XZ";
        try {
            PrintService ps = null;
            String sPrinterName = null;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

            for (int i = 0; i < services.length; i++) {

                PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
                sPrinterName = ((PrinterName) attr).getValue();

                if (sPrinterName.toLowerCase().indexOf("lp_c_eti") >= 0) {
                    ps = services[i];
                    break;
                }
            }
            if (ps == null) {
                System.out.println("Printer is not found.");
                //return;
            }
            DocPrintJob job = ps.createPrintJob();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            byte[] by = zpl.getBytes();
            Doc doc = new SimpleDoc(by, flavor, null);
            job.print(doc, null);

        } catch (PrintException e1) {
            e1.printStackTrace();
        }
    }

    public void printEtiketF(Lpab62 harness) {
        String zpl = "^XA^MC^EF^EF^MTT^MMT^MNY~SD20^LH0,0^LT00~TA00^PRA^PON^LRN^PMN^JZN^PQ1,1,1,Y^FS" +
                "^FO86,24^BY2,2.0^BCN,62,N^FD" +
                "20s" + harness.getProdNr().trim() +
                "^FS" +
                "^FO44,105^ADN,25,10^FDProd.-Nr.:^FS" +
                "^FO170,94^ADN,27,15^FD" +
                harness.getProdNr().trim() +
                "^FS" +
                "^FO170,130^ADN,27,15^FD" +
                harness.getAusfuehrung().trim() +
                "^FS" +
                "^FO220,130^ADN,27,15^FD" +
                harness.getProdgruppe().trim() +
                "^FS" +
                "^FO295,130^ADN,27,15^FD" +
                harness.getKabelsatzKz().trim() +
                "^FS" +
                "^FO44,165^ADN,24,10^FDProd.-Folge-Nr.:^FS" +
                "^FO306,165^ADN,24,10^FD" +
                harness.getProdReihenfNr()+
                "^FS" +
                "^FO44,190^ADN,24,10^FDProd.-Datum:^FS" +
                "^FO236,190^ADN,24,10^FD" +
                harness.getLieferDatum() +
                "^FS" +
                "^XZ";
        try {
            PrintService ps = null;
            String sPrinterName = null;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

            for (int i = 0; i < services.length; i++) {

                PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
                sPrinterName = ((PrinterName) attr).getValue();

                if (sPrinterName.toLowerCase().indexOf("printer6") >= 0) {
                    ps = services[i];
                    break;
                }
            }
            if (ps == null) {
                System.out.println("Printer is not found.");
                //return;
            }
            DocPrintJob job = ps.createPrintJob();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            byte[] by = zpl.getBytes();
            Doc doc = new SimpleDoc(by, flavor, null);
            job.print(doc, null);

        } catch (PrintException e1) {
            e1.printStackTrace();
        }
    }

    public void printMontageList(File file) {


        try {
            PrintService ps = null;
            String sPrinterName = null;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

            for (int i = 0; i < services.length; i++) {

                PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
                sPrinterName = ((PrinterName) attr).getValue();

                if (sPrinterName.toLowerCase().indexOf("printer_991_ml") >= 0) {
                    ps = services[i];
                    break;
                }
            }
            if (ps == null) {
                System.out.println("Printer is not found.");
                //return;
            }
            DocPrintJob job = ps.createPrintJob();
            FileInputStream fis = new FileInputStream(file);
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc doc = new SimpleDoc(fis, flavor, null);
            job.print(doc, null);

        } catch (PrintException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void printVariantScan(File file, String printerName) {
        try {
            PrintService ps = null;
            String sPrinterName = null;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

            for (int i = 0; i < services.length; i++) {

                PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
                sPrinterName = ((PrinterName) attr).getValue();

                if (sPrinterName.toLowerCase().indexOf(printerName.trim()) >= 0) {
                    ps = services[i];
                    break;
                }
            }
            if (ps == null) {
                System.out.println("Printer is not found.");
                //return;
            }
            DocPrintJob job = ps.createPrintJob();
            FileInputStream fis = new FileInputStream(file);
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc doc = new SimpleDoc(fis, flavor, null);
            job.print(doc, null);

        } catch (PrintException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }



    @Override
    public List<String> getPrinterNames() {
        List<String> printerList;
        printerList = new ArrayList<>();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

        for (int i = 0; i < services.length; i++) {

            PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
            String sPrinterName = ((PrinterName) attr).getValue();
            printerList.add(sPrinterName.trim());
        }
    return printerList;
    }
}
