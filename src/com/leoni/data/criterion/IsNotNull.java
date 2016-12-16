package com.leoni.data.criterion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Created by hrmi1005 on 22. 4. 2016.
 */
public class IsNotNull implements CriteriaAppender{

    String column;

    public IsNotNull(String column) {
        this.column = column;
    }

    @Override
    public void appendCriteria(DetachedCriteria criteria) {
        criteria.add(getCriterion());
    }

    @Override
    public Criterion getCriterion() {
        return Restrictions.isNotNull(column);
    }
}
