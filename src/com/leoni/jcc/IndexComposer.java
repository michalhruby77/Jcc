package com.leoni.jcc;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Menuitem;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 27.7.2012
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public class IndexComposer extends SelectorComposer
    {
    @Wire ("#test")
    private Button testButton;
    @Wire ("#contentContainer")
    private Component contentContainer;
    @WireVariable("#test1")
    private Boolean test1 = true;
    /*@Listen ("onClick=#test")
    public void testButtonClick(MouseEvent event)
        {
        testButton.setLabel("ahoj 2");
        contentContainer.appendChild(Executions.createComponents("/modulsEditor.zul", null, null));
        }*/
    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view) throws Exception {
        test1 = true;
        Selectors.wireComponents(view, this, false);}


    @Listen ("onClick=menuitem")
    public void menuItemClick(MouseEvent event)
        {
        Menuitem menuitem = (Menuitem) event.getTarget();
        String zulPath = menuitem.getValue();
        File zulFile = new File(zulPath);
        //System.out.println(zulFile.exists());
        Component component = null;
        contentContainer.getChildren().clear();
        component = Executions.createComponents(zulPath, null, null);
        contentContainer.appendChild(component);
        }
    }
