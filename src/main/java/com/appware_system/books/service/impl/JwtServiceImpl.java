package com.appware_system.books.service.impl;

import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl {

    private static final String SECRET = "9JyCfVdm8CZWwP0f42ixsmJbNYapKIkr9JyCfVdm8CZWwP0f42ixsmJbNYapKIkr";
    public static Map<String, Boolean> invalidatedTokens = new HashMap<>();

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token the JWT token
     * @return the expiration date extracted from the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the given JWT token using the provided claims resolver function.
     *
     * @param token          the JWT token
     * @param claimsResolver the function to resolve the desired claim from the token's claims
     * @param <T>            the type of the claim value
     * @return the extracted claim value
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token the JWT token
     * @return the extracted claims
     * @throws JwtException if the token is invalid or cannot be parsed
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the given JWT token has expired.
     *
     * @param token the JWT token
     * @return true if the token has expired, false otherwise
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validates the given JWT token against the provided user details.
     *
     * @param token       the JWT token to validate
     * @param userDetails the user details to compare against the token
     * @return true if the token is valid for the given user details, false otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenInvalidated(token));
    }

    /**
     * Generates a JWT token for the given email.
     *
     * @param email the email for which to generate the token
     * @return the generated JWT token
     */
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    /**
     * Creates a JWT token with the specified claims and subject.
     *
     * @param claims the claims to include in the token
     * @param email  the subject of the token
     * @return the created JWT token
     */
    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Retrieves the signing key used for generating and validating JWT tokens.
     *
     * @return the signing key
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Invalidates the specified token by adding it to the list of invalidated tokens.
     *
     * @param token the token to invalidate
     */
    public void invalidateToken(String token) {
        invalidatedTokens.put(token, true);
    }

    /**
     * Checks if the specified token has been invalidated.
     *
     * @param token the token to check
     * @return true if the token has been invalidated, false otherwise
     */
    private boolean isTokenInvalidated(String token) {
        return invalidatedTokens.containsKey(token);
    }
}
