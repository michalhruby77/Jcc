package com.leoni.data.manager;

import com.leoni.data.models.Lpab62;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.Moduls;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.6.2014
 * Time: 9:35
 * To change this template use File | Settings | File Templates.
 */
public interface Lpab64Manager   extends GenericManager<Lpab64>{
    public List<Lpab64> findByProdnrAndKabelsatz (String prodNr, String kabelsatz);
    public List<Lpab64> findGrundByProdnrAndKabelsatz (String prodNr, String kabelsatz);
    public int addrecords(List<Moduls> modulsList, String prodNr, String ksToAdd);
    public void addModul(String sachNrBest, String sachNrLieferant, String prodnr, boolean isGrund, String kabelsatzKz);
}
