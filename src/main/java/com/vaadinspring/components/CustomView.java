 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.components;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadinspring.presenter.UserPresenter;
import com.vaadinspring.presenter.UserPresenterImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.zhaksygeldy
 */
public class CustomView extends GridLayout{
    private UserPresenter userPresenter;
    private PersonalInfoLayout editLayout;
    private GridLayout mainLayout;
    private HorizontalLayout headerLayout;
    private GridLayout sidebarLayout;
    private Button editButton;
    private Button logoutButton;
    private File newImageFile;
    private Image mainImage;
    private Image newImage;
    private String newImageName;
    private Upload imageUploader; 
    private Window editWindow;        
    private String path;
    
    public CustomView(){
        userPresenter=new UserPresenterImpl();
        path=returnPath();
        this.setSizeFull();
        this.setColumns(2);
        this.setRows(2);
        this.setColumnExpandRatio(0, 1);
        this.setColumnExpandRatio(1, 3);
        this.setRowExpandRatio(0, 1);
        this.setRowExpandRatio(1, 4);
        
        this.createHeaderLayout();
        this.createSidebarLayout();
        
        this.addComponent(headerLayout,0,0,1,0);
        this.addComponent(sidebarLayout,0,1);
    }
    
    public void createHeaderLayout(){
        headerLayout=new HorizontalLayout();
        headerLayout.setSizeFull();
        Label welcome=new Label("You are welcome");
        welcome.addStyleName("welcome");
        headerLayout.addComponent(welcome);
        headerLayout.setComponentAlignment(welcome, Alignment.MIDDLE_CENTER);       
    }    
    
    public void createSidebarLayout(){
        sidebarLayout= new GridLayout(7,10);
        sidebarLayout.setSizeFull();
        sidebarLayout.setSpacing(true);
        sidebarLayout.setMargin(true);
        sidebarLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        sidebarLayout.setColumnExpandRatio(1, 2);
        sidebarLayout.setColumnExpandRatio(3, 2);     
        
        Label login=new Label(userPresenter.getCurrent().getLogin());
        FileResource file=new FileResource(new File(path+userPresenter.getCurrent().getImage()));
        mainImage=new Image(null,file); 
        mainImage.setWidth("180px"); 
        mainImage.setHeight("240px");  
        this.createEditWindow();
        this.createEditButton();
        this.createLogoutButton();
        
        sidebarLayout.addComponent(mainImage,1,1,3,1);
        GridLayout inner=new GridLayout(6,2);        
        inner.setSizeFull();        
        inner.addComponent(login,1,0); 
        inner.addComponent(editButton,0,1);
        inner.addComponent(logoutButton,3,1);
        sidebarLayout.addComponent(inner,1,2,3,2);
    }
    
    public void createEditButton(){
        editButton=new Button("Edit");
        editButton.addClickListener(event->{
            showWindow(editWindow);
        });
    }
    
    public void createLogoutButton(){
        logoutButton=new Button("Logout");
        logoutButton.addClickListener(event->{
            userPresenter.logout();
            Page.getCurrent().setLocation("");
            UI.getCurrent().getSession().close(); 
        });
    }
    
    public void createEditWindow(){
        editWindow=new Window();
        editWindow.center();
        editWindow.setHeightUndefined();
        editWindow.setModal(true);
        editLayout=new PersonalInfoLayout();
        this.createNewImage();
        this.createImageUploader();
        editLayout.setAllFields(userPresenter.getCurrent());
        editLayout.username.setReadOnly(true);
        editLayout.role.setReadOnly(true);        
        Button saveButton=new Button("Save"); 
        
        saveButton.addClickListener(event->{
            if(editLayout.isValidFields()){
                List<String> editInfoList=editLayout.getFieldValues();
                editInfoList.add(newImageName);
                userPresenter.edit(editInfoList);
                mainImage.setSource(new FileResource(newImageFile));
                editWindow.close();
            }
        });
        
        editLayout.addComponentAsFirst(imageUploader);
        editLayout.addComponentAsFirst(newImage); 
        editLayout.addComponent(saveButton);
        editWindow.setContent(editLayout);
    }
    
    public void createNewImage(){        
        newImageFile=new File(path+userPresenter.getCurrent().getImage());
        newImage=new Image(null,new FileResource(newImageFile));
        newImage.setSource(new FileResource(newImageFile));
        newImage.setWidth("180px"); 
        newImage.setHeight("240px");
    }       
    
    public void createImageUploader(){
        imageUploader=new Upload("",new Upload.Receiver() {
            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {
                newImageName=filename;
                FileOutputStream fos = null;
                try{
                    newImageFile=new File(path+newImageName);
                    fos=new FileOutputStream(newImageFile);
                }
                catch (FileNotFoundException ex) {
                    Notification.show(ex.getMessage());
                } catch (IOException ex) {
                    Logger.getLogger(CustomView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return fos;               
            }
        });
        
        imageUploader.addSucceededListener(e->{
            newImage.setSource(new FileResource(newImageFile));
        });
    }
    
    public void showWindow(Window window){
        UI.getCurrent().addWindow(window);
    }
    
    public String returnPath(){
        String path=this.getClass().getClassLoader().getResource("").toString();
        path=path.substring(6,path.length()-49)+"/images/";
        return path;
    }
    
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    
    }
}
