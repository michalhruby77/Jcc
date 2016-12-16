package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.GrundVarianteModule;
import com.leoni.data.models.Lpab64;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 30.4.2014
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
@Service("grundVarianteModuleManager")
public class GrundVarianteModuleManagerImpl    extends GenericManagerImpl<GrundVarianteModule> implements GrundVarianteModuleManager {


    public List<GrundVarianteModule> findByModulsId(Integer modulsId) {
        CriteriaAppender[] criteriaAppenders = {new Equal("moduls.id", modulsId)};
        return find(Arrays.asList(criteriaAppenders));
    }

    @Override
    protected DetachedCriteria appendCriteria(DetachedCriteria criteria, List<CriteriaAppender> criteriaAppenders)
    {
        criteria.createAlias("moduls", "moduls");
        return super.appendCriteria(criteria, criteriaAppenders);
    }
}
