package com.wegroceries.wegroceriesapi.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user details from the database or another source
        // Example:
        if ("testuser".equals(username)) {
            return new UserDetailsImpl(
                    "testuser",
                    "password",
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}