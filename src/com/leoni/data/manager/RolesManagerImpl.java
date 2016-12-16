package com.leoni.data.manager;

import com.leoni.data.models.Roles;
import com.leoni.data.models.Users;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 29.5.2014
 * Time: 8:56
 * To change this template use File | Settings | File Templates.
 */
@Service("rolesManager")
public class RolesManagerImpl  extends GenericManagerImpl<Roles> implements RolesManager{
    /*public UserRole saveUserRole(UserRole userRole) {
        return saveOrUpdate(userRole);
    }

    public UserRole addUserRole(Users username, String userRole) {
        UserRole newUserRole = new UserRole();
        //newUserRole.setUser(username);
        newUserRole.setRole(userRole);
        return create(newUserRole);
    } */
}
