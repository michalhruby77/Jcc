package com.leoni.viewModel;

import com.leoni.data.manager.BandTypeManager;
import com.leoni.data.manager.BoardTypeManager;
import com.leoni.data.manager.StepSequenceManager;
import com.leoni.data.models.BandType;
import com.leoni.data.models.BoardType;
import com.leoni.data.models.WorkplaceStepsSequence;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 27.8.2015
 * Time: 7:14
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class StepSequenceVM {

    @WireVariable
    private BandTypeManager bandTypeManager;

    @WireVariable
    private BoardTypeManager boardTypeManager;

    @WireVariable
    private StepSequenceManager stepSequenceManager;

    @Wire
    Window window;

    List<BandType> bandTypeList;
    List<BoardType> boardTypeList;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
         bandTypeList = bandTypeManager.getAll();
         for(BandType bandType : bandTypeList){
             List<WorkplaceStepsSequence> workplaceStepsSequenceListBand = stepSequenceManager.getBandTypeStepSequence(bandType);
             Collections.sort(workplaceStepsSequenceListBand);
             Hlayout hlayout = new Hlayout();
             Grid gridA = new Grid();
             gridA.setWidth("500px");
             if (bandType.getBoardTypeList().size()>2){gridA.setWidth("700px");}
             Auxhead auxheadA = new Auxhead();
             Auxheader auxheaderA = new Auxheader(bandType.getName()+" A");
             auxheadA.appendChild(auxheaderA);
             gridA.appendChild(auxheadA);
             Columns columns = new Columns();
             Column col1 = new Column("Poradie");
             Column col2 = new Column("Krok");
             columns.appendChild(col1);
             columns.appendChild(col2);
             for (BoardType boardType : bandType.getBoardTypeList()){
                 Column col = new Column(boardType.getName().trim());
                 col.setWidth("100px");
                 columns.appendChild(col);
             }
             gridA.appendChild(auxheadA);
             gridA.appendChild(columns);
             Rows rowsA = new Rows();
             Grid gridB = new Grid();
             gridB.setWidth("500px");
             if (bandType.getBoardTypeList().size()>2){gridB.setWidth("700px");}
             Auxhead auxheadB = new Auxhead();
             Auxheader auxheaderB = new Auxheader(bandType.getName()+" B");
             auxheadB.appendChild(auxheaderB);
             gridB.appendChild(auxheadB);
             Columns columnsB = new Columns();
             Column col1B = new Column("Poradie");
             Column col2B = new Column("Krok");
             columnsB.appendChild(col1B);
             columnsB.appendChild(col2B);
             for (BoardType boardType : bandType.getBoardTypeList()){
                 Column col = new Column(boardType.getName().trim());
                 col.setWidth("100px");
                 columnsB.appendChild(col);
             }
             gridB.appendChild(columnsB);
             Rows rowsB = new Rows();
             /*int counterA = 0;
             int counterB = 0;*/

             for (WorkplaceStepsSequence wpSeqBand : workplaceStepsSequenceListBand){
                 if (wpSeqBand.getWorkplace().getSide().equals("A")){
                    //counterA++;
                    Row rowA = new Row();
                    Label label1 = new Label();
                    label1.setValue(/*String.valueOf(counterA)*/String.valueOf(wpSeqBand.getSequence()));
                    Label label2 = new Label();
                    label2.setValue(wpSeqBand.getWorkplace().getStep()+ wpSeqBand.getWorkplace().getSide()+ "  " +
                            wpSeqBand.getWorkplace().getName().trim());

                    rowA.appendChild(label1);
                    rowA.appendChild(label2);
                    for (BoardType boardType : bandType.getBoardTypeList()){
                        Image img = new Image("images/delete.png");
                         //Cell cell = new Cell();
                         //cell.setStyle("background-color: #FF7E7E");
                         rowA.appendChild(img);
                         List<WorkplaceStepsSequence> wpStepsListBoard = boardType.getWorkplaceStepsSequenceList();
                         for (WorkplaceStepsSequence wpStepsBoard : wpStepsListBoard){
                             if (wpStepsBoard.getWorkplace().getStep().equals(wpSeqBand.getWorkplace().getStep())&&
                                 wpStepsBoard.getWorkplace().getSide().trim().equals(wpSeqBand.getWorkplace().getSide().trim())){
                                 img.setSrc("images/yes.png");
                                 //cell.setStyle("background-color: #99FF66");
                             }
                         }
                    }
                    rowsA.appendChild(rowA);
                 }
                 if (wpSeqBand.getWorkplace().getSide().equals("B")){
                     //counterB++;
                     Row rowB = new Row();
                     Label label1 = new Label();
                     label1.setValue(/*String.valueOf(counterB)*/String.valueOf(wpSeqBand.getSequence()));
                     Label label2 = new Label();
                     label2.setValue(wpSeqBand.getWorkplace().getStep()+ wpSeqBand.getWorkplace().getSide() + "  " +
                             wpSeqBand.getWorkplace().getName().trim());
                     rowB.appendChild(label1);
                     rowB.appendChild(label2);
                     for (BoardType boardType : bandType.getBoardTypeList()){
                         Image img = new Image("images/delete.png");
                         //Cell cell = new Cell();
                         //cell.setStyle("background-color: #FF7E7E");
                         rowB.appendChild(img);
                         List<WorkplaceStepsSequence> wpStepsListBoard = boardType.getWorkplaceStepsSequenceList();
                         for (WorkplaceStepsSequence wpStepsBoard : wpStepsListBoard){
                             if (wpStepsBoard.getWorkplace().getStep().equals(wpSeqBand.getWorkplace().getStep())&&
                                     wpStepsBoard.getWorkplace().getSide().trim().equals(wpSeqBand.getWorkplace().getSide().trim())){
                                 img.setSrc("images/yes.png");
                                 //cell.setStyle("background-color: #99FF66");
                             }
                         }
                     }
                     rowsB.appendChild(rowB);
                 }
             }
             Row rowA = new Row();
             Row rowB = new Row();
             Label lab1 = new Label();
             Label lab2 = new Label();
             Label lab3 = new Label();
             Label lab4 = new Label();
             rowA.appendChild(lab1);
             rowA.appendChild(lab2);
             rowB.appendChild(lab3);
             rowB.appendChild(lab4);

             for (BoardType boardType : bandType.getBoardTypeList()){
                 Button btnA = new Button();
                 Button btnB = new Button();
                 rowA.appendChild(btnA);
                 rowB.appendChild(btnB);
             }
             rowsA.appendChild(rowA);
             rowsB.appendChild(rowB);
             gridA.appendChild(rowsA);
             gridB.appendChild(rowsB);
             hlayout.appendChild(gridA);
             hlayout.appendChild(gridB);

             Label bandName = new Label(bandType.getName().trim());
             bandName.setStyle("font-weight:bold; font-size:25pt");
             Separator separator = new Separator();
             window.appendChild(bandName);
             window.appendChild(hlayout);
             window.appendChild(separator);
         }

    }
}
