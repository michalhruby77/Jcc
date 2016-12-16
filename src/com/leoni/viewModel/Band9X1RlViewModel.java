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
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 11.12.2013
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Band9X1RlViewModel {

    @WireVariable
    private BandManager bandManager;

    @WireVariable
    private BandTypeManager bandTypeManager;

    @WireVariable
    private BoardTypeManager boardTypeManager;


    @WireVariable
    private WorkplaceManager workplaceManager;

    @WireVariable
    private UsersManager usersManager;

    @WireVariable
    private PrnrManager prnrManager;

    @WireVariable
    private Lpab62Manager lpab62Manager;


    @Wire
    Listbox listbox;


    private List<Harness> harnessList = new ArrayList<Harness>();
    private String brettType="";
    private String prodNr="";
    private String brettOnClips;
    private Harness selectedHarness;
    private Harness thisHarness;
    private boolean showRL=true;
    private boolean showDisable981;
    private boolean showDisable991;
    private boolean showEnable981;
    private boolean showEnable991;
    private Users users;
    private String boardType;
    private String boardId;
    private String bandName;


    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        users = usersManager.getUser(user);
        bandName = "F991RL";
        BandRenderer br = new BandRenderer();
        br.init(bandTypeManager.getAll(),workplaceManager.getAll(), users);
        listbox.setItemRenderer(br);
        harnessList=bandManager.findByBrettTypeRLALL();
        Collections.sort(harnessList);
        showDisable981 = bandManager.is981enabled()&&bandManager.is991enabled();
        showDisable991 = bandManager.is981enabled()&&bandManager.is991enabled();
        showEnable981 = !bandManager.is981enabled();
        showEnable991 = !bandManager.is991enabled();
        System.out.println(showEnable981 + " " +showEnable991);
        if(showDisable981) brettOnClips = "";
        else{
            if(showEnable981){brettOnClips = "Mozete odblokovat ked bude na klipoch doska: "+bandManager.getBrettTypeAndIdOnClip("F981RL");}
            else{brettOnClips = "Mozete odblokovat ked bude na klipoch doska: "+bandManager.getBrettTypeAndIdOnClip("F991RL");}
        }
    }

    @GlobalCommand
    @NotifyChange ({"harnessList"})
    @Command
    public void refreshBand(){
        harnessList=bandManager.findByBrettTypeRLALL();
        Collections.sort(harnessList);
    }

    @Command
    @NotifyChange ({"harnessList"})
    public void searchByBrettType()
    {
        if (brettType.equals("")){harnessList=bandManager.getAll();}
        else harnessList = bandManager.findByBrettType(brettType);
    };

    @Command
    @NotifyChange ({"harnessList"})
    public void searchByProdNr()
    {
        if (prodNr.equals("")){harnessList=bandManager.getAll();}
        else harnessList = bandManager.findByProdNr(prodNr);
    };

    @Command
    @NotifyChange ({"harnessList"})
    public void disableHarness(@BindingParam("harness") Harness myHarness)
    {
       myHarness.setLock(true);
       selectedHarness = bandManager.updateHarness(myHarness);
        BindUtils.postNotifyChange(null, null, Band9X1RlViewModel.this, "selectedHarness");
        if (prodNr.equals("")&&brettType.equals("")){harnessList=bandManager.getAll();}
        else { if (!brettType.equals("")){harnessList=bandManager.findByBrettType(brettType);}
               if (!prodNr.equals("")){harnessList=bandManager.findByProdNr(prodNr);}

        }
        BindUtils.postNotifyChange(null, null, Band9X1RlViewModel.this, "harnessList");
    };

    @Command
    @NotifyChange ({"harnessList"})
    public void enableHarness(@BindingParam("harness") Harness myHarness)
    {
        myHarness.setLock(false);
        selectedHarness = bandManager.updateHarness(myHarness);
        BindUtils.postNotifyChange(null, null, Band9X1RlViewModel.this, "selectedHarness");
        if (prodNr.equals("")&&brettType.equals("")){harnessList=bandManager.getAll();}
        else {if (!brettType.equals("")){harnessList=bandManager.findByBrettType(brettType);}
            if (!prodNr.equals("")){harnessList=bandManager.findByProdNr(prodNr);}

        }
        BindUtils.postNotifyChange(null, null, Band9X1RlViewModel.this, "harnessList");
    };

    @Command
    @NotifyChange ({"harnessList","showDisable981","showDisable991","showEnable981","showEnable991","brettOnClips"})
    public void disableAll981RL()
    {
       bandManager.disableAll981();
       harnessList = bandManager.findByBrettTypeRLALL();
        Collections.sort(harnessList);
       brettOnClips = "Mozete odblokovat ked bude na klipoch doska: "+bandManager.getBrettTypeAndIdOnClip("F981RL");
       showDisable981 = false;
       showDisable991 = false;
       showEnable981 = true;
       showEnable991 = false;
    };

    @Command
    @NotifyChange ({"harnessList","showDisable981","showDisable991","showEnable981","showEnable991","brettOnClips"})
    public void disableAll991RL()
    {
       bandManager.disableAll991();
       harnessList = bandManager.findByBrettTypeRLALL();
        Collections.sort(harnessList);
        brettOnClips = "Mozete odblokovat ked bude na klipoch doska: "+bandManager.getBrettTypeAndIdOnClip("F991RL");
        showDisable981 = false;
        showDisable991 = false;
        showEnable981 = false;
        showEnable991 = true;
    };

    @Command
    @NotifyChange ({"harnessList","showDisable981","showDisable991","showEnable981","showEnable991","brettOnClips"})
    public void enableAll981RL()
    {
        if(bandManager.isFirstBrettIdOk("F981RL")){
        bandManager.enableAll981();
        harnessList = bandManager.findByBrettTypeRLALL();
            Collections.sort(harnessList);
            brettOnClips = "";
            showDisable981 = true;
            showDisable991 = true;
            showEnable981 = false;
            showEnable991 = false;
        }
        else{Messagebox.show("Dosky niesu v rovnakom stave ako pred prechodom!", "Error", Messagebox.OK, Messagebox.ERROR);}
    };

    @Command
    @NotifyChange ({"harnessList","showDisable981","showDisable991","showEnable981","showEnable991","brettOnClips"})
    public void enableAll991RL()
    {
        if(bandManager.isFirstBrettIdOk("F991RL")){
        bandManager.enableAll991();
        harnessList = bandManager.findByBrettTypeRLALL();
            Collections.sort(harnessList);
            brettOnClips = "";
            showDisable981 = true;
            showDisable991 = true;
            showEnable981 = false;
            showEnable991 = false;
        }
        else{
            Messagebox.show("Dosky niesu v rovnakom stave ako pred prechodom!", "Error", Messagebox.OK, Messagebox.ERROR);}
    };

    @Command
    @NotifyChange ({"harnessList","showRL"})
    public void showRL()
    {
        harnessList = bandManager.findByBrettTypeRL();
        showRL=false;
    };

    @Command
    @NotifyChange ({"harnessList","showRL"})
    public void showAll()
    {
        harnessList = bandManager.getAll();
        showRL=true;
    };


    @GlobalCommand
    public void moveOutFromBand(@BindingParam("harness") Harness myHarness){
        thisHarness = myHarness;
        showModal2(myHarness);
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
                            harnessList = bandManager.findByBrettTypeRLALL();
                            Collections.sort(harnessList);
                            BindUtils.postNotifyChange(null, null, Band9X1RlViewModel.this, "harnessList");
                        }
                    }
                }
        );
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
                            harnessList=bandManager.findByBrettTypeRLALL();
                            Collections.sort(harnessList);
                            BindUtils.postNotifyChange(null, null, Band9X1RlViewModel.this, "harnessList");
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
            harnessList = bandManager.findByBrettTypeRLALL();
            Collections.sort(harnessList);
            boardType = null;
            boardId = null;
            bandName = "F991RL";
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

    @Command
    public void saveHarness(String boardType, String boardId, String time, Boolean lock, String prodNr, String ksKz,
                            Integer sideA, Integer sideB, String bandName) {
        if (prodNr == null || prodNr.trim().equals("") ||
           (prodNr != null && !prodNr.trim().equals("") && !lpab62Manager.findByProdnrAndKabelsatz(prodNr, "F").isEmpty())) {
            selectedHarness.setBretttype(boardType);
            selectedHarness.setBrettid(boardId);
            Date date = null;
            if (time != null && !time.trim().equals("")) {
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
            selectedHarness.setKabelsatz_kz("F");
            selectedHarness.setSide_a_step(sideA);
            selectedHarness.setSide_b_step(sideB);
            selectedHarness.setBand_name(bandName);
            bandManager.saveOrUpdate(selectedHarness);
            harnessList = bandManager.findByBandName(bandName);
            Collections.sort(harnessList);
            selectedHarness = null;
            Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
        }
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
        saveBtn.setImage("images/save.png");
        if (!hasRoleAdmin()) saveBtn.setDisabled(true);
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
                BindUtils.postNotifyChange(null, null, Band9X1RlViewModel.this, "harnessList");
                Components.removeAllChildren(editRow);
            }
        });
        boardTypeTb.setDisabled(true);
        boardIdTb.setDisabled(true);
        ksKzTb.setDisabled(true);
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

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setHarnessList(List<Harness> harnessList) {
        this.harnessList = harnessList;
    }
    public List<Harness> getHarnessList() {
        return harnessList;
    }

    public String getBrettType() {
        return brettType;
    }

    public void setBrettType(String brettType) {
        this.brettType = brettType;
    }

    public Harness getSelectedHarness() {
        return selectedHarness;
    }

    public void setSelectedHarness(Harness selectedHarness) {
        this.selectedHarness = selectedHarness;
    }

    public String getProdNr() {
        return prodNr;
    }

    public void setProdNr(String prodNr) {
        this.prodNr = prodNr;
    }

    public boolean getShowRL() {
        return showRL;
    }

    public void setShowRL(boolean showRL) {
        this.showRL = showRL;
    }

    public boolean isShowDisable981() {
        return showDisable981;
    }

    public void setShowDisable981(boolean showDisable981) {
        this.showDisable981 = showDisable981;
    }

    public boolean isShowDisable991() {
        return showDisable991;
    }

    public void setShowDisable991(boolean showDisable991) {
        this.showDisable991 = showDisable991;
    }

    public boolean isShowEnable981() {
        return showEnable981;
    }

    public void setShowEnable981(boolean showEnable981) {
        this.showEnable981 = showEnable981;
    }

    public boolean isShowEnable991() {
        return showEnable991;
    }

    public void setShowEnable991(boolean showEnable991) {
        this.showEnable991 = showEnable991;
    }

    public String getBrettOnClips() {
        return brettOnClips;
    }

    public void setBrettOnClips(String brettOnClips) {
        this.brettOnClips = brettOnClips;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }
}
