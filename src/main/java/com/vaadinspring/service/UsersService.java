/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.service;

import com.vaadinspring.components.HibernateUtil;
import com.vaadinspring.model.Users;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 *
 * @author m.zhaksygeldy
 */
public class UsersService {
    
    public void create(Users user){
        Session session =HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }
    
    public void delete(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Users user=(Users) session.get(Users.class,id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
    
    public void update(Users newUser){
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
        List<Users> users=session.createCriteria(Users.class).list();
        System.out.println(users.get(0).getLogin());
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
}
