package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.Moduls;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 9.11.2012
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
@Service ("modulsManager")
public class ModulsManagerImpl extends GenericManagerImpl<Moduls> implements ModulsManager
    {
    public List<Moduls> findBySachNrBest(String sachNrBest)
        {
        CriteriaAppender[] criteriaAppenders = {new Equal("sachNrBest", sachNrBest)};
        return find(Arrays.asList(criteriaAppenders));
        }

    public List<Moduls> findBySachNrLieferant(String sachNrLieferant)
        {
        CriteriaAppender[] criteriaAppenders = {new Equal("sachNrLieferant", sachNrLieferant)};
        return find(Arrays.asList(criteriaAppenders));
        }

    public List<Moduls> findBySachNrBestAndSachNrLieferant(String sachNrBest, String sachNrLieferant)
        {
        CriteriaAppender[] criteriaAppenders = {new Equal("sachNrBest", sachNrBest),
                new Equal("sachNrLieferant", sachNrLieferant)};
        return find(Arrays.asList(criteriaAppenders));
        }
    }

