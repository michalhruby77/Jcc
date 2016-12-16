package com.leoni.data.manager.oldJIT;

import com.leoni.data.criterion.CriteriaAppender;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 5.6.2014
 * Time: 9:34
 * To change this template use File | Settings | File Templates.
 */
public interface GenericManagerOld<T> {
    public List<T> find (CriteriaAppender criteriaAppender);

    public List<T> find (List<CriteriaAppender> criteriaAppenderList);

    public List<T> find (List<CriteriaAppender> criteriaAppenderList, int offset, int limit);

    public List<T> find (List<CriteriaAppender> criteriaAppenderList, int offset, int limit, String orderColumn, boolean orderDirection);
}
