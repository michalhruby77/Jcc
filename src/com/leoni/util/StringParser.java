package com.leoni.util;

import com.leoni.data.dto.RelaisBoxDTO;
import com.leoni.data.dto.RelaisZoneDTO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by cigi1001 on 27. 11. 2016.
 */
public class StringParser {

    public static RelaisBoxDTO parseXML(String xml){
        RelaisBoxDTO relaisBoxDTO = new RelaisBoxDTO();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;

            builder = factory.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));

            Document document = builder.parse(is);

            document.getDocumentElement().normalize();

            NodeList rootNodeList = document.getDocumentElement().getChildNodes();

            for(int rootNodelistItem = 0; rootNodelistItem < rootNodeList.getLength(); rootNodelistItem++){

                Node zoneNode = rootNodeList.item(rootNodelistItem);

                if (zoneNode.getNodeType() == Node.ELEMENT_NODE){

                    RelaisZoneDTO relaisZoneDTO = new RelaisZoneDTO(zoneNode.getNodeName());

                    relaisBoxDTO.getRelaisZoneDTOList().add(relaisZoneDTO);

                    NodeList positionNodeList = zoneNode.getChildNodes();//document.getElementsByTagName("relais_treager1_vorne");

                    for(int positionNodeListItem = 0; positionNodeListItem < positionNodeList.getLength(); positionNodeListItem++){

                        Node positionNode = positionNodeList.item(positionNodeListItem);

                        if (positionNode.getNodeType() == Node.ELEMENT_NODE){

                            Element eElement = (Element) positionNode;

                            relaisZoneDTO.getPositionMap().put(eElement.getNodeName(), eElement.getTextContent());
                        }

                    }
                }
        }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relaisBoxDTO;
    }
}
