/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 *
 * @author Misho
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/PersonalFinanceManager");
        driverManagerDataSource.setUsername("postgres");
        driverManagerDataSource.setPassword("masterkey");
        return driverManagerDataSource;
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource())
                .usersByUsernameQuery(
                        "select user_username,user_password,user_enabled from users where user_username=?")
                .authoritiesByUsernameQuery(
                        "select username, user_role from user_roles where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/*").access("hasRole('ROLE_USER')")
                .and()
                .formLogin()
                .successHandler(savedRequestAwareAuthenticationSuccessHandler())
                .loginPage("/login")
                .failureUrl("/login?error")
                .loginProcessingUrl("/auth/login_check")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()
                .csrf()
                .and()
                .rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(1209600);
        
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource());
        return db;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler
            savedRequestAwareAuthenticationSuccessHandler() {

        SavedRequestAwareAuthenticationSuccessHandler auth
                = new SavedRequestAwareAuthenticationSuccessHandler();
        auth.setTargetUrlParameter("targetUrl");
        return auth;
    }
}
