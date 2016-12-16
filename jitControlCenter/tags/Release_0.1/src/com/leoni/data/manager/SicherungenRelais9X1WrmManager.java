package com.leoni.data.manager;

import com.leoni.data.models.SicherungenRelais9X1Wrm;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 18.1.2013
 * Time: 8:54
 * To change this template use File | Settings | File Templates.
 */
public interface SicherungenRelais9X1WrmManager extends GenericManager<SicherungenRelais9X1Wrm>
    {
    public List<String> getBoxList();
    public List<String> getPlatzList(String box);
    }
