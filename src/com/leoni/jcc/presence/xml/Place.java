package com.leoni.jcc.presence.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 11.2.2013
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */

@XmlAccessorType (XmlAccessType.FIELD)
public class Place implements Serializable
    {
    @XmlAttribute
    private String id;
    @XmlValue
    private String name;

    public Place()
        {
        }

    public Place(String id, String name)
        {
        this.id = id;
        this.name = name;
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
    }
