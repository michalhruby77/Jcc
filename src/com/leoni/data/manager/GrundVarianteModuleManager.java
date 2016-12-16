package com.leoni.data.manager;

import com.leoni.data.models.GrundVarianteModule;
import com.leoni.data.models.Lpab64;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.4.2014
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
public interface GrundVarianteModuleManager extends GenericManager<GrundVarianteModule>{
      public List<GrundVarianteModule> findByModulsId(Integer modulsId);
}
