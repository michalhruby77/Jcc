package com.leoni.data.manager.oldJIT;

import com.leoni.data.criterion.CriteriaAppender;
import org.hibernate.SessionFactory;
//import org.hibernate.classic.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 5.6.2014
 * Time: 9:34
 * To change this template use File | Settings | File Templates.
 */
@Transactional( value="transactionManagerOld")
public abstract class GenericManagerOldImpl<T> extends HibernateDaoSupport implements GenericManagerOld<T> {
    private Class<T> clazz;

    @Autowired
    public void init(SessionFactory sessionFactoryOld)
    {
        setSessionFactory(sessionFactoryOld);
    }


    @SuppressWarnings ("unchecked")
    public GenericManagerOldImpl()
    {
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /*public int test() {
        //Session session = getHibernateTemplate().getSessionFactory().openSession();
        //org.hibernate.Query q = session.createQuery("FROM AbruMaske");
        //List list = q.list();
        //session.close();

        Integer count = null;
        count = (Integer) getHibernateTemplate().find("SELECT count(*)  FROM AbruMaske").get(0);
    return count;
    } */

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
