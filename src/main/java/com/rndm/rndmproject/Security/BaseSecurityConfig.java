package com.rndm.rndmproject.Security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class BaseSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/NewThread").authenticated()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/{page}").permitAll()
                .antMatchers("/register").anonymous()
                .antMatchers("/login").anonymous()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/Category/{category}").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/linearicons-free/**").permitAll()
                .antMatchers("/font-awesome-4.0.3/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/rs-plugin/**").permitAll()
                .antMatchers("/fragment/**").permitAll()
                .and()
                .formLogin() //to use forms (web)
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .and()
                .httpBasic()
                .and()
                .rememberMe()
                .tokenValiditySeconds(2419200)
                .key("tecnocampus")
                .and()
                .logout()
                .logoutSuccessUrl("/"); //where to go when logout is successful

        http
                .csrf().disable()
                .headers()
                .frameOptions().disable();
    }
}
