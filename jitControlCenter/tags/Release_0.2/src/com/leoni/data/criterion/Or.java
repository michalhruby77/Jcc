package com.leoni.data.criterion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 25.2.2013
 * Time: 9:02
 * To change this template use File | Settings | File Templates.
 */
public class Or implements CriteriaAppender
    {
    List<CriteriaAppender> orList = new ArrayList<CriteriaAppender>();

    public Or(CriteriaAppender ca1, CriteriaAppender ca2)
        {
        orList.add(ca1);
        orList.add(ca2);
        }

    public boolean add(CriteriaAppender ca)
        {
        return orList.add(ca);
        }

    public void appendCriteria(DetachedCriteria criteria)
        {
        criteria.add(getCriterion());
        }

    public Criterion getCriterion()
        {
        Disjunction disjunction = Restrictions.disjunction();
        for (CriteriaAppender ca : orList)
            {
            disjunction.add(ca.getCriterion());
            }
        return disjunction;
        }
    }
