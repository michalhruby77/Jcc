package com.leoni.trying;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 30.7.2012
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
public class PersonData
    {
    private List<Person> personList = new ArrayList<Person>();

    public PersonData()
        {
        personList.add(new Person("Daniel", "Pastorek", 24, "pastorek.daniel@gmail.com", ""));
        personList.add(new Person("Mario", "Korcak", 23, "mario.korcak@gmail.com", ""));
        personList.add(new Person("Roman", "Pastorek", 18, "pastorek.roman@gmail.com", ""));
        personList.add(new Person("Miriama", "Danisova", 21, "miriama.danisova@gmail.com", ""));
        }

    public List<Person> getFilterPersons(PersonFilter personFilter)
        {
        List<Person> filteredPersons = new ArrayList<Person>();
        for (Person p : personList)
            {
            if (p.getFirstName().toLowerCase().indexOf(personFilter.getFirstName().toLowerCase()) >= 0 &&
                    p.getLastName().toLowerCase().indexOf(personFilter.getLastName().toLowerCase()) >= 0 &&
                    Integer.toString(p.getAge()).toLowerCase().indexOf(personFilter.getAge().toLowerCase()) >= 0)
                {
                filteredPersons.add(p);
                }
            }
        return filteredPersons;
        }

    public List<Person> getPersonList()
        {
        return personList;
        }

    public void setPersonList(List<Person> personList)
        {
        this.personList = personList;
        }
    }
