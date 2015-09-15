/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.components;

import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import java.io.File;

/**
 *
 * @author m.zhaksygeldy
 */
public class TemplateView{
    private GridLayout mainLayout;
    private GridLayout sidebarLayout;
    private HorizontalLayout headerLayout;
    private Label userLogin;
    private Button logoutButton;
    private Button editButton;
    private Image userImage;
    private FileResource file;
    public TemplateView(){
        mainLayout=MainLayout();
        mainLayout.setSizeFull();        
        sidebarLayout=SidebarLayout();
        sidebarLayout.setSizeFull();
        mainLayout.addComponent(sidebarLayout, 0, 1);
    }
    
    public GridLayout MainLayout(){
        GridLayout grid=new GridLayout(2,2);   
        grid.setColumnExpandRatio(0, 1);
        grid.setColumnExpandRatio(1, 3);
        grid.setRowExpandRatio(0, 1);
        grid.setRowExpandRatio(1, 4);
        return grid;
    }
    
    public GridLayout SidebarLayout() {
        GridLayout sideBar= new GridLayout(7,10);
        sideBar.setColumnExpandRatio(1, 2);
        sideBar.setColumnExpandRatio(3, 2);
        
        sideBar.setSizeFull();
        sideBar.setSpacing(true);
        sideBar.setMargin(true);
        userLogin=new Label("Username");
        file=new FileResource(new File("C:\\Users\\m.zhaksygeldy\\Desktop\\spring-integration\\src\\images\\"));     
        
        editButton=new Button("Edit");
        logoutButton=new Button("Logout");
        
        userImage.setWidth("180px"); 
        userImage.setHeight("240px");                
        
        sideBar.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        sideBar.addComponent(userImage,1,1,3,1);
        GridLayout inner=new GridLayout(6,2);        
        inner.setSizeFull();        
        inner.addComponent(userLogin,1,0); 
        inner.addComponent(editButton,0,1);
        inner.addComponent(logoutButton,3,1);
        sideBar.addComponent(inner,1,2,3,2);
        //layout.setComponentAlignment(userImage, Alignment.TOP_CENTER);
        //layout.setComponentAlignment(userLogin, Alignment.TOP_CENTER);
        //layout.setComponentAlignment(grid, Alignment.TOP_CENTER);
        return sideBar;  
    }
}
