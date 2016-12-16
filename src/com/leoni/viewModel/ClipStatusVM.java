package com.leoni.viewModel;

import com.leoni.data.manager.BandManager;
import com.leoni.data.manager.ClipStatusManager;
import com.leoni.data.models.ClipStatus;
import com.leoni.data.models.Harness;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;


import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.1.2015
 * Time: 9:20
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ClipStatusVM {

    @WireVariable
    private ClipStatusManager clipStatusManager;

    @WireVariable
    private BandManager bandManager;

    @Wire
    Auxheader auxBoardType;

    @Wire
    Auxheader auxBoardId;


    private List<ClipStatus> clipStatusList = new ArrayList<ClipStatus>();
    private ClipStatus selectedClipStatus;
    private ClipStatus clipStatusToDelete;
    private String idSwitch;
    private String idClip;
    private Boolean status = true;
    private String boardTyp;
    private String boardId;
    private String idSwitchSearch = "";
    private String idClipSearch = "";
    private String boardTypSearch = "";
    private String boardIdSearch = "";
    private String selectedBoardType;
    private String selectedBoardId;
    private List<Button> buttonBoardTypeList;
    private List<Button> buttonBoardIdList;
    private String bandName;

    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("bandName") String bandName)
    {
        Selectors.wireComponents(view, this, false);
        //clipStatusList=clipStatusManager.getAll();
        this.bandName = bandName;
        List<Harness> harnessList = bandManager.findByBandName(bandName);
        buttonBoardIdList = new ArrayList<>();
        buttonBoardTypeList = new ArrayList<>();
        SortedSet<String> boardTypeSet = new TreeSet<>();
        SortedSet<String> boardIdSet = new TreeSet<>();

        for (Harness harness : harnessList){
            boardTypeSet.add(harness.getBretttype().trim());
            boardIdSet.add(harness.getBrettid().trim());
        }

        for (String boardType : boardTypeSet){
            Button button = new Button(boardType);
            //button.setWidth("80px");
            //button.setHeight("40px");
            buttonBoardTypeList.add(button);
            button.setMold("trendy");
            button.addEventListener("onClick",new EventListener() {
            public void onEvent(Event evt) {
                boardTypSearch = boardType.trim();
                refreshButtonsBoardType();
                button.setStyle("color:green; border: solid #50b840 2px");
                search();
                BindUtils.postNotifyChange(null, null, ClipStatusVM.this, "clipStatusList");
            }});
            auxBoardType.appendChild(button);
        }

        for (String boardId : boardIdSet){
            Button button = new Button(boardId);
            //button.setWidth("80px");
            //button.setHeight("40px");
            button.setMold("trendy");
            buttonBoardIdList.add(button);
            button.addEventListener("onClick",new EventListener() {
                public void onEvent(Event evt) {
                    boardIdSearch = boardId.trim();
                    refreshButtonsBoardId();
                    button.setStyle("color:green; border: solid #50b840 2px");
                    search();
                    BindUtils.postNotifyChange(null, null, ClipStatusVM.this, "clipStatusList");
                }});
            auxBoardId.appendChild(button);
        }
    }

    @Command
    @NotifyChange ({"clipStatusList"})
    public void search()
    {    if (idSwitchSearch.equals("")&&idClipSearch.equals("")&&boardTypSearch.equals("")&&boardIdSearch.equals("")){
        clipStatusList=clipStatusManager.getAll();}
    else clipStatusList = clipStatusManager.findBy(idSwitchSearch,idClipSearch,boardTypSearch,boardIdSearch);

    }

    @Command
    @NotifyChange ({"clipStatusList","idSwitchSearch","idClipSearch","boardTypSearch","boardIdSearch"})
    public void cancelSearch()
    {   //clipStatusList = clipStatusManager.getAll();
        idSwitchSearch = "";
        idClipSearch = "";
        boardTypSearch = "";
        boardIdSearch = "";
        refreshButtonsBoardId();
        refreshButtonsBoardType();
    }

    @Command
    @NotifyChange({"clipStatusList"})
    public void deleteClipStatus(@BindingParam("clipStatus") ClipStatus myClipStatus)
    {
        clipStatusToDelete=myClipStatus;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            clipStatusManager.delete(clipStatusToDelete);
                            clipStatusList = clipStatusManager.getAll();
                            BindUtils.postNotifyChange(null, null, ClipStatusVM.this, "clipStatusList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange({"selectedClipStatus"})
    public void saveClipStatus(@ContextParam(ContextType.VIEW) Component comp,
                          @BindingParam("selectedData")ClipStatus editedClipStatus) {
        //if(clipStatusManager.findByIdSwitch(editedClipStatus.getIdSwitch().trim()).isEmpty()){
        clipStatusManager.saveEditedClipStatus(editedClipStatus);
        clipStatusList=clipStatusManager.getAll();
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null); //}
        //else{Messagebox.show("Zadana hodnota id switch uz existuje!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    @NotifyChange({"clipStatusList","idSwitch","idClip","status","boardId","boardTyp"})
    public void generateNewClipStatus() {
      if(idSwitch!=null&&boardId!=null&&boardTyp!=null){
       if(clipStatusManager.findByIdSwitch(idSwitch.trim()).isEmpty()){
       clipStatusManager.addNewClipStatus(idSwitch,idClip,status,boardTyp,boardId);
       clipStatusList = clipStatusManager.getAll();
       idSwitch = null;
       idClip = null;
       status = true;
       boardId = null;
       boardTyp = null;
       Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);}
          else{Messagebox.show("Zadana hodnota id switch uz existuje!", "Error", Messagebox.OK, Messagebox.ERROR);}
      }
      else{Messagebox.show("Nezadali ste id switch, board id alebo board typ!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    private void refreshButtonsBoardType(){
        for (Button button : buttonBoardTypeList){
            button.setStyle("color:black");
        }
    }

    private void refreshButtonsBoardId(){
        for (Button button : buttonBoardIdList){
            button.setStyle("color:black");
        }

    }

    public List<ClipStatus> getClipStatusList() {
        return clipStatusList;
    }

    public void setClipStatusList(List<ClipStatus> clipStatusList) {
        this.clipStatusList = clipStatusList;
    }

    public ClipStatus getSelectedClipStatus() {
        return selectedClipStatus;
    }

    public void setSelectedClipStatus(ClipStatus selectedClipStatus) {
        this.selectedClipStatus = selectedClipStatus;
    }

    public String getIdSwitch() {
        return idSwitch;
    }

    public void setIdSwitch(String idSwitch) {
        this.idSwitch = idSwitch;
    }

    public String getIdClip() {
        return idClip;
    }

    public void setIdClip(String idClip) {
        this.idClip = idClip;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBoardTyp() {
        return boardTyp;
    }

    public void setBoardTyp(String boardTyp) {
        this.boardTyp = boardTyp;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getIdSwitchSearch() {
        return idSwitchSearch;
    }

    public void setIdSwitchSearch(String idSwitchSearch) {
        this.idSwitchSearch = idSwitchSearch;
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

    public String getBoardIdSearch() {
        return boardIdSearch;
    }

    public void setBoardIdSearch(String boardIdSearch) {
        this.boardIdSearch = boardIdSearch;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }
}
