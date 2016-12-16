package com.leoni.data.manager;

import com.leoni.data.models.FoamWorkplace;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.10.2014
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public interface FoamWorkplaceManager  extends GenericManager<FoamWorkplace> {
   public List<FoamWorkplace> getAll();
   public FoamWorkplace add(Integer workplace, String form, String template, String workplaceName, String description, List<String> prefixList);
   public List<FoamWorkplace> findWorkplace(Integer workplace);
}
