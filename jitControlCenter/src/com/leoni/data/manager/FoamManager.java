package com.leoni.data.manager;

import com.leoni.data.models.Foam;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 10.1.2014
 * Time: 9:14
 * To change this template use File | Settings | File Templates.
 */
public interface FoamManager  extends GenericManager<Foam> {
    public List<Foam> findBySachNrBest(String sachNrBest);
    public List<Foam> findBySachNrLieferant(String sachNrLieferant);
    public Foam saveEditedFoam(Foam foam, String user);
    public Foam addNewFoam(String SachNrLief, String SachNrBest, double t1, double t2, double t3, double t4, double t5, String kabelsatzKz, String user);
    public File exportToFile(List<Foam> foamList);
}
