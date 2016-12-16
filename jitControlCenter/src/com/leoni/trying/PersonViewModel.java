package com.leoni.trying;


import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 30.7.2012
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
public class PersonViewModel
    {
    private Person selected;
    private List<String> titles = new ArrayList<String>();
    private PersonData personData = new PersonData();


   private List<Person> persons = new ArrayList<Person>(personData.getPersonList());

     private PersonFilter filter = new PersonFilter();

    @Init
    public void init()
        {
        selected=persons.get(0);
        titles.add("Bc.");
        titles.add("Ing.");
        titles.add("Dr.");
        titles.add("Phdr.");
        }

    @NotifyChange ("persons")
    @Command
    public void addNew()
        {
        Person p = new Person();
        persons.add(p);
        selected = p;
        }

    @NotifyChange("persons")
    @Command
    public void deletePerson ()
        {
        persons.remove(selected);
        selected = null;
        }

    @NotifyChange("persons")
    @Command
    public void filterPersons()
        {
        persons = personData.getFilterPersons(filter);
        }

    public PersonFilter getFilter()
        {
        return filter;
        }

    public void setFilter(PersonFilter filter)
        {
        this.filter = filter;
        }

    public Person getSelected()
        {
        return selected;
        }

    public void setSelected(Person selected)
        {
        this.selected = selected;
        }

    public List<String> getTitles()
        {
        return titles;
        }

    public void setTitles(List<String> titles)
        {
        this.titles = titles;
        }

    public List<Person> getPersons()
        {
        return persons;
        }

    public void setPersons(List<Person> persons)
        {
        this.persons = persons;
        }

    }
