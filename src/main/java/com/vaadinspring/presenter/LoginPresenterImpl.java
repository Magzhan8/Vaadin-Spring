/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.presenter;

import com.vaadin.navigator.View;
import com.vaadinspring.components.CustomAuthenticationProvider;
import com.vaadinspring.model.Users;
import com.vaadinspring.view.AdminView;
import com.vaadinspring.view.LoginView;
import com.vaadinspring.view.UserView;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author m.zhaksygeldy
 */
public class LoginPresenterImpl implements LoginPresenter{
    private CustomAuthenticationProvider authProvider;
    private Authentication auth;
    public LoginPresenterImpl(){
        authProvider=new CustomAuthenticationProvider();
    }
    
    public boolean doLogin(String username, String password) {
        auth=authProvider.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        if(auth==null) return false;
        else return true;
    }

    public String getNavigatorView(){
        if(auth.getAuthorities().iterator().next().getAuthority().equals("user")) return "user";
        else return "admin";
    }
    
    public boolean hasRole(String role){
        if(role.equals(SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority()))
        return true;
        else return false;
    }

    @Override
    public boolean canUserAccessView(View oldView, View newView) {
        if((!(hasRole("admin") || hasRole("user")) && (oldView instanceof LoginView))) return false;
        if(((hasRole("admin") || hasRole("user")) && !(oldView instanceof LoginView))) return false;
        else return true;
    }   
}
