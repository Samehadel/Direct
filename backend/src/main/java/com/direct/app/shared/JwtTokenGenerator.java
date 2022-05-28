package com.direct.app.shared;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

import static com.direct.app.security.SecurityConstants.EXPIRATION_TIME;
import static com.direct.app.security.SecurityConstants.TOKEN_SECRET;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
public class JwtTokenGenerator {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserAuthenticationHelper authenticationHelper;

	public String generateJwtByUsernameAndPassword(String username, String password) {
		authenticationHelper.setAuthentication(username, password);

		return generateToken(username);
	}

	private String generateToken(String subject) {
		return Jwts.builder()
					.setSubject(subject)
					.setExpiration(getTokenExpirationDate())
					.signWith(HS512, TOKEN_SECRET)
					.compact();
	}

	private Date getTokenExpirationDate(){
		return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
	}

	public String generateJwtByUsername(String username) {
		return generateToken(username);
	}
}
