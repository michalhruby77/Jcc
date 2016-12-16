package com.leoni.jcc.presence;

import com.leoni.data.models.Presence;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 6.2.2013
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
public class PresenceComparatorByBeginTime implements Comparator<Presence>
    {
    public int compare(Presence presence, Presence presence1)
        {
        return presence.getShiftBegin().compareTo(presence1.getShiftBegin());
        }
    }
