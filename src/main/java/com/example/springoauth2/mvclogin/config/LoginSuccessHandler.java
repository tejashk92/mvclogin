package com.example.springoauth2.mvclogin.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.springoauth2.mvclogin.pojo.CustomUser;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	
	private  RedirectStrategy redirectStrategy= new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		CustomUser principal=(CustomUser) authentication.getPrincipal();
		System.out.println("Token is "+principal.getToken());
		redirectStrategy.sendRedirect(request, response, "/home");
		
	}

}
