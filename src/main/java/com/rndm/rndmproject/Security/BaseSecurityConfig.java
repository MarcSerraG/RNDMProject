package com.rndm.rndmproject.Security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class BaseSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/{page}").permitAll()
                .antMatchers("/register").anonymous()
                .antMatchers("/login").anonymous()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/Category/{category}").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("font-awesome-4.0.3/**").permitAll()
                .antMatchers("fonts/**").permitAll()
                .antMatchers("img/**").permitAll()
                .antMatchers("js/**").permitAll()
                .antMatchers("rs-plugin/**").permitAll()
                .antMatchers("register.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin() //to use forms (web)
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .and()
                .rememberMe()
                .tokenValiditySeconds(2419200)
                .key("tecnocampus")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //needed only when csrf is enabled (as by default is post)
                .logoutSuccessUrl("/"); //where to go when logout is successful
        //.logoutUrl("logoutpage"); // default is "/logout""

        http
                .csrf().disable()
                .headers()
                .frameOptions().disable();
    }
}
