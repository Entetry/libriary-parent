package com.antonklintsevich.configure;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.antonklintcevich.restclient.userclient.RestUserClient;
import com.antonklintsevich.security.CustomAccessDeniedHandler;
import com.antonklintsevich.security.MySavedRequestAwareAuthenticationSuccessHandler;
import com.antonklintsevich.security.RestAuthenticationEntryPoint;
import com.antonklintsevich.services.UserServiceIml;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan({ "com.antonklintsevich", "com.antonklintcevich.restclient" })
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler;
    @Autowired
    private RestUserClient restUserClient;
  

    public SecurityJavaConfig() {
        super();
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(restUserClient).passwordEncoder(encoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
//            .and()
//            .exceptionHandling()
//            .accessDeniedHandler(accessDeniedHandler)
//            .authenticationEntryPoint(restAuthenticationEntryPoint)
//            .and()
//            .authorizeRequests(
                
               .antMatchers("/login").permitAll()
                .antMatchers("/books/**").permitAll()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/orders").permitAll()
                .antMatchers("/users/userdetails").permitAll()
                .antMatchers("/**").authenticated()
              .and().formLogin().successHandler(mySuccessHandler)
              
              
              
//                 .antMatchers("/books/**").hasRole("ADMIN")

//            .failureHandler(myFailureHandler)
//            .and()
//            .httpBasic()
                .and().logout();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(restUserClient);
        auth.setPasswordEncoder(encoder());
        return auth;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public EntityManagerFactory getEntityManagerFactory() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("com.antonklintsevich.entity_catalog");
        return entityManagerFactory;
    }
}