package com.leoni.jcc.presence.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 11.2.2013
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType (XmlAccessType.FIELD)
public class Workplace implements Serializable
    {
    @XmlAttribute (name = "id")
    private String id;
    @XmlAttribute (name = "n")
    private String name;
    @XmlAttribute (name = "i")
    private String icon;
    @XmlElement (name = "p")
    private List<Place> placeList;

    public Workplace()
        {
        }

    public Workplace(String id, String name, List<Place> placeList)
        {
        this.id = id;
        this.name = name;
        this.placeList = placeList;
        }

    public Workplace(String id, String name, String icon, List<Place> placeList)
        {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.placeList = placeList;
        }

    public Workplace(Workplace workplace)
        {
        this.id = workplace.getId();
        this.name = workplace.getName();
        this.icon = workplace.getIcon();
        this.placeList = workplace.getPlaceList();
        }

    public String getId()
        {
        return id;
        }

    public void setId(String id)
        {
        this.id = id;
        }

    public String getName()
        {
        return name;
        }

    public void setName(String name)
        {
        this.name = name;
        }

    public String getIcon()
        {
        return icon;
        }

    public void setIcon(String icon)
        {
        this.icon = icon;
        }

    public List<Place> getPlaceList()
        {
        return placeList;
        }

    public void setPlaceList(List<Place> placeList)
        {
        this.placeList = placeList;
        }
    }
