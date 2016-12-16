package com.leoni.viewModel;

import com.leoni.data.manager.ClipPositionManager;
import com.leoni.data.models.ClipPosition;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.1.2015
 * Time: 9:21
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ClipPositionVM {

    @WireVariable
    private ClipPositionManager clipPositionManager;

    private List<ClipPosition> clipPositionList = new ArrayList<ClipPosition>();
    private ClipPosition selectedClipPosition;
    private ClipPosition clipPositionToDelete;
    private String idClip;
    private String xAxis;
    private String yAxis;
    private String boardTyp;
    private String idClipSearch = "";
    private String boardTypSearch = "";

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        clipPositionList=clipPositionManager.getAll();
    }

    @Command
    @NotifyChange ({"clipPositionList"})
    public void search()
    {    if (idClipSearch.equals("")&&boardTypSearch.equals("")){
        clipPositionList=clipPositionManager.getAll();}
    else clipPositionList = clipPositionManager.findBy(idClipSearch,boardTypSearch);

    }

    @Command
    @NotifyChange ({"clipPositionList","idClipSearch","boardTypSearch"})
    public void cancelSearch()
    {   clipPositionList = clipPositionManager.getAll();
        idClipSearch = "";
        boardTypSearch = "";

    }

    @Command
    @NotifyChange({"clipPositionList"})
    public void deleteClipPosition(@BindingParam("clipPosition") ClipPosition myClipPosition)
    {
        clipPositionToDelete=myClipPosition;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            clipPositionManager.delete(clipPositionToDelete);
                            clipPositionList = clipPositionManager.getAll();
                            BindUtils.postNotifyChange(null, null, ClipPositionVM.this, "clipPositionList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange({"selectedClipPosition"})
    public void saveClipPosition(@ContextParam(ContextType.VIEW) Component comp,
                               @BindingParam("selectedData")ClipPosition editedClipPosition) {
        clipPositionManager.saveEditedClipPosition(editedClipPosition);
        clipPositionList=clipPositionManager.getAll();
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"clipPositionList","idClip","boardTyp","xAxis","yAxis"})
    public void generateNewClipPosition() {
        if(idClip!=null&&boardTyp!=null&&xAxis!=null){
            clipPositionManager.addNewClipPosition(idClip,boardTyp,xAxis,yAxis);
            clipPositionList = clipPositionManager.getAll();
            idClip = null;
            boardTyp = null;
            xAxis = null;
            yAxis = null;
            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
        }
        else{Messagebox.show("Nezadali ste id switch, board id alebo board typ!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }



    public List<ClipPosition> getClipPositionList() {
        return clipPositionList;
    }

    public void setClipPositionList(List<ClipPosition> clipPositionList) {
        this.clipPositionList = clipPositionList;
    }

    public ClipPosition getSelectedClipPosition() {
        return selectedClipPosition;
    }

    public void setSelectedClipPosition(ClipPosition selectedClipPosition) {
        this.selectedClipPosition = selectedClipPosition;
    }

    public String getIdClip() {
        return idClip;
    }

    public void setIdClip(String idClip) {
        this.idClip = idClip;
    }

    public String getxAxis() {
        return xAxis;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public String getyAxis() {
        return yAxis;
    }

    public void setyAxis(String yAxis) {
        this.yAxis = yAxis;
    }

    public String getBoardTyp() {
        return boardTyp;
    }

    public void setBoardTyp(String boardTyp) {
        this.boardTyp = boardTyp;
    }

    public String getIdClipSearch() {
        return idClipSearch;
    }

    public void setIdClipSearch(String idClipSearch) {
        this.idClipSearch = idClipSearch;
    }

    public String getBoardTypSearch() {
        return boardTypSearch;
    }

    public void setBoardTypSearch(String boardTypSearch) {
        this.boardTypSearch = boardTypSearch;
    }
}
