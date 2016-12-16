package com.leoni.data.criterion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 5.12.2012
 * Time: 11:13
 *
 * Base implementation of CriteriaAppender interface. It Provides also conversion to correct class attribute.
 */
public class BasicCriteriaAppender implements CriteriaAppender
    {
    protected String column = "";
    protected Object value;

    public BasicCriteriaAppender(String column, Object value)
        {
        this.column = column;
        this.value = value;
        }

    public void appendCriteria(DetachedCriteria criteria)
        {
        criteria.add(Restrictions.eq(column, value));
        }

    public Criterion getCriterion()
        {
        return Restrictions.eq(column, value);
        }

    public String getColumn()
        {
        return column;
        }

    public void setColumn(String column)
        {
        this.column = column;
        }

    public Object getValue()
        {
        return value;
        }

    public void setValue(Object value)
        {
        this.value = value;
        }
    }
