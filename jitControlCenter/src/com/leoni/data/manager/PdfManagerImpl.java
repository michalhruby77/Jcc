package com.leoni.data.manager;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.leoni.data.models.Lpab62;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.Moduls;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 16.9.2014
 * Time: 8:48
 * To change this template use File | Settings | File Templates.
 */
@Service("pdfManager")
public class PdfManagerImpl implements PdfManager{


    public File createMontageListPdf(Lpab62 harness, List<Lpab64> modulsList) {
        File file = new File("ml.pdf");
        try {
            OutputStream os = new FileOutputStream(file);
            Document document = new Document();
            document.setPageSize(PageSize.A4);
            document.newPage();

            PdfWriter.getInstance(document, os);
            document.open();
            String date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
            Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 15,Font.BOLD);
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
            Paragraph p1 = new Paragraph("MONTAGEAUFTRAG / OBJEDNAVKA NA MONTAZ",font1);
            String kunde = "";
            if (harness.getKundenNr().trim().equals("323350")){kunde = "PORSCHE";}
            if (harness.getKundenNr().trim().equals("323357")){kunde = "OSNABRUECK";}
            Paragraph p2 = new Paragraph(kunde, font1);
            Paragraph p3 = new Paragraph("Druckdatum/Datum tlace: "+ date,font2);
            PdfPTable table1 = new PdfPTable(3); // 3 columns.
            //table1.setWidthPercentage(100);
            table1.setSpacingBefore(5);
            table1.setSpacingAfter(5);
            float[] columnWidths = { 10, 10, 10};
            table1.setWidths(columnWidths);
            Paragraph table1par10 = new Paragraph("Prod.c.:",font2);
            Paragraph table1par1 = new Paragraph(  harness.getProdNr().trim(),font1);
            PdfPCell table1cell1 = new PdfPCell();
            table1cell1.addElement(table1par10);
            table1cell1.addElement(table1par1);
            String prodGroup = "";
            prodGroup = prodGroup + harness.getKabelsatzKz().trim() + "-" + harness.getProdgruppe().trim() + "-"
                      + harness.getAusfuehrung().trim();
            Paragraph table1par20 = new Paragraph("Prod.skup.:",font2);
            Paragraph table1par2 = new Paragraph(prodGroup ,font1);
            PdfPCell table1cell2 = new PdfPCell();
            table1cell2.addElement(table1par20);
            table1cell2.addElement(table1par2);
            String newDate = "";
            if(harness.getLieferDatum()!=null){
               newDate = harness.getLieferDatum().toString().substring(6,8) + "."
                       + harness.getLieferDatum().toString().substring(4,6) + "."
                       + harness.getLieferDatum().toString().substring(0,4);
            }
            Paragraph table1par30 = new Paragraph("Abruf datum:",font2);
            Paragraph table1par3 = new Paragraph(newDate,font1);
            PdfPCell table1cell3 = new PdfPCell();
            table1cell3.addElement(table1par30);
            table1cell3.addElement(table1par3);
            /*table1cell1.setRowspan(2);
            table1cell2.setRowspan(2);
            table1cell3.setRowspan(2);*/
            table1cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell(table1cell1);
            table1.addCell(table1cell2);
            table1.addCell(table1cell3);
            //MODULY
            //--------------
            PdfPTable tableBig = new PdfPTable(2); // 3 columns.
            tableBig.setSpacingBefore(5);
            tableBig.setSpacingAfter(5);
            tableBig.setWidthPercentage(100);
            //table2.setHorizontalAlignment(0);
            float[] columnWidthsBig = { 30, 30};
            tableBig.setWidths(columnWidthsBig);
            //--------------
            PdfPTable table2 = new PdfPTable(2); // 2 columns.
            table2.setSpacingBefore(5);
            table2.setSpacingAfter(5);
            table2.setWidthPercentage(40);
            table2.setHorizontalAlignment(5);
            float[] columnWidthsModuls = { 10, 10};
            table2.setWidths(columnWidthsModuls);
            Paragraph table2par1 = new Paragraph(  "Sachn. Liefer:",font1);
            PdfPCell table2cell1 = new PdfPCell(table2par1);
            Paragraph table2par2 = new Paragraph(  "Sachn. Kunde",font1);
            PdfPCell table2cell2 = new PdfPCell(table2par2);
            table2cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table2cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table2.addCell(table2cell1);
            table2.addCell(table2cell2);
            int counterTab2 = 1;
            for(Lpab64 item : modulsList){
                if(counterTab2<=30){
                counterTab2++;
                Paragraph table2parTemp = new Paragraph(item.getSachNrLieferant().trim(),font1);
                PdfPCell table2cellTemp = new PdfPCell(table2parTemp);
                Paragraph table2par2Temp = new Paragraph(item.getSachNrBest().trim(),font1);
                PdfPCell table2cell2Temp = new PdfPCell(table2par2Temp);
                table2.addCell(table2cellTemp);
                table2.addCell(table2cell2Temp);}
            }
            if(counterTab2<=30){
                    counterTab2++;
                    Paragraph table2parTemp = new Paragraph("",font1);
                    PdfPCell table2cellTemp = new PdfPCell(table2parTemp);
                    table2cellTemp.setFixedHeight(15);
                    Paragraph table2par2Temp = new Paragraph("",font1);
                    PdfPCell table2cell2Temp = new PdfPCell(table2par2Temp);
                    table2cell2Temp.setFixedHeight(15);
                    table2.addCell(table2cellTemp);
                    table2.addCell(table2cell2Temp);

            }

            PdfPTable table3 = new PdfPTable(2); // 3 columns.
            table3.setSpacingBefore(5);
            table3.setSpacingAfter(5);
            table3.setWidthPercentage(40);
            table3.setWidths(columnWidthsModuls);
            table3.setHorizontalAlignment(5);
            Paragraph table3par1 = new Paragraph(  "Sachn. Liefer",font1);
            PdfPCell table3cell1 = new PdfPCell(table3par1);
            table3cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            Paragraph table3par2 = new Paragraph(  "Sachn. Kunde",font1);
            PdfPCell table3cell2 = new PdfPCell(table3par2);
            table3cell2.setBackgroundColor(BaseColor.LIGHT_GRAY );
            table3.addCell(table3cell1);
            table3.addCell(table3cell2);
            if(modulsList.size()<=30){
                for (int i=1; i<=30;i++){
                    Paragraph table3parTemp = new Paragraph("",font1);
                    PdfPCell table3cellTemp = new PdfPCell(table3parTemp);
                    table3cellTemp.setFixedHeight(19);
                    Paragraph table3par2Temp = new Paragraph("",font1);
                    PdfPCell table3cell2Temp = new PdfPCell(table3par2Temp);
                    table3cell2Temp.setFixedHeight(19);
                    table3.addCell(table3cellTemp);
                    table3.addCell(table3cell2Temp);
                }
            }
            else{
                for(int i = counterTab2; i < modulsList.size();i++){
                    counterTab2++;
                    Paragraph table3parTemp = new Paragraph(modulsList.get(i).getSachNrLieferant().trim(),font1);
                    PdfPCell table3cellTemp = new PdfPCell(table3parTemp);
                    Paragraph table3par2Temp = new Paragraph(modulsList.get(i).getSachNrLieferant().trim(),font1);
                    PdfPCell table3cell2Temp = new PdfPCell(table3par2Temp);
                    table3.addCell(table3cellTemp);
                    table3.addCell(table3cell2Temp);
                }
                if(counterTab2<=60){
                    counterTab2++;
                    Paragraph table3parTemp = new Paragraph("",font1);
                    PdfPCell table3cellTemp = new PdfPCell(table3parTemp);
                    table3cellTemp.setFixedHeight(19);
                    Paragraph table3par2Temp = new Paragraph("",font1);
                    PdfPCell table3cell2Temp = new PdfPCell(table3par2Temp);
                    table3cell2Temp.setFixedHeight(19);
                    table3.addCell(table3cellTemp);
                    table3.addCell(table3cell2Temp);

                }
            }
            //---------
            PdfPCell tableBigCell1 = new PdfPCell(table2);
            tableBigCell1.setPadding(4);
            tableBigCell1.setBorder(PdfPCell.NO_BORDER);
            PdfPCell tableBigCell2 = new PdfPCell(table3);
            tableBigCell2.setPadding(4);
            tableBigCell2.setBorder(PdfPCell.NO_BORDER);
            tableBig.addCell(tableBigCell1);
            tableBig.addCell(tableBigCell2);
            //---------
            document.add(p1);
            document.add(p2);
            document.add(p3);
            document.add(table1);
            document.add(tableBig);
            //document.add(table2);
            //document.add(table3);
            document.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DocumentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return file;
    }

