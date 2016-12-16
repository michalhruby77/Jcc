package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.Moduls;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.6.2014
 * Time: 9:36
 * To change this template use File | Settings | File Templates.
 */
@Service("lpab64Manager")
public class Lpab64ManagerImpl  extends GenericManagerImpl<Lpab64> implements Lpab64Manager{

    public List<Lpab64> findByProdnrAndKabelsatz(String prodNr, String kabelsatz) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("prodNr", prodNr));
        caList.add(new Equal("kabelsatzKz",kabelsatz));
        return find(caList);
    }
    @Transactional
    public int addrecords(List<Moduls> modulsList, String prodNr) {
        int numberOfRecords = 0;
        Lpab64 newModul = new Lpab64();
        newModul.setProdNr(prodNr);
        for (Moduls item : modulsList){
            newModul.setSachNrLieferant(item.getSachNrLieferant());
            newModul.setSachNrBest(item.getSachNrBest());
            newModul.setKabelsatzKz(item.getKabelsatzKz());
            if(item.isGrund())newModul.setGrundmodul_kz("J");
            else newModul.setGrundmodul_kz("N");
            create(newModul);
            numberOfRecords++;
        }
    return numberOfRecords;
    }

    public void addModul(String sachNrBest, String sachNrLieferant, String prodnr, boolean isGrund, String kabelsatzKz) {
        Lpab64 newModul = new Lpab64();
        newModul.setSachNrBest(sachNrBest);
        newModul.setSachNrLieferant(sachNrLieferant);
        newModul.setProdNr(prodnr);
        newModul.setKabelsatzKz(kabelsatzKz);
        if(isGrund)newModul.setGrundmodul_kz("J");
        else newModul.setGrundmodul_kz("N");
        create(newModul);
    }
}
