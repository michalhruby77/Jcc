package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.Vs;
import com.leoni.data.models.VsModulyWrm;
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
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
@Service("vsModulyWrmManager")
public class VsModulyWrmManagerImpl  extends GenericManagerImpl<VsModulyWrm> implements VsModulyWrmManager {
    public List<VsModulyWrm> findById(int id) {
        CriteriaAppender[] criteriaAppenders = {new Equal("id", id)};
        return find(Arrays.asList(criteriaAppenders));
    }

    public List<VsModulyWrm> findBySachNrBest(String sachNrBest) {
        CriteriaAppender[] criteriaAppenders = {new Equal("moduls.sachNrBest", sachNrBest)};
        return find(Arrays.asList(criteriaAppenders));
    }

    public List<VsModulyWrm> findBySachNrLieferant(String sachNrLieferant) {
        CriteriaAppender[] criteriaAppenders = {new Equal("moduls.sachNrLieferant", sachNrLieferant)};
        return find(Arrays.asList(criteriaAppenders));
    }

    public VsModulyWrm saveEditedVsModul(VsModulyWrm vsModul, String user) {
        vsModul.setChangedBy(user);
        vsModul.setChangedDate(new Date());
        return saveOrUpdate(vsModul);
    }

    public VsModulyWrm addNewVsModul(Moduls moduls, String vodic, Double prierez, String popis, Vs vs, String user) {
        VsModulyWrm newVsModul = new VsModulyWrm();
        newVsModul.setModuls(moduls);
        newVsModul.setVodic(vodic);
        newVsModul.setPrierez(prierez);
        newVsModul.setPopis(popis);
        newVsModul.setVs(vs);
        newVsModul.setChangedBy(user);
        newVsModul.setChangedDate(new Date());
        return create(newVsModul);
    }

    public List<VsModulyWrm> SearchVsmodul(String id,String sachNrLieferantSearch,String sachNrBestSearch,String ausfuehrungSearch,String prodGruppeSearch, String vsSearch, String vodicSearch) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        if(!id.equals("")){caList.add(new Equal("id", Integer.parseInt(id)));}
        if(!sachNrLieferantSearch.equals("")){caList.add(new Like("moduls.sachNrLieferant", "%"+sachNrLieferantSearch.trim()+"%"));}
        if(!sachNrBestSearch.equals("")){caList.add(new Like("moduls.sachNrBest", "%"+sachNrBestSearch.trim()+"%"));}
        if(!ausfuehrungSearch.equals("")){caList.add(new Like("moduls.ausfuehrung", "%"+ausfuehrungSearch.trim()+"%"));}
        if(!prodGruppeSearch.equals("")){caList.add(new Like("moduls.prodGruppe", "%"+prodGruppeSearch.trim()+"%"));}
        if(!vsSearch.equals("")){caList.add(new Like("vs.nazov", "%"+vsSearch.trim()+"%"));}
        if(!vodicSearch.equals("")){caList.add(new Like("vodic", "%"+vodicSearch.trim()+"%"));}
        return find(caList);
    }

    public File exportToFile(List<VsModulyWrm> vsList) {
        File file;
        try {
            file = new File("report.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Sach. Nr.; Lief. Nr.;Prod. Gruppe;Ausfuehrung;Vodic;Prierez;Popis \n");
            for (VsModulyWrm item : vsList){
                bw.write(item.getModuls().getSachNrBest()+";"+item.getModuls().getSachNrLieferant()+";"+
                        item.getModuls().getProdGruppe()+";"+item.getModuls().getAusfuehrung()+";"+
                        item.getVodic()+";"+item.getPrierez()+";"+item.getVs().getNazov()+"\n"
                );
            }
            bw.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public List<VsModulyWrm> findByModulId(Integer id) {
        CriteriaAppender[] criteriaAppenders = {new Equal("moduls.id", id)};
        return find(Arrays.asList(criteriaAppenders));
    }

   /* @Override
    public void addVsModulList(List<VsModulyWrm> vsModulyWrmList, String user) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        for (VsModulyWrm item:vsModulyWrmList){
            caList.clear();
            {caList.add(new Like("moduls.sachNrLieferant", "%"+item.getModuls().getSachNrLieferant().trim()+"%"));}
            {caList.add(new Equal("id_vs", item.getId_vs()));}
            {caList.add(new Like("vodic", "%"+item.getVodic().trim()+"%"));}
            {caList.add(new Equal("prierez", item.getPrierez()));}
            if( find(caList).isEmpty()){
            item.setChangedBy(user);
            item.setChangedDate(new Date());
            create(item);               }

        }
    }*/

    @Override
    protected DetachedCriteria appendCriteria(DetachedCriteria criteria, List<CriteriaAppender> criteriaAppenders)
    {
        criteria.createAlias("vs", "vs");
        criteria.createAlias("moduls", "moduls");
        return super.appendCriteria(criteria, criteriaAppenders);
    }
}
