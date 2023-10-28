package com.example.springoauth2.mvclogin.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;

import com.example.springoauth2.mvclogin.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		 final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
	        if (StringUtils.isEmpty(username)) {
	            throw new BadCredentialsException("invalid login details");
	        }
		try {
			return userService.getUser(authentication);
		} catch(OAuth2AccessDeniedException e) {
			System.err.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
			
			if(e.getCause() instanceof InvalidGrantException) {
				throw new BadCredentialsException("invalid login details");
			}else if(e.getCause() instanceof ResourceAccessException) {
				throw new AuthenticationServiceException("Login service not reachable");
			}else {
				throw e;
			}
			
		}	catch(Exception e) {
			if(e instanceof ResourceAccessException) {
				System.err.println(e.getMessage());
				System.out.println(e.getCause());
				e.printStackTrace();
				throw new AuthenticationServiceException("Login service not reachable");
			} else {
				throw e;
			}
			
		}	 	
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
