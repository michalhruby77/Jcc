package com.leoni.data.manager;

import com.leoni.data.models.VersandExport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2015
 * Time: 9:26
 * To change this template use File | Settings | File Templates.
 */
public interface VersandExportManager extends GenericManager<VersandExport> {
    public List<VersandExport> getAll();

}
