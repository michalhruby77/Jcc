package com.leoni.data.manager.vm;

import com.leoni.data.manager.GenericManager;
import com.leoni.data.models.vm.VmBrett;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 3.8.2015
 * Time: 8:33
 * To change this template use File | Settings | File Templates.
 */
public interface VmBrettManager extends GenericManager<VmBrett> {
    public List<VmBrett> getAll();
    List<VmBrett> findByName(String name);
}
