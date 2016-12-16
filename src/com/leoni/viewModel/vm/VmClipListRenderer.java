package com.leoni.viewModel.vm;

import com.leoni.data.models.vm.VmClip;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 19.8.2015
 * Time: 8:03
 * To change this template use File | Settings | File Templates.
 */
public class VmClipListRenderer implements ComboitemRenderer {
    @Override
    public void render(Comboitem comboitem, Object o, int i) throws Exception {
        VmClip clip = (VmClip) o;
        comboitem.setLabel(/*"   "*/clip.getName());
        comboitem.setValue("   ");

    }
}
