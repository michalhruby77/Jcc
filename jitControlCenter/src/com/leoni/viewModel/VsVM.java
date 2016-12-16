package com.leoni.viewModel;

import com.leoni.data.manager.VsManager;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 18.12.2014
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VsVM {

    @WireVariable
    private VsManager vsManager;
}
