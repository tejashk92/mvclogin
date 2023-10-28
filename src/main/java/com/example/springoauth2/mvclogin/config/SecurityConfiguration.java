package com.example.springoauth2.mvclogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration{
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	 @Bean
	 AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
			return http.getSharedObject(AuthenticationManagerBuilder.class)
					.authenticationProvider(customAuthenticationProvider)
					.build();
	 }
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests(requests -> requests
                .anyRequest().authenticated())
				.formLogin(login -> login.successHandler(loginSuccessHandler));
		return http.build();
	 }
	
	@Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/static/**", "/css/**","/js");
    }

}
