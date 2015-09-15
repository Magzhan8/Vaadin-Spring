/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.ui;

/**
 *
 * @author m.zhaksygeldy
 */
import javax.servlet.ServletContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringContextHelper {

    private ApplicationContext context;
    public SpringContextHelper(ServletContext servletContext) {
      
        
        context = WebApplicationContextUtils.
                getRequiredWebApplicationContext(servletContext);
       // System.out.println(context.getBeanDefinitionNames());
    }

    public Object getBean(final String beanRef) {
        return context.getBean(beanRef);
    }    
    
    public String[] getBeans() {
        return context.getBeanDefinitionNames();
    }    
}