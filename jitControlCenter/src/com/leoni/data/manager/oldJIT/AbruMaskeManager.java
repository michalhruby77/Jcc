package com.leoni.data.manager.oldJIT;

import com.leoni.data.models.oldJIT.AbruMaske;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.6.2014
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
public interface AbruMaskeManager  extends GenericManagerOld<AbruMaske>{
    public List<String> getUebtNrsStatus012();
}
