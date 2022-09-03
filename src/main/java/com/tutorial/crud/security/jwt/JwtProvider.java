package com.tutorial.crud.security.jwt;

import com.tutorial.crud.security.entity.UserPrincipal;
//import io.jsonwebtoken.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtProvider {
    private  final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder().setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusHours(1).toInstant()))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }
    public String getUserNameFromToken (String token) {

        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;

        }catch (MalformedJwtException e) {
            logger.error("token mal formado");
        }catch (UnsupportedJwtException e) {
            logger.error("token mal formado");
        }catch (ExpiredJwtException e){
                logger.error("token mal formado");
            }catch (IllegalArgumentException e) {
            logger.error("token mal formado");
        }catch (SecurityException e) {
            logger.error("fail en la firma");
        }
        return false;
    }
}
