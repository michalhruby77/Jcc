package com.leoni.data.manager;

import com.leoni.data.models.Customer;
import com.leoni.data.models.ProdGroup;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 5.9.2014
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public interface GroupDeriveManager  extends GenericManager<ProdGroup>{
    List<ProdGroup> deriveProdGroup(String sachNrBest);
    List<ProdGroup> deriveProdGroup(String sachNrBest, Customer customer);
}
