/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
        

/**
 *
 * @author m.zhaksygeldy
 */
@Entity
public class Users implements Serializable {
   
   @Id
   @GeneratedValue
   private int user_id;
   
   @NotBlank(message="Login cannot be blank")
//   @Size(min=5, max=15)
   private String login;
   
   @NotBlank(message="Password cannot be blank")
//   @Size(min=5, max=15)
   private String password;
   
   @NotBlank(message="Email cannot be blank")
//   @Email
   private String email;
   
   private String image="default.jpg"; 
   
   @ManyToOne
   @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
   private Role role; 
   
   
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
   
   public String getLogin() {
        return login;
    }

   public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
   

    public Role getRole(){
        return this.role;
    }
    
    public void setRole(Role role){
        this.role=role;
    }
}
