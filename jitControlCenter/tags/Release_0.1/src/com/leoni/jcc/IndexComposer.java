package com.leoni.jcc;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
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

    @Listen ("onClick=#test")
    public void testButtonClick(MouseEvent event)
        {
        testButton.setLabel("ahoj 2");
        contentContainer.appendChild(Executions.createComponents("/modulsEditor.zul", null, null));
        }

    @Listen ("onClick=menuitem")
    public void menuItemClick(MouseEvent event)
        {
        Menuitem menuitem = (Menuitem) event.getTarget();
        String zulPath = menuitem.getValue();
        File zulFile = new File(zulPath);
        System.out.println(zulFile.exists());
        Component component = null;
        contentContainer.getChildren().clear();
        component = Executions.createComponents(zulPath, null, null);
        contentContainer.appendChild(component);
        }
    }
