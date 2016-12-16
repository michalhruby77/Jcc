package com.leoni.data.manager.vm;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.manager.GenericManagerImpl;
import com.leoni.data.models.vm.VmBrett;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 3.8.2015
 * Time: 8:36
 * To change this template use File | Settings | File Templates.
 */
@Service("vmBrettManager")
public class VmBrettManagerImpl  extends GenericManagerImpl<VmBrett> implements VmBrettManager{
    public List<VmBrett> getAll(){
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VmBrett.class);
        detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<VmBrett>) getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    @Override
    public List<VmBrett> findByName(String name) {
        CriteriaAppender[] criteriaAppenders = {new Equal("name", name.trim())};
        return find(Arrays.asList(criteriaAppenders));
    }
}
