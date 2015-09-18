/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.view;

import com.vaadin.data.validator.BeanValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadinspring.components.CustomView;
import com.vaadinspring.components.PersonalInfoLayout;
import com.vaadinspring.presenter.UserPresenter;
import com.vaadinspring.presenter.UserPresenterImpl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author m.zhaksygeldy
 */
public class AdminView extends Panel implements View{
    private UserPresenter userPresenter;
    private CustomView customView;
    private GridLayout mainLayout;
    private VerticalLayout contentLayout;
    private Table userTable;
    private Window addWindow;
    private Window updateWindow;
    private VerticalLayout windowLayout;
    public AdminView(){
        userPresenter=new UserPresenterImpl();
        mainLayout=new CustomView();
        contentLayout=new VerticalLayout();
        contentLayout.setHeightUndefined();
        contentLayout.setSpacing(true);
        contentLayout.setMargin(true);
        
        createTable();
        populateTable();
        userTable.setSizeFull();
        createAddWindow();
        
        Button addButton=new Button("Add");
        addButton.addClickListener(e-> {
             showWindow(addWindow);
        });        
        
        contentLayout.addComponent(addButton);
        contentLayout.setComponentAlignment(addButton, Alignment.TOP_RIGHT);
        contentLayout.addComponent(userTable);
        
        mainLayout.addComponent(contentLayout, 1, 1);
        setContent(mainLayout);
    }
    
    public void createTable(){
        userTable = new Table();
        userTable.addContainerProperty("Name", String.class, null);
        userTable.addContainerProperty("Password",String.class, null);
        userTable.addContainerProperty("Email",String.class, null);
        userTable.addContainerProperty("Role",String.class, null);
        addGeneratedButtonColumns();
        userTable.setWidthUndefined();
    }
    
    public void addGeneratedButtonColumns(){
        userTable.addGeneratedColumn("delete", new Table.ColumnGenerator() {
              @Override public Object generateCell(final Table source, final Object itemId, Object columnId) {
              Button button = new Button("Delete");              
              //delete button
              button.addClickListener(e-> { 
              source.getContainerDataSource().removeItem(itemId);
              userPresenter.delete(Integer.parseInt(itemId.toString()));              
              });              
              return button;
            }
        });
        
        userTable.addGeneratedColumn("update", new Table.ColumnGenerator() {
              @Override public Object generateCell(final Table source, final Object itemId, Object columnId) {
              Button button1 = new Button("Update");   
              button1.addClickListener(e-> {                            
                   createUpdateWindow(Integer.parseInt(itemId.toString()));       
                   showWindow(updateWindow);
              });              
              return button1;
            }
        });
    }
    
    public void createAddWindow(){
        addWindow=new Window("Add User");
        addWindow.center();
        addWindow.setModal(true);
        PersonalInfoLayout addLayout=new PersonalInfoLayout();        
        Button addButton=new Button("Add");
        
        addButton.addClickListener(e->{
             if(addLayout.isValidFields()){
                userPresenter.create(addLayout.getFieldValues());
                addLayout.clearFieldValues();
                updateTable();
             }
        });
        
        addLayout.addComponent(addButton);
        addWindow.setContent(addLayout);    
    }
    
    public void createUpdateWindow(int itemId){
        updateWindow=new Window("Update User");
        updateWindow.center();
        updateWindow.setModal(true);
        PersonalInfoLayout updateLayout=new PersonalInfoLayout();
        updateLayout.setAllFields(userPresenter.getUser(itemId));
        Button updateButton=new Button("Update");
        
        updateButton.addClickListener(e->{
            if(updateLayout.isValidFields()){
                userPresenter.update(updateLayout.getFieldValues());
                updateTable();
                updateWindow.close();
            }
        });
        
        updateLayout.addComponent(updateButton);       
        updateWindow.setContent(updateLayout);
    }
    
    public void updateTable(){
        userTable.removeAllItems();
        for(int i=0;i<userPresenter.getUsers().size();i++){
            userTable.addItem(new Object[]{userPresenter.getUsers().get(i).getLogin(),userPresenter.getUsers().get(i).getPassword(),userPresenter.getUsers().get(i).getEmail(),userPresenter.getUsers().get(i).getRole().getRole_name()},userPresenter.getUsers().get(i).getUserId());
        }
    }
    
    public void populateTable(){
        for(int i=0;i<userPresenter.getUsers().size();i++){
            userTable.addItem(new Object[]{userPresenter.getUsers().get(i).getLogin(),userPresenter.getUsers().get(i).getPassword(),userPresenter.getUsers().get(i).getEmail(),userPresenter.getUsers().get(i).getRole().getRole_name()},userPresenter.getUsers().get(i).getUserId());
        }        
    }
  
    public void showWindow(Window window){
        getUI().addWindow(window);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    
    }
   
}