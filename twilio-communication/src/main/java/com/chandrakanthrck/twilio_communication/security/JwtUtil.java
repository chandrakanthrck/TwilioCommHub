package com.chandrakanthrck.twilio_communication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private String SECRET_KEY = "my_secret_key";  // Use a stronger secret key in production

    // Extract username from JWT token
    public String extractUsername(String token) {
        logger.info("Extracting username from token.");
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from JWT token
    public Date extractExpiration(String token) {
        logger.info("Extracting expiration date from token.");
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract specific claim from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        logger.info("Extracting claims from token.");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Retrieve all claims from token
    private Claims extractAllClaims(String token) {
        logger.info("Retrieving all claims from token.");
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // Check if token has expired
    private Boolean isTokenExpired(String token) {
        logger.info("Checking if token has expired.");
        return extractExpiration(token).before(new Date());
    }

    // Generate a new JWT token
    public String generateToken(String username) {
        logger.info("Generating JWT token for username: {}", username);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token valid for 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Validate the token by checking username and expiration
    public Boolean validateToken(String token, UserDetails userDetails) {
        logger.info("Validating token for username: {}", userDetails.getUsername());
        final String username = extractUsername(token);  // Extract the username from the token
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));  // Compare with user details
    }
}
