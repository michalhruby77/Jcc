package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.models.Color;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.SchrittModulRm9X1Wrm;
import com.leoni.data.models.SicherungenRelais9X1Wrm;
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
 * Date: 10.1.2014
 * Time: 10:19
 * To change this template use File | Settings | File Templates.
 */
@Service("schrittManager")
public class SchrittModulRm9X1WrmManagerImpl   extends GenericManagerImpl<SchrittModulRm9X1Wrm> implements SchrittModulRm9X1WrmManager{
    public List<SchrittModulRm9X1Wrm> findById(int id) {
        CriteriaAppender[] criteriaAppenders = {new Equal("id", id)};
        return find(Arrays.asList(criteriaAppenders));
    }

    public List<SchrittModulRm9X1Wrm> findBySachNrBest(String sachNrBest) {
        CriteriaAppender[] criteriaAppenders = {new Equal("moduls.sachNrBest", sachNrBest)};
        return find(Arrays.asList(criteriaAppenders));
    }

    public List<SchrittModulRm9X1Wrm> findBySachNrLieferant(String sachNrLieferant) {
        CriteriaAppender[] criteriaAppenders = {new Equal("moduls.sachNrLieferant", sachNrLieferant)};
        return find(Arrays.asList(criteriaAppenders));
    }

    public SchrittModulRm9X1Wrm saveEditedSchritt(SchrittModulRm9X1Wrm schritt, String user) {
        schritt.setChangedBy(user);
        schritt.setChangedDate(new Date());
        return saveOrUpdate(schritt);
    }

    public SchrittModulRm9X1Wrm addNewSchritt(Moduls moduls, String variante1, String variante2, String variante3, String variante4, String seite, String schritt, String popis, String farba, String isCheck, String bandName, String user) {
        SchrittModulRm9X1Wrm newSchritt = new SchrittModulRm9X1Wrm();
        newSchritt.setModuls(moduls);
        newSchritt.setVariante1(variante1);
        newSchritt.setVariante2(variante2);
        newSchritt.setVariante3(variante3);
        newSchritt.setVariante4(variante4);
        newSchritt.setSeite(seite);
        newSchritt.setSchritt(schritt);
        newSchritt.setPopis(popis);
        newSchritt.setFarba(farba);
        newSchritt.setIsCheck(isCheck);
        newSchritt.setBandName(bandName);
        newSchritt.setChangedBy(user);
        newSchritt.setChangedDate(new Date());
        return create(newSchritt);
    }

    public List<SchrittModulRm9X1Wrm> findBy(String id, String sachNrLieferantSearch, String sachNrBestSearch, String ausfuehrungSearch, String prodGruppeSearch, String bandNameSearch, String seiteSearch, String schrittSearch) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        if(!id.equals("")){caList.add(new Equal("id", Integer.parseInt(id)));}
        if(!sachNrLieferantSearch.equals("")){caList.add(new Like("moduls.sachNrLieferant", "%"+sachNrLieferantSearch.trim()+"%"));}
        if(!sachNrBestSearch.equals("")){caList.add(new Like("moduls.sachNrBest", "%"+sachNrBestSearch.trim()+"%"));}
        if(!ausfuehrungSearch.equals("")){caList.add(new Like("moduls.ausfuehrung", "%"+ausfuehrungSearch.trim()+"%"));}
        if(!prodGruppeSearch.equals("")){caList.add(new Like("moduls.prodGruppe", "%"+prodGruppeSearch.trim()+"%"));}
        if(!bandNameSearch.equals("")){caList.add(new Like("bandName", "%"+bandNameSearch.trim()+"%"));}
        if(!seiteSearch.equals("")){caList.add(new Like("seite", "%"+seiteSearch.trim()+"%"));}
        if(!schrittSearch.equals("")){caList.add(new Like("schritt", "%"+schrittSearch.trim()+"%"));}


        return find(caList);
    }

    public File exportToFile(List<SchrittModulRm9X1Wrm> schrittList) {
        File file;
        try {
            file = new File("report.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Sach. Nr.; Lief. Nr.;Prod. Gruppe;Ausfuehrung;Seite;Schritt;Popis;Farba;Is check;band name \n");
            for (SchrittModulRm9X1Wrm item : schrittList){
                bw.write(item.getModuls().getSachNrBest()+";"+item.getModuls().getSachNrLieferant()+";"+
                        item.getModuls().getProdGruppe()+";"+item.getModuls().getAusfuehrung()+";"+item.getSeite()+
                        ";"+item.getSchritt()+";"+item.getPopis()+";"+item.getFarba()+";"+
                        item.getIsCheck()+";"+item.getBandName()+"\n"
                );
            }
            bw.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public List<SchrittModulRm9X1Wrm> findByModulIdSchrittSeite(Integer id, Integer schritt, String seite) {
        CriteriaAppender[] criteriaAppenders = {new Equal("moduls.id", id),new Equal("schritt", String.valueOf(schritt)),
                new Equal("seite", seite)};
        return find(Arrays.asList(criteriaAppenders));
    }

    @Override
    protected DetachedCriteria appendCriteria(DetachedCriteria criteria, List<CriteriaAppender> criteriaAppenders)
    {
        criteria.createAlias("moduls", "moduls");
        return super.appendCriteria(criteria, criteriaAppenders);
    }

}
