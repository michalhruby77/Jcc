package com.leoni.data.manager;

import com.leoni.data.models.Roles;
import com.leoni.data.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 27.10.2014
 * Time: 13:26
 * To change this template use File | Settings | File Templates.
 */
@Service("customAuthenticationProvider")
public class CustomAuthenticationProviderImpl implements  CustomAuthenticationProvider, Serializable {

    @Autowired
    UsersManager usersManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        Users user = null;
        if(name!=null||!name.isEmpty()) user = usersManager.getUser(name.trim());
        if(user!=null)
        {MessageDigest md = null;
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
        String pwMD5 = sb.toString();

        if (pwMD5.equals(user.getPassword().trim())) {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            for (Roles item : user.getUserRoles()){
            grantedAuths.add(new SimpleGrantedAuthority(item.getRole().trim()));}
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
            return auth;
        } else {
            throw  new BadCredentialsException("Zle meno alebo heslo1!");
        }
        }else{throw  new BadCredentialsException("Zle meno alebo heslo2!");}

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
