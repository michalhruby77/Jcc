package com.leoni.data.manager.oldJIT;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.Lpab64;
import com.leoni.data.models.nonDBmodels.JanzmgrpObj;
import com.leoni.data.models.oldJIT.Jitpab64;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 27.11.2014
 * Time: 8:53
 * To change this template use File | Settings | File Templates.
 */
@Service("jitpab64Manager")
@Transactional( value="transactionManagerOld", readOnly = true)
public class Jitpab64ManagerImpl  extends GenericManagerOldImpl<Jitpab64> implements Jitpab64Manager {
    @Override
    public void getJanzmgrpCount(JanzmgrpObj janzmgrpObj) {
        Query query = getSessionFactory().getCurrentSession().createQuery("SELECT  sum(jitpab64.abrufMenge) FROM Jitpab64 jitpab64 WHERE "
                + " jitpab64.prodNr IN (:prodNrSet)" + " AND  jitpab64.kabelsatzKz = '"+ janzmgrpObj.getKsKz().trim() + "' AND jitpab64.beipackKz is null");
        query.setParameterList("prodNrSet", janzmgrpObj.getProdNrSet());
        int count = ((Long)query.uniqueResult()).intValue();
        janzmgrpObj.setCount(count);
    }

    @Override
    public List<Lpab64> getJitpab64(String prodnr) {
        Query query = getSessionFactory().getCurrentSession().createQuery("SELECT  jitpab64.sachNrBest,jitpab64.sachNrLieferant FROM Jitpab64 jitpab64 WHERE "+
                 " jitpab64.prodNr = '"+ prodnr + "' AND  jitpab64.kabelsatzKz = 'F'");
        ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
        List<Lpab64> lpab64List = new ArrayList();
        while (results.next()) {

            String best = ((String) results.get(0)).trim();
            String lief = ((String) results.get(1)).trim();

            Lpab64 lpab64 = new Lpab64();
            lpab64.setSachNrBest(best);
            lpab64.setSachNrLieferant(lief);
            lpab64List.add(lpab64);
        }
        return lpab64List;
    }
}
