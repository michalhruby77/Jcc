package com.leoni.viewModel;

import com.leoni.data.models.ModulsAlias;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.9.2015
 * Time: 7:39
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ShowAliasVM {

    private List<ModulsAlias> modulsAlias;
    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("aliasList") List<ModulsAlias> myObject) throws Exception {

        modulsAlias = myObject;
    }

    public List<ModulsAlias> getModulsAlias() {
        return modulsAlias;
    }

    public void setModulsAlias(List<ModulsAlias> modulsAlias) {
        this.modulsAlias = modulsAlias;
    }
}
