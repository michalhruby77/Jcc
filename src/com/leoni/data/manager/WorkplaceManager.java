package com.leoni.data.manager;

import com.leoni.data.models.Workplace;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2014
 * Time: 9:29
 * To change this template use File | Settings | File Templates.
 */
public interface WorkplaceManager extends GenericManager<Workplace>  {
    public Workplace saveEditedWorkplace(Workplace workplace);
    public Workplace addNewWorkplace(String name, String bandName, Integer step, String side, String operation, String alias);
    public List<Workplace> findBy(String bandNameSearch);
    List<Workplace> findStatusNotNull();
}
