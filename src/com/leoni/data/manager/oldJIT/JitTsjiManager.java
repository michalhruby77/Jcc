package com.leoni.data.manager.oldJIT;

import com.leoni.data.models.oldJIT.JitTsji;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.6.2014
 * Time: 11:00
 * To change this template use File | Settings | File Templates.
 */
public interface JitTsjiManager extends GenericManagerOld<JitTsji>{
    public String existsSachNrBestInTsji(String sachNrBest);
}
