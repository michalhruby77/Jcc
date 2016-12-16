package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 21.11.2012
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */

@Transactional (readOnly = true)
public abstract class GenericManagerImpl<T> extends HibernateDaoSupport implements GenericManager<T>
    {
    private Class<T> clazz;

    @Autowired
    public void init(SessionFactory factory)
        {
        setSessionFactory(factory);
        }

    @SuppressWarnings ("unchecked")
    public GenericManagerImpl()
        {
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }

    public List<T> getAll()
        {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
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

    public T create(T t)
        {
        getHibernateTemplate().persist(t);
        return t;
        }

    @Transactional(readOnly = false)
    public void delete(T t)
        {
        getHibernateTemplate().delete(t);
        }

    @SuppressWarnings ("unchecked")
    public T find(Serializable id)
        {
        return (T) getHibernateTemplate().get(clazz.getClass(), id);
        }

    @Transactional(readOnly = false)
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
