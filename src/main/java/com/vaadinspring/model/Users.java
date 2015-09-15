/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
        

/**
 *
 * @author m.zhaksygeldy
 */
@Entity
public class Users {
   
   @Id
   @GeneratedValue
   private int UserId;
   
   @NotBlank(message="Login cannot be blank")
   @Size(min=5, max=15)
   private String login;
   
   @NotBlank(message="Password cannot be blank")
   @Size(min=5, max=15)
   private String password;
   
   @NotBlank(message="Email cannot be blank")
   @Email
   private String email;
   
   @NotBlank(message="Role cannot be blank")
   private String role; 
   
   private String image="default.jpg";

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
   
}
