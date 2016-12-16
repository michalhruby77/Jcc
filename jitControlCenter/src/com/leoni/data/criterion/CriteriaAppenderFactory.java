package com.leoni.data.criterion;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 5.12.2012
 * Time: 12:00
 *
 * Simple Factory for getting right CriteriaAppender class
 */
public class CriteriaAppenderFactory
    {
    private CriteriaAppenderFactory()
        {
        }

    public static CriteriaAppender getCriteriaAppender(String column, String criterion, String value)
        {
        return getCriteriaAppender(column, Criteria.valueOf(criterion), value);
        }

    public static CriteriaAppender getCriteriaAppender(String column, Criteria criterion, String value)
        {
        switch (criterion)
            {
            case eq:
                return new Equal(column, value);

            case like:
                return new Like(column, value);

            default:
                return new BasicCriteriaAppender(column, value);
            }
        }
    }
