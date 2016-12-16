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
    //private String xAxis;
    private String yAxis;
    //private String boardTyp;
    private String idClipSearch = "";
    private String boardTypSearch = "";
    private List<String> boardTypeList;
    private List<String> positionList;
    private String selectedBoardType;
    private String selectedBoardTypeSearch;
    private String selectedPosition;
    private String selectedPositionSearch;

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        boardTypeList = clipPositionManager.getDistinctBoardType();
        positionList = clipPositionManager.getDistinctPosition();
        clipPositionList=clipPositionManager.getAll();
    }

    @Command
    @NotifyChange ({"clipPositionList"})
    public void search()
    {    if (idClipSearch.equals("")&&selectedBoardTypeSearch==null&&selectedPosition==null){
        clipPositionList=clipPositionManager.getAll();}
    else clipPositionList = clipPositionManager.findBy(idClipSearch,selectedBoardTypeSearch,selectedPositionSearch);

    }

    @Command
    @NotifyChange ({"clipPositionList","idClipSearch","selectedBoardTypeSearch","selectedPositionSearch"})
    public void cancelSearch()
    {   clipPositionList = clipPositionManager.getAll();
        idClipSearch = "";
        selectedBoardTypeSearch = null;
        selectedPositionSearch = null;
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
        selectedClipPosition = null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
    }

    @Command
    @NotifyChange({"clipPositionList","idClip","selectedBoardType","selectedPosition","yAxis"})
    public void generateNewClipPosition() {
        if(idClip!=null&&selectedBoardType!=null&&selectedPosition!=null){
            clipPositionManager.addNewClipPosition(idClip,selectedBoardType,selectedPosition,yAxis);
            clipPositionList = clipPositionManager.getAll();
            idClip = null;
            selectedBoardType = null;
            selectedPosition = null;
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

    public String getSelectedPositionSearch() {
        return selectedPositionSearch;
    }

    public void setSelectedPositionSearch(String selectedPositionSearch) {
        this.selectedPositionSearch = selectedPositionSearch;
    }

    public String getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(String selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public String getSelectedBoardTypeSearch() {
        return selectedBoardTypeSearch;
    }

    public void setSelectedBoardTypeSearch(String selectedBoardTypeSearch) {
        this.selectedBoardTypeSearch = selectedBoardTypeSearch;
    }

    public String getSelectedBoardType() {
        return selectedBoardType;
    }

    public void setSelectedBoardType(String selectedBoardType) {
        this.selectedBoardType = selectedBoardType;
    }

    public List<String> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<String> positionList) {
        this.positionList = positionList;
    }

    public List<String> getBoardTypeList() {
        return boardTypeList;
    }

    public void setBoardTypeList(List<String> boardTypeList) {
        this.boardTypeList = boardTypeList;
    }

    public String getyAxis() {
        return yAxis;
    }

    public void setyAxis(String yAxis) {
        this.yAxis = yAxis;
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
