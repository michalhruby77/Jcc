package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.SicherungenRelais9X1Wrm;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 18.1.2013
 * Time: 8:55
 * To change this template use File | Settings | File Templates.
 */

@Service ("sicherungenRelais9X1WrmManager")
public class Sicherungenrelais9X1WrmManagerImpl extends GenericManagerImpl<SicherungenRelais9X1Wrm> implements SicherungenRelais9X1WrmManager
    {

    private static List<String> boxList = Arrays.asList(
            "",
            "relais_treager1_vorne",
            "relais_treager2_hinten",
            "sicherungs_dose_links",
            "sicherungs_dose_rechts");

    private static final Map<String, List<String>> platzMap = new HashMap<String, List<String>>()
    {
    {
    put("relais_treager1_vorne", Arrays.asList("", "K1", "K2A", "K2B", "K3", "K4", "K5", "K6", "K7", "K8", "K9"));
    put("relais_treager2_hinten", Arrays.asList("", "K1", "K2", "K3", "K4", "K5", "K6A", "K6B", "K7A", "K7B", "K8", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5"));
    put("sicherungs_dose_rechts", Arrays.asList("", "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10"));
    put("sicherungs_dose_links", Arrays.asList("", "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "E", "F"));
    }
    };

        public List<SicherungenRelais9X1Wrm> findById(int id) {
            CriteriaAppender[] criteriaAppenders = {new Equal("id", id)};
            return find(Arrays.asList(criteriaAppenders));
        }

        public List<SicherungenRelais9X1Wrm> findBySachNrBest(String sachNrBest) {
            CriteriaAppender[] criteriaAppenders = {new Equal("moduls.sachNrBest", sachNrBest)};
            return find(Arrays.asList(criteriaAppenders));
        }

        public List<SicherungenRelais9X1Wrm> findBySachNrLieferant(String sachNrLieferant) {
            CriteriaAppender[] criteriaAppenders = {new Equal("moduls.sachNrLieferant", sachNrLieferant)};
            return find(Arrays.asList(criteriaAppenders));
        }

        public SicherungenRelais9X1Wrm saveEditedRelay(SicherungenRelais9X1Wrm relay, String user){
            relay.setChangedBy(user);
            relay.setChangedDate(new Date());
            return saveOrUpdate(relay);
        }

        public SicherungenRelais9X1Wrm addNewRelay(Moduls moduls, String box, String platz, String wert, String beschreibung, String user) {
            SicherungenRelais9X1Wrm newRelay = new SicherungenRelais9X1Wrm();
            newRelay.setModuls(moduls);
            newRelay.setBox(box);
            newRelay.setPlatz(platz);
            newRelay.setWert(wert);
            newRelay.setBeschreibung(beschreibung);
            newRelay.setChangedDate(new Date());
            newRelay.setChangedBy(user);
            return create(newRelay);
        }

        public List<String> getBoxList()
        {
        return boxList;
        }


    public List<String> getPlatzList(String box)
        {
        return platzMap.containsKey(box) ? platzMap.get(box) : null;
        }

        public List<SicherungenRelais9X1Wrm> findBy(String id, String sachNrLieferantSearch, String sachNrBestSearch, String ausfuehrungSearch, String prodGruppeSearch, String boxSearch, String platzSearch, String wertSearch) {
            List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
            if(!id.equals("")){caList.add(new Equal("id", Integer.parseInt(id)));}
            if(!sachNrLieferantSearch.equals("")){caList.add(new Like("moduls.sachNrLieferant", "%"+sachNrLieferantSearch.trim()+"%"));}
            if(!sachNrBestSearch.equals("")){caList.add(new Like("moduls.sachNrBest", "%"+sachNrBestSearch.trim()+"%"));}
            if(!ausfuehrungSearch.equals("")){caList.add(new Like("moduls.ausfuehrung", "%"+ausfuehrungSearch.trim()+"%"));}
            if(!prodGruppeSearch.equals("")){caList.add(new Like("moduls.prodGruppe", "%"+prodGruppeSearch.trim()+"%"));}
            if(!boxSearch.equals("")){caList.add(new Like("box", "%"+boxSearch.trim()+"%"));}
            if(!platzSearch.equals("")){caList.add(new Like("platz", "%"+platzSearch.trim()+"%"));}
            if(!wertSearch.equals("")){caList.add(new Like("wert", "%"+wertSearch.trim()+"%"));}


            return find(caList);
        }

        public File exportToFile(List<SicherungenRelais9X1Wrm> sicherList) {
            File file;
            try {
                file = new File("report.csv");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("Sach. Nr.; Lief. Nr.;Prod. Gruppe;Ausfuehrung;Box;Platz;Wert;Beschreibung \n");
                for (SicherungenRelais9X1Wrm item : sicherList){
                    bw.write(item.getModuls().getSachNrBest()+";"+item.getModuls().getSachNrLieferant()+";"+
                            item.getModuls().getProdGruppe()+";"+item.getModuls().getAusfuehrung()+";"+item.getBox()+
                            ";"+item.getPlatz()+";"+item.getWert()+";"+item.getBeschreibung()+"\n"
                    );
                }
                bw.close();
                return file;
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return null;
        }

        public List<SicherungenRelais9X1Wrm> findByModulId(Integer id) {
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
