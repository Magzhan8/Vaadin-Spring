/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.view;

import com.vaadin.data.validator.BeanValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadinspring.components.CustomAuthenticationProvider;
import com.vaadinspring.presenter.*;
import org.springframework.security.core.Authentication;
/**
 *
 * @author m.zhaksygeldy
 */
public class LoginView extends Panel implements View{
    private LoginPresenter loginPresenter;
    public String username;
    public String password;
    public Authentication authentication;
    public CustomAuthenticationProvider cust;
    public LoginView(){ 
        setSizeFull();
        loginPresenter=new LoginPresenterImpl();
//        if(loginPresenter.hasRole("user")) Page.getCurrent().setUriFragment("#!user");
//        if(loginPresenter.hasRole("admin")) Page.getCurrent().setUriFragment("#!admin");
//        else{
            final VerticalLayout layout=new VerticalLayout();
            layout.setSizeFull();   
            VerticalLayout innerLayout=loginLayout();
            layout.addComponent(innerLayout);
            layout.setComponentAlignment(innerLayout, Alignment.MIDDLE_CENTER);
            setContent(layout);
//        }     
    }
    
    public VerticalLayout loginLayout(){
        final VerticalLayout innerLayout=new VerticalLayout();
        innerLayout.setSpacing(true);
        innerLayout.setWidth(200, Sizeable.Unit.PIXELS);
        innerLayout.setHeightUndefined();
        innerLayout.setMargin(true);
        innerLayout.setPrimaryStyleName("login");        
        final TextField username=new TextField("Username");
        final PasswordField password=new PasswordField("Password");
        Button login=new Button("Login");
                
        username.addFocusListener(e->{
            username.setImmediate(true);
            username.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "login"));
        });
        password.addFocusListener(e->{
            password.setImmediate(true);
            password.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "password"));
        });
        login.addClickListener(e->{
            if(username.isValid() && password.isValid()){
            boolean log=loginPresenter.doLogin(username.getValue(), password.getValue());
                if(log){
                    navigateToView();
                }
                else loginError("Bad credentials","try again");
            }
        });
        
        
        innerLayout.addComponent(username);
        innerLayout.addComponent(password);
        innerLayout.addComponent(login);
        innerLayout.setComponentAlignment(login, Alignment.MIDDLE_CENTER);
        return innerLayout;
    }
    
    public void navigateToView(){
        getUI().getNavigator().addView("admin", new AdminView());
        getUI().getNavigator().addView("user", new UserView());
        if(loginPresenter.getNavigatorView().equals("user")){            
            //getUI().getNavigator().addView("user", new UserView());
            getUI().getNavigator().navigateTo("user");
        }
        else{
            //getUI().getNavigator().addView("admin", new AdminView());
            getUI().getNavigator().navigateTo("admin");
        }
    }
    
    public void loginError(String main,String detail){
        Notification loginError=new Notification(main,detail,Notification.TYPE_ERROR_MESSAGE);
        loginError.setDelayMsec(1000);
        loginError.show(Page.getCurrent());
    }
    
    public void enter(ViewChangeListener.ViewChangeEvent event) {    
    }    
}
