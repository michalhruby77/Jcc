package com.leoni.viewModel.vm;

import com.leoni.data.models.vm.VmClip;
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
 * Date: 19.8.2015
 * Time: 8:37
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ShowClipsVM {

    private Set<VmClip> clipsSet;
    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("clipsSet") Set<VmClip> myObject) throws Exception {

        clipsSet = myObject;
    }


    public Set<VmClip> getClipsSet() {
        return clipsSet;
    }

    public void setClipsSet(Set<VmClip> clipsSet) {
        this.clipsSet = clipsSet;
    }
}
