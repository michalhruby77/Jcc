package com.leoni.data.criterion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 14.10.2015
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
public class IsNull implements CriteriaAppender{

    String column;

    public IsNull(String column) {
        this.column = column;
    }

    @Override
    public void appendCriteria(DetachedCriteria criteria) {
        criteria.add(getCriterion());
    }

    @Override
    public Criterion getCriterion() {
        return Restrictions.isNull(column);
    }
}
