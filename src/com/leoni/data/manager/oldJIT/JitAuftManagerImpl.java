package com.leoni.data.manager.oldJIT;

import com.leoni.data.models.oldJIT.JitAuft;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.6.2014
 * Time: 12:52
 * To change this template use File | Settings | File Templates.
 */
@Service("jitAuftManager")
public class JitAuftManagerImpl  extends GenericManagerOldImpl<JitAuft> implements JitAuftManager{
    @Transactional(value="transactionManagerOld",readOnly = true)
    public String existsSachNrBestInAuft(String sachNrBest, String kundenNr) {
        Long count;
        String modulsInJitAuft = "";
        String[] modulsArray = sachNrBest.split("\\+");
        for (String item : modulsArray){
            count = (Long) getHibernateTemplate().find("SELECT count(*)  FROM JitAuft WHERE sachNrBest = '" + item.trim() +
                    "' and kundenNr = '"+kundenNr + "'").get(0);
            if(count>0) {
                if(modulsInJitAuft.equals("")) modulsInJitAuft = item;
                else modulsInJitAuft = modulsInJitAuft + " + " +item;
            }
        }
        return modulsInJitAuft;
    }
}
