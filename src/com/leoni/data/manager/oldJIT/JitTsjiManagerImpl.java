package com.leoni.data.manager.oldJIT;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.oldJIT.JitTsji;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.6.2014
 * Time: 11:00
 * To change this template use File | Settings | File Templates.
 */
@Service("jitTsjiManager")
public class JitTsjiManagerImpl  extends GenericManagerOldImpl<JitTsji> implements JitTsjiManager{
    @Transactional(value="transactionManagerOld",readOnly = true)
    public String existsSachNrBestInTsji(String sachNrBest) {
        Long count;
        String modulsInJitTsji = "";
        String[] modulsArray = sachNrBest.split("\\+");
        for (String item : modulsArray){
            count = (Long) getHibernateTemplate().find("SELECT count(*)  FROM JitTsji WHERE sachNrBest = '" + item.trim() + "'").get(0);
            if(count>0) {
                if(modulsInJitTsji.equals("")) modulsInJitTsji = item;
                else modulsInJitTsji = modulsInJitTsji + " + " +item;
            }
        }
        return modulsInJitTsji;
    }
}
