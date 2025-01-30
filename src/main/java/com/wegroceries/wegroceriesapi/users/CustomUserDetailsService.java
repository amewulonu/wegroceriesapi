package com.wegroceries.wegroceriesapi.users;

// import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CustomUserDetailsService /* implements UserDetailsService */ {

    @Autowired
    private UserRepository userRepository;

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     User user = userRepository.findByUsername(username)
    //         .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    //     return user; // Ensure your User entity implements UserDetails
    // }
}
