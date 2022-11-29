package com.ngahp.authenticationservice.model;

import java.util.Collection;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class User implements UserDetails, CredentialsContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2278230586840366051L;

	private Long userId;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private boolean blocked;

	@Override
	public void eraseCredentials() {
		this.password = null;

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !blocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
