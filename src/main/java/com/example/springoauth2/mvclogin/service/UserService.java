package com.example.springoauth2.mvclogin.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Service;

import com.example.springoauth2.mvclogin.pojo.CustomUser;
import com.example.springoauth2.mvclogin.pojo.UserResponse;

@Service
@PropertySource("classpath:mvclogin.properties")
public class UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${myapp.auth.client.id}")
	private String clientId;
	
	@Value("${myapp.auth.client.secret}")
	private String clientSecret;
	
	@Value("${myapp.auth.grant.type}")
	private String grantType;
	
	@Value("${myapp.auth.token.url}")
	private String tokenUrl;
	
	public Authentication getUser(Authentication authentication) {
		String encodedPassword=passwordEncoder.encode(authentication.getCredentials().toString());
		OAuth2RestTemplate oAuth2RestTemplate=oAuth2RestTemplate(authentication);
		UserResponse response = oAuth2RestTemplate.getForObject("http://localhost:8080/me", UserResponse.class);
		CustomUser principal= new CustomUser(authentication.getName(), encodedPassword, response.getGrantedAuthorities(),response.getToken());
		Authentication auth=new UsernamePasswordAuthenticationToken(principal ,encodedPassword, response.getGrantedAuthorities());
		return  auth;
	}
	
	private OAuth2RestTemplate oAuth2RestTemplate(Authentication auth) {
		System.out.println("Building Oauth2 Restemplate for" +auth. getName ()) ; 
		ResourceOwnerPasswordResourceDetails resource=new ResourceOwnerPasswordResourceDetails();
		resource. setAccessTokenUri (tokenUrl);
		resource. setClientId(clientId); 
		resource. setClientSecret (clientSecret);
		resource. setGrantType (grantType);
		resource. setUsername (auth. getName () ) ;
		resource. setPassword(auth.getCredentials().toString());
		AccessTokenRequest atr = new DefaultAccessTokenRequest (); 
		return new OAuth2RestTemplate (resource, new DefaultOAuth2ClientContext(atr));
	}

}
