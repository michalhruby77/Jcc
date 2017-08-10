package com.leoni.data.manager;

import com.leoni.data.models.Stoff;
import org.zkoss.image.AImage;

import java.util.List;

public interface StoffManager extends GenericManager<Stoff> {

    Stoff addStoff(String nazov, String stoffNr, AImage obrazok, String user);
    Stoff saveEditedStoff(Stoff stoff, String user);
    List<Stoff> findByStoffNr(String stoffNr);

}
