/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author Misho
 */

@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
   
   // @Autowired
   // DataSource dataSource;
   
   // @Autowired
   // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                     "select user_username,user_password from users where user_username=?")
                 .authoritiesByUsernameQuery(
                     "select username, user_role from user_roles where username=?");*/
        /*auth
        .jdbcAuthentication()
        .dataSource()
        .usersByUsernameQuery(getUserQuery())
        .authoritiesByUsernameQuery(getAuthoritiesQuery());*/
    //        auth.inMemoryAuthentication().withUser("admin").password("123456").roles("USERasfasf");
//	}
    
    //@Override
  //  protected void configure(HttpSecurity http) throws Exception {
    //  http.authorizeRequests()
      //  .antMatchers("/*").permitAll();
        /*.access("hasRole('USER')")
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll();*/
  //  }
}
