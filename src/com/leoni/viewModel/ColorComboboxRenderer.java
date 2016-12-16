package com.leoni.viewModel;

import com.leoni.data.models.Color;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 27.7.2015
 * Time: 7:33
 * To change this template use File | Settings | File Templates.
 */
public class ColorComboboxRenderer  implements ComboitemRenderer {
    @Override
    public void render(Comboitem comboitem, Object o, int i) throws Exception {
        Color color = (Color) o;
        comboitem.setLabel(/*"   "*/color.getName());
        comboitem.setValue("   ");
        comboitem.setStyle("background:" + color.getName().toLowerCase() + "; color:" + color.getName().toLowerCase() +";");
    }
}
