package com.leoni.viewModel;

import com.leoni.data.manager.RolesManager;
import com.leoni.data.manager.UsersManager;
import com.leoni.data.models.Roles;
import com.leoni.data.models.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 28.10.2014
 * Time: 11:02
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EditUserVM {
    Users user;
    String username;
    String password;
    String name;
    Set<Roles> rolesSet;
    List<Combobox> cbList = new ArrayList<Combobox>();
    @WireVariable
    private UsersManager usersManager;

    @WireVariable
    private RolesManager rolesManager;

    @Wire
    Hlayout hlayout;

    @AfterCompose
    public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
                               @ExecutionArgParam("myUser") Users myUser) throws Exception {
        Selectors.wireComponents(view, this, false);
        user=myUser;
        username = myUser.getUsername().trim();
        password = "";
        name = myUser.getName();
        rolesSet = myUser.getUserRoles();
        for (Roles item:rolesSet){
            List<Roles> selectedRoles = new ArrayList<Roles>();
            List<Roles> allRoles = rolesManager.getAll();
            //selectedRole.add(item);
            Combobox customRolesCombobox = new Combobox();
            ListModelList lml = new ListModelList<Roles>(allRoles);
            //lml.add(selectedRole.get(0));
            customRolesCombobox.setModel(lml);
            for (Roles itemTemp :allRoles){
               if (itemTemp.equals(item))selectedRoles.add(itemTemp);
            }
            lml.setSelection(selectedRoles);
            RoleComboboxRenderer roleComboboxRenderer = new RoleComboboxRenderer();
            customRolesCombobox.setItemRenderer(roleComboboxRenderer);
            customRolesCombobox.setWidth("100px");
            cbList.add(customRolesCombobox);
            hlayout.appendChild(customRolesCombobox);
        }
    }

    @Command
    //@NotifyChange({"modul21Vis", "modul31Vis", "modul41Vis", "modul51Vis", "modul61Vis"})
    public void addTextBoxHorizont() {
        Combobox customRolesCombobox = new Combobox();
        RoleComboboxRenderer roleComboboxRenderer = new RoleComboboxRenderer();
        customRolesCombobox.setItemRenderer(roleComboboxRenderer);
        ListModelList lml = new ListModelList<Roles>(rolesManager.getAll());
        customRolesCombobox.setModel(lml);
        customRolesCombobox.setWidth("100px");
        cbList.add(customRolesCombobox);
        hlayout.appendChild(customRolesCombobox);
        }

    @Command
    public void submit(@ContextParam(ContextType.VIEW) Component view) {
        if (username!=null&&!username.trim().equals("")){
        String newPw = null;
        if(password!=null&&!password.trim().equals("")){
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
            newPw = sb.toString();
        }
        SortedSet<Roles> selectedRoles = new TreeSet<Roles>();
        for (Combobox item:cbList){
            ListModelList lml = (ListModelList) item.getModel();
            Set<Roles> tempSet = lml.getSelection();
            for(Roles tempRole : tempSet){
                selectedRoles.add(tempRole);
            }
        }
        user.setUsername(username.trim());
        if(newPw!=null)user.setPassword(newPw);
        user.setName(name.trim());
        user.setUserRoles(selectedRoles);
        usersManager.saveUser(user);
        BindUtils.postGlobalCommand(null, null, "refresh", null);
        Messagebox.show("Zaznam ulozeny!", "Ulozene.", Messagebox.OK, null);
        view.detach();}
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Roles> getRolesSet() {
        return rolesSet;
    }

    public void setRolesSet(Set<Roles> rolesSet) {
        this.rolesSet = rolesSet;
    }
}
