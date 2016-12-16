package com.leoni.data.manager.vm;

import com.leoni.data.manager.GenericManager;
import com.leoni.data.models.vm.VmBrett;
import com.leoni.data.models.vm.VmSwitch;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 3.8.2015
 * Time: 8:34
 * To change this template use File | Settings | File Templates.
 */
public interface VmSwitchManager extends GenericManager<VmSwitch> {
    public List<VmSwitch> getAll();
    public List<VmSwitch> findById(Integer id);
    List<VmSwitch> findByBoard(VmBrett vmBrett);
    List<VmSwitch> findByAddress(String address);
}
