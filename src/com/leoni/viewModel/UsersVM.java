package com.leoni.viewModel;

import com.leoni.data.manager.RolesManager;
import com.leoni.data.manager.SicherungenRelais9X1WrmManager;
import com.leoni.data.manager.UsersManager;
import com.leoni.data.models.Roles;
import com.leoni.data.models.Users;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;


import javax.management.relation.Role;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 29.5.2014
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UsersVM {

    @WireVariable
    private UsersManager usersManager;

    @WireVariable
    private RolesManager rolesManager;

    List<Users> usersList;
    Users userToDelete;
    private List<Combobox> comboList;
    private Hbox hboxIn;
    private String username;
    private String password;
    private Boolean enabled;
    private String name;

    @Wire
    Hbox hbox;

    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view)
    {   Selectors.wireComponents(view, this, false);
        hboxIn = new Hbox();
        enabled = true;
        hbox.appendChild(hboxIn);
        comboList = new ArrayList<Combobox>();
        usersList = usersManager.getAllUsers();
        Combobox customRolesCombobox = new Combobox();
        RoleComboboxRenderer roleComboboxRenderer = new RoleComboboxRenderer();
        customRolesCombobox.setItemRenderer(roleComboboxRenderer);
        customRolesCombobox.setModel(new ListModelList<Roles>(rolesManager.getAll()));
        customRolesCombobox.setWidth("100px");
        hboxIn.appendChild(customRolesCombobox);
        comboList.add(customRolesCombobox);
        Button addBtn = new Button("+");
        addBtn.setMold("trendy");
        addBtn.addEventListener("onClick", new EventListener() {
            public void onEvent(Event evt) {
               Combobox customRolesCombobox = new Combobox();
                RoleComboboxRenderer roleComboboxRenderer = new RoleComboboxRenderer();
                customRolesCombobox.setItemRenderer(roleComboboxRenderer);
                customRolesCombobox.setModel(new ListModelList<Roles>(rolesManager.getAll()));
                customRolesCombobox.setWidth("100px");
                hboxIn.appendChild(customRolesCombobox);
                comboList.add(customRolesCombobox);
            }
        });
        hbox.appendChild(addBtn);
    }

    @Command
    @NotifyChange({"usersList"})
    public void deleteUser(@BindingParam("user") Users myUser)
    {
        userToDelete=myUser;
        Messagebox.show("Are you sure?", "Delete?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                new EventListener() {
                    public void onEvent(Event evt) {
                        if ("onYes".equals(evt.getName())) {
                            usersManager.delete(userToDelete);
                            usersList = usersManager.getAllUsers();
                            BindUtils.postNotifyChange(null, null, UsersVM.this, "usersList");
                        }
                    }
                }
        );
    }

    @Command
    @NotifyChange({"usersList","username","password","enabled","name"})
    public void generateNewUser()
    {
        if(username!=null&&password!=null){
          int enabledInt;
          if(enabled){enabledInt=1;} else enabledInt=0;
          //Set<UserRole> setRoles = new HashSet<UserRole>();
            MessageDigest md = null;
            try {
              md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

          //Users newUser =  usersManager.addUser(username,password,name,enabledInt);
            SortedSet<Roles> userRoles = new TreeSet<Roles>();
            for(Combobox item : comboList){
                ListModelList lml = (ListModelList)item.getModel();
                Set<Roles> rolesSet = (Set<Roles>) lml.getSelection();
                Roles selectedRole = null;
                for(Roles tempRole : rolesSet){
                    selectedRole = tempRole;
                }
                if (selectedRole!=null)userRoles.add(selectedRole);
          }
            Users newUser =  usersManager.addUser(username,sb.toString(),name,enabledInt,userRoles);
          username=null;
          password=null;
          name=null;
          enabled=true;
          for(Combobox item : comboList){
              hboxIn.removeChild(item);
          }
         comboList.clear();
         Combobox customRolesCombobox = new Combobox();
         RoleComboboxRenderer roleComboboxRenderer = new RoleComboboxRenderer();
         customRolesCombobox.setItemRenderer(roleComboboxRenderer);
         customRolesCombobox.setModel(new ListModelList<Roles>(rolesManager.getAll()));
         customRolesCombobox.setWidth("100px");
         hboxIn.appendChild(customRolesCombobox);
         comboList.add(customRolesCombobox);
        }
        usersList = usersManager.getAllUsers();
    }

    @Command
    @NotifyChange({"usersList"})
    public void editUser(@BindingParam("user") Users myUser)
    {
                            Map<String, Users> arguments = new HashMap<String, Users>();
                            arguments.put("myUser", myUser);
                            Window window = (Window) Executions.createComponents(
                                    "usersEditor/editUser.zul", null, arguments);
                            window.doModal();

    }

    @GlobalCommand
    @NotifyChange({"usersList"})
    public void refresh(){
        usersList = usersManager.getAllUsers();
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Users getUserToDelete() {
        return userToDelete;
    }

    public void setUserToDelete(Users userToDelete) {
        this.userToDelete = userToDelete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
