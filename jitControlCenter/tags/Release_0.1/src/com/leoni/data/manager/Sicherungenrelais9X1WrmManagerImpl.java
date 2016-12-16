package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.models.SicherungenRelais9X1Wrm;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 18.1.2013
 * Time: 8:55
 * To change this template use File | Settings | File Templates.
 */

@Service ("sicherungenRelais9X1WrmManager")
public class Sicherungenrelais9X1WrmManagerImpl extends GenericManagerImpl<SicherungenRelais9X1Wrm> implements SicherungenRelais9X1WrmManager
    {
    private static List<String> boxList = Arrays.asList(
            "",
            "relais_treager1_vorne",
            "relais_treager2_hinten",
            "sicherungs_dose_links",
            "sicherungs_dose_rechts");

    private static final Map<String, List<String>> platzMap = new HashMap<String, List<String>>()
    {
    {
    put("relais_treager1_vorne", Arrays.asList("", "K1", "K2A", "K2B", "K3", "K4", "K5", "K6", "K7", "K8", "K9"));
    put("relais_treager2_hinten", Arrays.asList("", "K1", "K2", "K3", "K4", "K5", "K6A", "K6B", "K7A", "K7B", "K8", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5"));
    put("sicherungs_dose_rechts", Arrays.asList("", "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10"));
    put("sicherungs_dose_links", Arrays.asList("", "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "E", "F"));
    }
    };

    public List<String> getBoxList()
        {
        return boxList;
        }


    public List<String> getPlatzList(String box)
        {
        return platzMap.containsKey(box) ? platzMap.get(box) : null;
        }

    @Override
    protected DetachedCriteria appendCriteria(DetachedCriteria criteria, List<CriteriaAppender> criteriaAppenders)
        {
        criteria.createAlias("moduls", "moduls");
        return super.appendCriteria(criteria, criteriaAppenders);
        }
    }
