/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.presenter;

import com.vaadinspring.components.HibernateUtil;
import com.vaadinspring.model.Role;
import com.vaadinspring.model.Users;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author m.zhaksygeldy
 */
public class UserPresenterImpl implements UserPresenter{
    public void create(String username, String password,String email,String role){
        Users user=new Users();
        user.setLogin(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(findRole(role));
        Session session =HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }
    
    public Role findRole(String role_name){
        Session session =HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Role.class);
        cr.add(Restrictions.eq("role_name", role_name));
        Role role=(Role) cr.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return role;
    }
    
    public void delete(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Users user=(Users) session.get(Users.class,id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
    
    public void update(String username, String password,String email,String role){
        Users newUser=getUser(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setRole(findRole(role));
        Session session =HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Users user=(Users) session.merge(newUser);
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();
    }
    
    public void edit(String username, String password,String email,String image){
        Users newUser=getUser(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setImage(image);
        Session session =HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Users user=(Users) session.merge(newUser);
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();
    }
    
    public List<Users> getUsers(){
        Session session =HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Users.class);
        List<Users> users=cr.list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    public Users getUser(int id) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Users user=(Users) session.get(Users.class,id);
        session.getTransaction().commit();
        session.close();
        return user;
    }
    
    public Users getUser(String login) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Users.class);
        cr.add(Restrictions.eq("login", login));
        Users user=(Users) cr.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user;
    }
    
    public Users getCurrent(){
        Users currentUser=new Users();
        currentUser=getUser(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return currentUser;
    }
    
    public void logout(){
        SecurityContextHolder.clearContext();
    }
    
    public boolean hasRole(String role){
        if(role.equals(SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority()))
        return true;
        else return false;
    }

    public String getImagePath() {
        String path=this.getClass().getClassLoader().getResource("").toString();
        return path.substring(6,path.length()-49)+"src/main/resources/images/";
    }
}
