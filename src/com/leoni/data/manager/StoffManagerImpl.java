package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.Stoff;
import org.springframework.stereotype.Service;
import org.zkoss.image.AImage;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("stoffManager")
public class StoffManagerImpl extends GenericManagerImpl<Stoff> implements StoffManager {

    @Override
    public Stoff addStoff(String nazov, String stoffNr, AImage obrazok, String user) {
        Stoff stoff = new Stoff();
        stoff.setNazov(nazov);
        stoff.setStoffNr(stoffNr);
        stoff.setObrazok(obrazok.getByteData());
        stoff.setChangedBy(user);
        stoff.setChangedDate(new Date());
        return create(stoff);
    }

    @Override
    public Stoff saveEditedStoff(Stoff stoff, String user) {
        stoff.setChangedBy(user);
        stoff.setChangedDate(new Date());
        return saveOrUpdate(stoff);
    }

    @Override
    public List<Stoff> findByStoffNr(String stoffNr) {
        CriteriaAppender[] criteriaAppenders = {new Equal("stoffNr", stoffNr)};
        return find(Arrays.asList(criteriaAppenders));
    }
}
