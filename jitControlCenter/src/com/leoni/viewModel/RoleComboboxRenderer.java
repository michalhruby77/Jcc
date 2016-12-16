package com.leoni.viewModel;

import com.leoni.data.models.Roles;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 28.10.2014
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
public class RoleComboboxRenderer implements ComboitemRenderer {
    @Override
    public void render(Comboitem comboitem, Object o, int i) throws Exception {
        comboitem.setLabel(((Roles) o).getRole());
    }
}
