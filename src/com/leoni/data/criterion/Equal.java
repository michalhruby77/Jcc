package com.leoni.data.criterion;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 5.12.2012
 * Time: 11:12
 * To change this template use File | Settings | File Templates.
 */
public class Equal extends BasicCriteriaAppender implements CriteriaAppender
    {
    public Equal(String column, Object value)
        {
        super(column, value);
        }
    }
