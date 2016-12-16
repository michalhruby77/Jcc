package com.leoni.data.manager;

import com.leoni.data.models.Color;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2014
 * Time: 9:29
 * To change this template use File | Settings | File Templates.
 */
public interface ColorManager extends GenericManager<Color> {
    public Color saveEditedColor(Color color);
    public Color addNewColor(String name, String rgbValue);
}
