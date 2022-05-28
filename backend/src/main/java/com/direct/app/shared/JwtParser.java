package com.direct.app.shared;

import com.direct.app.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import static com.direct.app.security.SecurityConstants.TOKEN_SECRET;

@Component
public class JwtParser {

	public String parseForUsername(String jwt){
		String username = Jwts.parser()
				.setSigningKey(TOKEN_SECRET)
				.parseClaimsJws(jwt)
				.getBody()
				.getSubject();

		return username;
	}
}
