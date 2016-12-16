package com.leoni.viewModel.vm;

import com.leoni.data.models.vm.VmVariante;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 1.10.2015
 * Time: 7:56
 * To change this template use File | Settings | File Templates.
 */
public class VmVariantListRenderer  implements ComboitemRenderer {
    @Override
    public void render(Comboitem comboitem, Object o, int i) throws Exception {
        VmVariante vmVariante = (VmVariante) o;
        comboitem.setLabel(vmVariante.getName());
        comboitem.setValue("   ");

    }
}