/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadinspring.components.CustomView;
import com.vaadinspring.presenter.UserPresenter;
import com.vaadinspring.presenter.UserPresenterImpl;

/**
 *
 * @author m.zhaksygeldy
 */
public class UserView extends Panel implements View{
    private CustomView customView;
    private UserPresenter userPresenter;
    private GridLayout mainLayout;
    private VerticalLayout contentLayout;
    
    public UserView(){
        setSizeFull();
        userPresenter=new UserPresenterImpl();
        customView=new CustomView();
        mainLayout=customView.getTemplate();
        contentLayout=new VerticalLayout();
        contentLayout.setHeightUndefined();
        contentLayout.setSpacing(true);
        contentLayout.setMargin(true);
        
        contentLayout.addComponent(new Label("This is user's page"));
        mainLayout.addComponent(contentLayout, 1, 1);
        setContent(mainLayout);
    }
    
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
    
}
