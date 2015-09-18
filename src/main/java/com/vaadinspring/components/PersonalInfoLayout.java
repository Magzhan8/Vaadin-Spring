/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.components;

import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import com.vaadinspring.model.Users;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author m.zhaksygeldy
 */
public class PersonalInfoLayout extends VerticalLayout{
    public TextField username;
    public TextField password;
    public TextField email;
    public Image userImage;
    public ComboBox role;
    public Button button;
    public PersonalInfoLayout(){
        this.setSpacing(true);
        this.setMargin(true);
        
        username=new TextField("username");
        password=new TextField("password");
        email=new TextField("email");
        role=new ComboBox("Role");
        role.addItem("user");
        role.addItem("admin"); 
        button=new Button();
        
        this.addFieldValidators();
        this.addComponent(username);
        this.addComponent(password);
        this.addComponent(email);
        this.addComponent(role);
    }
    
    public void addFieldValidators(){
        username.addFocusListener(e->{
            username.setImmediate(true);
            username.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "login"));
        });
        password.addFocusListener(e->{
            password.setImmediate(true);
            password.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "password"));
        });
        email.addFocusListener(e->{
            email.setImmediate(true);
            email.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "email"));
        });
        role.addFocusListener(e->{
            role.setImmediate(true);
            role.addValidator(new BeanValidator(com.vaadinspring.model.Role.class, "role_name"));
        });   
    }
    
    public boolean isValidFields(){
        if(username.isValid() && password.isValid() && email.isValid() && role.isValid()) return true;
        else return false;
    }
    
    public String getUsernameValue(){
        return username.getValue();
    }
    
    public String getPasswordValue(){
        return password.getValue();
    }
    
    public String getEmailValue(){
        return email.getValue();
    }
    
    public String getRoleValue(){
        return role.getValue().toString();
    }
    
    public List<String> getFieldValues(){
        List<String> allFields=new ArrayList<String>();
        allFields.add(username.getValue());
        allFields.add(password.getValue());
        allFields.add(email.getValue());
        allFields.add(role.getValue().toString());
        return allFields;
    }
    
    public void clearFieldValues(){
        username.setValue("");
        password.setValue("");
        email.setValue("");
        role.setValue("");
    }
    
    public void setAllFields(Users user){
        username.setValue(user.getLogin());
        password.setValue(user.getPassword());
        email.setValue(user.getEmail());
        role.setValue(user.getRole().getRole_name());
    }
    
    public void setUsernameValue(String username){
        this.username.setValue(username);
    }
    
    public void setPasswordValue(String password){
        this.password.setValue(password);
    }
    
    public void setEmailValue(String email){
        this.email.setValue(email);
    }
    
    public void setRoleValue(String role){
        this.role.setValue(role);
    }
}
