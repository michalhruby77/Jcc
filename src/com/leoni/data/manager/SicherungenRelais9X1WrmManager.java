package com.leoni.data.manager;

import com.leoni.data.models.Moduls;
import com.leoni.data.models.SicherungenRelais9X1Wrm;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 18.1.2013
 * Time: 8:54
 * To change this template use File | Settings | File Templates.
 */
public interface SicherungenRelais9X1WrmManager extends GenericManager<SicherungenRelais9X1Wrm>
    {
    public List<SicherungenRelais9X1Wrm> findById(int id);
    public List<SicherungenRelais9X1Wrm> findBySachNrBest(String sachNrBest);
    public List<SicherungenRelais9X1Wrm> findBySachNrLieferant(String sachNrLieferant);
    public SicherungenRelais9X1Wrm saveEditedRelay(SicherungenRelais9X1Wrm relay, String user);
    public SicherungenRelais9X1Wrm addNewRelay(Moduls moduls, String box, String platz, String wert, String beschreibung, String user);
    public List<String> getBoxList();
    public List<String> getPlatzList(String box);
    public List<SicherungenRelais9X1Wrm> findBy(String id,String sachNrLieferantSearch,String sachNrBestSearch,
             String ausfuehrungSearch,String prodGruppeSearch, String boxSearch,  String platzSearch, String wertSearch);
    public File exportToFile(List<SicherungenRelais9X1Wrm> sicherList);
    public List<SicherungenRelais9X1Wrm> findByModulId(Integer id);
    }
