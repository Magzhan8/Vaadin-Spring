 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.components;

import com.vaadin.data.validator.BeanValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadinspring.model.Users;
import com.vaadinspring.presenter.UserPresenter;
import com.vaadinspring.presenter.UserPresenterImpl;
import com.vaadinspring.ui.MyUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author m.zhaksygeldy
 */
public class CustomView extends Panel implements View{
    private UserPresenter userPresenter;
    public String imageName;
    public Users currentUser;
    public File file1;
    public Image mainImage;
    public Window editWindow;        
    public String path;
    public GridLayout getTemplate()
    { 
        path=returnPath();
        userPresenter=new UserPresenterImpl();
        GridLayout grid=createMainLayout();
        HorizontalLayout header=getHeaderLayout();    
        GridLayout v2=getSidebarLayout(); 
        editWindow=createEditWindow();
        
        grid.setSizeFull();
        v2.setSizeFull();
        header.setSizeFull();
        grid.addComponent(header,0,0,1,0);
        grid.addComponent(v2,0,1);
        return grid;
    }
    
    public GridLayout createMainLayout(){
        GridLayout grid=new GridLayout(2,2);        
        
        grid.setColumnExpandRatio(0, 1);
        grid.setColumnExpandRatio(1, 3);
        grid.setRowExpandRatio(0, 1);
        grid.setRowExpandRatio(1, 4);
        
        return grid;
    }
    
    public HorizontalLayout getHeaderLayout(){
        HorizontalLayout header=new HorizontalLayout();
        Label welcome=new Label("You are welcome");
        welcome.addStyleName("welcome");
        header.addComponent(welcome);
        header.setComponentAlignment(welcome, Alignment.MIDDLE_CENTER);        
        return header;        
    }    
    
    public GridLayout getSidebarLayout(){
        GridLayout sideBar= new GridLayout(7,10);
        sideBar.setColumnExpandRatio(1, 2);
        sideBar.setColumnExpandRatio(3, 2);
        
        sideBar.setSizeFull();
        sideBar.setSpacing(true);
        sideBar.setMargin(true);
        Label login=new Label(userPresenter.getCurrent().getLogin());
        FileResource file=new FileResource(new File(path+userPresenter.getCurrent().getImage()));
        mainImage=new Image(null,file);        
        
        Button edit=new Button("Edit");
        Button logout=new Button("Logout");
        
        
        mainImage.setWidth("180px"); 
        mainImage.setHeight("240px");                
        
        edit.addClickListener(e-> {
                showWindow(editWindow);
        });        
        logout.addClickListener(e-> {
                userPresenter.logout();
                Page.getCurrent().setLocation("");
                UI.getCurrent().getSession().close();  
        });
        sideBar.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        sideBar.addComponent(mainImage,1,1,3,1);
        GridLayout inner=new GridLayout(6,2);        
        inner.setSizeFull();        
        inner.addComponent(login,1,0); 
        inner.addComponent(edit,0,1);
        inner.addComponent(logout,3,1);
        sideBar.addComponent(inner,1,2,3,2);
        //layout.setComponentAlignment(mainImage, Alignment.TOP_CENTER);
        //layout.setComponentAlignment(login, Alignment.TOP_CENTER);
        //layout.setComponentAlignment(grid, Alignment.TOP_CENTER);
        return sideBar;        
    }
    
    public Window createEditWindow()
    {   
        final String login=userPresenter.getCurrent().getLogin();
        String password=userPresenter.getCurrent().getPassword();
        String email=userPresenter.getCurrent().getEmail();
        final String role=userPresenter.getCurrent().getRole().getRole_name();        
        final Window userWindow=new Window();
        userWindow.setModal(true);
        FileResource file=new FileResource(new File(path+userPresenter.getCurrent().getImage()));
        final Image image=new Image(null,file);
        image.setWidth("180px"); 
        image.setHeight("240px");
        Upload upload = new Upload("", new Upload.Receiver() {
            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {
                imageName=filename;
                FileOutputStream fos=null;
                try {  
                    file1=new File(path+filename);
                    fos=new FileOutputStream(file1);
                } catch (FileNotFoundException ex) {
                    Notification.show(ex.getMessage());
                } catch (IOException ex) {
                    Logger.getLogger(CustomView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return fos;             
            }
        });
        
        upload.addSucceededListener(new Upload.SucceededListener() {
            @Override
            public void uploadSucceeded(Upload.SucceededEvent event) {
                    image.setSource(new FileResource(file1));
                
            }
        });
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        TextField t1=new TextField("Login");
        final TextField t2=new TextField("Password");
        final TextField t3=new TextField("Email");
        TextField t4=new TextField("Role");
        Button save=new Button("Save");
        t1.setValue(login);
        t2.setValue(password);
        t3.setValue(email);        
        t4.setValue(role);
        t1.setReadOnly(true);
        t4.setReadOnly(true);
        t2.addFocusListener(e->{
            t2.setImmediate(true);
            t2.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "password"));
        });
        t3.addFocusListener(e->{
            t3.setImmediate(true);
            t3.addValidator(new BeanValidator(com.vaadinspring.model.Users.class, "email"));
        });
        save.addClickListener(e-> {
            if(t2.isValid() && t3.isValid()){
                userPresenter.edit(login, t2.getValue(), t3.getValue(), imageName);
                mainImage.setSource(new FileResource(file1));
                userWindow.close();
            }
        });
        layout.addComponent(image);
        layout.addComponent(upload);
        layout.addComponents(t1,t2,t3,t4); 
        layout.addComponent(save);
        userWindow.setContent(layout);            
        return userWindow;
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
