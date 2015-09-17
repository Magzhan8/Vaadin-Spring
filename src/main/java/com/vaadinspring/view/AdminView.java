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
import com.vaadinspring.presenter.UserPresenter;
import com.vaadinspring.presenter.UserPresenterImpl;

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
    public AdminView(){
        userPresenter=new UserPresenterImpl();
        customView=new CustomView();
        mainLayout=customView.getTemplate();
        contentLayout=new VerticalLayout();
        contentLayout.setHeightUndefined();
        contentLayout.setSpacing(true);
        contentLayout.setMargin(true);
        
        userTable=createTable();
        userTable.setSizeFull();
        userTable=populateTable(userTable);
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
    
    public Table createTable(){
        Table table = new Table();
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Password",String.class, null);
        table.addContainerProperty("Email",String.class, null);
        table.addContainerProperty("Role",String.class, null);
        table.addGeneratedColumn("delete", new Table.ColumnGenerator() {
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
        
        table.addGeneratedColumn("update", new Table.ColumnGenerator() {
              @Override public Object generateCell(final Table source, final Object itemId, Object columnId) {
              Button button1 = new Button("Update");   
              button1.addClickListener(e-> {                            
                   createUpdateWindow(Integer.parseInt(itemId.toString()));       
                   showWindow(updateWindow);
              });              
              return button1;
            }
        });
        
        table.setWidthUndefined();
        return table;
    }
    
    public void createAddWindow(){
        addWindow=new Window("Add User");
        addWindow.center();
        addWindow.setModal(true);
        VerticalLayout layout = new VerticalLayout();        
        layout.setSpacing(true);
        layout.setMargin(true);
        final TextField t1=new TextField("Login");
        final TextField t2=new TextField("Password");
        final TextField t3=new TextField("Email");
        final ComboBox t4=new ComboBox("Select role");
        t4.addItem("user");
        t4.addItem("admin");
        
        t1.addFocusListener(e->{
            t1.setImmediate(true);
            t1.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "login"));
        });
        t2.addFocusListener(e->{
            t2.setImmediate(true);
            t2.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "password"));
        });
        t3.addFocusListener(e->{
            t3.setImmediate(true);
            t3.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "email"));
        });
        t4.addFocusListener(e->{
            t4.setImmediate(true);
            t4.addValidator(new BeanValidator(com.vaadinspring.model.Role.class, "role_name"));
        });        

        layout.addComponent(t1);
        layout.addComponent(t2);
        layout.addComponent(t3);
        layout.addComponent(t4);
        
        
        Button addBtn=new Button("Add");
        
        addBtn.addClickListener(e->{ 
                  if(t1.isValid() && t2.isValid() && t3.isValid() && t4.isValid()){
                  userPresenter.create(t1.getValue(), t2.getValue(),
                          t3.getValue(), t4.getValue().toString());
                  t1.setValue("");
                  t2.setValue("");
                  t3.setValue("");
                  t3.setValue("");
                  userTable=populateTable(userTable);
                  }                  
        }); 
        
        layout.addComponents(addBtn);
        addWindow.setContent(layout);
    }
    
    public void createUpdateWindow(int itemId){
        updateWindow=new Window("Update User");
        updateWindow.center();
        updateWindow.setModal(true);
        VerticalLayout layout = new VerticalLayout();
        updateWindow.setContent(layout);
        final TextField t1=new TextField("Login");
        final TextField t2=new TextField("Password");
        final TextField t3=new TextField("Email");
        final TextField t4=new TextField("Role");
        Button updBtn=new Button("Update");
            t1.setValue(userPresenter.getUser(itemId).getLogin());
            t2.setValue(userPresenter.getUser(itemId).getPassword());
            t3.setValue(userPresenter.getUser(itemId).getEmail());
            t4.setValue(userPresenter.getUser(itemId).getRole().getRole_name());         
            
            t1.addFocusListener(e->{
                t1.setImmediate(true);
                t1.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "login"));
            });
            t2.addFocusListener(e->{
                t2.setImmediate(true);
                t2.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "password"));
            });
            t3.addFocusListener(e->{
                t3.setImmediate(true);
                t3.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "email"));
            });
            t4.addFocusListener(e->{
                t4.setImmediate(true);
                t4.addValidator(new BeanValidator(com.vaadinspring.model.Role.class, "role_name"));
            });
            
            layout.setSizeUndefined();
            layout.setSpacing(true);
            layout.setMargin(true);
            
            layout.addComponent(t1);
            layout.addComponent(t2);
            layout.addComponent(t3);
            layout.addComponent(t4);
            layout.addComponent(updBtn);  
            
            updBtn.addClickListener(e-> { 
                  if(t1.isValid() && t2.isValid() && t3.isValid() && t4.isValid()){
                  userPresenter.update(t1.getValue(),t2.getValue(), 
                          t3.getValue(),t4.getValue());
                  userTable.removeAllItems();
                  userTable=populateTable(userTable);                  
                  updateWindow.close();
                  userTable.refreshRowCache();
                  }
              });             
    }
    
    
    public void populateTable(){
        userTable=createTable();
        for(int i=0;i<userPresenter.getUsers().size();i++){
            userTable.addItem(new Object[]{userPresenter.getUsers().get(i).getLogin(),userPresenter.getUsers().get(i).getPassword(),userPresenter.getUsers().get(i).getEmail(),userPresenter.getUsers().get(i).getRole().getRole_name()},userPresenter.getUsers().get(i).getUserId());
        }        
    }
    
    public Table populateTable(Table table){
        for(int i=0;i<userPresenter.getUsers().size();i++){
            table.addItem(new Object[]{userPresenter.getUsers().get(i).getLogin(),userPresenter.getUsers().get(i).getPassword(),userPresenter.getUsers().get(i).getEmail(),userPresenter.getUsers().get(i).getRole().getRole_name()},userPresenter.getUsers().get(i).getUserId());
        }
        return table;
    }
    
    public void showWindow(Window window){
        getUI().addWindow(window);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    
    }
   
}