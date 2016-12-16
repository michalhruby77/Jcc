package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.models.Moduls;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
//import org.hibernate.classic.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 21.11.2012
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */

@Transactional( value="transactionManager")
public abstract class GenericManagerImpl<T> extends HibernateDaoSupport implements GenericManager<T>
    {
    private Class<T> clazz;

    @Autowired
    public void init(SessionFactory sessionFactory)
        {
        setSessionFactory(sessionFactory);
        }

    @SuppressWarnings ("unchecked")
    public GenericManagerImpl()
        {
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
    //@SuppressWarnings ("unchecked")
    public List<T> getAll()
        {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        return find(criteria);
        }
    public List<T> getObjectsByModulsId(int id)
        {
            DetachedCriteria criteria = DetachedCriteria.forClass(clazz).createCriteria("moduls");
            criteria.add(Restrictions.eq("id",id));
            return find(criteria);
        }

    public List<T> getObjectsByLiefNr(String liefNr)
        {
            DetachedCriteria criteria = DetachedCriteria.forClass(clazz);

            criteria.add(Restrictions.eq("sachNrLief",liefNr));
            return find(criteria);
        }
    /**
     * Method that returns the number of entries from a table that meet some
     * criteria (where clause params)
     *
     * @param criteria Use this criteria for matching
     * @return Number of matching entries
     */
    protected int count(DetachedCriteria criteria)
        {
        criteria.setProjection(Projections.rowCount());
        return DataAccessUtils.intResult(getHibernateTemplate().findByCriteria(criteria));
        }

    public int count(List<CriteriaAppender> criteriaAppenderList)
        {
        return count(appendCriteria(createCriteria(), criteriaAppenderList));
        }

    /**
     * List all entries match the given criteria
     *
     * @param criteria Criteria for search
     * @return List of matching entries
     */
    //@SuppressWarnings ("unchecked")
    protected List<T> find(DetachedCriteria criteria)
        {
        return find(criteria, 0, 0);
        }

    @SuppressWarnings ("unchecked")
    protected List<T> find(DetachedCriteria criteria, int offset, int limit)
        {
        if (criteria == null)
            {
            criteria = DetachedCriteria.forClass(clazz);
            }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<T>) getHibernateTemplate().findByCriteria(criteria, offset, limit);
        }

    public List<T> find(CriteriaAppender criteriaAppender)
        {
        return find(appendCriteria(createCriteria(), Arrays.asList(criteriaAppender)));
        }

    public List<T> find(List<CriteriaAppender> criteriaAppenderList)
        {
        return find(appendCriteria(createCriteria(), criteriaAppenderList));
        }

    public List<T> find(List<CriteriaAppender> criteriaAppenderList, int offset, int limit)
        {
        return find(appendCriteria(createCriteria(), criteriaAppenderList), offset, limit);
        }

    public List<T> find(List<CriteriaAppender> criteriaAppenderList, int offset, int limit, String orderColumn, boolean orderDirection)
        {
        DetachedCriteria detachedCriteria = appendCriteria(createCriteria(), criteriaAppenderList);
        detachedCriteria.addOrder(orderDirection ? Order.asc(orderColumn) : Order.desc(orderColumn));
        return find(detachedCriteria, offset, limit);
        }


   public List<T> findByForModuls(String id,String sachNrLieferantSearch,String sachNrBestSearch,String ausfuehrungSearch,String prodGruppeSearch) {
            List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
            if(!sachNrLieferantSearch.equals("")){caList.add(new Like("sachNrLieferant", "%"+sachNrLieferantSearch.trim()+"%"));}
            if(!sachNrBestSearch.equals("")){caList.add(new Like("sachNrBest", "%"+sachNrBestSearch.trim()+"%"));}
            if(!ausfuehrungSearch.equals("")){caList.add(new Like("ausfuehrung", "%"+ausfuehrungSearch.trim()+"%"));}
            if(!prodGruppeSearch.equals("")){caList.add(new Like("prodGruppe", "%"+prodGruppeSearch.trim()+"%"));}
            return find(caList);
        }
   public List<T> findBy(String id,String sachNrLieferantSearch,String sachNrBestSearch,String ausfuehrungSearch,String prodGruppeSearch) {
            List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
            if(!id.equals("")){caList.add(new Equal("id", Integer.parseInt(id)));}
            if(!sachNrLieferantSearch.equals("")){caList.add(new Like("moduls.sachNrLieferant", "%"+sachNrLieferantSearch.trim()+"%"));}
            if(!sachNrBestSearch.equals("")){caList.add(new Like("moduls.sachNrBest", "%"+sachNrBestSearch.trim()+"%"));}
            if(!ausfuehrungSearch.equals("")){caList.add(new Like("moduls.ausfuehrung", "%"+ausfuehrungSearch.trim()+"%"));}
            if(!prodGruppeSearch.equals("")){caList.add(new Like("moduls.prodGruppe", "%"+prodGruppeSearch.trim()+"%"));}
            return find(caList);
        }
   public Long getCount(String attribute, String date, String ausfuehrung, String kskz, String kundenNr, String prodGruppe) {
       Long count = null;
       if(ausfuehrung.equals("RL")||kundenNr.equals("323357")||kskz.equals("C"))
       {count = (Long) getHibernateTemplate().find("select count(*) from Lpab62 where " + "lieferDatum " + "=" + date +
               " and ausfuehrung = '" + ausfuehrung + "' and kabelsatzKz = '" + kskz + "' and kundenNr = '" + kundenNr +
               "' and " + attribute + " IS NOT null").get(0);
               //System.out.println("prva");
       }
       else{
           if(prodGruppe.equals("981")){
           count = (Long) getHibernateTemplate().find("select count(*) from Lpab62 where " + "lieferDatum " + "=" + date +
                   " and ausfuehrung = '" + ausfuehrung + "' and kabelsatzKz = '" + kskz + "' and kundenNr = '" + kundenNr + "'"+
                   " and " + "prodNr LIKE '1%' and " + attribute + " IS NOT null").get(0);
               //System.out.println("druha");
           }
           else{
               count = (Long) getHibernateTemplate().find("select count(*) from Lpab62 where " + "lieferDatum " + "=" + date +
                       " and ausfuehrung = '" + ausfuehrung + "' and kabelsatzKz = '" + kskz + "' and kundenNr = '" + kundenNr + "'"+
                       " and " + "prodNr NOT LIKE '1%' and " + attribute + " IS NOT null").get(0);
               //System.out.println("tretia");
           }
       }
       return count;
  }

        public Long getCountAll(String attribute, String date, String ausfuehrung, String kskz, String kundenNr, String prodGruppe) {
            Long count = null;
            if(ausfuehrung.equals("RL")||kundenNr.equals("323357")||kskz.equals("C"))
            {count = (Long) getHibernateTemplate().find("select count(*) from Lpab62 where " + attribute + "=" + date +
                    " and ausfuehrung = '" + ausfuehrung + "' and kabelsatzKz = '" + kskz + "' and kundenNr = '" + kundenNr + "'").get(0);
                //System.out.println("prva");
            }
            else{
                if(prodGruppe.equals("981")){
                    count = (Long) getHibernateTemplate().find("select count(*) from Lpab62 where " + attribute + "=" + date +
                            " and ausfuehrung = '" + ausfuehrung + "' and kabelsatzKz = '" + kskz + "' and kundenNr = '" + kundenNr + "'"+
                            " and " + "prodNr LIKE '1%'").get(0);
                    //System.out.println("druha");
                }
                else{
                    count = (Long) getHibernateTemplate().find("select count(*) from Lpab62 where " + attribute + "=" + date +
                            " and ausfuehrung = '" + ausfuehrung + "' and kabelsatzKz = '" + kskz + "' and kundenNr = '" + kundenNr + "'"+
                            " and " + "prodNr NOT LIKE '1%'").get(0);
                    //System.out.println("tretia");
                }
            }
            return count;
        }




        @Transactional(value="transactionManager", readOnly = false)
    public T create(T t)
        {
        getHibernateTemplate().persist(t);
        return t;
        }

    @Transactional(value="transactionManager", readOnly = false)
    public void delete(T t)
        {
        getHibernateTemplate().delete(t);
        }

    @SuppressWarnings ("unchecked")
    public T find(Serializable id)
        {
        return (T) getHibernateTemplate().get(clazz.getClass(), id);
        }

    @Transactional(value="transactionManager", readOnly = false)
    public T saveOrUpdate(T t)
        {
        getHibernateTemplate().saveOrUpdate(t);
        return t;
        }

    /**
     * Append criteria from criteriaAppenders list to DetachedCriteria
     *
     * @param criteria - DetachedCriteria for add criterion from criteriaAppenders list
     * @param criteriaAppenders - List of CriteriaAppender, every criteriaAppender
     * @return DetachedCriteria from parameter
     */
    protected DetachedCriteria appendCriteria(DetachedCriteria criteria, List<CriteriaAppender> criteriaAppenders)
        {
        for (CriteriaAppender ca : criteriaAppenders)
            {
            ca.appendCriteria(criteria);
            }
        return criteria;
        }

    /**
     * Create criteria for T class and append criteria from criteriaAppenders List
     *
     * @return new DetachedCriteria
     */
    protected DetachedCriteria createCriteria()
        {
        return DetachedCriteria.forClass(clazz);
        }

    }
