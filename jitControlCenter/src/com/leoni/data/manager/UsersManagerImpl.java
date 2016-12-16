package com.leoni.data.manager;

import com.leoni.data.criterion.CriteriaAppender;
import com.leoni.data.criterion.Equal;
import com.leoni.data.models.Roles;
import com.leoni.data.models.Users;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 29.5.2014
 * Time: 8:57
 * To change this template use File | Settings | File Templates.
 */
@Service("usersManager")
public class UsersManagerImpl  extends GenericManagerImpl<Users> implements UsersManager {
    public Users saveUser(Users user) {
        return saveOrUpdate(user);
    }

    public Users addUser(String userName, String password, String name, Integer enabled, SortedSet<Roles> roles) {
        Users newUser = new Users();
        newUser.setUsername(userName);
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setUserRoles(roles);
        return create(newUser);
    }

    public List<Users> getAllUsers() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Users.class);
        detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Users>)getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    public Users getUser(String userName) {
        CriteriaAppender[] criteriaAppenders = {new Equal("username", userName)};
        List<Users> usersList =  find(Arrays.asList(criteriaAppenders));
        if (!usersList.isEmpty()) return usersList.get(0);
        else return null;

    }
}
