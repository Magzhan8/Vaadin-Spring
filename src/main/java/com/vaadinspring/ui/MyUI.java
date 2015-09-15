/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadinspring.presenter.LoginPresenter;
import com.vaadinspring.presenter.LoginPresenterImpl;
import com.vaadinspring.view.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 *
 * @author m.zhaksygeldy
 */
@Component
@Scope("prototype")
@Theme("tests-valo-facebook")
//@PreserveOnRefresh
public class MyUI extends UI implements ErrorHandler{  

    public Navigator navigator;    
    
    @Override
    protected void init(VaadinRequest request) {
//            this.setErrorHandler(new ErrorHandler() {
//            
//            @Override
//            public void error(com.vaadin.server.ErrorEvent event) {
//                Notification.show("Error", event.getThrowable().getMessage(), Notification.Type.ERROR_MESSAGE);
//            }});
          LoginPresenter loginPresenter=new LoginPresenterImpl();
          navigator=new Navigator(this,this);
          navigator.addView("",new LoginView());
          navigator.addViewChangeListener(new ViewChangeListener(){
              @Override
              public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
                  View newView=event.getNewView();
                  View oldView=event.getOldView();
                  return loginPresenter.canUserAccessView(oldView, newView);
              }

              @Override
              public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {                  
              }              
          });
          setNavigator(navigator);
    }
    
    @Override
    public void error(com.vaadin.server.ErrorEvent event) {
        if(event.getThrowable().getCause() instanceof IllegalArgumentException) 
                  Notification.show("you are trying to navigate to unknown page");
    }
 
}
