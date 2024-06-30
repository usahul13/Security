package com.sahul.jwt.Secutity.jwt;

import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sahul.jwt.Secutity.services.UserDetailsimpl;
import java.util.Date;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class jwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(jwtUtils.class);

	@Value("${sahul.app.jwtSecret}")
	private String jwtSecret;
	@Value("${sahul.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateToken(Authentication authentication) {
		UserDetailsimpl userPrinciple = (UserDetailsimpl) authentication.getPrincipal();
		return Jwts.builder().setSubject(userPrinciple.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}

	public Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public boolean validateJsonTokens(String AuthToken) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(AuthToken);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("invalid token" + e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("unsupported token" + e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("Expired token" + e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("jwt  token is empty" + e.getMessage());
		}
		return false;
	}
}
