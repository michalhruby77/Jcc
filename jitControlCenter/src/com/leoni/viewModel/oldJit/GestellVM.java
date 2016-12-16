package com.leoni.viewModel.oldJit;

import com.leoni.data.manager.oldJIT.GestellManager;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 11.6.2014
 * Time: 10:44
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GestellVM {

    @WireVariable
    private GestellManager gestellManager;

    private Integer updatedRows;

    @Command
    @NotifyChange({"updatedRows"})
    public void submit(){
              updatedRows = gestellManager.doGestell();
    }

    public Integer getUpdatedRows() {
        return updatedRows;
    }

    public void setUpdatedRows(Integer updatedRows) {
        this.updatedRows = updatedRows;
    }
}
