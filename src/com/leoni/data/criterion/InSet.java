package com.leoni.data.criterion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hrmi1005 on 7. 1. 2016.
 */
public class InSet implements CriteriaAppender{
    String column;
    Set<String> set;

    public InSet(String column, Set<String> set) {
        this.column = column;
        this.set = set;
    }

    @Override
    public void appendCriteria(DetachedCriteria criteria) {
        criteria.add(getCriterion());
    }

    @Override
    public Criterion getCriterion() {
        return Restrictions.in(column,set);
    }
}
