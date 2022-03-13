package com.personal.utils;

import static java.lang.String.format;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.personal.common.UserTypeEnum;
import com.personal.entity.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	private final String JWT_SECRET = "bach";
	private final long JWT_EXPIRATION = 604800000L;
	
	public String generateToken(UserPrincipal userDetails, String type) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.
        String jwtToken = null;
        if(UserTypeEnum.ADMIN.name.equalsIgnoreCase(type)) {
        	jwtToken =  Jwts.builder()
//                  .setSubject(Long.toString(userDetails.getUser().getId()))
                  .setSubject(format("%s,%s", userDetails.getId(), userDetails.getUsername().concat(":").concat(UserTypeEnum.ADMIN.name)))
                  .setIssuedAt(now)
                  .setExpiration(expiryDate)
                  .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                  .compact();
        } else if(UserTypeEnum.USER.name.equalsIgnoreCase(type)) {
        	jwtToken =  Jwts.builder()
//                  .setSubject(Long.toString(userDetails.getUser().getId()))
                  .setSubject(format("%s,%s", userDetails.getId(), userDetails.getUsername().concat(":").concat(UserTypeEnum.USER.name)))
                  .setIssuedAt(now)
                  .setExpiration(expiryDate)
                  .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                  .compact();
        }
        return jwtToken;
    }
	
	public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();
        
        return Long.parseLong(claims.getSubject().split(",")[0]);
    }
	
	public String getUsername(String token) {
        Claims claims = Jwts.parser()
        				.setSigningKey(JWT_SECRET)
        				.parseClaimsJws(token)
        				.getBody();

        return claims.getSubject().split(",")[1];
    }
	
	public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
