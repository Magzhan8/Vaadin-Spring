package com.vaadinspring.components;


import com.vaadinspring.model.Users;
import com.vaadinspring.presenter.UserPresenter;
import com.vaadinspring.presenter.UserPresenterImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author m.zhaksygeldy
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
   public UserPresenter userPresenter=new UserPresenterImpl();
   @Override
   public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        Users user=userPresenter.getUser(name);
        if (user.getLogin()!=null && password.equals(user.getPassword())) {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>(1);
            grantedAuths.add(new SimpleGrantedAuthority(user.getRole().getRole_name()));
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return auth;
        } else {
            return null;
        }
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
