package com.example.springoauth2.mvclogin.pojo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class UserResponse {
	private  Set<Authority> authorities;
	
	private TokenDetails details;

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
	
	public Set<GrantedAuthority> getGrantedAuthorities(){
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for(Authority auth:this.authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority(auth.getAuthority()));
		}
		return grantedAuthorities;
	}

	public TokenDetails getDetails() {
		return details;
	}

	public void setDetails(TokenDetails details) {
		this.details = details;
	}
	
	public String getToken() {
		return this.details.getTokenValue();
	}
}

class Authority{
	
	private String authority;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}

class TokenDetails {
	
	private String tokenValue;
	private String tokenType;
	
	public String getTokenValue() {
		return tokenValue;
	}
	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

}