package com.leoni.data.manager.oldJIT;

import com.leoni.data.models.nonDBmodels.JanzmgrpObj;
import com.leoni.data.models.oldJIT.Jitpab62;
import org.hibernate.ScrollableResults;

import java.util.List;
import java.util.SortedSet;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 25.11.2014
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public interface Jitpab62Manager extends GenericManagerOld<Jitpab62> {
    public SortedSet<JanzmgrpObj> selecteInnerJoinJitpab62Jitpab64(String kundenNr, String dateFrom, String dateTo);
    public int selecteInnerJoinJitpab62Jitpab64E(String kundenNr, String dateFrom, String dateTo);
    public int selecteInnerJoinJitpab62Jitpab64R(String kundenNr, String dateFrom, String dateTo);
}
