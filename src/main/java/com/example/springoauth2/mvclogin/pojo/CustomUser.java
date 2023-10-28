package com.example.springoauth2.mvclogin.pojo;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUser implements UserDetails{
	
	private static final long serialVersionUID = 1344109022220186063L;
	private String userName;
	private String password;
	private Set<GrantedAuthority> authorities;
	private String token;
	

	public CustomUser(String userName, String password, Set<GrantedAuthority> authorities , String token) {
		super();
		this.userName = userName;
		this.password = password;
		this.authorities = authorities;
		this.token = token;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getToken() {
		return token;
	}

}
