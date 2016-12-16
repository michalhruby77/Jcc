package com.leoni.viewModel;

import com.leoni.data.manager.Lpab62Manager;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 6.5.2014
 * Time: 9:48
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class OverwievViewModel {
    @WireVariable
    private Lpab62Manager lpab62Manager;


   @Wire
   private Rows rows;
   @Wire
   private Rows rows2;



    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        int numberOfDates = lpab62Manager.getPorscheDates().size();
        int numberOfDatesO = lpab62Manager.getOsnabruckDates().size();
        List<Integer> porscheDatesList = lpab62Manager.getPorscheDates();
        Collections.sort(porscheDatesList);
        List<Integer> osnabruckDatesList = lpab62Manager.getOsnabruckDates();
        Collections.sort(osnabruckDatesList);
        int pocet = 0;
        for (Integer item : porscheDatesList){
            Row tempRow = new Row();
            tempRow.setZclass("Non_Existant_Class");
            tempRow.setHeight("20px");
            if(pocet==0)
            {
            pocet++;
            Cell tempCell = new Cell();
            tempCell.setStyle("border: 1px solid #000");
            Label tempLabel = new Label("Porsche");
            tempCell.setRowspan(numberOfDates*5);
            tempCell.appendChild(tempLabel);
            Cell tempCell2  = new Cell();
            tempCell2.setStyle("border: 1px solid #000");
            Label tempLabel2 = new Label("F991LL");
            tempCell2.setRowspan(numberOfDates);
            tempCell2.appendChild(tempLabel2);
            tempRow.appendChild(tempCell);
            tempRow.appendChild(tempCell2);}
            Cell tempCell3  = new Cell();
            tempCell3.setStyle("border: 1px solid #000");
            Label tempLabel3 = new Label(String.valueOf(item));
            tempCell3.appendChild(tempLabel3);
            tempRow.appendChild(tempCell3);
            Cell tempCellAll = new Cell();
            tempCellAll.setStyle("border: 1px solid #000");
            Label tempLabelAll = new Label(String.valueOf(lpab62Manager.getAllHarnessesForDate(String.valueOf(item),"LL","F","323350","991")));
            tempCellAll.appendChild(tempLabelAll);
            tempRow.appendChild(tempCellAll);

            Cell tempCellEin = new Cell();
            tempCellEin.setStyle("border: 1px solid #000");
            Label tempLabelEin = new Label(String.valueOf(lpab62Manager.getEinlaufForDate(String.valueOf(item),"LL","F","323350","991")));
            tempCellEin.appendChild(tempLabelEin);
            tempRow.appendChild(tempCellEin);

            Cell tempCellAus = new Cell();
            tempCellAus.setStyle("border: 1px solid #000");
            Label tempLabelAus = new Label(String.valueOf(lpab62Manager.getAuslaufForDate(String.valueOf(item),"LL","F","323350","991")));
            tempCellAus.appendChild(tempLabelAus);
            tempRow.appendChild(tempCellAus);

            Cell tempCellBdos = new Cell();
            tempCellBdos.setStyle("border: 1px solid #000");
            Label tempLabelBdos = new Label(String.valueOf(lpab62Manager.getBdoseForDate(String.valueOf(item),"LL","F","323350","991")));
            tempCellBdos.appendChild(tempLabelBdos);
            tempRow.appendChild(tempCellBdos);

            Cell tempCellFoam = new Cell();
            tempCellFoam.setStyle("border: 1px solid #000");
            Label tempLabelFoam = new Label(String.valueOf(lpab62Manager.getFoamForDate(String.valueOf(item),"LL","F","323350","991")));
            tempCellFoam.appendChild(tempLabelFoam);
            tempRow.appendChild(tempCellFoam);

            Cell tempCellelektr = new Cell();
            tempCellelektr.setStyle("border: 1px solid #000");
            Label tempLabelelektr = new Label(String.valueOf(lpab62Manager.getElektroForDate(String.valueOf(item),"LL","F","323350","991")));
            tempCellelektr.appendChild(tempLabelelektr);
            tempRow.appendChild(tempCellelektr);

            Cell tempCellPR = new Cell();
            tempCellPR.setStyle("border: 1px solid #000");
            Label tempLabelPR = new Label(String.valueOf(lpab62Manager.getPRForDate(String.valueOf(item),"LL","F","323350","991")));
            tempCellPR.appendChild(tempLabelPR);
            tempRow.appendChild(tempCellPR);

            Cell tempCellEsd = new Cell();
            tempCellEsd.setStyle("border: 1px solid #000");
            Label tempLabelEsd = new Label(String.valueOf(lpab62Manager.getEsdScrewForDate(String.valueOf(item),"LL","F","323350","991")));
            tempCellEsd.appendChild(tempLabelEsd);
            tempRow.appendChild(tempCellEsd);

            Cell tempCellPhoto = new Cell();
            tempCellPhoto.setStyle("border: 1px solid #000");
            Label tempLabelPhoto = new Label(String.valueOf(lpab62Manager.getPhotoWaForDate(String.valueOf(item),"LL","F","323350","991")));
            tempCellPhoto.appendChild(tempLabelPhoto);
            tempRow.appendChild(tempCellPhoto);

            rows.appendChild(tempRow);
        }
        int pocet1 = 0;
        for (Integer item : porscheDatesList){
            Row tempRow = new Row();
            tempRow.setZclass("Non_Existant_Class");
            tempRow.setHeight("20px");
            if(pocet1==0)
            {
            pocet1++;
            Cell tempCell2  = new Cell();
            tempCell2.setStyle("border: 1px solid #000");
            Label tempLabel2 = new Label("F981LL");
            tempCell2.setRowspan(numberOfDates);
            tempCell2.appendChild(tempLabel2);
            //tempRow.appendChild(tempCell);
            tempRow.appendChild(tempCell2);}
            Cell tempCell3  = new Cell();
            tempCell3.setStyle("border: 1px solid #000");
            Label tempLabel3 = new Label(String.valueOf(item));
            tempCell3.appendChild(tempLabel3);
            tempRow.appendChild(tempCell3);
            Cell tempCellAll = new Cell();
            tempCellAll.setStyle("border: 1px solid #000");
            Label tempLabelAll = new Label(String.valueOf(lpab62Manager.getAllHarnessesForDate(String.valueOf(item),"LL","F","323350","981")));
            tempCellAll.appendChild(tempLabelAll);
            tempRow.appendChild(tempCellAll);

            Cell tempCellEin = new Cell();
            tempCellEin.setStyle("border: 1px solid #000");
            Label tempLabelEin = new Label(String.valueOf(lpab62Manager.getEinlaufForDate(String.valueOf(item),"LL","F","323350","981")));
            tempCellEin.appendChild(tempLabelEin);
            tempRow.appendChild(tempCellEin);

            Cell tempCellAus = new Cell();
            tempCellAus.setStyle("border: 1px solid #000");
            Label tempLabelAus = new Label(String.valueOf(lpab62Manager.getAuslaufForDate(String.valueOf(item),"LL","F","323350","981")));
            tempCellAus.appendChild(tempLabelAus);
            tempRow.appendChild(tempCellAus);

            Cell tempCellBdos = new Cell();
            tempCellBdos.setStyle("border: 1px solid #000");
            Label tempLabelBdos = new Label(String.valueOf(lpab62Manager.getBdoseForDate(String.valueOf(item),"LL","F","323350","981")));
            tempCellBdos.appendChild(tempLabelBdos);
            tempRow.appendChild(tempCellBdos);

            Cell tempCellFoam = new Cell();
            tempCellFoam.setStyle("border: 1px solid #000");
            Label tempLabelFoam = new Label(String.valueOf(lpab62Manager.getFoamForDate(String.valueOf(item),"LL","F","323350","981")));
            tempCellFoam.appendChild(tempLabelFoam);
            tempRow.appendChild(tempCellFoam);

            Cell tempCellelektr = new Cell();
            tempCellelektr.setStyle("border: 1px solid #000");
            Label tempLabelelektr = new Label(String.valueOf(lpab62Manager.getElektroForDate(String.valueOf(item),"LL","F","323350","981")));
            tempCellelektr.appendChild(tempLabelelektr);
            tempRow.appendChild(tempCellelektr);

            Cell tempCellPR = new Cell();
            tempCellPR.setStyle("border: 1px solid #000");
            Label tempLabelPR = new Label(String.valueOf(lpab62Manager.getPRForDate(String.valueOf(item),"LL","F","323350","981")));
            tempCellPR.appendChild(tempLabelPR);
            tempRow.appendChild(tempCellPR);

            Cell tempCellEsd = new Cell();
            tempCellEsd.setStyle("border: 1px solid #000");
            Label tempLabelEsd = new Label(String.valueOf(lpab62Manager.getEsdScrewForDate(String.valueOf(item),"LL","F","323350","981")));
            tempCellEsd.appendChild(tempLabelEsd);
            tempRow.appendChild(tempCellEsd);

            Cell tempCellPhoto = new Cell();
            tempCellPhoto.setStyle("border: 1px solid #000");
            Label tempLabelPhoto = new Label(String.valueOf(lpab62Manager.getPhotoWaForDate(String.valueOf(item),"LL","F","323350","981")));
            tempCellPhoto.appendChild(tempLabelPhoto);
            tempRow.appendChild(tempCellPhoto);


            rows.appendChild(tempRow);
        }
        int pocet2 = 0;
        for (Integer item : porscheDatesList){
            Row tempRow = new Row();
            tempRow.setZclass("Non_Existant_Class");
            tempRow.setHeight("20px");
            if(pocet2==0)
            {
                pocet2++;
                Cell tempCell2  = new Cell();
                tempCell2.setStyle("border: 1px solid #000");
                Label tempLabel2 = new Label("F9X1RL");
                tempCell2.setRowspan(numberOfDates);
                tempCell2.appendChild(tempLabel2);
                //tempRow.appendChild(tempCell);
                tempRow.appendChild(tempCell2);}
            Cell tempCell3  = new Cell();
            tempCell3.setStyle("border: 1px solid #000");
            Label tempLabel3 = new Label(String.valueOf(item));
            tempCell3.appendChild(tempLabel3);
            tempRow.appendChild(tempCell3);
            Cell tempCellAll = new Cell();
            tempCellAll.setStyle("border: 1px solid #000");
            Label tempLabelAll = new Label(String.valueOf(lpab62Manager.getAllHarnessesForDate(String.valueOf(item),"RL","F","323350","9X1")));
            tempCellAll.appendChild(tempLabelAll);
            tempRow.appendChild(tempCellAll);

            Cell tempCellEin = new Cell();
            tempCellEin.setStyle("border: 1px solid #000");
            Label tempLabelEin = new Label(String.valueOf(lpab62Manager.getEinlaufForDate(String.valueOf(item),"RL","F","323350","9X1")));
            tempCellEin.appendChild(tempLabelEin);
            tempRow.appendChild(tempCellEin);

            Cell tempCellAus = new Cell();
            tempCellAus.setStyle("border: 1px solid #000");
            Label tempLabelAus = new Label(String.valueOf(lpab62Manager.getAuslaufForDate(String.valueOf(item),"RL","F","323350","9X1")));
            tempCellAus.appendChild(tempLabelAus);
            tempRow.appendChild(tempCellAus);

            Cell tempCellBdos = new Cell();
            tempCellBdos.setStyle("border: 1px solid #000");
            Label tempLabelBdos = new Label(String.valueOf(lpab62Manager.getBdoseForDate(String.valueOf(item),"RL","F","323350","9X1")));
            tempCellBdos.appendChild(tempLabelBdos);
            tempRow.appendChild(tempCellBdos);

            Cell tempCellFoam = new Cell();
            tempCellFoam.setStyle("border: 1px solid #000");
            Label tempLabelFoam = new Label(String.valueOf(lpab62Manager.getFoamForDate(String.valueOf(item),"RL","F","323350","9X1")));
            tempCellFoam.appendChild(tempLabelFoam);
            tempRow.appendChild(tempCellFoam);

            Cell tempCellelektr = new Cell();
            tempCellelektr.setStyle("border: 1px solid #000");
            Label tempLabelelektr = new Label(String.valueOf(lpab62Manager.getElektroForDate(String.valueOf(item),"RL","F","323350","9X1")));
            tempCellelektr.appendChild(tempLabelelektr);
            tempRow.appendChild(tempCellelektr);

            Cell tempCellPR = new Cell();
            tempCellPR.setStyle("border: 1px solid #000");
            Label tempLabelPR = new Label(String.valueOf(lpab62Manager.getPRForDate(String.valueOf(item),"RL","F","323350","9X1")));
            tempCellPR.appendChild(tempLabelPR);
            tempRow.appendChild(tempCellPR);

            Cell tempCellEsd = new Cell();
            tempCellEsd.setStyle("border: 1px solid #000");
            Label tempLabelEsd = new Label(String.valueOf(lpab62Manager.getEsdScrewForDate(String.valueOf(item),"RL","F","323350","9X1")));
            tempCellEsd.appendChild(tempLabelEsd);
            tempRow.appendChild(tempCellEsd);

            Cell tempCellPhoto = new Cell();
            tempCellPhoto.setStyle("border: 1px solid #000");
            Label tempLabelPhoto = new Label(String.valueOf(lpab62Manager.getPhotoWaForDate(String.valueOf(item),"RL","F","323350","9X1")));
            tempCellPhoto.appendChild(tempLabelPhoto);
            tempRow.appendChild(tempCellPhoto);


            rows.appendChild(tempRow);
        }
        int pocet3 = 0;
        for (Integer item : porscheDatesList){
            Row tempRow = new Row();
            tempRow.setZclass("Non_Existant_Class");
            tempRow.setHeight("20px");
            if(pocet3==0)
            {
                pocet3++;
                Cell tempCell2  = new Cell();
                tempCell2.setStyle("border: 1px solid #000");
                Label tempLabel2 = new Label("C9X1LL");
                tempCell2.setRowspan(numberOfDates);
                tempCell2.appendChild(tempLabel2);
                //tempRow.appendChild(tempCell);
                tempRow.appendChild(tempCell2);}
            Cell tempCell3  = new Cell();
            tempCell3.setStyle("border: 1px solid #000");
            Label tempLabel3 = new Label(String.valueOf(item));
            tempCell3.appendChild(tempLabel3);
            tempRow.appendChild(tempCell3);
            Cell tempCellAll = new Cell();
            tempCellAll.setStyle("border: 1px solid #000");
            Label tempLabelAll = new Label(String.valueOf(lpab62Manager.getAllHarnessesForDate(String.valueOf(item),"LL","C","323350","9X1")));
            tempCellAll.appendChild(tempLabelAll);
            tempRow.appendChild(tempCellAll);

            Cell tempCellEin = new Cell();
            tempCellEin.setStyle("border: 1px solid #000");
            Label tempLabelEin = new Label(String.valueOf(lpab62Manager.getEinlaufForDate(String.valueOf(item),"LL","C","323350","9X1")));
            tempCellEin.appendChild(tempLabelEin);
            tempRow.appendChild(tempCellEin);

            Cell tempCellAus = new Cell();
            tempCellAus.setStyle("border: 1px solid #000");
            Label tempLabelAus = new Label(String.valueOf(lpab62Manager.getAuslaufForDate(String.valueOf(item),"LL","C","323350","9X1")));
            tempCellAus.appendChild(tempLabelAus);
            tempRow.appendChild(tempCellAus);

            Cell tempCellBdos = new Cell();
            tempCellBdos.setStyle("border: 1px solid #000");
            Label tempLabelBdos = new Label(String.valueOf(lpab62Manager.getBdoseForDate(String.valueOf(item),"LL","C","323350","9X1")));
            tempCellBdos.appendChild(tempLabelBdos);
            tempRow.appendChild(tempCellBdos);

            Cell tempCellFoam = new Cell();
            tempCellFoam.setStyle("border: 1px solid #000");
            Label tempLabelFoam = new Label(String.valueOf(lpab62Manager.getFoamForDate(String.valueOf(item),"LL","C","323350","9X1")));
            tempCellFoam.appendChild(tempLabelFoam);
            tempRow.appendChild(tempCellFoam);

            Cell tempCellelektr = new Cell();
            tempCellelektr.setStyle("border: 1px solid #000");
            Label tempLabelelektr = new Label(String.valueOf(lpab62Manager.getElektroForDate(String.valueOf(item),"LL","C","323350","9X1")));
            tempCellelektr.appendChild(tempLabelelektr);
            tempRow.appendChild(tempCellelektr);

            Cell tempCellPR = new Cell();
            tempCellPR.setStyle("border: 1px solid #000");
            Label tempLabelPR = new Label(String.valueOf(lpab62Manager.getPRForDate(String.valueOf(item),"LL","C","323350","9X1")));
            tempCellPR.appendChild(tempLabelPR);
            tempRow.appendChild(tempCellPR);

            Cell tempCellEsd = new Cell();
            tempCellEsd.setStyle("border: 1px solid #000");
            Label tempLabelEsd = new Label(String.valueOf(lpab62Manager.getEsdScrewForDate(String.valueOf(item),"LL","C","323350","9X1")));
            tempCellEsd.appendChild(tempLabelEsd);
            tempRow.appendChild(tempCellEsd);

            Cell tempCellPhoto = new Cell();
            tempCellPhoto.setStyle("border: 1px solid #000");
            Label tempLabelPhoto = new Label(String.valueOf(lpab62Manager.getPhotoWaForDate(String.valueOf(item),"LL","C","323350","9X1")));
            tempCellPhoto.appendChild(tempLabelPhoto);
            tempRow.appendChild(tempCellPhoto);


            rows.appendChild(tempRow);
        }
        int pocet4 = 0;
        for (Integer item : porscheDatesList){
            Row tempRow = new Row();
            tempRow.setZclass("Non_Existant_Class");
            tempRow.setHeight("20px");
            if(pocet4==0)
            {
            pocet4++;
            Cell tempCell2  = new Cell();
            tempCell2.setStyle("border: 1px solid #000");
            Label tempLabel2 = new Label("C9X1RL");
            tempCell2.setRowspan(numberOfDates);
            tempCell2.appendChild(tempLabel2);
            tempRow.appendChild(tempCell2);}
            Cell tempCell3  = new Cell();
            tempCell3.setStyle("border: 1px solid #000");
            Label tempLabel3 = new Label(String.valueOf(item));
            tempCell3.appendChild(tempLabel3);
            tempRow.appendChild(tempCell3);
            Cell tempCellAll = new Cell();
            tempCellAll.setStyle("border: 1px solid #000");
            Label tempLabelAll = new Label(String.valueOf(lpab62Manager.getAllHarnessesForDate(String.valueOf(item),"RL","C","323350","9X1")));
            tempCellAll.appendChild(tempLabelAll);
            tempRow.appendChild(tempCellAll);

            Cell tempCellEin = new Cell();
            tempCellEin.setStyle("border: 1px solid #000");
            Label tempLabelEin = new Label(String.valueOf(lpab62Manager.getEinlaufForDate(String.valueOf(item),"RL","C","323350","9X1")));
            tempCellEin.appendChild(tempLabelEin);
            tempRow.appendChild(tempCellEin);

            Cell tempCellAus = new Cell();
            tempCellAus.setStyle("border: 1px solid #000");
            Label tempLabelAus = new Label(String.valueOf(lpab62Manager.getAuslaufForDate(String.valueOf(item),"RL","C","323350","9X1")));
            tempCellAus.appendChild(tempLabelAus);
            tempRow.appendChild(tempCellAus);

            Cell tempCellBdos = new Cell();
            tempCellBdos.setStyle("border: 1px solid #000");
            Label tempLabelBdos = new Label(String.valueOf(lpab62Manager.getBdoseForDate(String.valueOf(item),"RL","C","323350","9X1")));
            tempCellBdos.appendChild(tempLabelBdos);
            tempRow.appendChild(tempCellBdos);

            Cell tempCellFoam = new Cell();
            tempCellFoam.setStyle("border: 1px solid #000");
            Label tempLabelFoam = new Label(String.valueOf(lpab62Manager.getFoamForDate(String.valueOf(item),"RL","C","323350","9X1")));
            tempCellFoam.appendChild(tempLabelFoam);
            tempRow.appendChild(tempCellFoam);

            Cell tempCellelektr = new Cell();
            tempCellelektr.setStyle("border: 1px solid #000");
            Label tempLabelelektr = new Label(String.valueOf(lpab62Manager.getElektroForDate(String.valueOf(item),"RL","C","323350","9X1")));
            tempCellelektr.appendChild(tempLabelelektr);
            tempRow.appendChild(tempCellelektr);

            Cell tempCellPR = new Cell();
            tempCellPR.setStyle("border: 1px solid #000");
            Label tempLabelPR = new Label(String.valueOf(lpab62Manager.getPRForDate(String.valueOf(item),"RL","C","323350","9X1")));
            tempCellPR.appendChild(tempLabelPR);
            tempRow.appendChild(tempCellPR);

            Cell tempCellEsd = new Cell();
            tempCellEsd.setStyle("border: 1px solid #000");
            Label tempLabelEsd = new Label(String.valueOf(lpab62Manager.getEsdScrewForDate(String.valueOf(item),"RL","C","323350","9X1")));
            tempCellEsd.appendChild(tempLabelEsd);
            tempRow.appendChild(tempCellEsd);

            Cell tempCellPhoto = new Cell();
            tempCellPhoto.setStyle("border: 1px solid #000");
            Label tempLabelPhoto = new Label(String.valueOf(lpab62Manager.getPhotoWaForDate(String.valueOf(item),"RL","C","323350","9X1")));
            tempCellPhoto.appendChild(tempLabelPhoto);
            tempRow.appendChild(tempCellPhoto);


            rows.appendChild(tempRow);
        }
        //-----------------------

        int poceto = 0;
        for (Integer item : osnabruckDatesList){
            Row tempRow = new Row();
            tempRow.setZclass("Non_Existant_Class");
            tempRow.setHeight("18px");
            if(poceto==0)
            {
                poceto++;
                Cell tempCell = new Cell();
                tempCell.setStyle("border: 1px solid #000");
                Label tempLabel = new Label("Osnabruck");
                tempCell.setRowspan(numberOfDatesO*4);
                tempCell.appendChild(tempLabel);
                Cell tempCell2  = new Cell();
                tempCell2.setStyle("border: 1px solid #000");
                Label tempLabel2 = new Label("F981LL");
                tempCell2.setRowspan(numberOfDatesO);
                tempCell2.appendChild(tempLabel2);
                tempRow.appendChild(tempCell);
                tempRow.appendChild(tempCell2);}
            Cell tempCell3  = new Cell();
            tempCell3.setStyle("border: 1px solid #000");
            Label tempLabel3 = new Label(String.valueOf(item));
            tempCell3.appendChild(tempLabel3);
            tempRow.appendChild(tempCell3);
            Cell tempCellAll = new Cell();
            tempCellAll.setStyle("border: 1px solid #000");
            Label tempLabelAll = new Label(String.valueOf(lpab62Manager.getAllHarnessesForDate(String.valueOf(item),"LL","F","323357","981")));
            tempCellAll.appendChild(tempLabelAll);
            tempRow.appendChild(tempCellAll);

            Cell tempCellEin = new Cell();
            tempCellEin.setStyle("border: 1px solid #000");
            Label tempLabelEin = new Label(String.valueOf(lpab62Manager.getEinlaufForDate(String.valueOf(item),"LL","F","323357","981")));
            tempCellEin.appendChild(tempLabelEin);
            tempRow.appendChild(tempCellEin);

            Cell tempCellAus = new Cell();
            tempCellAus.setStyle("border: 1px solid #000");
            Label tempLabelAus = new Label(String.valueOf(lpab62Manager.getAuslaufForDate(String.valueOf(item),"LL","F","323357","981")));
            tempCellAus.appendChild(tempLabelAus);
            tempRow.appendChild(tempCellAus);

            Cell tempCellBdos = new Cell();
            tempCellBdos.setStyle("border: 1px solid #000");
            Label tempLabelBdos = new Label(String.valueOf(lpab62Manager.getBdoseForDate(String.valueOf(item),"LL","F","323357","981")));
            tempCellBdos.appendChild(tempLabelBdos);
            tempRow.appendChild(tempCellBdos);

            Cell tempCellFoam = new Cell();
            tempCellFoam.setStyle("border: 1px solid #000");
            Label tempLabelFoam = new Label(String.valueOf(lpab62Manager.getFoamForDate(String.valueOf(item),"LL","F","323357","981")));
            tempCellFoam.appendChild(tempLabelFoam);
            tempRow.appendChild(tempCellFoam);

            Cell tempCellelektr = new Cell();
            tempCellelektr.setStyle("border: 1px solid #000");
            Label tempLabelelektr = new Label(String.valueOf(lpab62Manager.getElektroForDate(String.valueOf(item),"LL","F","323357","981")));
            tempCellelektr.appendChild(tempLabelelektr);
            tempRow.appendChild(tempCellelektr);

            Cell tempCellPR = new Cell();
            tempCellPR.setStyle("border: 1px solid #000");
            Label tempLabelPR = new Label(String.valueOf(lpab62Manager.getPRForDate(String.valueOf(item),"LL","F","323357","981")));
            tempCellPR.appendChild(tempLabelPR);
            tempRow.appendChild(tempCellPR);

            Cell tempCellEsd = new Cell();
            tempCellEsd.setStyle("border: 1px solid #000");
            Label tempLabelEsd = new Label(String.valueOf(lpab62Manager.getEsdScrewForDate(String.valueOf(item),"LL","F","323357","981")));
            tempCellEsd.appendChild(tempLabelEsd);
            tempRow.appendChild(tempCellEsd);

            Cell tempCellPhoto = new Cell();
            tempCellPhoto.setStyle("border: 1px solid #000");
            Label tempLabelPhoto = new Label(String.valueOf(lpab62Manager.getPhotoWaForDate(String.valueOf(item),"LL","F","323357","981")));
            tempCellPhoto.appendChild(tempLabelPhoto);
            tempRow.appendChild(tempCellPhoto);

            rows2.appendChild(tempRow);
        }
        int pocet1o = 0;
        for (Integer item : osnabruckDatesList){
            Row tempRow = new Row();
            tempRow.setZclass("Non_Existant_Class");
            tempRow.setHeight("18px");
            if(pocet1o==0)
            {
                pocet1o++;
                Cell tempCell2  = new Cell();
                tempCell2.setStyle("border: 1px solid #000");
                Label tempLabel2 = new Label("F981RL");
                tempCell2.setRowspan(numberOfDatesO);
                tempCell2.appendChild(tempLabel2);
                //tempRow.appendChild(tempCell);
                tempRow.appendChild(tempCell2);}
            Cell tempCell3  = new Cell();
            tempCell3.setStyle("border: 1px solid #000");
            Label tempLabel3 = new Label(String.valueOf(item));
            tempCell3.appendChild(tempLabel3);
            tempRow.appendChild(tempCell3);
            Cell tempCellAll = new Cell();
            tempCellAll.setStyle("border: 1px solid #000");
            Label tempLabelAll = new Label(String.valueOf(lpab62Manager.getAllHarnessesForDate(String.valueOf(item),"RL","F","323357","981")));
            tempCellAll.appendChild(tempLabelAll);
            tempRow.appendChild(tempCellAll);

            Cell tempCellEin = new Cell();
            tempCellEin.setStyle("border: 1px solid #000");
            Label tempLabelEin = new Label(String.valueOf(lpab62Manager.getEinlaufForDate(String.valueOf(item),"RL","F","323357","981")));
            tempCellEin.appendChild(tempLabelEin);
            tempRow.appendChild(tempCellEin);

            Cell tempCellAus = new Cell();
            tempCellAus.setStyle("border: 1px solid #000");
            Label tempLabelAus = new Label(String.valueOf(lpab62Manager.getAuslaufForDate(String.valueOf(item),"RL","F","323357","981")));
            tempCellAus.appendChild(tempLabelAus);
            tempRow.appendChild(tempCellAus);

            Cell tempCellBdos = new Cell();
            tempCellBdos.setStyle("border: 1px solid #000");
            Label tempLabelBdos = new Label(String.valueOf(lpab62Manager.getBdoseForDate(String.valueOf(item),"RL","F","323357","981")));
            tempCellBdos.appendChild(tempLabelBdos);
            tempRow.appendChild(tempCellBdos);

            Cell tempCellFoam = new Cell();
            tempCellFoam.setStyle("border: 1px solid #000");
            Label tempLabelFoam = new Label(String.valueOf(lpab62Manager.getFoamForDate(String.valueOf(item),"RL","F","323357","981")));
            tempCellFoam.appendChild(tempLabelFoam);
            tempRow.appendChild(tempCellFoam);

            Cell tempCellelektr = new Cell();
            tempCellelektr.setStyle("border: 1px solid #000");
            Label tempLabelelektr = new Label(String.valueOf(lpab62Manager.getElektroForDate(String.valueOf(item),"RL","F","323357","981")));
            tempCellelektr.appendChild(tempLabelelektr);
            tempRow.appendChild(tempCellelektr);

            Cell tempCellPR = new Cell();
            tempCellPR.setStyle("border: 1px solid #000");
            Label tempLabelPR = new Label(String.valueOf(lpab62Manager.getPRForDate(String.valueOf(item),"RL","F","323357","981")));
            tempCellPR.appendChild(tempLabelPR);
            tempRow.appendChild(tempCellPR);

            Cell tempCellEsd = new Cell();
            tempCellEsd.setStyle("border: 1px solid #000");
            Label tempLabelEsd = new Label(String.valueOf(lpab62Manager.getEsdScrewForDate(String.valueOf(item),"RL","F","323357","981")));
            tempCellEsd.appendChild(tempLabelEsd);
            tempRow.appendChild(tempCellEsd);

            Cell tempCellPhoto = new Cell();
            tempCellPhoto.setStyle("border: 1px solid #000");
            Label tempLabelPhoto = new Label(String.valueOf(lpab62Manager.getPhotoWaForDate(String.valueOf(item),"RL","F","323357","981")));
            tempCellPhoto.appendChild(tempLabelPhoto);
            tempRow.appendChild(tempCellPhoto);


            rows2.appendChild(tempRow);
        }
        int pocet2o = 0;
        for (Integer item : osnabruckDatesList){
            Row tempRow = new Row();
            tempRow.setZclass("Non_Existant_Class");
            tempRow.setHeight("18px");
            if(pocet2o==0)
            {
                pocet2o++;
                Cell tempCell2  = new Cell();
                tempCell2.setStyle("border: 1px solid #000");
                Label tempLabel2 = new Label("C981LL");
                tempCell2.setRowspan(numberOfDatesO);
                tempCell2.appendChild(tempLabel2);
                //tempRow.appendChild(tempCell);
                tempRow.appendChild(tempCell2);}
            Cell tempCell3  = new Cell();
            tempCell3.setStyle("border: 1px solid #000");
            Label tempLabel3 = new Label(String.valueOf(item));
            tempCell3.appendChild(tempLabel3);
            tempRow.appendChild(tempCell3);
            Cell tempCellAll = new Cell();
            tempCellAll.setStyle("border: 1px solid #000");
            Label tempLabelAll = new Label(String.valueOf(lpab62Manager.getAllHarnessesForDate(String.valueOf(item),"LL","C","323357","981")));
            tempCellAll.appendChild(tempLabelAll);
            tempRow.appendChild(tempCellAll);

            Cell tempCellEin = new Cell();
            tempCellEin.setStyle("border: 1px solid #000");
            Label tempLabelEin = new Label(String.valueOf(lpab62Manager.getEinlaufForDate(String.valueOf(item),"LL","C","323357","981")));
            tempCellEin.appendChild(tempLabelEin);
            tempRow.appendChild(tempCellEin);

            Cell tempCellAus = new Cell();
            tempCellAus.setStyle("border: 1px solid #000");
            Label tempLabelAus = new Label(String.valueOf(lpab62Manager.getAuslaufForDate(String.valueOf(item),"LL","C","323357","981")));
            tempCellAus.appendChild(tempLabelAus);
            tempRow.appendChild(tempCellAus);

            Cell tempCellBdos = new Cell();
            tempCellBdos.setStyle("border: 1px solid #000");
            Label tempLabelBdos = new Label(String.valueOf(lpab62Manager.getBdoseForDate(String.valueOf(item),"LL","C","323357","981")));
            tempCellBdos.appendChild(tempLabelBdos);
            tempRow.appendChild(tempCellBdos);

            Cell tempCellFoam = new Cell();
            tempCellFoam.setStyle("border: 1px solid #000");
            Label tempLabelFoam = new Label(String.valueOf(lpab62Manager.getFoamForDate(String.valueOf(item),"LL","C","323357","981")));
            tempCellFoam.appendChild(tempLabelFoam);
            tempRow.appendChild(tempCellFoam);

            Cell tempCellelektr = new Cell();
            tempCellelektr.setStyle("border: 1px solid #000");
            Label tempLabelelektr = new Label(String.valueOf(lpab62Manager.getElektroForDate(String.valueOf(item),"LL","C","323357","981")));
            tempCellelektr.appendChild(tempLabelelektr);
            tempRow.appendChild(tempCellelektr);

            Cell tempCellPR = new Cell();
            tempCellPR.setStyle("border: 1px solid #000");
            Label tempLabelPR = new Label(String.valueOf(lpab62Manager.getPRForDate(String.valueOf(item),"LL","C","323357","981")));
            tempCellPR.appendChild(tempLabelPR);
            tempRow.appendChild(tempCellPR);

            Cell tempCellEsd = new Cell();
            tempCellEsd.setStyle("border: 1px solid #000");
            Label tempLabelEsd = new Label(String.valueOf(lpab62Manager.getEsdScrewForDate(String.valueOf(item),"LL","C","323357","981")));
            tempCellEsd.appendChild(tempLabelEsd);
            tempRow.appendChild(tempCellEsd);

            Cell tempCellPhoto = new Cell();
            tempCellPhoto.setStyle("border: 1px solid #000");
            Label tempLabelPhoto = new Label(String.valueOf(lpab62Manager.getPhotoWaForDate(String.valueOf(item),"LL","C","323357","981")));
            tempCellPhoto.appendChild(tempLabelPhoto);
            tempRow.appendChild(tempCellPhoto);


            rows2.appendChild(tempRow);
        }
        int pocet3o = 0;
        for (Integer item : osnabruckDatesList){
            Row tempRow = new Row();
            tempRow.setZclass("Non_Existant_Class");
            tempRow.setHeight("18px");
            if(pocet3o==0)
            {
                pocet3o++;
                Cell tempCell2  = new Cell();
                tempCell2.setStyle("border: 1px solid #000");
                Label tempLabel2 = new Label("C991RL");
                tempCell2.setRowspan(numberOfDatesO);
                tempCell2.appendChild(tempLabel2);
                //tempRow.appendChild(tempCell);
                tempRow.appendChild(tempCell2);}
            Cell tempCell3  = new Cell();
            tempCell3.setStyle("border: 1px solid #000");
            Label tempLabel3 = new Label(String.valueOf(item));
            tempCell3.appendChild(tempLabel3);
            tempRow.appendChild(tempCell3);
            Cell tempCellAll = new Cell();
            tempCellAll.setStyle("border: 1px solid #000");
            Label tempLabelAll = new Label(String.valueOf(lpab62Manager.getAllHarnessesForDate(String.valueOf(item),"RL","C","323357","981")));
            tempCellAll.appendChild(tempLabelAll);
            tempRow.appendChild(tempCellAll);

            Cell tempCellEin = new Cell();
            tempCellEin.setStyle("border: 1px solid #000");
            Label tempLabelEin = new Label(String.valueOf(lpab62Manager.getEinlaufForDate(String.valueOf(item),"RL","C","323357","981")));
            tempCellEin.appendChild(tempLabelEin);
            tempRow.appendChild(tempCellEin);

            Cell tempCellAus = new Cell();
            tempCellAus.setStyle("border: 1px solid #000");
            Label tempLabelAus = new Label(String.valueOf(lpab62Manager.getAuslaufForDate(String.valueOf(item),"RL","C","323357","981")));
            tempCellAus.appendChild(tempLabelAus);
            tempRow.appendChild(tempCellAus);

            Cell tempCellBdos = new Cell();
            tempCellBdos.setStyle("border: 1px solid #000");
            Label tempLabelBdos = new Label(String.valueOf(lpab62Manager.getBdoseForDate(String.valueOf(item),"RL","C","323357","981")));
            tempCellBdos.appendChild(tempLabelBdos);
            tempRow.appendChild(tempCellBdos);

            Cell tempCellFoam = new Cell();
            tempCellFoam.setStyle("border: 1px solid #000");
            Label tempLabelFoam = new Label(String.valueOf(lpab62Manager.getFoamForDate(String.valueOf(item),"RL","C","323357","981")));
            tempCellFoam.appendChild(tempLabelFoam);
            tempRow.appendChild(tempCellFoam);

            Cell tempCellelektr = new Cell();
            tempCellelektr.setStyle("border: 1px solid #000");
            Label tempLabelelektr = new Label(String.valueOf(lpab62Manager.getElektroForDate(String.valueOf(item),"RL","C","323357","981")));
            tempCellelektr.appendChild(tempLabelelektr);
            tempRow.appendChild(tempCellelektr);

            Cell tempCellPR = new Cell();
            tempCellPR.setStyle("border: 1px solid #000");
            Label tempLabelPR = new Label(String.valueOf(lpab62Manager.getPRForDate(String.valueOf(item),"RL","C","323357","981")));
            tempCellPR.appendChild(tempLabelPR);
            tempRow.appendChild(tempCellPR);

            Cell tempCellEsd = new Cell();
            tempCellEsd.setStyle("border: 1px solid #000");
            Label tempLabelEsd = new Label(String.valueOf(lpab62Manager.getEsdScrewForDate(String.valueOf(item),"RL","C","323357","981")));
            tempCellEsd.appendChild(tempLabelEsd);
            tempRow.appendChild(tempCellEsd);

            Cell tempCellPhoto = new Cell();
            tempCellPhoto.setStyle("border: 1px solid #000");
            Label tempLabelPhoto = new Label(String.valueOf(lpab62Manager.getPhotoWaForDate(String.valueOf(item),"RL","C","323357","981")));
            tempCellPhoto.appendChild(tempLabelPhoto);
            tempRow.appendChild(tempCellPhoto);


            rows2.appendChild(tempRow);
        }

    }
}
