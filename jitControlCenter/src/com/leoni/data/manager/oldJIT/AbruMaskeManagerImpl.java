package com.leoni.data.manager.oldJIT;

import com.leoni.data.models.oldJIT.AbruMaske;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.6.2014
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
@Service("abruMaskeManager")
public class AbruMaskeManagerImpl  extends GenericManagerOldImpl<AbruMaske> implements AbruMaskeManager{
    @Transactional(readOnly = true,value="transactionManagerOld")
    public List<String> getUebtNrsStatus012() {
        org.hibernate.Query q = getSessionFactory().getCurrentSession().createQuery("SELECT uebtNrNeu FROM AbruMaske WHERE status < 3");
        //List list = q.list();
        return q.list();
    }
}
