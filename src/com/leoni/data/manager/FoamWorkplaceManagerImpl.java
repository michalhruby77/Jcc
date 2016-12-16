package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.criterion.Like;
import com.leoni.data.models.FoamWorkplace;
import com.leoni.data.models.FoamWorkplaceModuls;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.10.2014
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
@Service("foamWorkplaceManager")
public class FoamWorkplaceManagerImpl  extends GenericManagerImpl<FoamWorkplace> implements FoamWorkplaceManager{

    @WireVariable
    @Autowired
    FoamWorkplaceModulsManager foamWorkplaceModulsManager;

    public List<FoamWorkplace> getAll() {
          DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FoamWorkplace.class);
          detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
          return (List<FoamWorkplace>) getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    public FoamWorkplace add(Integer workplace, String form, String template, String workplaceName, String description, List<String> prefixList) {

        FoamWorkplace foamWorkplace = new FoamWorkplace();
        foamWorkplace.setForm(form);
        foamWorkplace.setTemplate(template);
        foamWorkplace.setWorkplace(workplace);
        foamWorkplace.setWorkplaceName(workplaceName);
        foamWorkplace.setDescription(description);
        create(foamWorkplace);
        foamWorkplaceModulsManager.addFoamWorkplaceModulsList(foamWorkplace,prefixList);


        return foamWorkplace;
    }

    public List<FoamWorkplace> findWorkplace(Integer workplace) {
        CriteriaAppender[] criteriaAppenders = {new Equal("workplace", workplace)};
        return find(Arrays.asList(criteriaAppenders));
    }

    @Override
    public List<FoamWorkplace> findByNazovAndModul(String nazov, String modul) {

        Criteria crit  =  getSessionFactory().getCurrentSession().createCriteria(FoamWorkplace.class);
        if (!nazov.trim().equals("")) {crit.add(Restrictions.like("workplaceName","%" + nazov.trim() + "%"));}
        if (!modul.trim().equals("")) {
            crit.createAlias("modulsList","moduls");
            crit.add(Restrictions.like("moduls.prodNrPrefix","%" + modul.trim() + "%"));}
        crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return crit.list();
    }
}
