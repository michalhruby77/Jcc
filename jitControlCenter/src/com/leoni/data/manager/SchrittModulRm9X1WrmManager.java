package com.leoni.data.manager;

import com.leoni.data.models.Color;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.SchrittModulRm9X1Wrm;
import com.leoni.data.models.SicherungenRelais9X1Wrm;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 10.1.2014
 * Time: 10:19
 * To change this template use File | Settings | File Templates.
 */
public interface SchrittModulRm9X1WrmManager extends GenericManager<SchrittModulRm9X1Wrm> {
    public List<SchrittModulRm9X1Wrm> findById(int id);
    public List<SchrittModulRm9X1Wrm> findBySachNrBest(String sachNrBest);
    public List<SchrittModulRm9X1Wrm> findBySachNrLieferant(String sachNrLieferant);
    public SchrittModulRm9X1Wrm saveEditedSchritt(SchrittModulRm9X1Wrm relay, String user);
    public SchrittModulRm9X1Wrm addNewSchritt(Moduls moduls, String variante1, String variante2, String variante3, String variante4, String seite, String schritt, String popis, String farba, String isCheck, String bandName, String user);
    public List<SchrittModulRm9X1Wrm> findBy(String id,String sachNrLieferantSearch,String sachNrBestSearch,String ausfuehrungSearch,String prodGruppeSearch, String bandNameSearch,  String seiteSearch, String schrittSearch);
    public File exportToFile(List<SchrittModulRm9X1Wrm> schrittList);
    public List<SchrittModulRm9X1Wrm> findByModulIdSchrittSeite(Integer id, Integer schritt, String seite);
}
