/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.view;

import com.vaadin.data.validator.BeanValidator;
import com.vaadin.navigator.*;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadinspring.components.CustomAuthenticationProvider;
import com.vaadinspring.presenter.*;
/**
 *
 * @author m.zhaksygeldy
 */
public class LoginView extends Panel implements View{
    private LoginPresenter loginPresenter;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    
    public LoginView(){ 
        loginPresenter=new LoginPresenterImpl();
        usernameField=new TextField("Username");
        passwordField=new PasswordField("Password");
        loginButton=new Button("Login");
        setSizeFull();        
        VerticalLayout loginLayout=new VerticalLayout();
        VerticalLayout innerLayout=createLoginLayout();
        addFieldValidators();
        onLoginButtonClick();
        loginLayout.setSizeFull(); 
        loginLayout.addComponent(innerLayout);
        loginLayout.setComponentAlignment(innerLayout, Alignment.MIDDLE_CENTER);
        setContent(loginLayout);
    }
    
    public VerticalLayout createLoginLayout(){
        VerticalLayout innerLayout=new VerticalLayout();
        innerLayout.setSpacing(true);
        innerLayout.setWidth(200, Sizeable.Unit.PIXELS);
        innerLayout.setHeightUndefined();
        innerLayout.setMargin(true);
        innerLayout.setPrimaryStyleName("login"); 
        innerLayout.addComponent(usernameField);
        innerLayout.addComponent(passwordField);
        innerLayout.addComponent(loginButton);
        innerLayout.setComponentAlignment(loginButton, Alignment.MIDDLE_CENTER);
        return innerLayout;
    }
    
    public void addFieldValidators(){
        usernameField.addFocusListener(e->{
            usernameField.setImmediate(true);
            usernameField.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "login"));
        });
        passwordField.addFocusListener(e->{
            passwordField.setImmediate(true);
            passwordField.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "password"));
        });
    }
    
    public void onLoginButtonClick(){
        loginButton.addClickListener(e->{
            if(usernameField.isValid() && passwordField.isValid()){
                boolean successLogin=loginPresenter.doLogin(usernameField.getValue(),passwordField.getValue());
                if(successLogin) navigateAfterLogin();
                else showLoginError();
            }
        });
    }    
    
    public void navigateAfterLogin(){    
        if(loginPresenter.getNavigatorView().equals("user")){       
            getUI().getNavigator().addView("user", new UserView());
            getUI().getNavigator().navigateTo("user");
        }
        else{;
            getUI().getNavigator().addView("admin", new AdminView());
            getUI().getNavigator().navigateTo("admin");
        }
    }
    
    public void showLoginError(){
        Notification loginError=new Notification("Bad Credentials","Try Again",Notification.TYPE_ERROR_MESSAGE);
        loginError.setDelayMsec(1000);
        loginError.show(Page.getCurrent());
    }
    
    public void enter(ViewChangeListener.ViewChangeEvent event) {    
    }    
}
