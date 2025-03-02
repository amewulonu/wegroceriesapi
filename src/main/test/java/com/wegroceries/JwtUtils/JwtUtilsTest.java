import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

public class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtils = new JwtUtils();
        jwtUtils.setJwtSecret("mySuperSecretKey1234567890abcdef1234567890abcdef");
        jwtUtils.setJwtExpirationMs(86400000);
    }

    @Test
    public void testGenerateJwtToken() {
        // Mock UserDetailsImpl object
        UserDetails userDetails = new UserDetailsImpl(
                "testuser",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        // Mock authentication
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Generate JWT token
        String token = jwtUtils.generateJwtToken(authentication);
        assertNotNull(token);
    }

    @Test
    public void testValidateJwtToken() {
        // Mock UserDetailsImpl object
        UserDetails userDetails = new UserDetailsImpl(
                "testuser",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        // Mock authentication
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Generate JWT token
        String token = jwtUtils.generateJwtToken(authentication);

        // Validate JWT token
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    public void testGetUserNameFromJwtToken() {
        // Mock UserDetailsImpl object
        UserDetails userDetails = new UserDetailsImpl(
                "testuser",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        // Mock authentication
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Generate JWT token
        String token = jwtUtils.generateJwtToken(authentication);

        // Extract username from JWT token
        String username = jwtUtils.getUserNameFromJwtToken(token);
        assertEquals("testuser", username);
    }
}