package com.leoni.data.manager;

import com.leoni.data.models.TuelleBugVarianteName;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 21.7.2014
 * Time: 11:55
 * To change this template use File | Settings | File Templates.
 */
public interface TuelleBugVarianteNameManager  extends GenericManager<TuelleBugVarianteName>{
    public List<TuelleBugVarianteName> findBySumWertigkeit(Integer sumWertigkeit);
}
