package com.leoni.data.manager;

import com.leoni.data.models.VersandExport;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2015
 * Time: 9:26
 * To change this template use File | Settings | File Templates.
 */
@Service("versandExportManager")
public class VersandExportManagerImpl  extends GenericManagerImpl<VersandExport> implements VersandExportManager{
    public List<VersandExport> getAll() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VersandExport.class);
        detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<VersandExport>) getHibernateTemplate().findByCriteria(detachedCriteria);
    }

}
