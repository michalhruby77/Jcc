package com.leoni.trying;

/**
 * Created with IntelliJ IDEA.
 * User: pada1005
 * Date: 30.7.2012
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class Person
    {
    private String firstName;
    private String lastName;
    private int age;
    private String mail;
    private String title;

    public Person()
        {
        firstName = "";
        lastName = "";
        age = 0;
        mail = "";
        title = "";
        }

    public Person(String firstName, String lastName, int age, String mail, String title)
        {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.mail = mail;
        this.title = title;
        }

    public String getFirstName()
        {
        return firstName;
        }

    public void setFirstName(String firstName)
        {
        this.firstName = firstName;
        }

    public String getLastName()
        {
        return lastName;
        }

    public void setLastName(String lastName)
        {
        this.lastName = lastName;
        }

    public int getAge()
        {
        return age;
        }

    public void setAge(int age)
        {
        this.age = age;
        }

    public String getMail()
        {
        return mail;
        }

    public void setMail(String mail)
        {
        this.mail = mail;
        }

    public String getTitle()
        {
        return title;
        }

    public void setTitle(String title)
        {
        this.title = title;
        }
    }
