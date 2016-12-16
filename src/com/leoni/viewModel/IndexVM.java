package com.leoni.viewModel;

import com.leoni.data.manager.UsersManager;
import com.leoni.data.manager.XmlManager;
import com.leoni.data.models.Roles;
import com.leoni.data.models.Users;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 29.10.2014
 * Time: 10:12
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class IndexVM {

    @WireVariable
    private UsersManager usersManager;

    @WireVariable
    private XmlManager xmlManager;

    @Wire("#contentContainer")
    private Component contentContainer;
    @Wire
    private Tabs tabs;
    @Wire
    private Tabpanels tabpanels;

    private String userName;
    private Users user;

    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = usersManager.getUser(userName);
        SortedSet<Roles> userRoles =  user.getUserRoles();
        for (Roles item : userRoles){
            String xmlMenu = item.getMenuXml();
            Tabs newTabs = xmlManager.buildTabs(xmlMenu,tabs);
            Components.replace(tabs,newTabs);
            Tabpanels newTabpanels = xmlManager.buildTabPanels(xmlMenu, tabpanels, contentContainer);
            Components.replace(tabpanels,newTabpanels);
        }
    }

    @Command
    public void open(@BindingParam("pageName") String pageName){
        Component component = null;
        contentContainer.getChildren().clear();
        component = Executions.createComponents(pageName, null, null);
        contentContainer.appendChild(component);
    }
}
