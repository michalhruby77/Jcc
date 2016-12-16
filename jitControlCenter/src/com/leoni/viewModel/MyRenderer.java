package com.leoni.viewModel;

import com.leoni.data.models.Moduls;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 25.8.2014
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public class MyRenderer implements ComboitemRenderer {

    public void render(Comboitem item, Object data, int index) throws Exception {
        item.setLabel(((Moduls) data).getSachNrLieferant());
    }
}
