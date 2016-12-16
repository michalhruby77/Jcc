package com.leoni.data.manager;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.10.2014
 * Time: 7:41
 * To change this template use File | Settings | File Templates.
 */
public interface XmlManager {
    public Tabs buildTabs(String xmlString, Tabs tabs);
    public Tabpanels buildTabPanels(String xmlString, Tabpanels tabpanels, Component contentContainer);
}
