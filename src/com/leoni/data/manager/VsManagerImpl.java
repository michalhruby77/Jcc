package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.Vs;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 16.12.2014
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
@Service("vsManager")
public class VsManagerImpl  extends GenericManagerImpl<Vs> implements VsManager{
    @Override
    public List<Vs> findByNazov(String nazov) {
        CriteriaAppender[] criteriaAppenders = {new Equal("nazov", nazov.trim())};
        return find(Arrays.asList(criteriaAppenders));
    }
}
