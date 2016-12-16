package com.leoni.data.manager;

import com.leoni.data.models.DmeVarianteName;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.8.2014
 * Time: 9:02
 * To change this template use File | Settings | File Templates.
 */
public interface ServletManager {
    public byte[] getDmeSwImg(String prodNr, String kabelsatzKz);
    public byte[] getDmeGrImg(String prodNr, String kabelsatzKz);
    public byte[] getDmeGrImg(List<String> modulesInProdNr);
    public byte[] getDmeSwImg(List<String> modulesInProdNr);
}
