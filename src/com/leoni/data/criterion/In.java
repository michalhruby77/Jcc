package com.leoni.data.criterion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 15.10.2015
 * Time: 8:02
 * To change this template use File | Settings | File Templates.
 */
public class In implements CriteriaAppender{
    String column;
    List<String> list;

    public In(String column, List<String> list) {
        this.column = column;
        this.list = list;
    }

    @Override
    public void appendCriteria(DetachedCriteria criteria) {
        criteria.add(getCriterion());
    }

    @Override
    public Criterion getCriterion() {
        Restrictions.in(column,new HashSet());
        return Restrictions.in(column,list);
    }
}
