package com.leoni.data.manager.oldJIT;

import com.leoni.data.criterion.Between;
import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.nonDBmodels.JanzmgrpObj;
import com.leoni.data.models.oldJIT.Jitpab62;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 25.11.2014
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
@Service("jitpab62Manager")
@Transactional( value="transactionManagerOld", readOnly = true)
public class Jitpab62ManagerImpl  extends GenericManagerOldImpl<Jitpab62> implements Jitpab62Manager{


  public SortedSet<JanzmgrpObj> selecteInnerJoinJitpab62Jitpab64(String kundenNr, String dateFrom, String dateTo){
       String query;
        if(kundenNr.equals("*")){
            query = "SELECT distinct  jitpab62.prodNr,jitpab62.kabelsatzKz,jitpab62.lieferDatum, jitpab62.ausfuehrung,jitpab64.grundmodulKz,jitpab64.sachNrBest " +
                    "FROM Jitpab62 jitpab62, " +
                    "Jitpab64 jitpab64 " +
                    "WHERE jitpab62.lieferDatum>=" + dateFrom + " and "+"jitpab62.lieferDatum<=" + dateTo +
                    " and jitpab64.grundmodulKz='J'" +
                    " and jitpab64.kabelsatzKz='F'" + " and  jitpab62.prodNr=jitpab64.prodNr and  jitpab62.kabelsatzKz=jitpab64.kabelsatzKz";
        }
        else{
        query = "SELECT distinct  jitpab62.prodNr,jitpab62.kabelsatzKz,jitpab62.lieferDatum, jitpab62.ausfuehrung,jitpab64.grundmodulKz,jitpab64.sachNrBest " +
                "FROM Jitpab62 jitpab62, " +
                "Jitpab64 jitpab64 " +
                "WHERE jitpab62.lieferDatum>=" + dateFrom + " and "+"jitpab62.lieferDatum<=" + dateTo +
                " and jitpab62.kundenNr = " + kundenNr + " and jitpab64.grundmodulKz='J'" +
                " and jitpab64.kabelsatzKz='F'" + " and  jitpab62.prodNr=jitpab64.prodNr and  jitpab62.kabelsatzKz=jitpab64.kabelsatzKz";}
        Query query1 = getSessionFactory().getCurrentSession().createQuery(query);
        query1.setFetchSize(Integer.valueOf(1000));
        query1.setReadOnly(true);
        query1.setLockMode("a", LockMode.NONE);
        ScrollableResults results = query1.scroll(ScrollMode.FORWARD_ONLY);
        SortedSet<JanzmgrpObj> janzmgrpObjSet = new TreeSet<>();
        while (results.next()) {
            //Object o[]=(Object[])it.next();
            //System.out.println(o[0] + " " + o[1]+"++++++++++++++++++++++++++++++++++++++++++++++++ "+o[3]+" "+o[5]);
            JanzmgrpObj janzmgrpObj = new JanzmgrpObj();
            String prodNr = ((String) results.get(0)).trim();
            janzmgrpObj.setKsKz(((String)results.get(1)).trim());
            janzmgrpObj.setAusfuehrung(((String)results.get(3)).trim());
            janzmgrpObj.setGroup((((String)results.get(5)).trim()).substring(0, 3));
            if(!janzmgrpObjSet.contains(janzmgrpObj)){
                Set<String> prodNrSet = new HashSet<>();
                prodNrSet.add(prodNr);
                janzmgrpObj.setProdNrSet(prodNrSet);
                janzmgrpObjSet.add(janzmgrpObj);

            }
            else{
                Iterator itSet = janzmgrpObjSet.iterator();
                while(itSet.hasNext())
                {
                    JanzmgrpObj  janzmgrpObj1 = (JanzmgrpObj) itSet.next();
                    if (janzmgrpObj1.getGroup().trim().equals(janzmgrpObj.getGroup().trim())&&
                            janzmgrpObj1.getAusfuehrung().trim().equals(janzmgrpObj.getAusfuehrung().trim())&&
                            janzmgrpObj1.getKsKz().trim().equals(janzmgrpObj.getKsKz().trim())){
                        Set prodNrSet = janzmgrpObj1.getProdNrSet();
                        prodNrSet.add(prodNr) ;
                        janzmgrpObj1.setProdNrSet(prodNrSet);
                    }
                }
            }
        }
        results.close();
        return janzmgrpObjSet;
    }

    public int selecteInnerJoinJitpab62Jitpab64E(String kundenNr, String dateFrom, String dateTo){
        Query query;
        if(kundenNr.equals("*")){
        query = getSessionFactory().getCurrentSession().createQuery("SELECT  sum(jitpab64.abrufMenge) FROM Jitpab62 jitpab62, Jitpab64 jitpab64 "
                + "WHERE jitpab62.lieferDatum>=" + dateFrom + " and "+"jitpab62.lieferDatum<=" + dateTo +
                " and jitpab64.kabelsatzKz='E'" + " and  jitpab62.prodNr=jitpab64.prodNr and  jitpab62.kabelsatzKz=jitpab64.kabelsatzKz");}
        else{
            query = getSessionFactory().getCurrentSession().createQuery("SELECT  sum(jitpab64.abrufMenge) FROM Jitpab62 jitpab62, Jitpab64 jitpab64 "
                    + "WHERE jitpab62.lieferDatum>=" + dateFrom + " and "+"jitpab62.lieferDatum<=" + dateTo + " and jitpab62.kundenNr = " + kundenNr +
                    " and jitpab64.kabelsatzKz='E'" + " and  jitpab62.prodNr=jitpab64.prodNr and  jitpab62.kabelsatzKz=jitpab64.kabelsatzKz");
        }
        int count = ((Long)query.uniqueResult()).intValue();
        return count;
    }

    public int selecteInnerJoinJitpab62Jitpab64R(String kundenNr, String dateFrom, String dateTo){
        Query query;
        if(kundenNr.equals("*")){
        query = getSessionFactory().getCurrentSession().createQuery("SELECT  sum(jitpab64.abrufMenge) FROM Jitpab62 jitpab62, Jitpab64 jitpab64 "
                + "WHERE jitpab62.lieferDatum>=" + dateFrom + " and "+"jitpab62.lieferDatum<=" + dateTo +
                " and jitpab64.kabelsatzKz='R'" + " and  jitpab62.prodNr=jitpab64.prodNr and  jitpab62.kabelsatzKz=jitpab64.kabelsatzKz");}
        else{
            query = getSessionFactory().getCurrentSession().createQuery("SELECT  sum(jitpab64.abrufMenge) FROM Jitpab62 jitpab62, Jitpab64 jitpab64 "
                    + "WHERE jitpab62.lieferDatum>=" + dateFrom + " and "+"jitpab62.lieferDatum<=" + dateTo +  " and jitpab62.kundenNr = " + kundenNr +
                    " and jitpab64.kabelsatzKz='E'" + " and  jitpab62.prodNr=jitpab64.prodNr and  jitpab62.kabelsatzKz=jitpab64.kabelsatzKz");
        }
        int count = ((Long)query.uniqueResult()).intValue();
        return count;
    }
}
