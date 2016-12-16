package com.leoni.viewModel;

import com.leoni.data.manager.BandManager;
import com.leoni.data.models.Harness;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by hrmi1005 on 21. 4. 2016.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BandVisualVM {
    @Wire
    private Cell cell21;

    @Wire
    private Cell cell31;

    @Wire
    private Cell cell41;

    @Wire
    private Cell cell51;

    @Wire
    private Cell cell61;

    @Wire
    private Cell cell71;

    @Wire
    private Cell cell81;

    @Wire
    private Cell cell91;

    @Wire
    private Cell cell101;

    @Wire
    private Cell cell111;

    @Wire
    private Cell cell12;

    @Wire
    private Cell cell122;

    @Wire
    private Cell cell23;

    @Wire
    private Cell cell33;

    @Wire
    private Cell cell43;

    @Wire
    private Cell cell53;

    @Wire
    private Cell cell63;

    @Wire
    private Cell cell73;

    @Wire
    private Cell cell83;

    @Wire
    private Cell cell93;

    @Wire
    private Cell cell103;

    @Wire
    private Cell cell113;

    @WireVariable
    private BandManager bandManager;

    private String bandName;

    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view,@ExecutionArgParam("bandName") String bandName)
    {
        Selectors.wireComponents(view, this, false);
        this.bandName = bandName;
        List<Harness> harnessList = bandManager.findByBandNameOnBand(bandName);
        Collections.sort(harnessList);
        
        for (int i = harnessList.size() - 1; i >= 0; i--){
            if (i == harnessList.size() - 1) {
                cell21.appendChild(createVbox(harnessList.get(i)));
                }
            if (i == harnessList.size() - 2) {
                cell31.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 3) {

                cell41.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 4) {
                cell51.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 5) {
                cell61.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 6) {
                cell71.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 7) {
                cell81.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 8) {
                cell91.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 9) {
                cell101.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 10) {
                cell111.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 11) {
                cell122.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 12) {
                cell113.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 13) {
                cell103.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 14) {
                cell93.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 15) {
                cell83.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 16) {
                cell73.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 17) {
                cell63.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 18) {
                cell53.appendChild(createVbox(harnessList.get(i)));
               
            }
            if (i == harnessList.size() - 19) {
                cell43.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 20) {
                cell33.appendChild(createVbox(harnessList.get(i)));
               
            }
            if (i == harnessList.size() - 21) {
                cell23.appendChild(createVbox(harnessList.get(i)));
                
            }
            if (i == harnessList.size() - 22) {
                cell12.appendChild(createVbox(harnessList.get(i)));
                
            }


        }
    }

    private Grid createVbox(Harness harness){
        Grid grid = new Grid();
        Rows rows = new Rows();
        Row row0 = new Row();
        Row row1 = new Row();
        Row row2 = new Row();
        Row row3 = new Row();
        Label label = new Label(harness.getProd_nr());
        if (harness.getLock()) {label.setStyle("font-weight:bold; color: red");}
        else {label.setStyle("font-weight:bold");}
        Label label2 = new Label("A:"+harness.getSide_a_step());
        Label label3 = new Label("B:"+harness.getSide_b_step());
        Label label4 = new Label(harness.getBretttype().trim());
        Label label5 = new Label(harness.getBrettid().trim());
        Button btn1 = new Button();
        btn1.setImage("images/container.png");
        btn1.setMold("trendy");
        Cell cell = new Cell();
        cell.setColspan(2);
        cell.appendChild(label);
        Cell cell2 = new Cell();
        cell2.setColspan(2);
        cell2.appendChild(btn1);
        row0.appendChild(cell);
        row1.appendChild(label2);
        row1.appendChild(label3);
        row2.appendChild(label4);
        row2.appendChild(label5);
        row3.appendChild(cell2);
        rows.appendChild(row0);
        rows.appendChild(row1);
        rows.appendChild(row2);
        rows.appendChild(row3);
        grid.appendChild(rows);
        return grid;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }
}
