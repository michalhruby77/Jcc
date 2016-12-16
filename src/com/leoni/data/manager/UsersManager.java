package com.leoni.data.manager;


import com.leoni.data.models.Roles;
import com.leoni.data.models.Users;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 29.5.2014
 * Time: 8:49
 * To change this template use File | Settings | File Templates.
 */
public interface UsersManager extends GenericManager<Users> {
    public Users saveUser(Users user);
    public Users addUser(String userName, String password, String name, Integer enabled, SortedSet<Roles> roles);
    public List<Users> getAllUsers();
    public Users getUser(String userName);


}
