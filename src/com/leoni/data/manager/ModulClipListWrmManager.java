package com.leoni.data.manager;

import com.leoni.data.models.ModulClipListWrm;
import com.leoni.data.models.Moduls;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.1.2014
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */
public interface ModulClipListWrmManager  extends GenericManager<ModulClipListWrm> {
    public List<ModulClipListWrm> findById(int id);
    public List<ModulClipListWrm> findBySachNrBest(String sachNrBest);
    public List<ModulClipListWrm> findBySachNrLieferant(String sachNrLieferant);
    public ModulClipListWrm saveEditedModulClip(ModulClipListWrm modulClip, String user);
    public ModulClipListWrm addNewModulClip(Moduls moduls, String codeClip, boolean isBrett, String note, String conditionErzNr, String user);
    public List<ModulClipListWrm> findBy(String id,String sachNrLieferantSearch,String sachNrBestSearch,String ausfuehrungSearch,String prodGruppeSearch, String codeClipSearch);
    public File exportToFile(List<ModulClipListWrm> modulClipList);
    public List<ModulClipListWrm> findByModulId(Integer id);
}
