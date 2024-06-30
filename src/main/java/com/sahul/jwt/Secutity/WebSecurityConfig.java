package com.sahul.jwt.Secutity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sahul.jwt.Secutity.jwt.AuthEntryPointJwt;
import com.sahul.jwt.Secutity.jwt.AuthTokenFilter;
import com.sahul.jwt.Secutity.services.UserDetailsServiceimpl;


@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailsServiceimpl detailsServiceimpl;

	@Autowired
	private AuthEntryPointJwt unathorizedHandler;

	@Bean
	public AuthTokenFilter authenticationjwtfiltertoken() {
		return new AuthTokenFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationprovider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(detailsServiceimpl);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authconfig) throws Exception {
		return authconfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).exceptionHandling(excep -> excep.authenticationEntryPoint(unathorizedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("api/test/**").permitAll()
						.requestMatchers("api/auth/**").permitAll().anyRequest().authenticated());
		http.authenticationProvider(authenticationprovider());
		http.addFilterBefore(authenticationjwtfiltertoken(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
