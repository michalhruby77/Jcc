package com.leoni.data.manager;

import com.leoni.data.models.GrundVarianteModule;
import com.leoni.data.models.GrundVarianteName;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 21.7.2014
 * Time: 10:17
 * To change this template use File | Settings | File Templates.
 */
public interface GrundVarianteNameManager extends GenericManager<GrundVarianteName>{
    public List<GrundVarianteName> findBySumWertigkeit(Integer sumWertigkeit);
}
