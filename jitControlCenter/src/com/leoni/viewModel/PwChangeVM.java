package com.leoni.viewModel;

import com.leoni.data.manager.UsersManager;
import com.leoni.data.models.SchrittModulRm9X1Wrm;
import com.leoni.data.models.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 14.7.2014
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PwChangeVM {

    @WireVariable
    private UsersManager usersManager;

    private String oldPw = "";
    private String newPw = "";
    private String newPw2 = "";

    @Command
    @NotifyChange({"oldPw","newPw","newPw2"})
    public void submit() {
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users tempUser = usersManager.getUser(user);

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        md.update(oldPw.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        String oldPwMD5 = sb.toString();


        if(tempUser.getPassword().trim().equals(oldPwMD5.trim())){
            if(newPw.trim().length()>3){
                if(newPw.trim().equals(newPw2.trim())){

                    md = null;
                    try {
                        md = MessageDigest.getInstance("MD5");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    md.update(newPw.getBytes());
                    digest = md.digest();
                    sb = new StringBuffer();
                    for (byte b : digest) {
                        sb.append(String.format("%02x", b & 0xff));
                    }

                    tempUser.setPassword(sb.toString());
                    usersManager.saveUser(tempUser);
                    Messagebox.show("Heslo zmenene!", "Zmena hesla", Messagebox.OK, null);
                    oldPw = "";
                    newPw = "";
                    newPw2 = "";
                }
                else{Messagebox.show("Nove heslo sa nezhoduje!", "Chyba.", Messagebox.OK, Messagebox.ERROR);}
            } else{Messagebox.show("Nove heslo musi mat aspon 4 znaky!", "Chyba.", Messagebox.OK, Messagebox.ERROR);}
        }  else{Messagebox.show("Zle zadane stare heslo!", "Chyba.", Messagebox.OK, Messagebox.ERROR);}
    }

    public String getOldPw() {
        return oldPw;
    }

    public void setOldPw(String oldPw) {
        this.oldPw = oldPw;
    }

    public String getNewPw() {
        return newPw;
    }

    public void setNewPw(String newPw) {
        this.newPw = newPw;
    }

    public String getNewPw2() {
        return newPw2;
    }

    public void setNewPw2(String newPw2) {
        this.newPw2 = newPw2;
    }
}
