package com.leoni.data.manager;

import com.leoni.data.models.*;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 9.11.2012
 * Time: 11:12
 * To change this template use File | Settings | File Templates.
 */
public interface ModulsManager extends GenericManager<Moduls>
    {
    public List<Moduls> findById(int id);
    public List<Moduls> findBySachNrBest(String sachNrBest);
    public List<Moduls> findBySachNrLieferant(String sachNrLieferant);
    public List<Moduls> findBySachNrBestAndSachNrLieferant(String sachNrBest, String sachNrLieferant);
    public Moduls addCopiedModul(Moduls modul, String newSachNrBest, String newSachNrLief, String user);
    public Moduls addNewModul(String sachNrBest,String sachNrLieferant,String prodGruppe,String kabelSatz,
                              String ausfuehrung,boolean grund, boolean blocked, String commentary,
                              List<ModulsAlias> aliasList, String createPerson, String user, String foamForm, String carBody,
                              Color color, Integer difficulty, String assTime);
    public Moduls saveEditedModul(Moduls modul, String user);
    public List<String> getAllModulsLief();
    public File exportToFile(List<Moduls> modulsList);
    public boolean containsDiffModul(List<Lpab64> modulsList);

    }
