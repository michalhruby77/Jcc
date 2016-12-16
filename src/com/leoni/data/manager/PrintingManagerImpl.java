package com.leoni.data.manager;

import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.leoni.data.models.Lpab62;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.Users;
import com.leoni.data.models.vm.VmVariante;
import org.springframework.stereotype.Service;

import javax.print.*;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterName;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public void printEtiketC(Lpab62 harness, String printerName) {
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

                if (sPrinterName.toLowerCase().indexOf(printerName.toLowerCase().trim()) >= 0) {
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

    public void printEtiketF(Lpab62 harness, String printerName) {
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

                if (sPrinterName.toLowerCase().indexOf(printerName.toLowerCase().trim()) >= 0) {
                    ps = services[i];
                    break;
                }
            }
            if (ps == null) {
                System.out.println("Printer is not found.");
                //return;
            }
            //System.out.println("podporovane docflavors: "+ps.getSupportedDocFlavors());
            DocPrintJob job = ps.createPrintJob();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            byte[] by = zpl.getBytes();
            Doc doc = new SimpleDoc(by, flavor, null);
            job.print(doc, null);

        } catch (PrintException e1) {
            e1.printStackTrace();
        }
    }

    public void printMontageList(File file, String printerName) {
        //String printerName = "prlas097";
        try {
            PrintService ps = null;
            String sPrinterName = null;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

            for (int i = 0; i < services.length; i++) {

                PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
                sPrinterName = ((PrinterName) attr).getValue();

                if (sPrinterName.toLowerCase().indexOf(printerName.toLowerCase().trim()) >= 0) {
                    ps = services[i];
                    break;
                }
            }
            if (ps == null) {
                System.out.println("Printer is not found.");
                //return;
            }
            System.out.println("podporovane docflavors: ");
            for (DocFlavor df : ps.getSupportedDocFlavors()){
                System.out.println("------------------------");
                System.out.println(df);
                System.out.println(df.getMediaSubtype());
                System.out.println(df.getMimeType());
                System.out.println("------------------------");

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
            System.out.println("podporovane docflavors: "+ps.getSupportedDocFlavors());
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
    public void printVmVariantLabel(VmVariante vmVariante, Users user, String printerName) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String dateStr = dateFormat.format(date);
        String zpl = "^XA^MC^EF^EF^MTT^MMT^MNY~SD20^LH50,50^LT00~TA00^PRA^PON^LRN^PMN^JZN^PQ1,1,1,Y^FS" +
                "^FO70,70^BXN,7,200^FD"+vmVariante.getErznr().trim() +"^FS" +
                "^FO635,05^ADR,25,10^FDModul:^FS" +
                "^FO595,05^ADR,27,12^FD" + vmVariante.getName().trim() +"^FS" +
                "^FO560,05^ADR,24,10^FDStelle:^FS" +
                "^FO560,110^ADR,24,10^FD" + "MODUL ASSEMBLY" +"^FS" +
                "^FO530,05^ADR,24,10^FDVar.Nr.:^FS" +
                "^FO530,110^ADR,24,10^FD" + vmVariante.getErznr() +"^FS" +
                "^FO500,05^ADR,24,10^FDCount Nr.:^FS" +
                "^FO500,150^ADR,24,10^FD" + "0^FS"+
                "^FO470,05^ADR,24,10^FDProd.Datum:^FS" +
                "^FO470,150^ADR,24,10^FD" + dateStr +"^FS"+
                "^FO440,05^ADR,24,10^FDZmena:^FS"+
                "^FO440,100^ADR,24,10^FD"+""+"^FS"+
                "^XZ";
        try {
            PrintService ps = null;
            String sPrinterName = null;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

            for (int i = 0; i < services.length; i++) {

                PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
                sPrinterName = ((PrinterName) attr).getValue();
                //System.out.println("neeeeeejm " + printerName.trim());
                if (sPrinterName.toLowerCase().indexOf(printerName) >= 0) {
                    ps = services[i];
                    break;
                }
            }
            if (ps == null) {
                System.out.println("Printer is not found.");
                //return;
            }
            //System.out.println("podporovane docflavors: "+ps.getSupportedDocFlavors());
            DocPrintJob job = ps.createPrintJob();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            byte[] by = zpl.getBytes();
            Doc doc = new SimpleDoc(by, flavor, null);
            job.print(doc, null);

        } catch (PrintException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void printModulLabel(Moduls moduls, Users user, Integer nrOfCopies) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String dateStr = dateFormat.format(date);
        String zpl = "^XA^MC^EF^EF^MTT^MMT^MNY~SD20^LH50,50^LT00~TA00^PRA^PON^LRN^PMN^JZN^PQ"+ nrOfCopies +",1,1,Y^FS" +
                "^FO70,70^BXN,7,200^FD"+moduls.getSachNrLieferant().trim() +"^FS" +
                "^FO635,05^ADR,25,10^FDModul:^FS" +
                "^FO595,05^ADR,27,12^FD" + moduls.getSachNrBest().trim() +"^FS" +
                "^FO560,05^ADR,24,10^FDStelle:^FS" +
                "^FO560,110^ADR,24,10^FD" + user.getName().trim() +"^FS" +
                "^FO530,05^ADR,24,10^FDVar.Nr.:^FS" +
                "^FO530,110^ADR,24,10^FD" + moduls.getSachNrLieferant() +"^FS" +
                "^FO500,05^ADR,24,10^FDCount Nr.:^FS" +
                "^FO500,150^ADR,24,10^FD" + "0^FS"+
                "^FO470,05^ADR,24,10^FDProd.Datum:^FS" +
                "^FO470,150^ADR,24,10^FD" + dateStr +"^FS"+
                "^FO440,05^ADR,24,10^FDZmena:^FS"+
                "^FO440,100^ADR,24,10^FD"+"AV"+"^FS"+
                "^XZ";
        try {
            PrintService ps = null;
            String sPrinterName = null;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

            for (int i = 0; i < services.length; i++) {

                PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
                sPrinterName = ((PrinterName) attr).getValue();
                //System.out.println("neeeeeejm " + printerName.trim());
                if (sPrinterName.toLowerCase().indexOf("mont_etiket_printer") >= 0) {
                    ps = services[i];
                    break;
                }
            }
            if (ps == null) {
                System.out.println("Printer is not found.");
                //return;
            }
            //System.out.println("podporovane docflavors: "+ps.getSupportedDocFlavors());
            //for (int i = 1; i <= nrOfCopies; i++){
            DocPrintJob job = ps.createPrintJob();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            byte[] by = zpl.getBytes();
            Doc doc = new SimpleDoc(by, flavor, null);
            job.print(doc, null);//}

        } catch (PrintException e1) {
            e1.printStackTrace();
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
