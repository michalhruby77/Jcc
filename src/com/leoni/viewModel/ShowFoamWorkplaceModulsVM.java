package com.leoni.viewModel;

import com.leoni.data.models.FoamWorkplaceModuls;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

import java.util.List;

/**
 * Created by hrmi1005 on 26. 5. 2016.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ShowFoamWorkplaceModulsVM {

    private List<FoamWorkplaceModuls> foamWorkplaceModuls;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("fwList") List<FoamWorkplaceModuls> myObject) throws Exception {

        foamWorkplaceModuls = myObject;
    }

    public List<FoamWorkplaceModuls> getFoamWorkplaceModuls() {
        return foamWorkplaceModuls;
    }

    public void setFoamWorkplaceModuls(List<FoamWorkplaceModuls> foamWorkplaceModuls) {
        this.foamWorkplaceModuls = foamWorkplaceModuls;
    }
}
