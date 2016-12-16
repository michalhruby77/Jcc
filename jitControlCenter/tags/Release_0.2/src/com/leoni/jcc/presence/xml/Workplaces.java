package com.leoni.jcc.presence.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 11.2.2013
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */

@XmlAccessorType (XmlAccessType.FIELD)
@XmlRootElement
public class Workplaces implements Serializable
    {
    @XmlElement(name = "wp")
    private List<Workplace> workplaces;

    public Workplaces()
        {
        workplaces = new ArrayList<Workplace>();
        }

    public Workplaces(List<Workplace> workplaces)
        {
        this.workplaces = workplaces;
        }

    public List<Workplace> getWorkplaces()
        {
        return workplaces;
        }

    public void setWorkplaces(List<Workplace> workplaces)
        {
        this.workplaces = workplaces;
        }
    }
