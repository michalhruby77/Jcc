package com.leoni.data.manager.oldJIT;

import com.leoni.data.models.Lpab64;
import com.leoni.data.models.nonDBmodels.JanzmgrpObj;
import com.leoni.data.models.oldJIT.Jitpab64;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 27.11.2014
 * Time: 8:53
 * To change this template use File | Settings | File Templates.
 */
public interface Jitpab64Manager  extends GenericManagerOld<Jitpab64> {
    public void getJanzmgrpCount(JanzmgrpObj janzmgrpObj);
    public List<Lpab64> getJitpab64(String prodnr);
}
