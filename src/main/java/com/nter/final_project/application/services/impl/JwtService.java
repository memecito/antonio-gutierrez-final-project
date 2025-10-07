package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.services.ApiUserService;
import com.nter.final_project.exception.UnauthenticatedException;
import com.nter.final_project.exception.UnauthorizedException;
import com.nter.final_project.persistence.entity.ApiUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    @Lazy
    @Autowired
    private ApiUserService apiUserService;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Getter
    @Value("${security.jwt.access-token-expiration}")
    private String accessTokenExpiration;

    @Value("${security.jwt.refresh-token-expiration}")
    private String refreshTokenExpiration;

    @Getter
    @Value("${security.jwt.secure-access}")
    private boolean secureAccess;

    private final static String REFRESH_TOKEN_CLAIM = "refresh_token";
    private final static String ACCESS_TOKEN_CLAIM = "access_token";

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, accessTokenExpiration, ACCESS_TOKEN_CLAIM);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, refreshTokenExpiration, REFRESH_TOKEN_CLAIM);
    }

    private String generateToken(UserDetails userDetails, String longExpirationTime, String accessType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        claims.put("access_type", accessType);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(Long.parseLong(longExpirationTime))))
                .signWith(getSignInKey())
                .addClaims(claims)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValidAccessToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && isTokenOfType(token, ACCESS_TOKEN_CLAIM);
    }

    public boolean isValidRefreshToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && isTokenOfType(token, REFRESH_TOKEN_CLAIM);
    }

    private boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
    }

    private boolean isTokenOfType(String token, String expectedType) {
        String actualType = extractClaim(token, claims -> claims.get("access_type", String.class));
        return expectedType.equals(actualType);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /***
     *
     * @param id
     * @param token
     * @return boolean
     */
    public boolean authorization(Long id, String token) {
        ApiUser userToken = apiUserService.getByEmail(extractUsername(token.substring(7)));
        if (userToken.getAdmin()) {
            return true;
        }
        if (!Objects.equals(apiUserService.getById(id).getEmail(), userToken.getEmail())) {
            throw new UnauthorizedException("No tienes permisos para entrar, JWS01");
        }
        return true;
    }
}
