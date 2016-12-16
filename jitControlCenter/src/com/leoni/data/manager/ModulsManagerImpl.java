package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.SicherungenRelais9X1Wrm;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 9.11.2012
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
@Service ("modulsManager")
public class ModulsManagerImpl extends GenericManagerImpl<Moduls> implements ModulsManager
    {
    public List<Moduls> findBySachNrBest(String sachNrBest)
        {

        CriteriaAppender[] criteriaAppenders = {new Equal("sachNrBest", sachNrBest)};
        return find(Arrays.asList(criteriaAppenders));
        }

    public List<Moduls> findBySachNrLieferant(String sachNrLieferant)
        {
        CriteriaAppender[] criteriaAppenders = {new Equal("sachNrLieferant", sachNrLieferant)};
        return find(Arrays.asList(criteriaAppenders));
        }

    public List<Moduls> findById(int id)
        {
            CriteriaAppender[] criteriaAppenders = {new Equal("id", id)};
            return find(Arrays.asList(criteriaAppenders));
        }

    public List<Moduls> findBySachNrBestAndSachNrLieferant(String sachNrBest, String sachNrLieferant)
        {
        CriteriaAppender[] criteriaAppenders = {new Equal("sachNrBest", sachNrBest),
                new Equal("sachNrLieferant", sachNrLieferant)};
        return find(Arrays.asList(criteriaAppenders));
        }



        public Moduls addCopiedModul(Moduls modul, String newSachNrBest, String newSachNrLief, String user)
        {
          Moduls newModul;
          //SicherungenRelais9X1WrmManager srman = new Sicherungenrelais9X1WrmManagerImpl();
          List<Moduls> lm = getAll();
          int oldId = modul.getId();
          modul.setId(null);
          modul.setSachNrBest(newSachNrBest);
          modul.setSachNrLieferant(newSachNrLief);
          modul.setCreateTime(new Timestamp((new Date()).getTime()));
            modul.setChangedDate(new Date());
            modul.setChangedBy(user);
            newModul=create(modul);
            return newModul;
        }
    public Moduls addNewModul(String sachNrBest,String sachNrLieferant,String prodGruppe,String kabelSatz,String ausfuehrung,boolean grund, boolean blocked, String commentary, String createPerson, String user){
        Moduls newModul=new Moduls();
        newModul.setSachNrBest(sachNrBest);
        newModul.setSachNrLieferant(sachNrLieferant);
        newModul.setProdGruppe(prodGruppe);
        newModul.setKabelsatzKz(kabelSatz);
        newModul.setAusfuehrung(ausfuehrung);
        newModul.setGrund(grund);
        newModul.setBlock(blocked);
        newModul.setCreatePerson(createPerson);
        newModul.setCommentary(commentary);
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        newModul.setCreateTime(new Timestamp((new Date()).getTime()));
        newModul.setChangedDate(new Date());
        newModul.setChangedBy(user);
        return create(newModul);
        }


    public Moduls saveEditedModul(Moduls modul, String user){
        modul.setChangedDate(new Date());
        modul.setChangedBy(user);
        return saveOrUpdate(modul);
        }

        public List<String> getAllModulsLief() {
           List<Moduls> modulsList = getAll();
           List<String> modulsListString = new ArrayList<String>();
            for (Moduls item : modulsList){
                  modulsListString.add(item.getSachNrLieferant().trim());
            }
            return modulsListString;
        }

        public File exportToFile(List<Moduls> modulsList) {
            File file;
            try {
                file = new File("report.csv");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("Sach. Nr.; Lief. Nr.;Prod. Gruppe;Ausfuehrung;Typ;Grund;Blokovany;Cas vytvorenia;Vytvoril;Komentar \n");
                for (Moduls item : modulsList){
                    bw.write(item.getSachNrBest()+";"+item.getSachNrLieferant()+";"+
                            item.getProdGruppe()+";"+item.getAusfuehrung()+";"+item.getKabelsatzKz()+
                            ";"+item.getGrund()+";"+item.getBlock()+";"+item.getCreateTime()+";"+item.getCreatePerson()+
                            ";"+item.getCommentary()+"\n"
                    );
                }
                bw.close();
                return file;
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return null;
        }


    }

