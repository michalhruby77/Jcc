package com.leoni.data.manager;


import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.models.ModulClipListWrm;
import com.leoni.data.models.Moduls;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 13.1.2014
 * Time: 10:43
 * To change this template use File | Settings | File Templates.
 */
@Service("modulClipListWrmManager")
public class ModulClipListWrmManagerImpl    extends GenericManagerImpl<ModulClipListWrm> implements ModulClipListWrmManager{
    public List<ModulClipListWrm> findById(int id) {
        CriteriaAppender[] criteriaAppenders = {new Equal("id", id)};
        return find(Arrays.asList(criteriaAppenders));
    }

    public List<ModulClipListWrm> findBySachNrBest(String sachNrBest) {
        CriteriaAppender[] criteriaAppenders = {new Equal("moduls.sachNrBest", sachNrBest)};
        return find(Arrays.asList(criteriaAppenders));
    }

    public List<ModulClipListWrm> findBySachNrLieferant(String sachNrLieferant) {
        CriteriaAppender[] criteriaAppenders = {new Equal("moduls.sachNrLieferant", sachNrLieferant)};
        return find(Arrays.asList(criteriaAppenders));
    }

    public ModulClipListWrm saveEditedModulClip(ModulClipListWrm modulClip, String user) {
        modulClip.setChangedDate(new Date());
        modulClip.setChangedBy(user);
        return saveOrUpdate(modulClip);
    }

    public ModulClipListWrm addNewModulClip(Moduls moduls, String codeClip, boolean isBrett, String note, String conditionErzNr, String user) {
        ModulClipListWrm newModulClip = new ModulClipListWrm();
        newModulClip.setModuls(moduls);
        newModulClip.setCodeClip(codeClip);
        newModulClip.setIsBrett(isBrett);
        newModulClip.setNote(note);
        newModulClip.setConditionErzNr(conditionErzNr);
        newModulClip.setChangedDate(new Date());
        newModulClip.setChangedBy(user);
        return create(newModulClip);
    }

    public List<ModulClipListWrm> findBy(String id, String sachNrLieferantSearch, String sachNrBestSearch, String ausfuehrungSearch, String prodGruppeSearch, String codeClipSearch) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        if(!id.equals("")){caList.add(new Equal("id", Integer.parseInt(id)));}
        if(!sachNrLieferantSearch.equals("")){caList.add(new Like("moduls.sachNrLieferant", "%"+sachNrLieferantSearch.trim()+"%"));}
        if(!sachNrBestSearch.equals("")){caList.add(new Like("moduls.sachNrBest", "%"+sachNrBestSearch.trim()+"%"));}
        if(!ausfuehrungSearch.equals("")){caList.add(new Like("moduls.ausfuehrung", "%"+ausfuehrungSearch.trim()+"%"));}
        if(!prodGruppeSearch.equals("")){caList.add(new Like("moduls.prodGruppe", "%"+prodGruppeSearch.trim()+"%"));}
        if(!codeClipSearch.equals("")){caList.add(new Like("codeClip", "%"+codeClipSearch.trim()+"%"));}
        return find(caList);
    }

    public File exportToFile(List<ModulClipListWrm> modulClipList) {
        File file;
        try {
            file = new File("report.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Sach. Nr.; Lief. Nr.;Prod. Gruppe;Ausfuehrung;Code clip;Is brett;Note;Condition erz. nr. \n");
            for (ModulClipListWrm item : modulClipList){
                bw.write(item.getModuls().getSachNrBest()+";"+item.getModuls().getSachNrLieferant()+";"+
                        item.getModuls().getProdGruppe()+";"+item.getModuls().getAusfuehrung()+";"+item.getCodeClip()+
                        ";"+item.getIsBrett()+";"+item.getNote()+";"+item.getConditionErzNr()+"\n"
                );
            }
            bw.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public List<ModulClipListWrm> findByModulId(Integer id) {
        CriteriaAppender[] criteriaAppenders = {new Equal("moduls.id", id)};
        return find(Arrays.asList(criteriaAppenders));
    }

    @Override
    protected DetachedCriteria appendCriteria(DetachedCriteria criteria, List<CriteriaAppender> criteriaAppenders)
    {
        criteria.createAlias("moduls", "moduls");
        return super.appendCriteria(criteria, criteriaAppenders);
    }
}
