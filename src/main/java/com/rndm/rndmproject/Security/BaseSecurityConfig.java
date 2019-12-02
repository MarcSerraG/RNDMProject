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
                .antMatchers("/Page/**").permitAll()
                .antMatchers("/register").anonymous()
                .antMatchers("/profile").permitAll()
                .antMatchers("/login").anonymous()
                .antMatchers("loginCorrect").permitAll()
                .antMatchers("/login_error").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/Category/{category}").permitAll()
                .antMatchers("/Search/{title}").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/font-awesome-4.0.3/**").permitAll()
                .antMatchers("/linearicons-free/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/rs-plugin/**").permitAll()
                .antMatchers("/fragment/**").permitAll()
                .antMatchers("/Static.html").permitAll()
                .antMatchers("/Thread/{id}").permitAll()
                .antMatchers("/Modidythread").permitAll()
                .antMatchers("/ModifyThread/{id}").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin() //to use forms (web)
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login_error")
                .defaultSuccessUrl("/success", true)
                .and()
                .rememberMe()
                .tokenValiditySeconds(2419200)
                .key("tecnocampus")
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .permitAll();
                //.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //needed only when csrf is enabled (as by default is post)
                //.logoutSuccessUrl("/"); //where to go when logout is successful
        //.logoutUrl("logoutpage"); // default is "/logout""

        http
                .csrf().disable()
                .headers().httpStrictTransportSecurity().disable()
                .frameOptions().disable();
    }
}
