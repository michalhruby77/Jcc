package com.leoni.data.manager.vm;

import com.leoni.data.manager.GenericManager;
import com.leoni.data.models.vm.VmVariante;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 3.8.2015
 * Time: 8:35
 * To change this template use File | Settings | File Templates.
 */
public interface VmVarianteManager extends GenericManager<VmVariante> {
    public List<VmVariante> getAll();

}
