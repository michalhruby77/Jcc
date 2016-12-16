package com.leoni.data.manager;

import com.leoni.data.models.DmeVarianteName;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 21.7.2014
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
public interface DmeVarianteNameManager  extends GenericManager<DmeVarianteName>{
    public List<DmeVarianteName> findBySumWertigkeit(Integer sumWertigkeit);
}
