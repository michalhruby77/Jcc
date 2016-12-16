package com.leoni.data.models;

import com.leoni.data.manager.ModulsManager;
import com.leoni.viewModel.MyHlayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Tabbox;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 21.3.2014
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */

public class XMLBuilder {

    public static String buildDesc(Map<Integer,Set<Moduls>> myModulsMap){

        String description = "";
        int mapCounter = 0;
        for (Map.Entry<Integer, Set<Moduls>> entry : myModulsMap.entrySet())
        {   mapCounter++;
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            boolean firstOr = true;
            for(Moduls item : entry.getValue()){
              if (firstOr) {description = description + item.getSachNrBest().substring(0,11); firstOr = false;}
              else description = description + "/" + item.getSachNrBest().substring(8,11);
           }
           if(mapCounter != myModulsMap.size()) description = description + "... + ";
             else description = description + "...";
        }
        return description;
    }

    public static String buildXML(Map<Integer,Set<Moduls>> myModulsMap){
        String result = "";
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("moduls");
            doc.appendChild(rootElement);

        for (Map.Entry<Integer, Set<Moduls>> entry : myModulsMap.entrySet())
        {   Element group = doc.createElement("group");
            rootElement.appendChild(group);

            for(Moduls item : entry.getValue()){
                Element modul = doc.createElement("modul");
                group.appendChild(modul);
                Attr attr = doc.createAttribute("id");
                attr.setValue(String.valueOf(item.getId()));
                modul.setAttributeNode(attr);
            }
        }
            StringWriter sw = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            //StreamResult result = new StreamResult();
            transformer.transform(source, new StreamResult(sw));
            result = sw.toString();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }

    public static Map<Integer,Set<Integer>> buildMapFromXML(String xmlString){
        Map<Integer,Set<Integer>> modulsMap = new HashMap<Integer, Set<Integer>>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlString));
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("group");
            int counterMap = 0;
            for (int tempGroup = 0; tempGroup < nList.getLength(); tempGroup++) {
                Node nNode = nList.item(tempGroup);
                Set<Integer> tempSet = new HashSet<Integer>();
                for (int tempModuls = 0; tempModuls < nNode.getChildNodes().getLength(); tempModuls++){
                   Node modNode = nNode.getChildNodes().item(tempModuls);
                   if (modNode.getNodeType() == Node.ELEMENT_NODE) {
                       Element eElement = (Element) modNode;
                       tempSet.add(Integer.valueOf(eElement.getAttribute("id")));
                   }
               }
                modulsMap.put(counterMap,tempSet);
                counterMap++;
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

     return modulsMap;
    }

    /*public static Tabbox buildTabbox(String xmlString, Tabbox tb){

    } */
}
