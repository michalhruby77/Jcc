package com.leoni.data.manager;

import com.leoni.data.models.FoamWorkplace;
import com.leoni.data.models.FoamWorkplaceModuls;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.10.2014
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */
public interface FoamWorkplaceModulsManager extends GenericManager<FoamWorkplaceModuls>  {
 public void addFoamWorkplaceModulsList(FoamWorkplace foamWorkplace, List<String> prefixList);
 public void deleteItems(FoamWorkplace foamWorkplace);
}
