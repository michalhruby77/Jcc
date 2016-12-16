package com.leoni.viewModel;

import com.leoni.data.models.Moduls;
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
 * Date: 12.2.2015
 * Time: 10:44
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ShowModulsVM {

    private Set<Moduls> modulsSet;
    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("modulsSet") Set<Moduls> myObject) throws Exception {

    modulsSet = myObject;
    System.out.println(modulsSet);
    }

    public Set<Moduls> getModulsSet() {
        return modulsSet;
    }

    public void setModulsSet(Set<Moduls> modulsSet) {
        this.modulsSet = modulsSet;
    }
}

