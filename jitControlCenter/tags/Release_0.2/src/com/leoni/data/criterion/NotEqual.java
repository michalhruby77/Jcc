package com.leoni.data.criterion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 25.2.2013
 * Time: 8:49
 * To change this template use File | Settings | File Templates.
 */
public class NotEqual extends Equal implements CriteriaAppender
    {
    public NotEqual(String column, Object value)
        {
        super(column, value);
        }

    @Override
    public void appendCriteria(DetachedCriteria criteria)
        {
        criteria.add(Restrictions.ne(column, value));
        }

    @Override
    public Criterion getCriterion()
        {
        return Restrictions.ne(column, value);
        }
    }
