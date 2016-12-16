package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.FoamWorkplace;
import com.leoni.data.models.FoamWorkplaceModuls;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 9.10.2014
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
@Service("foamWorkplaceModulsManager")
public class FoamWorkplaceModulsManagerImpl  extends GenericManagerImpl<FoamWorkplaceModuls> implements FoamWorkplaceModulsManager{
    public void addFoamWorkplaceModulsList(FoamWorkplace foamWorkplace, List<String> prefixList) {

        for(String item : prefixList){

           FoamWorkplaceModuls foamWorkplaceModuls = new FoamWorkplaceModuls();
           foamWorkplaceModuls.setFoamWorkplace(foamWorkplace);
           foamWorkplaceModuls.setProdNrPrefix(item);
           create(foamWorkplaceModuls);
        }
    }

    public void deleteItems(FoamWorkplace foamWorkplace) {
        CriteriaAppender[] criteriaAppenders = {new Equal("foamWorkplace.workplace", foamWorkplace.getWorkplace())};
        for (FoamWorkplaceModuls item : find(Arrays.asList(criteriaAppenders))){
            System.out.println(item.getProdNrPrefix());
            delete(item);
        }
    }


}
