package com.gvstave.mistergift.api.configuration;

import com.gvstave.mistergift.api.auth.filter.AuthenticationFilter;
import com.gvstave.mistergift.api.auth.filter.AuthorizationFilter;
import com.gvstave.mistergift.api.auth.filter.UnauthorizedEntryPoint;
import com.gvstave.mistergift.api.auth.handler.AuthenticationHandler;
import com.gvstave.mistergift.api.filter.UserAccessFilter;
import com.gvstave.mistergift.data.configuration.DataWebConfiguration;
import com.gvstave.mistergift.data.service.query.UserProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.inject.Inject;

/**
 * .
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan(basePackageClasses = DataWebConfiguration.class)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationProvider(new AuthenticationHandler())
            .userDetailsService(userProvider())
            .passwordEncoder(bcryptEncoder());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**")
            .and().ignoring().antMatchers(HttpMethod.POST, "/users")
            .and().ignoring().antMatchers(HttpMethod.POST, "/landing");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
            .csrf().disable();

        http
            .antMatcher("/authenticate")
            .addFilterBefore(authenticationFilter(), BasicAuthenticationFilter.class);

        http
            .antMatcher("/**")
            .addFilterBefore(userAccessFilter(), ChannelProcessingFilter.class)
            .addFilterAfter(authorizationFilter(), BasicAuthenticationFilter.class);

    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter("/authenticate");
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public UserProvider userProvider() {
        return new UserProvider();
    }

    @Bean
    public UserAccessFilter userAccessFilter() {
        return new UserAccessFilter();
    }

    @Bean
    public UnauthorizedEntryPoint unauthorizedEntryPoint() {
        return new UnauthorizedEntryPoint();
    }

    @Bean
    public AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(unauthorizedEntryPoint());
    }

}
