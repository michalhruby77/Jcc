package com.leoni.data.manager;

import com.leoni.data.models.Harness;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.12.2013
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */

public interface BandManager extends GenericManager<Harness> {
    public List<Harness> findByBrettType(String brettType);
    public List<Harness> findByProdNr(String prodNr);
    public Harness updateHarness(Harness harness);
    public List<Harness> findByBrettTypeRL();
    public List<Harness> findByBrettTypeRLALL();
    public List<Harness> disableAll991();
    public List<Harness> disableAll981();
    public List<Harness> enableAll991();
    public List<Harness> enableAll981();
    public boolean isFirstBrettIdOk(String band_nameToEnable);
    public boolean is991enabled();
    public boolean is981enabled();
    public String getBrettTypeAndIdOnClip(String band_nameToEnable);
}
