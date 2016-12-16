package com.leoni.data.manager;

import com.leoni.data.models.NewModules;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 11.6.2014
 * Time: 15:20
 * To change this template use File | Settings | File Templates.
 */
public interface NewModulesManager  extends GenericManager<NewModules>{
    public NewModules saveNewModule(NewModules newModules);
    public NewModules addNewModule(String sachNrBest, String notiz);
    public List<String> getNewModulesList(String modulsString);
    public List<NewModules> findBySachNrBest(String sachNrBest);
}
