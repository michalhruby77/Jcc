package com.leoni.trying;

import com.leoni.data.manager.BandManagerImpl;
import com.leoni.data.manager.ModulsManager;
import com.leoni.data.manager.ModulsManagerImpl;
import com.leoni.data.models.Harness;
import com.leoni.data.models.Moduls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 17.12.2013
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */
public class ModulsViewModel {
    private BandManagerImpl bmgr = new BandManagerImpl();
    private ModulsManager modmgr = new ModulsManagerImpl();
    //private List<Person> persons = new ArrayList<Person>(personData.getPersonList());
    private List<Moduls> persons = new ArrayList<Moduls>(modmgr.getAll());
    public List<Moduls> getPersons()
    {
        return persons;
    }

    public void setPersons(List<Moduls> persons)
    {
        this.persons = persons;
    }

}

