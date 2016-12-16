package com.leoni.data.manager;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.10.2014
 * Time: 7:41
 * To change this template use File | Settings | File Templates.
 */
@Service("xmlManager")
public class XmlManagerImpl implements XmlManager {
    @Override
    public Tabs buildTabs(String xmlString, Tabs tabs) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlString));
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("tab");
            for (int tempTab = 0; tempTab < nList.getLength(); tempTab++) {

                Node nNode = nList.item(tempTab);
                Element element = (Element) nNode;
                String tabName = element.getAttribute("name").trim();
                Tab newTab = new Tab();
                newTab.setLabel(tabName);
                tabs.appendChild(newTab);

            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tabs;
    }

    @Override
    public Tabpanels buildTabPanels(String xmlString, Tabpanels tabpanels,  Component contentContainer) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlString));
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("tabpanel");
            for (int tempTabpanel = 0; tempTabpanel < nList.getLength(); tempTabpanel++) {
                Tabpanel tabpanel  = new Tabpanel();
                tabpanel.setWidth("200px");
                Menubar menubar = new Menubar();
                menubar.setOrient("vertical");
                Node nNode = nList.item(tempTabpanel);
                NodeList menuitemList = nNode.getChildNodes();
                for (int tempMenuitem = 0; tempMenuitem < menuitemList.getLength(); tempMenuitem++){
                    Node menuitemNode = menuitemList.item(tempMenuitem);
                    if (menuitemNode.getNodeType() == Node.ELEMENT_NODE) {
                        if(menuitemNode.getNodeName().equals("menuitem")){
                    Element element = (Element) menuitemNode;
                    String menuitemName = element.getAttribute("name").trim();
                    final String menuitemPath = element.getAttribute("path").trim();
                    Menuitem menuitem = new Menuitem();
                    menuitem.setLabel(menuitemName);
                    menuitem.addEventListener("onClick", new EventListener<Event>() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            Component component = null;
                            contentContainer.getChildren().clear();
                            component = Executions.createComponents(menuitemPath, null, null);
                            contentContainer.appendChild(component);
                        }
                    });
                    menubar.appendChild(menuitem);}
                    else if (menuitemNode.getNodeName().equals("menu")){
                            NodeList menuList = menuitemNode.getChildNodes();
                            Element element = (Element) menuitemNode;
                            String menuName = element.getAttribute("label").trim();
                            Menupopup menupopup = new Menupopup();
                            Menu menu = new Menu(menuName);
                            menu.appendChild(menupopup);
                            menubar.appendChild(menu);
                            //---------------------
                            for (int tempMenu = 0; tempMenu < menuList.getLength(); tempMenu++){
                                Node menuNode = menuList.item(tempMenu);
                                if (menuNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element elementMenu = (Element) menuNode;
                                    String menuitemName = elementMenu.getAttribute("name").trim();
                                    final String menuitemPath = elementMenu.getAttribute("path").trim();
                                    Menuitem menuitem = new Menuitem();
                                    menuitem.setLabel(menuitemName);
                                    menuitem.addEventListener("onClick", new EventListener<Event>() {
                                        @Override
                                        public void onEvent(Event event) throws Exception {
                                            Component component = null;
                                            contentContainer.getChildren().clear();
                                            component = Executions.createComponents(menuitemPath, null, null);
                                            contentContainer.appendChild(component);
                                        }
                                    });
                                menupopup.appendChild(menuitem);
                                }
                            }
                            //---------------------

                        }
                    }
                }
                Menuseparator menuseparator = new Menuseparator();
                Menuitem logout = new Menuitem();
                logout.setLabel("Odhlasit");
                logout.setHref("/j_spring_security_logout");
                menubar.appendChild(menuseparator);
                menubar.appendChild(logout);
                tabpanel.appendChild(menubar);
                tabpanels.appendChild(tabpanel);

            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tabpanels;
    }
}
