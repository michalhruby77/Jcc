package com.leoni.data.manager;

import com.leoni.data.models.Moduls;
import com.leoni.data.models.Variant;
import com.leoni.data.models.Vs;
import com.leoni.data.models.VsModulyWrm;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.1.2014
 * Time: 11:03
 * To change this template use File | Settings | File Templates.
 */
public interface VsModulyWrmManager  extends GenericManager<VsModulyWrm>{
    public List<VsModulyWrm> findById(int id);
    public List<VsModulyWrm> findBySachNrBest(String sachNrBest);
    public List<VsModulyWrm> findBySachNrLieferant(String sachNrLieferant);
    public VsModulyWrm saveEditedVsModul(VsModulyWrm vsModul, String user);
    public VsModulyWrm addNewVsModul(Moduls moduls, String vodic, Double prierez, String popis, Vs vs, String user);
    public List<VsModulyWrm> SearchVsmodul(String id,String sachNrLieferantSearch,String sachNrBestSearch,String ausfuehrungSearch,String prodGruppeSearch, String vsSearch, String vodicSearch);
    public File exportToFile(List<VsModulyWrm> vsList);
    public List<VsModulyWrm> findByModulId(Integer id);
    //public void addVsModulList(List<VsModulyWrm> vsModulyWrmList, String user);
}
