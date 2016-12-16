package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.models.Color;
import com.leoni.data.models.Moduls;
import com.leoni.data.models.Variant;
import com.leoni.data.models.Workplace;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2014
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
@Service("variantManager")
public class VariantManagerImpl  extends GenericManagerImpl<Variant> implements VariantManager {

    public Variant saveEditedVariant(Variant variant, String user) {
        variant.setChangedBy(user);
        variant.setChangedDate(new Date());
        return saveOrUpdate(variant);
    }

    public Variant addNewVariant(Workplace workplace, Color color, String name, String description, String scanString,
                                 boolean scanRequired, Integer modulsCount, Set<Moduls> modulsSet, String xmlModuls, String typ, String user) {
        Variant variant = new Variant();
        variant.setWorkplace(workplace);
        variant.setColor(color);
        variant.setName(name);
        variant.setDescription(description);
        variant.setScanString(scanString);
        variant.setScanRequired(scanRequired);
        variant.setModulsCount(modulsCount);
        variant.setModulsSet(modulsSet);
        variant.setTyp(typ);
        variant.setXmlModuls(xmlModuls);
        variant.setChangedBy(user);
        variant.setChangedDate(new Date());
        return create(variant);
    }

    public List<Variant> getAll() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Variant.class);
        detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Variant>) getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    public List<Variant> getSelected(List<CriteriaAppender> criteriaAppenderList) {
        DetachedCriteria criteria = appendCriteria(createCriteria(), criteriaAppenderList);
        //find(criteria, 0, 0);

        //DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Variant.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Variant>) getHibernateTemplate().findByCriteria(criteria);
    }

    public File exportToFile(List<Variant> variantList) {
        File file;
        try {
            file = new File("report.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Name; Description;Scan string;Scan;Count;Color;Workplace;Moduly \n");
            for (Variant item : variantList){
                bw.write(item.getName()+";"+item.getDescription()+";"+
                        item.getScanString()+";"+item.getScanRequired()+";"+item.getModulsCount()+";"+
                        item.getColor().getName()+";"+item.getWorkplace().getName()+";");
                        for(Moduls moduly : item.getModulsSet()){
                            bw.write(moduly.getSachNrLieferant());
                        }
                        bw.write("\n");

            }
            bw.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public Map<String,List<Variant>> getVariantMap(Set<Integer> modulsSet) {
        //System.out.println("ZACINAM");
        List<Variant> variantList = getAll();
        List<Variant> variantsToShow = new ArrayList<Variant>();
        Map<String,List<Variant>>  variantMap = new HashMap<>();
        for(Variant item : variantList){
            int modulCounter = 0;
            Set<Moduls> modulsInVariant = item.getModulsSet();
             for (Moduls modul : modulsInVariant){
                  if(modulsSet.contains(modul.getId())){modulCounter++;}
             }
            if(item.getModulsCount()==modulCounter&&item.getModulsCount()!=0){
                //System.out.println("workplacename "+item.getWorkplace().getName()+item.getTyp());
                String wpName = item.getWorkplace().getName().trim() + "-" + item.getTyp().trim();
              //----------------------------------------------------------------------------
               if (variantMap.containsKey(wpName)){
                   if(item.getModulsCount()>variantMap.get(wpName).get(0).getModulsCount()){
                       //variantMap.put(wpName,item);
                       List<Variant> tempList = new ArrayList<>();
                       tempList.add(item);
                       variantMap.put(wpName,tempList);
                   }
                   else if(item.getModulsCount()==variantMap.get(wpName).get(0).getModulsCount()){
                       List<Variant> tempList = variantMap.get(wpName);
                       tempList.add(item);
                       variantMap.put(wpName,tempList);
                   }
               }
               else{
                    List<Variant> tempList = new ArrayList<>();
                    tempList.add(item);
                    variantMap.put(wpName,tempList);
                   }
              //-----------------------------------------------------------------------------------------------------------
            }
        }
        System.out.println("----------------------------------");
        for (Map.Entry entry : variantMap.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
     return variantMap;
    }

    @Override
    public List<Variant> getSingleListVariantFromMap(Set<Integer> modulsSet, String key) {
        Map<String,List<Variant>> variantMap = getVariantMap(modulsSet);
        List<Variant> variantCandidateList = new ArrayList<>();
        for (Map.Entry entry : variantMap.entrySet()) {
            if (entry.getKey().toString().contains(key)) {
                if (variantCandidateList.isEmpty()) variantCandidateList = (List<Variant>)entry.getValue();
                else{
                    if(variantCandidateList.get(0).getModulsCount() < ((List<Variant>)entry.getValue()).get(0).getModulsCount()){
                        variantCandidateList = (List<Variant>)entry.getValue();
                    }
                    else if(variantCandidateList.get(0).getModulsCount() == ((List<Variant>)entry.getValue()).get(0).getModulsCount()){
                        variantCandidateList.addAll((List<Variant>)entry.getValue());
                    }
                }
            }
        }
        return variantCandidateList;
    }


    @Override
    public List<String> getAllTypes() {
        org.hibernate.Query q = getSessionFactory().getCurrentSession().createQuery("SELECT DISTINCT typ FROM Variant");
        //List list = q.list();
        return q.list();
    }

    @Override
    public List<Variant> getAllByType(String type, String group, String ausfuehrung) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        if(!type.trim().equals(""))caList.add(new Like("typ", "%"+type.trim()+"%"));
        if(!ausfuehrung.trim().equals(""))caList.add(new Like("workplace.name", "%1" + ausfuehrung.trim() + "%"));
        if(!group.trim().equals("")&&!ausfuehrung.trim().equals(""))caList.add(new Like("workplace.name", "%"+group.trim()+ausfuehrung.trim()+"%"));
        return getSelected(caList);
    }

    @Override
    public List<Variant> findVariantsWithModul(Moduls modul) {
        List<Variant> selectedVriants = new ArrayList<Variant>();
        List<Variant> allVriants = getAll();

        for (Variant tempVariant : allVriants) {
            //System.out.println("aaaaaaaaaaaa"+tempVariant.getId());
            Set<Moduls> tempModulsSet = tempVariant.getModulsSet();
            boolean isInselectedVriants = false;
            for (Moduls tempModul : tempModulsSet) {
                if (!isInselectedVriants&&tempModul.getSachNrLieferant().trim().equals(modul.getSachNrLieferant().trim())){
                    selectedVriants.add(tempVariant);
                    isInselectedVriants = true;
                }
            }
        }
        return selectedVriants;
    }


    public List<Variant> findBySachNrLieferant(String sachNrLieferant, String workplaceSearch, String descriptionSearch,
                                               String nameSearch, String scanStringSearch, String typSearch) {
        List<Variant> selectedVriants;
        if(workplaceSearch.equals("")&&descriptionSearch.equals("")&&nameSearch.equals("")&&scanStringSearch.equals("")&&typSearch.equals("")){
            selectedVriants = new ArrayList<Variant>();
            List<Variant> allVriants = getAll();

        for (Variant tempVariant : allVriants) {
            //System.out.println("aaaaaaaaaaaa"+tempVariant.getId());
            Set<Moduls> tempModulsSet = tempVariant.getModulsSet();
            boolean isInselectedVriants = false;
            for (Moduls tempModul : tempModulsSet) {
                if (!isInselectedVriants&&tempModul.getSachNrLieferant().trim().contains(sachNrLieferant)){
                selectedVriants.add(tempVariant);
                isInselectedVriants = true;
                }
            }
        }
        }
        else{

            if(sachNrLieferant.equals("")){
                selectedVriants = new ArrayList<Variant>();
                List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
                if(!descriptionSearch.equals("")){caList.add(new Like("description", "%"+descriptionSearch.trim()+"%"));}
                if(!workplaceSearch.equals("")){caList.add(new Like("workplace.name", "%"+workplaceSearch.trim()+"%"));}
                if(!nameSearch.equals("")){caList.add(new Like("name", "%"+nameSearch.trim()+"%"));}
                if(!scanStringSearch.equals("")){caList.add(new Like("scanString", "%"+scanStringSearch.trim()+"%"));}
                if(!typSearch.equals("")){caList.add(new Like("typ", "%"+typSearch.trim()+"%"));}
                selectedVriants = getSelected(caList);
            }
            else {
                selectedVriants = new ArrayList<Variant>();
            List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
            //caList.add(new Like("workplace.name", "%"+workplaceSearch+"%"));
                if(!descriptionSearch.equals("")){caList.add(new Like("description", "%"+descriptionSearch.trim()+"%"));}
                if(!workplaceSearch.equals("")){caList.add(new Like("workplace.name", "%"+workplaceSearch.trim()+"%"));}
                if(!nameSearch.equals("")){caList.add(new Like("name", "%"+nameSearch.trim()+"%"));}
                if(!scanStringSearch.equals("")){caList.add(new Like("scanString", "%"+scanStringSearch.trim()+"%"));}
                if(!typSearch.equals("")){caList.add(new Like("typ", "%"+typSearch.trim()+"%"));}
            List<Variant> allVriants = getSelected(caList);
            for (Variant tempVariant : allVriants) {
                boolean isInselectedVriants = false;
                Set<Moduls> tempModulsSet = tempVariant.getModulsSet();
                for (Moduls tempModul : tempModulsSet) {
                    if (!isInselectedVriants&&tempModul.getSachNrLieferant().trim().contains(sachNrLieferant)){selectedVriants.add(tempVariant);
                        isInselectedVriants = true;
                    }
                }
            }
        }
        }
        return selectedVriants;
    }

    @Override
    protected DetachedCriteria appendCriteria(DetachedCriteria criteria, List<CriteriaAppender> criteriaAppenders)
    {
        criteria.createAlias("workplace", "workplace");
        return super.appendCriteria(criteria, criteriaAppenders);
    }
}
