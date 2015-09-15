/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.presenter;

import com.vaadinspring.components.HibernateUtil;
import com.vaadinspring.model.Users;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author m.zhaksygeldy
 */
public interface UserPresenter {
    public void create(String username, String password,String email,String role);    
    public void delete(int id);    
    public void update(String username, String password,String email,String role);    
    public List<Users> getUsers();
    public Users getUser(int id);    
    public Users getUser(String login);
    public Users getCurrent();
    public boolean hasRole(String role);
    public void logout();
}
