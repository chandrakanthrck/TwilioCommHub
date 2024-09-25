package com.chandrakanthrck.twilio_communication.security;

import com.chandrakanthrck.twilio_communication.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the Authorization header
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Check if Authorization header is present and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // Extract the JWT token
            username = jwtUtil.extractUsername(jwt);  // Extract username from the JWT
            logger.info("Extracted JWT for user: {}", username);
        }

        // Validate the token and set up authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("Loading user details for username: {}", username);
            UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);

            // If token is valid, set up Spring Security context
            if (jwtUtil.validateToken(jwt, userDetails)) {
                logger.info("JWT is valid for user: {}", username);
                // Set up authentication object
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                logger.warn("Invalid JWT token for user: {}", username);
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
