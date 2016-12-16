package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.NewModules;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 11.6.2014
 * Time: 15:22
 * To change this template use File | Settings | File Templates.
 */
@Service("newModulesManager")
public class NewModulesManagerImpl extends GenericManagerImpl<NewModules> implements NewModulesManager{

    public NewModules saveNewModule(NewModules newModules) {
        return saveOrUpdate(newModules);    }

    public NewModules addNewModule(String sachNrBest, String notiz) {
        NewModules newModules = new NewModules();
        newModules.setSachNrBest(sachNrBest);
        newModules.setNotiz(notiz);
        return create(newModules);
    }

    public List<String> getNewModulesList(String modulsString) {
        List<String> tempList = new ArrayList<String>();
        String[] modulsArray = modulsString.split("\\+");
        for (String item : modulsArray){
            tempList.add(item);
        }
        return tempList;
    }

    public List<NewModules> findBySachNrBest(String sachNrBest) {
        CriteriaAppender[] criteriaAppenders = {new Equal("sachNrBest", sachNrBest.trim())};
        return find(Arrays.asList(criteriaAppenders));
    }


}
