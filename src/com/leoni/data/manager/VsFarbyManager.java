package com.leoni.data.manager;

import com.leoni.data.models.VsFarby;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 16.12.2014
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public interface VsFarbyManager extends GenericManager<VsFarby> {
    public VsFarby addVsFarba( String popis, AImage obrazok, String user);
    public VsFarby saveEditedVsFarba(VsFarby vsFarba, String user);
}
