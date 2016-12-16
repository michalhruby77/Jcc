package com.leoni.data.manager;

import com.leoni.data.models.Moduls;

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
    public List<Moduls> findBySachNrBest(String sachNrBest);
    public List<Moduls> findBySachNrLieferant(String sachNrLieferant);
    public List<Moduls> findBySachNrBestAndSachNrLieferant(String sachNrBest, String sachNrLieferant);
    }
