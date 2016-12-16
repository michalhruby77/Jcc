package com.leoni.data.manager.oldJIT;

import com.leoni.data.models.CriticalNewModul;
import com.leoni.data.models.NewModules;
import com.leoni.data.models.oldJIT.JitAbru;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.6.2014
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public interface JitAbruManager  extends GenericManagerOld<JitAbru> {
    //public CriticalNewModul getCriticalNewModul(String dfueNrEin, String sachNrBestInString, Session session);
    public Map<String,Set<String>> getModulesInCarMap(String dfueNrEin);
    public List<CriticalNewModul> checkForModules(String modulsString,Map<String,Set<String>> modulsInProdNr, String dfueNr);
}
