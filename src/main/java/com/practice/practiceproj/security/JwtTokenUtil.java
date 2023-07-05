package com.practice.practiceproj.security;

import com.practice.practiceproj.core.dtos.user.MyUserDetails;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.practice.practiceproj.properties.JwtProperty;

@Component
public class JwtTokenUtil {
    private final JwtProperty jwtProperty;

    public JwtTokenUtil(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    public String generateAccessToken(MyUserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer(jwtProperty.getJwtIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))) // 1 week
                .claim("uuid", user.getUuid())
                .claim("fio", user.getFio())
                .claim("role", user.getRole().name())
                .signWith(SignatureAlgorithm.HS512, jwtProperty.getJwtSecret())
                .compact();
    }

    public String generateAccessToken(String name) {
        return Jwts.builder()
                .setSubject(name)
                .setIssuer(jwtProperty.getJwtIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtProperty.getJwtSecret())
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getUserRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role").toString();
    }


    public String getFio(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.get("fio").toString();
    }

    public String getUserUUID(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.get("uuid").toString();
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperty.getJwtSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
