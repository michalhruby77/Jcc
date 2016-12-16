package com.leoni.data.criterion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 19.2.2013
 * Time: 8:26
 * To change this template use File | Settings | File Templates.
 */
public class Between implements CriteriaAppender
    {
    String column;
    Object hiValue;
    Object loValue;

    public Between(String column, Object loValue, Object hiValue)
        {
        this.column = column;
        this.hiValue = hiValue;
        this.loValue = loValue;
        }

    public void appendCriteria(DetachedCriteria criteria)
        {
        criteria.add(getCriterion());
        }

    public Criterion getCriterion()
        {
        return Restrictions.between(column,loValue,hiValue);
        }

    public Object getHiValue()
        {
        return hiValue;
        }

    public void setHiValue(Object hiValue)
        {
        this.hiValue = hiValue;
        }

    public String getColumn()
        {
        return column;
        }

    public void setColumn(String column)
        {
        this.column = column;
        }

    public Object getLoValue()
        {
        return loValue;
        }

    public void setLoValue(Object loValue)
        {
        this.loValue = loValue;
        }
    }
