package com.leoni.data.manager.vm;

import com.leoni.data.manager.GenericManagerImpl;
import com.leoni.data.models.vm.VmVariante;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 3.8.2015
 * Time: 8:41
 * To change this template use File | Settings | File Templates.
 */
@Service("vmVarianteManager")
public class VmVarianteManagerImpl extends GenericManagerImpl<VmVariante> implements VmVarianteManager {

    public List<VmVariante> getAll() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VmVariante.class);
        detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<VmVariante>) getHibernateTemplate().findByCriteria(detachedCriteria);
    }
}
