package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.DmeVarianteName;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 21.7.2014
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
@Service("dmeVarianteNameManager")
public class DmeVarianteNameManagerImpl  extends GenericManagerImpl<DmeVarianteName> implements DmeVarianteNameManager{
    @Transactional ( value="transactionManager")
    public List<DmeVarianteName> findBySumWertigkeit(Integer sumWertigkeit) {
        List<CriteriaAppender> caList = new ArrayList<CriteriaAppender>();
        caList.add(new Equal("sumWertigkeit", sumWertigkeit));
        return find(caList);
    }
}
