package com.leoni.data.manager.vm;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.manager.GenericManagerImpl;
import com.leoni.data.models.vm.VmBrett;
import com.leoni.data.models.vm.VmSwitch;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 3.8.2015
 * Time: 8:40
 * To change this template use File | Settings | File Templates.
 */
@Service("vmSwitchManager")
public class VmSwitchManagerImpl extends GenericManagerImpl<VmSwitch> implements VmSwitchManager {
    public List<VmSwitch> getAll(){
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VmSwitch.class);
        detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<VmSwitch>) getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    @Override
    public List<VmSwitch> findById(Integer id) {
        CriteriaAppender[] criteriaAppenders = {new Equal("id", id)};
        return find(Arrays.asList(criteriaAppenders));

    }

    @Override
    public List<VmSwitch> findByBoard(VmBrett vmBrett) {
        CriteriaAppender[] criteriaAppenders = {new Equal("vmBrett", vmBrett)};
        return find(Arrays.asList(criteriaAppenders));
    }

    @Override
    public List<VmSwitch> findByAddress(String address) {
        CriteriaAppender[] criteriaAppenders = {new Equal("address", address)};
        return find(Arrays.asList(criteriaAppenders));
    }
}
