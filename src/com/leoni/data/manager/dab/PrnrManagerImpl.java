package com.leoni.data.manager.dab;

import com.leoni.data.criterion.*;
import com.leoni.data.models.dab.Prnr;
import com.leoni.data.models.nonDBmodels.PrnrLpab64Obj;
import com.leoni.data.models.nonDBmodels.ReportResultObj;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 25.8.2015
 * Time: 9:34
 * To change this template use File | Settings | File Templates.
 */
@Service("prnrManager")
@Transactional( value="transactionManagerDab")
public class PrnrManagerImpl extends HibernateDaoSupport implements PrnrManager {

    @Autowired
    public void init(SessionFactory sessionFactoryDab)
    {
        setSessionFactory(sessionFactoryDab);
    }

    @Override
    public List<ReportResultObj> getPrnrByProdNrList(List<String> prodnrList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prodnrList.size(); i++){
           if (i == 0) sb.append("'" + prodnrList.get(i)+ "'");
           else sb.append(",'" + prodnrList.get(i) + "'");
        }

        String prodNrListSr = sb.toString();

        String queryStr = "SELECT distinct  prnr.prodDate, prnr.prodNr, prnr.customerNr, prnr.statusIn " +
                "FROM Prnr prnr " +
                "WHERE prnr.kabelsatzKz = 'F' and prnr.prodNr in (" + prodNrListSr +")";
        Query query1 = getSessionFactory().getCurrentSession().createQuery(queryStr);
        ScrollableResults results = query1.scroll(ScrollMode.FORWARD_ONLY);
        List<ReportResultObj> resultObjList = new ArrayList<>();
        while (results.next()) {
            ReportResultObj item = new ReportResultObj((String)results.get(1),(String)results.get(2),
                    ((Integer)results.get(0)).toString(),(String)results.get(3));
            resultObjList.add(item);
            //System.out.println (results.get(0) + "   " + results.get(1) + " " + results.get(2) + " " +  results.get(3));
        }
        return resultObjList;
    }

    @Override
    public List<Prnr> getPrnrList(Integer prodDate, String ksKz, String customerNr) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new IsNull("statusPrint"));
        caList.add(new IsNull("statusBand"));
        caList.add(new Equal("prodDate", prodDate));
        caList.add(new Equal("kabelsatzKz",ksKz));
        caList.add(new Equal("customerNr",customerNr));

        return (List<Prnr>) getHibernateTemplate().findByCriteria(appendCriteria(createCriteria(), caList));

    }

    @Override
    public List<Prnr> getPrnr(String prodNr, String ksKz) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("kabelsatzKz",ksKz));
        caList.add(new Equal("prodNr",prodNr));
        return (List<Prnr>) getHibernateTemplate().findByCriteria(appendCriteria(createCriteria(), caList));
    }

    @Override
    public Prnr saveOrUpdate(Prnr prnr) {
        getHibernateTemplate().saveOrUpdate(prnr);
        return prnr;
    }

    @Override
    public List<Prnr> getPrnrList( String ksKz, String datum) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        //caList.add(new Between("prodDate", 20160101,20160101));
        caList.add(new Between("staMontageListe", datum +"0000",datum + "2359"));
        caList.add(new Equal("kabelsatzKz",ksKz));
        caList.add(new Equal("customerNr","323350"));
        caList.add(new Or(new Equal("boardType","F991LL"),new Equal("boardType","F99FLL")));
        return (List<Prnr>) getHibernateTemplate().findByCriteria(appendCriteria(createCriteria(), caList));

    }

    @Override
    public List<Prnr> getPrnr(Set<String> containerNameSet) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new IsNull("statusBand"));
        caList.add(new InSet("boardType",containerNameSet));

        return (List<Prnr>) getHibernateTemplate().findByCriteria(appendCriteria(createCriteria(), caList));
    }

    @Override
    public List<PrnrLpab64Obj> setContainer(List<PrnrLpab64Obj> prnrLpab64ObjList, Set<String> containerSet) {
        List<PrnrLpab64Obj> prnrLpab64ObjListNew = new ArrayList<>();
        for (PrnrLpab64Obj prnrLpab64Obj : prnrLpab64ObjList){
            List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
            caList.add(new Equal("kabelsatzKz",prnrLpab64Obj.getKsKz()));
            caList.add(new Equal("prodNr",prnrLpab64Obj.getPrnr()));
            List<Prnr> prnrList = ((List<Prnr>) getHibernateTemplate().findByCriteria(
                    appendCriteria(createCriteria(), caList)));
            if (!prnrList.isEmpty())
            {
            Prnr prnr = prnrList.get(0);
            String container = prnr.getBoardType();
            String prodDate = String.valueOf(prnr.getProdDate());
            if (containerSet.contains(container)){
            prnrLpab64Obj.setContainer(container);
            prnrLpab64Obj.setProdDate(prodDate);
            prnrLpab64ObjListNew.add(prnrLpab64Obj);
            }
            }
        }
        return  prnrLpab64ObjListNew;
    }

    @Override
    public int lockHarness(List<PrnrLpab64Obj> prnrLpab64ObjList, Set<String> containerSet) {
        int count = 0;
        for (PrnrLpab64Obj prnrLpab64Obj : prnrLpab64ObjList){
            List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
            caList.add(new Equal("kabelsatzKz",prnrLpab64Obj.getKsKz()));
            caList.add(new Equal("prodNr",prnrLpab64Obj.getPrnr()));
            List<Prnr> prnrList = ((List<Prnr>) getHibernateTemplate().findByCriteria(
                    appendCriteria(createCriteria(), caList)));
            if (!prnrList.isEmpty() && containerSet.contains(prnrList.get(0).getBoardType().trim())){
                Prnr prnr = prnrList.get(0);
                prnr.setLock("X");
                System.out.println("Blokujem: " + prnr.getProdNr());
                saveOrUpdate(prnr);
                count++;
            }
        }
        return count;
    }

    protected DetachedCriteria appendCriteria(DetachedCriteria criteria, List<CriteriaAppender> criteriaAppenders)
    {
        for (CriteriaAppender ca : criteriaAppenders)
        {
            ca.appendCriteria(criteria);
        }
        return criteria;
    }

    protected DetachedCriteria createCriteria()
    {
        return DetachedCriteria.forClass(Prnr.class);
    }
}
