package com.leoni.viewModel;

import com.leoni.data.models.VsFarby;
import org.zkoss.image.AImage;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.10.2015
 * Time: 9:54
 * To change this template use File | Settings | File Templates.
 */
public class VsFarbaCbRenderer  implements ComboitemRenderer {
    @Override
    public void render(Comboitem comboitem, Object o, int i) throws Exception {
        VsFarby  vsFarby = (VsFarby) o;
        //comboitem.setLabel(vsFarby.getPopis());
        comboitem.setValue(vsFarby);
        comboitem.setImageContent(coerceToUi(vsFarby.getObrazok()));
        comboitem.setHeight("80px");
    }

    public AImage coerceToUi(byte[] beanProp) {
        try {
            if (beanProp != null && beanProp.length > 0) {
                AImage im = new AImage("", beanProp);
                return im;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
