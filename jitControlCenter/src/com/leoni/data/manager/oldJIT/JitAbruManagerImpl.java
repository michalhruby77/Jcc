package com.leoni.data.manager.oldJIT;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.models.CriticalNewModul;
import com.leoni.data.models.oldJIT.JitAbru;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.select.annotation.WireVariable;


import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.6.2014
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
@Service("jitAbruManager")
public class JitAbruManagerImpl  extends GenericManagerOldImpl<JitAbru> implements JitAbruManager{

    /*public CriticalNewModul getCriticalNewModul(String dfueNrEin, String sachNrBestInString, Session session) {
        String sachNrBestAll = "";
        String modulsInJitAuft = "";
        String modulsInJitTsji = "";
        List<String> sachNrBestList = getNewModulesList(sachNrBestInString);
        Map<String,Integer> modulsCountInProdNr = new HashMap<String, Integer>();
        for(String sachNrBest : sachNrBestList){
            if (!sachNrBestAll.equals(""))sachNrBestAll = sachNrBestAll + " + " + sachNrBest;
               else  sachNrBestAll = sachNrBestAll + sachNrBest;
            List<JitAbru> tempjitAbruList = session.createCriteria(JitAbru.class)
                    .add(Restrictions.eq("dfueNrEin", dfueNrEin))
                    .add(Restrictions.sqlRestriction("pab_daten_4916[6,20] = '"+sachNrBest.trim()+"'"))
                    .list();
            for (JitAbru item : tempjitAbruList){
                if (modulsCountInProdNr.containsKey(item.getProdNr().trim())){
                Integer tempCounter = modulsCountInProdNr.get(item.getProdNr().trim());
                        tempCounter++;
                modulsCountInProdNr.put(item.getProdNr().trim(),tempCounter);
                }
                else{modulsCountInProdNr.put(item.getProdNr().trim(),1);
            }
        }
    }

        for (Map.Entry<String, Integer> entry : modulsCountInProdNr.entrySet())
        { System.out.println("kontrola "+entry.getValue());
         if(entry.getValue()==sachNrBestList.size()){
             System.out.println("nasiel som modul");
             JitAbru tempJitAbru = (JitAbru) session.createCriteria(JitAbru.class)
                     .add(Restrictions.eq("dfueNrEin", dfueNrEin))
                     .add(Restrictions.sqlRestriction("pab_daten_4916[6,20] = '"+sachNrBestList.get(0).trim()+"'"))
                     .add(Restrictions.eq("prodNr", entry.getKey()))
                     .list().get(0);
             CriticalNewModul cnm = new CriticalNewModul();
             cnm.setSachNrBest(sachNrBestAll);
             cnm.setPrNr(entry.getKey());
             cnm.setDfueNrEin(dfueNrEin);
             cnm.setKundenNr(tempJitAbru.getKdNrDfue());
             cnm.setLiefDatum(tempJitAbru.getPabDaten().substring(58,64));
         return cnm;
         }
        }
    return null;
    }     */

        public List<String> getNewModulesList(String modulsString) {
            List<String> tempList = new ArrayList<String>();
            String[] modulsArray = modulsString.split("\\+");
            for (String item : modulsArray){
                tempList.add(item);
            }
            return tempList;
        }
    @Transactional (readOnly = true,value="transactionManagerOld")
    public Map<String,Set<String>> getModulesInCarMap(String dfueNrEin){
        Map<String,Set<String>> modulsInProdNr = new HashMap<String, Set<String>>();
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("dfueNrEin", dfueNrEin));
        caList.add(new Equal ("pabSatzart","664"));
        List<JitAbru> jitAbruList = find(caList);

           for (JitAbru item:jitAbruList){
               if(modulsInProdNr.containsKey(item.getProdNr().trim())){
                  Set<String> tempModulsSet = modulsInProdNr.get(item.getProdNr().trim());
                  tempModulsSet.add(item.getPabDaten().substring(5,19).trim());
                  modulsInProdNr.put(item.getProdNr().trim(),tempModulsSet);
               }
               else{
                   Set<String> tempModulsSet = new HashSet<String>();
                   tempModulsSet.add(item.getPabDaten().substring(5,19).trim());
                   modulsInProdNr.put(item.getProdNr().trim(),tempModulsSet);}
           }

        return modulsInProdNr;
    }

    @Transactional (value="transactionManagerOld",readOnly = true)
    public List<CriticalNewModul> checkForModules(String modulsString,Map<String,Set<String>> modulsInProdNr, String dfueNr){
        List<String> sachNrBestList = getNewModulesList(modulsString);
        List<CriticalNewModul> criticalNewModulList = new ArrayList<CriticalNewModul>();
        int counter;
            for (Map.Entry<String, Set<String>> entry : modulsInProdNr.entrySet()){
                counter=0;
                for(String sachNrBest : sachNrBestList){
                    if(entry.getValue().contains(sachNrBest.trim())) {counter++;}
                }
                if(counter==sachNrBestList.size()){
                    System.out.println("Nasiel som kriticky modul!!! sach nr: "+modulsString+" prod cislo: "+ entry.getKey()+" dfue: "+dfueNr);
                    CriticalNewModul cnm = new CriticalNewModul();
                    cnm.setSachNrBest(modulsString.trim());
                    cnm.setPrNr(entry.getKey().trim());
                    cnm.setDfueNrEin(dfueNr);
                    JitAbru tempJitAbru = (JitAbru) getSessionFactory().getCurrentSession().createCriteria(JitAbru.class)
                            .add(Restrictions.eq("dfueNrEin", cnm.getDfueNrEin().trim()))
                            .add(Restrictions.eq("prodNr", cnm.getPrNr().trim()))
                            .add(Restrictions.sqlRestriction("pab_daten_4916[6,20] = '"+sachNrBestList.get(0).trim()+"'"))
                            .list().get(0);
                    cnm.setKundenNr(tempJitAbru.getKdNrDfue().trim());
                    cnm.setLiefDatum(tempJitAbru.getPabDaten().substring(58,64));
                    criticalNewModulList.add(cnm);
                    System.out.println("hodnota counteru "+counter);
                }
    }
       // }

        //System.out.println(counter+"   +++   "+sachNrBestList.size()+" "+prodNr);
       //************************************************************************************************
       /* for(CriticalNewModul cnm : criticalNewModulList){
            String modulsInJitAuft = "";
            System.out.println("parametre: "+cnm.getDfueNrEin().trim()+" "+cnm.getPrNr()+" "+getNewModulesList(cnm.getSachNrBest()).get(0).trim());
            JitAbru tempJitAbru = (JitAbru) getSession().createCriteria(JitAbru.class)
                    .add(Restrictions.eq("dfueNrEin", cnm.getDfueNrEin().trim()))
                    .add(Restrictions.eq("prodNr", cnm.getPrNr().trim()))
                    .add(Restrictions.sqlRestriction("pab_daten_4916[6,20] = '"+getNewModulesList(cnm.getSachNrBest()).get(0).trim()+"'"))
                    .list().get(0);
            cnm.setKundenNr(tempJitAbru.getKdNrDfue());
            cnm.setLiefDatum(tempJitAbru.getPabDaten().substring(58,64));
            */
        //************************************************************************************************
       // System.out.println(criticalNewModulList.size());
        return criticalNewModulList;

    }
}
