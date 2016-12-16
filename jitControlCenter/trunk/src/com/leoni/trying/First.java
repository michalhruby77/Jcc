package com.leoni.trying;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 23.7.2012
 * Time: 8:15
 * To change this template use File | Settings | File Templates.
 */

public class First extends SelectorComposer
    {
    @Wire
    private Button shootButton;
    @Wire
    private Window innerWindow;

    @Listen ("onClick = button#shootButton")
    public void shootButtonClick()
        {
//        Messagebox.show("I'm shooting");
        innerWindow.setClosable(true);
        innerWindow.setMaximizable(true);
        innerWindow.setMinimizable(true);
        }
    }
