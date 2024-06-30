package com.sahul.jwt.Secutity.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sahul.jwt.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsimpl implements UserDetails {

	/**
	 * 
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = -6093702001821683513L;
	private Long id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authories;

	public UserDetailsimpl(long id, String username, String password, List<GrantedAuthority> authorities) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.username=username;
		this.password=password;
		this.authories=authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authories;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	public static UserDetailsimpl buildI(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		return new UserDetailsimpl(user.getId(),user.getUsername(),user.getPassword(),authorities);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsimpl user = (UserDetailsimpl) o;
		return Objects.equals(id, user.id);
	}

}
