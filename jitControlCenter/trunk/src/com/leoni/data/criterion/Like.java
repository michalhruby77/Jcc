package com.leoni.data.criterion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 5.12.2012
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public class Like extends BasicCriteriaAppender implements CriteriaAppender
    {
    public Like(String column, String value)
        {
        super(column, value);
        }

    @Override
    public void appendCriteria(DetachedCriteria criteria)
        {
        criteria.add(Restrictions.like(column, value));
        }

    @Override
    public Criterion getCriterion()
        {
        return Restrictions.like(column, value);
        }
    }
