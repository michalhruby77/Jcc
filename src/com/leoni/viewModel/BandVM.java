package com.leoni.viewModel;

import com.leoni.data.manager.*;
import com.leoni.data.manager.dab.PrnrManager;
import com.leoni.data.models.*;
import com.leoni.data.models.dab.Prnr;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 21.10.2015
 * Time: 8:24
 * To change this template use File | Settings | File Templates.
 */
public class BandVM {

    @WireVariable
    private BandManager bandManager;

    @WireVariable
    private PrnrManager prnrManager;

    @WireVariable
    private Lpab62Manager lpab62Manager;

    @WireVariable
    private BandTypeManager bandTypeManager;

    @WireVariable
    private BoardTypeManager boardTypeManager;

    @WireVariable
    private WorkplaceManager workplaceManager;

    @WireVariable
    private UsersManager usersManager;


    @Wire
    Listbox listbox;

    private List<Harness> harnessList;
    private String bandName;
    private Harness thisHarness;
    private Harness selectedHarness;
    private Users users;
    private String boardType;
    private String boardId;


    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("bandName") String bandName)
    {
        Selectors.wireComponents(view, this, false);
        BandRenderer br = new BandRenderer();
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        users = usersManager.getUser(user);
        br.init(bandTypeManager.getAll(), workplaceManager.getAll(), users);
        listbox.setItemRenderer(br);
        this.bandName = bandName;
        harnessList=bandManager.findByBandName(bandName);
        Collections.sort(harnessList);

    }

    @GlobalCommand
    @NotifyChange({"harnessList"})
    @Command
    public void refreshBand(){
        harnessList = bandManager.findByBandName(bandName);
        Collections.sort(harnessList);
    }

    @GlobalCommand
    public void moveOutFromBand(@BindingParam("harness") Harness myHarness){
        thisHarness = myHarness;
        showModal2(myHarness);
    }

    @GlobalCommand
    public void backToContainer(@BindingParam("harness") Harness myHarness){
        thisHarness = myHarness;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            System.out.println(myHarness.getProd_nr());
                            sendBackToContainer(myHarness);
                            harnessList = bandManager.findByBandName(bandName);
                            Collections.sort(harnessList);
                            BindUtils.postNotifyChange(null, null, BandVM.this, "harnessList");
                        }
                    }
                }
        );
    }

    @GlobalCommand
    @NotifyChange({"harnessList"})
    public void deleteFromBand(@BindingParam("harness") Harness myHarness){
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            bandManager.delete(myHarness);
                            harnessList = bandManager.findByBandName(bandName);
                            Collections.sort(harnessList);
                            BindUtils.postNotifyChange(null, null, BandVM.this, "harnessList");
                        }
                    }
                }
        );
    }


    @GlobalCommand
    public void moveToOtherBoard(@BindingParam("harness") Harness myHarness){
        thisHarness = myHarness;
        showModal3(myHarness);
    }

    @Command
    @SmartNotifyChange({"harnessList","boardType","boardId","bandName"})
    public void create(){
        if (boardId != null && boardId.length() == 3 && boardType != null && !boardType.trim().equals("")){
        Harness harness = new Harness();
        harness.setBretttype(boardType.trim());
        harness.setBrettid(boardId.trim());
        harness.setLock(false);
        harness.setSide_b_step(0);
        harness.setSide_a_step(0);
        harness.setBand_name(bandName);
        bandManager.create(harness);
        harnessList = bandManager.findByBandName(bandName);
        Collections.sort(harnessList);
        boardType = null;
        boardId = null;
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
        }
        else{Messagebox.show("Vyplnte vsetky udaje!", "Chyba!", Messagebox.OK, Messagebox.ERROR);}
    }


    private void sendBackToContainer(Harness harness){
        List<Prnr> prnrList = prnrManager.getPrnr(harness.getProd_nr(),harness.getKabelsatz_kz());
        if (prnrList.size() == 1){
            Prnr prnr = prnrList.get(0);
            prnr.setStatusPrint(null);
            prnr.setStatusBand(null);
            prnr.setBrettId(null);
            prnr.setLock("X");
            System.out.println(prnr.getProdNr());
            List<Lpab62> lpab62List = lpab62Manager.findByProdnrAndKabelsatz(harness.getProd_nr(), harness.getKabelsatz_kz());
            if (lpab62List.size() == 1){
               Lpab62 lpab62 = lpab62List.get(0);
               lpab62.setStaBandEinlauf(null);
               System.out.println(lpab62.getProdNr());
               thisHarness.setBusytime(null);
               thisHarness.setProd_nr(null);
               thisHarness.setKabelsatz_kz(null);
               thisHarness.setModulen(null);
               thisHarness.setSide_a_step(0);
               thisHarness.setSide_b_step(0);
               prnrManager.saveOrUpdate(prnr);
               lpab62Manager.saveOrUpdate(lpab62);
               bandManager.saveOrUpdate(thisHarness);
               showModal(prnr.getProdNr(),prnr.getKabelsatzKz());
            }
            else{Messagebox.show("Kablovka sa nenasla v lpab62!", "Error", Messagebox.OK, Messagebox.ERROR);}
        }
        else{Messagebox.show("Kablovka sa nenasla v prnr!", "Error", Messagebox.OK, Messagebox.ERROR);}
    }

    @Command
    public void saveHarness(String boardType, String boardId, String time, Boolean lock, String prodNr, String ksKz,
                            Integer sideA, Integer sideB, String bandName) {
        if (prodNr == null || prodNr.trim().equals("") ||
                (prodNr != null && !prodNr.trim().equals("") && ksKz != null && !ksKz.trim().equals("") && !lpab62Manager.findByProdnrAndKabelsatz(prodNr, ksKz).isEmpty())) {
            selectedHarness.setBretttype(boardType);
            selectedHarness.setBrettid(boardId);
            Date date = null;
            if (time != null && !time.trim().equals("")){
                SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm");

                try {
                    date = dt.parse(time);
                    } catch (ParseException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
             }
            selectedHarness.setBusytime(date);
            selectedHarness.setLock(lock);
            selectedHarness.setProd_nr(prodNr);
            selectedHarness.setKabelsatz_kz(ksKz);
            selectedHarness.setSide_a_step(sideA);
            selectedHarness.setSide_b_step(sideB);
            selectedHarness.setBand_name(bandName);
            bandManager.saveOrUpdate(selectedHarness);
            harnessList=bandManager.findByBandName(bandName);
            Collections.sort(harnessList);
            selectedHarness = null;
            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null); }
        else {Messagebox.show("Kablovka sa nenasla v lpab62!", "Error", Messagebox.OK, Messagebox.ERROR);}

    }

    @Command
    public void showEdit(@BindingParam("editRow") Row editRow){
        Components.removeAllChildren(editRow);
        BoardType boardType = boardTypeManager.find(selectedHarness.getBretttype()).get(0);
        List<WorkplaceStepsSequence> workplaceStepsSequenceList = boardType.getWorkplaceStepsSequenceList();
        Collections.sort(workplaceStepsSequenceList);
        List<Workplace> workplaceListA = new ArrayList<>();
        List<Workplace> workplaceListB = new ArrayList<>();
        for (WorkplaceStepsSequence wss : workplaceStepsSequenceList){
            if (wss.getWorkplace().getSide().trim().equals("A")){
                workplaceListA.add(wss.getWorkplace());
            }
            if (wss.getWorkplace().getSide().trim().equals("B")){
                workplaceListB.add(wss.getWorkplace());
            }
        }
        Textbox boardTypeTb = new Textbox(selectedHarness.getBretttype());
        Textbox boardIdTb = new Textbox(selectedHarness.getBrettid());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Textbox timeTb;
        if(selectedHarness.getBusytime() == null) timeTb = new Textbox();
        else timeTb = new Textbox(sdf.format(selectedHarness.getBusytime()));
        Checkbox activeCheck = new Checkbox();
        activeCheck.setChecked(!selectedHarness.getLock());
        Textbox prodNrTb = new Textbox(selectedHarness.getProd_nr());
        Textbox ksKzTb = new Textbox(selectedHarness.getKabelsatz_kz());
        ksKzTb.setWidth("90%");
        Combobox cbA = new Combobox();
        cbA.setReadonly(true);
        cbA.setItemRenderer(new WorkplaceComboboxRenderer());
        ListModelList lmla =  new ListModelList<Workplace>(workplaceListA);
        cbA.setModel(lmla);
        for (Workplace wp : workplaceListA){
            if (wp.getSide().trim().equals("A") && wp.getStep().equals(selectedHarness.getSide_a_step()))
            {List<Workplace> selectedA = new ArrayList<>();
             selectedA.add(wp);
             lmla.setSelection(selectedA);}
        }
        Combobox cbB = new Combobox();
        cbB.setReadonly(true);
        cbB.setItemRenderer(new WorkplaceComboboxRenderer());
        ListModelList lmlb =  new ListModelList<Workplace>(workplaceListB);
        cbB.setModel(lmlb);
        for (Workplace wp : workplaceListB){
            if (wp.getSide().trim().equals("B") && wp.getStep().equals(selectedHarness.getSide_b_step()))
            {List<Workplace> selectedB = new ArrayList<>();
                selectedB.add(wp);
                lmlb.setSelection(selectedB);}
        }
        Textbox bandTb = new Textbox(selectedHarness.getBand_name());
        Button saveBtn = new Button();
        if (!hasRoleAdmin()) saveBtn.setDisabled(true);
        saveBtn.setImage("images/save.png");
        saveBtn.addEventListener("onClick", new EventListener() {
            public void onEvent(Event evt) {
                Integer sideAstep = null;
                Integer sideBstep = null;
                for (Object o : lmla.getSelection()){
                     Workplace wp = (Workplace) o;
                     sideAstep = wp.getStep();
                };
                for (Object o : lmlb.getSelection()){
                    Workplace wp = (Workplace) o;
                    sideBstep = wp.getStep();
                };
                saveHarness(boardTypeTb.getValue(),boardIdTb.getValue(),timeTb.getValue(),!activeCheck.isChecked(),
                        prodNrTb.getValue(),ksKzTb.getValue(),sideAstep,sideBstep, bandTb.getValue());
                BindUtils.postNotifyChange(null, null, BandVM.this, "harnessList");
                Components.removeAllChildren(editRow);
            }
        });
        boardTypeTb.setDisabled(true);
        boardIdTb.setDisabled(true);
        //ksKzTb.setDisabled(true);
        bandTb.setDisabled(true);
        editRow.appendChild(boardTypeTb);
        editRow.appendChild(boardIdTb);
        editRow.appendChild(timeTb);
        editRow.appendChild(activeCheck);
        editRow.appendChild(prodNrTb);
        editRow.appendChild(ksKzTb);
        editRow.appendChild(cbA);
        editRow.appendChild(cbB);
        editRow.appendChild(bandTb);
        editRow.appendChild(saveBtn);
    }

    public void showModal(String prodNr, String ksKz) {
        Map<String, String> arguments = new HashMap();
        arguments.put("prodNr", prodNr);
        arguments.put("ksKz", ksKz);
        Window window = (Window) Executions.createComponents(
                "prodNrLog/harnessLog.zul", null, arguments);
        window.doModal();
    }

    public void showModal2(Harness harness) {
        Map<String, Harness> arguments = new HashMap();
        arguments.put("harness", harness);
        Window window = (Window) Executions.createComponents(
                "band/deleteFromBand.zul", null, arguments);
        window.doModal();
    }

    public void showModal3(Harness harness) {
        Map<String, Harness> arguments = new HashMap();
        arguments.put("harness", harness);
        Window window = (Window) Executions.createComponents(
                "band/moveToOtherBoard.zul", null, arguments);
        window.doModal();
    }

    boolean hasRoleAdmin(){
        for (Roles roles : users.getUserRoles()){
            if (roles.getRole().trim().equals("ROLE_ADMIN")) return true;
        }
        return false;
    }


    public List<Harness> getHarnessList() {
        return harnessList;
    }

    public void setHarnessList(List<Harness> harnessList) {
        this.harnessList = harnessList;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public Harness getSelectedHarness() {
        return selectedHarness;
    }

    public void setSelectedHarness(Harness selectedHarness) {
        this.selectedHarness = selectedHarness;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }


}
