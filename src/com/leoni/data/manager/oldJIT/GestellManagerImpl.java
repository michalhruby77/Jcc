package com.leoni.data.manager.oldJIT;


import com.leoni.data.models.oldJIT.Jitpab62;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
//import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 11.6.2014
 * Time: 9:56
 * To change this template use File | Settings | File Templates.
 */
@Service("gestellManager")
public class GestellManagerImpl extends GenericManagerOldImpl<Jitpab62> implements GestellManager {

    public Integer doGestell() {
       Query query = getSessionFactory().getCurrentSession().createQuery("UPDATE Jitpab62 SET abladeStelle='S2506' WHERE kundenNr = 323357 and abladeStelle = 'A450'");
        int result = query.executeUpdate();
        System.out.println("vysledok gestellu:                 "+result);
        return result;
    }
}
