package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.ModulsAlias;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 4.9.2015
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
@Service("modulsAliasManager")
public class ModulsAliasManagerImpl  extends GenericManagerImpl<ModulsAlias> implements ModulsAliasManager{
    @Override
    public List<ModulsAlias> findBySachNrLieferant(String sachNrLieferant) {
        CriteriaAppender[] criteriaAppenders = {new Equal("sachNrLieferant", sachNrLieferant)};
        return find(Arrays.asList(criteriaAppenders));
    }
}
