package com.leoni.viewModel.vm;

import com.leoni.data.models.vm.VmVariante;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 1.10.2015
 * Time: 9:27
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ShowVariantsVM {

    private Set<VmVariante> variantsSet;
    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("variantsSet") Set<VmVariante> myObject) throws Exception {

        variantsSet = myObject;

    }

    public Set<VmVariante> getVariantsSet() {
        return variantsSet;
    }

    public void setVariantsSet(Set<VmVariante> variantsSet) {
        this.variantsSet = variantsSet;
    }
}
