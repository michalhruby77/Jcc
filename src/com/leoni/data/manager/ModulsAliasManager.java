package com.leoni.data.manager;

import com.leoni.data.models.ModulsAlias;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.9.2015
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
public interface ModulsAliasManager  extends GenericManager<ModulsAlias>{
    public List<ModulsAlias> findBySachNrLieferant(String sachNrLieferant);
}
