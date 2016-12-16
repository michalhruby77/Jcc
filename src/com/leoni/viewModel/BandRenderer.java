package com.leoni.viewModel;


import com.leoni.data.manager.BandTypeManager;
import com.leoni.data.manager.BandTypeManagerImpl;
import com.leoni.data.manager.WorkplaceManager;
import com.leoni.data.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 22.10.2015
 * Time: 7:38
 * To change this template use File | Settings | File Templates.
 */
public class BandRenderer implements ListitemRenderer {

    List<BandType> bandTypeList;
    List<Workplace> workplaceList;
    Users users;

    public void init(List<BandType> bandTypeList, List<Workplace> workplaceList, Users users) {

        this.bandTypeList = bandTypeList;
        this.workplaceList = workplaceList;
        this.users = users;
    }

    @Override
    public void render(Listitem listitem, Object o, int i) throws Exception {
        Harness harness = (Harness) o;
        Label boardType = new Label(harness.getBretttype());
        Listcell lcBoardType = new Listcell();
        lcBoardType.appendChild(boardType);
        lcBoardType.setParent(listitem);
        Label boardId = new Label(harness.getBrettid());
        Listcell lcBoardId = new Listcell();
        lcBoardId.appendChild(boardId);
        lcBoardId.setParent(listitem);
        Datebox time = new Datebox(harness.getBusytime());
        time.setFormat("yyyy/MM/dd HH:mm");
        time.setDisabled(true);
        time.setMold("rounded");
        time.setWidth("90%");
        Listcell lcTime = new Listcell();
        lcTime.appendChild(time);
        lcTime.setParent(listitem);
        Image active = new Image("images/yes.png");
        if (harness.getLock())  active.setSrc("images/delete.png");
        Listcell lcActive = new Listcell();
        lcActive.appendChild(active);
        lcActive.setParent(listitem);
        Label prodNr = new Label(harness.getProd_nr());
        prodNr.setStyle("font-weight:bold; color:#CC0099; font-size:16pt");
        Listcell lcProdNr = new Listcell();
        lcProdNr.appendChild(prodNr);
        lcProdNr.setParent(listitem);
        Label labelA = new Label(getWorkplaceName(harness.getBand_name(), harness.getSide_a_step(), "A"));
        Listcell lcA = new Listcell();
        lcA.appendChild(labelA);
        lcA.setParent(listitem);
        Label labelB = new Label(getWorkplaceName(harness.getBand_name(), harness.getSide_b_step(), "B"));
        Listcell lcB = new Listcell();
        lcB.appendChild(labelB);
        lcB.setParent(listitem);
        Button btnMoveOut = new Button();
        btnMoveOut.setImage("images/out.png");
        btnMoveOut.setMold("trendy");
        btnMoveOut.setDisabled(!contains("ROLE_ADMIN"));
            btnMoveOut.addEventListener("onClick",new EventListener() {
                public void onEvent(Event evt) {
                    Map<String, Object> arguments = new HashMap();
                    arguments.put("harness", harness);
                    BindUtils.postGlobalCommand(null, null, "moveOutFromBand", arguments);
                }});
        Listcell lcMoveOut = new Listcell();
        lcMoveOut.appendChild(btnMoveOut);
        lcMoveOut.setParent(listitem);

        Button btnContainer = new Button();
        btnContainer.setImage("images/container.png");
        btnContainer.setMold("trendy");
        btnContainer.setDisabled(!contains("ROLE_ADMIN"));
            btnContainer.addEventListener("onClick",new EventListener() {
                public void onEvent(Event evt) {
                    Map<String, Object> arguments = new HashMap();
                    arguments.put("harness", harness);
                    BindUtils.postGlobalCommand(null, null, "backToContainer", arguments);
                }});
        Listcell lcContainer = new Listcell();
        lcContainer.appendChild(btnContainer);
        lcContainer.setParent(listitem);

        Button btnMove = new Button();
        btnMove.setImage("images/move.png");
        btnMove.setMold("trendy");
        btnMove.setDisabled(!contains("ROLE_ADMIN"));
        btnMove.addEventListener("onClick",new EventListener() {
            public void onEvent(Event evt) {
                Map<String, Object> arguments = new HashMap();
                arguments.put("harness", harness);
                BindUtils.postGlobalCommand(null, null, "moveToOtherBoard", arguments);
            }});
        Listcell lcMove = new Listcell();
        lcMove.appendChild(btnMove);
        lcMove.setParent(listitem);

        Button btnDelete = new Button();
        btnDelete.setImage("images/delete.png");
        btnDelete.setMold("trendy");
        btnDelete.setDisabled(!contains("ROLE_ADMIN"));
        btnDelete.addEventListener("onClick",new EventListener() {
            public void onEvent(Event evt) {
                Map<String, Object> arguments = new HashMap();
                arguments.put("harness", harness);
                BindUtils.postGlobalCommand(null, null, "deleteFromBand", arguments);
            }});
        Listcell lcDelete = new Listcell();
        lcDelete.appendChild(btnDelete);
        lcDelete.setParent(listitem);
    }

    private String getWorkplaceName(String bandName, Integer step, String side){
      if (bandName.trim().equals("F991RL")) {bandName = "F9X1RL";}
      BandType bandType = null;
      String wpName = null;
      if (!step.equals(0)){
        for (BandType bt : bandTypeList){
                   if (bandName.trim().equals(bt.getName().trim())) bandType = bt;
               }
        if (bandType != null){
            for (Workplace wp : workplaceList){
                if (wp.getBand() != null) {
                if (wp.getBand().equals(bandType) && wp.getStep().equals(step) && wp.getSide().trim().equals(side.trim()))
                    wpName = wp.getName();
                }
            }
        }
      }
        return wpName;
    }

    private boolean contains(String roleName){
        boolean contains = false;
        for (Roles roles : users.getUserRoles()){
            if (roles.getRole().trim().equals(roleName)) return true;
        }
        return contains;
    }


}
