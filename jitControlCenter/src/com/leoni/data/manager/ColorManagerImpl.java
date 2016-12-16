package com.leoni.data.manager;

import com.leoni.data.models.Color;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2014
 * Time: 9:28
 * To change this template use File | Settings | File Templates.
 */
@Service("colorManager")
public class ColorManagerImpl extends GenericManagerImpl<Color> implements ColorManager{

    public Color saveEditedColor(Color color) {
        return saveOrUpdate(color);
    }

    public Color addNewColor(String name, String rgbValue) {
        Color color = new Color();
        color.setName(name);
        color.setRgbValue(rgbValue);
        return create(color);
    }
}
