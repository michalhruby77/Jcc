package com.leoni.data.manager.oldJIT;

import com.leoni.data.models.oldJIT.JitAuft;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.6.2014
 * Time: 12:51
 * To change this template use File | Settings | File Templates.
 */
public interface JitAuftManager  extends GenericManagerOld<JitAuft> {
    public String existsSachNrBestInAuft(String sachNrBest, String kundenNr);
}
