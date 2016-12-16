package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.VersandExport;
import com.leoni.data.models.VersandModul;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.3.2015
 * Time: 9:27
 * To change this template use File | Settings | File Templates.
 */
@Service("versandModulManager")
public class VersandModulManagerImpl  extends GenericManagerImpl<VersandModul> implements VersandModulManager{
    @Override
    public List<VersandModul> getAllVersandModuls(VersandExport versandExport) {
        CriteriaAppender[] criteriaAppenders = {new Equal("versandExport", versandExport)};
        return find(Arrays.asList(criteriaAppenders));
    }

    /*@Override
    protected DetachedCriteria appendCriteria(DetachedCriteria criteria, List<CriteriaAppender> criteriaAppenders)
    {
        criteria.createAlias("versandExport", "versandExport");
        return super.appendCriteria(criteria, criteriaAppenders);
    } */
}