    @Override
    public File createVariantScan(String variantName, String scanString, String user, int nrOfPieces) {
        File file = new File("variantScan.pdf");
        try {
            OutputStream os = new FileOutputStream(file);
            Document document = new Document();
            document.setPageSize(PageSize.A4);
            document.newPage();
            PdfWriter.getInstance(document, os);
            document.open();
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 6);
            //PdfWriter writer = PdfWriter.getInstance(document, os);
            for (int i=1; i<=nrOfPieces;i++){
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(30);
                //-----BARCODE TABLE
                PdfPTable tableBarcode = new PdfPTable(1);
                tableBarcode.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cellTableBarcode = new PdfPCell();
                cellTableBarcode.setBorder(Rectangle.NO_BORDER);
                cellTableBarcode.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTableBarcode.setFixedHeight(30);
                PdfPTable tableText = new PdfPTable(1);
                tableText.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cellTableText = new PdfPCell();
                cellTableText.setBorder(Rectangle.NO_BORDER);
                cellTableText.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTableText.setFixedHeight(50);
                BarcodeDatamatrix datamatrix = new BarcodeDatamatrix();
                datamatrix.generate(scanString);
                Image img = datamatrix.createImage();
                img.setAlignment(Element.ALIGN_CENTER);
                img.setSpacingBefore(10);
                PdfPCell cellBarcode1 = new PdfPCell();
                //cellBarcode1.setBorderWidth(0);
                PdfPCell cellBarcode2 = new PdfPCell();
                //cellBarcode2.setBorderWidth(0);
                cellTableBarcode.addElement(img);
                tableBarcode.addCell(cellTableBarcode);

                Paragraph paragraph = new Paragraph(variantName,font);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                cellTableText.addElement(paragraph);
                Paragraph paragraph1 = new Paragraph(scanString,font);
                paragraph1.setAlignment(Element.ALIGN_CENTER);
                cellTableText.addElement(paragraph1);
                Paragraph paragraph2 = new Paragraph(user,font);
                paragraph2.setAlignment(Element.ALIGN_CENTER);
                cellTableText.addElement(paragraph2);
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                Paragraph paragraph3 = new Paragraph(df.format(new Date()),font);
                paragraph3.setAlignment(Element.ALIGN_CENTER);
                cellTableText.addElement(paragraph3);
                tableText.addCell(cellTableText);
                cellBarcode1.addElement(tableBarcode);
                cellBarcode2.addElement(tableText);
                PdfPCell cellBarcode = new PdfPCell();
                table.addCell(cellBarcode1);
                table.addCell(cellBarcode2);

                document.add(table);
            }
            document.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DocumentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return file;
    }
}
