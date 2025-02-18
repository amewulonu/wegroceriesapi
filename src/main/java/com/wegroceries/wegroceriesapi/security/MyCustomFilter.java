package com.wegroceries.wegroceriesapi.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyCustomFilter implements Filter {

    // The filter is applied to incoming HTTP requests and responses.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Custom filtering logic can be added here
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        System.out.println("Filtering request: " + requestURI);

        // Example: Allow requests to proceed only if they are authorized
        // You can add authentication or logging logic here.
        
        // Check for authentication or JWT token, for example:
        String authHeader = ((HttpServletRequest) request).getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Missing or Invalid Token");
            return; // Stop further processing of the request
        }

        // Continue the request-response chain
        chain.doFilter(request, response);
    }
}
