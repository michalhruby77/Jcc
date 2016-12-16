package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 21.11.2012
 * Time: 10:39
 * To change this template use File | Settings | File Templates.
 */
public interface GenericManager<T>
    {
    public int count(List<CriteriaAppender> criteriaAppenderList);

    public List<T> getAll();

    public List<T> find (CriteriaAppender criteriaAppender);

    public List<T> find (List<CriteriaAppender> criteriaAppenderList);

    public List<T> find (List<CriteriaAppender> criteriaAppenderList, int offset, int limit);

    public List<T> find (List<CriteriaAppender> criteriaAppenderList, int offset, int limit, String orderColumn, boolean orderDirection);

    public T create(T t);

    public void delete(T id);

    public T saveOrUpdate(T t);
    }
