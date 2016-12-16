package com.leoni.viewModel;

import com.leoni.data.manager.BoardTypeManager;
import com.leoni.data.manager.GroupDeriveManager;
import com.leoni.data.manager.Lpab64Manager;
import com.leoni.data.manager.SchrittModulRm9X1WrmManager;
import com.leoni.data.manager.dab.PrnrManager;
import com.leoni.data.models.BoardType;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.ProdGroup;
import com.leoni.data.models.SchrittModulRm9X1Wrm;
import com.leoni.data.models.dab.Prnr;
import com.leoni.data.models.nonDBmodels.SchulzObj;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hrmi1005 on 13. 4. 2016.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AloReportVM{

    @WireVariable
    protected Properties adminProps;

    @WireVariable
    private PrnrManager prnrManager;

    @WireVariable
    private Lpab64Manager lpab64Manager;

    @WireVariable
    private SchrittModulRm9X1WrmManager schrittManager;

    @WireVariable
    private BoardTypeManager boardTypeManager;

    @WireVariable
    private GroupDeriveManager groupDeriveManager;

    Date date;
    String dateString;


    @Init
    public void init(){

        date = new Date();
        //Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, -1);
        //date = cal.getTime();
    }

    @Command
    public void submit(){
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        dateString = df.format(date);
        List<Prnr> prnrList = prnrManager.getPrnrList("F",dateString);
        List<SchulzObj> schulzObjList = new ArrayList<>();
        for (Prnr prnr : prnrList){
            SchulzObj so = new SchulzObj(prnr.getProdNr(), prnr.getKabelsatzKz(), prnr.getBoardType(),prnr.getAusfuehrung(),
                    prnr.getReihfNr(),prnr.getCustomerNr(),prnr.getProdDate(), prnr.getProdGroup());
            schulzObjList.add(so);
        }
        for (SchulzObj so: schulzObjList){
            List<Lpab64> lpab64List = lpab64Manager.findByProdnrAndKabelsatz(so.getProdNr(),"F");
            so.setModulList(lpab64List);
        }
        //File file;
        try {
            //file = new File();
            //FileWriter fw = new FileWriter(file);
           // InputStream is = media.getStreamData();//fis;
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("report");

            XSSFRow rowhead = sheet.createRow((short)0);
            rowhead.createCell(0).setCellValue("Prod. Nr.");
            rowhead.createCell(1).setCellValue("Ks kz");
            rowhead.createCell(2).setCellValue("Ausf");
            rowhead.createCell(3).setCellValue("Prod. group");
            rowhead.createCell(4).setCellValue("Band");
            rowhead.createCell(5).setCellValue("Kunde");
            rowhead.createCell(6).setCellValue("Lieferant");
            rowhead.createCell(7).setCellValue("Reihenfolge");
            rowhead.createCell(8).setCellValue("Sach nr. best");
            rowhead.createCell(9).setCellValue("Sach nr. lief");
            rowhead.createCell(10).setCellValue("Assembly level");
            rowhead.createCell(11).setCellValue("Workplace");
            int i = 0;
            for (SchulzObj item : schulzObjList){
                String prodGroup = "";
                for (Lpab64 modul : item.getModulList()){
                    List<ProdGroup> prodGroupList = groupDeriveManager.deriveProdGroup(modul.getSachNrBest().trim());
                    if (!prodGroupList.isEmpty()){
                         prodGroup = prodGroupList.get(0).getProgramDesc();
                                /*if (prodGroup.getProdGroup().trim().equals()) flCounter++;
                                if () ll991Counter++;
                                if () rl991Counter++;
                                if () ll981Counter++;
                                if () rl981Counter++;
                                if () cupCounter++;*/
                    }
                }
                for (Lpab64 modul : item.getModulList()){

                    List<SchrittModulRm9X1Wrm> schrittModulRm9X1WrmList = schrittManager.findBySachNrLieferant(modul.getSachNrLieferant());
                    for (SchrittModulRm9X1Wrm schritt : schrittModulRm9X1WrmList){
                        i++;
                        XSSFRow rowhead1 = sheet.createRow(i);
                        rowhead1.createCell(0).setCellValue(item.getProdNr());
                        rowhead1.createCell(1).setCellValue(item.getKsKz());
                        rowhead1.createCell(2).setCellValue(item.getAusf());
                        rowhead1.createCell(3).setCellValue(prodGroup);
                        List<BoardType> boardTypeList = boardTypeManager.find(item.getBoardType());
                        if (!boardTypeList.isEmpty()) {
                        rowhead1.createCell(4).setCellValue(boardTypeList.get(0).getBand().getName());}
                        else {rowhead1.createCell(4).setCellValue("NOT FOUND");}
                        rowhead1.createCell(5).setCellValue(item.getKdnr());
                        rowhead1.createCell(6).setCellValue(item.getLiefdkd());
                        rowhead1.createCell(7).setCellValue(item.getReihenf());
                        rowhead1.createCell(8).setCellValue(modul.getSachNrBest().trim());
                        rowhead1.createCell(9).setCellValue(modul.getSachNrLieferant().trim());
                        rowhead1.createCell(10).setCellValue(schritt.getAssLevelSeq());
                        rowhead1.createCell(11).setCellValue(schritt.getSchritt().trim() + schritt.getSeite().trim());
                    }

                }

            }
            FileOutputStream fileOut = new FileOutputStream(adminProps.getProperty("reportExportPath"));
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Your excel file has been generated!");


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Messagebox.show("Report vygenerovany", "Hotovo!", Messagebox.OK, null);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
