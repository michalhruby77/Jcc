package com.leoni.data.criterion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 5.12.2012
 * Time: 10:13
 * To change this template use File | Settings | File Templates.
 */
public interface CriteriaAppender
    {
    /**
     * Append criteria to DetachedCriteria
     */
    //@Deprecated
    public void appendCriteria(DetachedCriteria criteria);

    /**
     * Return Criterion for add to DetachedCriteria.
     * @return Criterion
     */
    public Criterion getCriterion ();

//    public String getColumn();
//    void setColumn(String column);
//    public Object getValue();
//    void setValue(String value);
    }
