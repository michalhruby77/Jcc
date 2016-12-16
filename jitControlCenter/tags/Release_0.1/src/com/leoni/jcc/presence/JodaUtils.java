package com.leoni.jcc.presence;

import org.joda.time.Interval;
import org.joda.time.MutableInterval;

import java.util.Date;
import java.util.List;

public class JodaUtils
    {
    public static Interval createInterval ()
        {
        return createInterval(new Date(), new Date());
        }

    public static Interval createInterval (Date start, Date end)
        {
        return new Interval(start.getTime(), end.getTime());
        }

    public static MutableInterval createMutableInterval ()
        {
        return createMutableInterval(new Date(), new Date());
        }

    public static MutableInterval createMutableInterval (Date start, Date end)
        {
        return createInterval(start, end).toMutableInterval();
        }

    /**
     * Check if the given intervals are overlap.
     * @param interval1 First interval.
     * @param interval2 Second interval.
     * @return True if intervals overlap.
     */
    public static boolean isOverlap(Interval interval1, Interval interval2)
        {
        return interval1.overlaps(interval2);
        }

    /**
     * Check if the given interval is overlap with list of intervals.
     * @param interval Interval for check.
     * @param intervals List of intervals.
     * @return True if interval overlap with one of intervals. Otherwise false.
     */
    public static boolean isOverlap(Interval interval, List<Interval> intervals)
        {
        for (Interval i : intervals)
            {
            if (isOverlap(interval, i))
                {
                return true;
                }
            }
        return false;
        }
    }
