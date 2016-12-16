package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.models.Foam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 10.1.2014
 * Time: 9:13
 * To change this template use File | Settings | File Templates.
 */
@Service("foamManager")
public class FoamManagerImpl  extends GenericManagerImpl<Foam> implements FoamManager{
    public List<Foam> findBySachNrBest(String sachNrBest) {
        CriteriaAppender[] criteriaAppenders = {new Like("sachNrBest", "%"+sachNrBest+"%")};
        return find(Arrays.asList(criteriaAppenders));
    }

    public List<Foam> findBySachNrLieferant(String sachNrLieferant) {
        CriteriaAppender[] criteriaAppenders = {new Like("sachNrLief", "%"+sachNrLieferant+"%")};
        return find(Arrays.asList(criteriaAppenders));
    }

    public Foam saveEditedFoam(Foam foam, String user) {
        foam.setChangedBy(user);
        foam.setChangedDate(new Date());
        return saveOrUpdate(foam);
    }

    public Foam addNewFoam(String sachNrLief, String sachNrBest, double t1, double t2, double t3, double t4, double t5, String kabelsatzKz, String user) {
        Foam newFoam = new Foam();
        newFoam.setSachNrLief(sachNrLief);
        newFoam.setSachNrBest(sachNrBest);
        newFoam.setT1(t1);
        newFoam.setT2(t2);
        newFoam.setT3(t3);
        newFoam.setT4(t4);
        newFoam.setT5(t5);
        newFoam.setKabelsatzKz(kabelsatzKz);
        newFoam.setChangedBy(user);
        newFoam.setChangedDate(new Date());
        return create(newFoam);
    }

    public File exportToFile(List<Foam> foamList) {
        File file;
        try {
            file = new File("report.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Sach. Nr.; Lief. Nr.;T1;T2;T3;T4;T5;Kabelsatz \n");
            for (Foam item : foamList){
                bw.write(item.getSachNrBest()+";"+item.getSachNrLief()+";"+
                        item.getT1()+";"+item.getT2()+";"+item.getT3()+
                        ";"+item.getT4()+";"+item.getT5()+";"+item.getKabelsatzKz()+"\n"
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
