package com.leoni.data.manager;

import com.leoni.data.models.Nacharbeit;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 19.1.2015
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public interface NacharbeitManager  extends GenericManager<Nacharbeit>{
    public List<Nacharbeit> findByProdNrAndKs(String prodNr,String kabelSatz, String modus);
    public List<Nacharbeit> findByDate(String dateFrom,String dateTo, String kabelSatz, String modus);
    public File exportToFile(List<Nacharbeit> nacharbeitList);
    public List<Nacharbeit> getAll610();
    List<Nacharbeit> findByDateStatusAndSperre(String dateFrom,String dateTo, String timeFrom, String timeTo, String ksKz);
}
