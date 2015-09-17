/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author m.zhaksygeldy
 */
@Entity
public class User_role implements Serializable {
   @Id
   @GeneratedValue
   private int role_user_id;
   @Id
   private int user_id;
   @Id
   private int role_id;
   
   
    public int getRole_user_id() {
        return role_user_id;
    }

    public void setRole_user_id(int role_user_id) {
        this.role_user_id = role_user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    } 
}
