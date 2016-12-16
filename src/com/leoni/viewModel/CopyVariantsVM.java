package com.leoni.viewModel;

import com.leoni.data.models.Variant;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 26.1.2015
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CopyVariantsVM {

    @Wire
    Listbox listbox;

    private List<Variant> variantList;
    private List<Variant> selectedVariants;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("myVariantList") List<Variant> myObject) throws Exception {
        Selectors.wireComponents(view, this, false);
        variantList=myObject;
        }

    @Command
    public void copyInVariants(@ContextParam(ContextType.VIEW) Component view) {
        Set<Listitem> li =  listbox.getSelectedItems();
        if(!li.isEmpty())
        {
            selectedVariants = new ArrayList<Variant>();
            for (Listitem item : li){
                selectedVariants.add((Variant) item.getValue());
            }
            listbox.setSelectedIndex(-1);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("selectedVariants", selectedVariants);

            Binder bind = (Binder) view.getParent().getAttribute("binder");
            if (bind == null)
                return;
            bind.postCommand("setVariantsToCopy", params);
        }


            view.detach();
    }

    public List<Variant> getVariantList() {
        return variantList;
    }

    public void setVariantList(List<Variant> variantList) {
        this.variantList = variantList;
    }
}
