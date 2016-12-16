package com.leoni.data.manager;

import com.leoni.data.models.VsFarby;
import org.springframework.stereotype.Service;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 16.12.2014
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
@Service("vsFarbyManager")
public class VsFarbyManagerImpl  extends GenericManagerImpl<VsFarby> implements VsFarbyManager{
    @Override
    public VsFarby addVsFarba( String popis, AImage obrazok, String user) {
        VsFarby vsFarby = new VsFarby();
        vsFarby.setPopis(popis);
        vsFarby.setObrazok(obrazok.getByteData());
        vsFarby.setChangedBy(user);
        vsFarby.setChangedDate(new Date());
        return create(vsFarby);
    }

    public VsFarby saveEditedVsFarba(VsFarby vsFarba, String user) {
        vsFarba.setChangedBy(user);
        vsFarba.setChangedDate(new Date());
        return saveOrUpdate(vsFarba);
    }
}
