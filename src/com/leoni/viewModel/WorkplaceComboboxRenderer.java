package com.leoni.viewModel;

import com.leoni.data.models.Workplace;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 29.10.2015
 * Time: 10:38
 * To change this template use File | Settings | File Templates.
 */
public class WorkplaceComboboxRenderer implements ComboitemRenderer {
    @Override
    public void render(Comboitem comboitem, Object o, int i) throws Exception {
        comboitem.setLabel(((Workplace) o).getName());
    }
}
