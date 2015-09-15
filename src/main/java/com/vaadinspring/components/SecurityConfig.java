/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.components;

/**
 *
 * @author m.zhaksygeldy
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        CustomAuthenticationProvider cust;
    
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
          auth.authenticationProvider(cust);
//	  auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
//	  auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
	}
       
	@Override
	protected void configure(HttpSecurity http) throws Exception {

	   http.authorizeRequests()
                .antMatchers("/maga").anonymous()
		.antMatchers("/#!main").access("hasRole('user')")
                .antMatchers("/#!admin").access("hasRole('admin')")
		.and()
		    .formLogin().loginPage("/").loginProcessingUrl("/login").defaultSuccessUrl("/#!main").failureUrl("/error")
		    .usernameParameter("username").passwordParameter("password")		
		.and()
		    .logout().logoutSuccessUrl("/logout")
                .and()
                   .exceptionHandling()
                   .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/main"))
		.and()
		    .csrf().disable(); 
		
	}
}
