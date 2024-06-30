package com.sahul.jwt.Secutity.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sahul.jwt.Secutity.services.UserDetailsServiceimpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private jwtUtils jwtUtils;
	@Autowired
	UserDetailsServiceimpl detailsServiceimpl;

	private String parseJwt(HttpServletRequest request) {
		String AuthHeader = request.getHeader("Authorization");
		if (StringUtils.hasLength(AuthHeader) && AuthHeader.startsWith("Bearer ")) {
			return AuthHeader.substring(7);
		}
		return null;

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJsonTokens(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				UserDetails userDetails = detailsServiceimpl.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
		} catch (Exception e) {
			logger.error(e);
		}
          filterChain.doFilter(request, response);
	}

}
