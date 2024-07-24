package com.fourback.runus.global.security.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fourback.runus.domains.member.service.JpaUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	 @Value("${jwt.secret}")
	    private String salt;

	    private Key secretKey;

	    @Value("${jwt.expiration}")
	    private long exp;

	    private final JpaUserDetailsService userDetailsService;

	    public JwtProvider(JpaUserDetailsService userDetailsService) {
	        this.userDetailsService = userDetailsService;
	    }

	    @PostConstruct
	    protected void init() {
	        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
	    }

	    public String createToken(String email) {
	        logger.info("Creating token for email: {}", email);
	        Claims claims = Jwts.claims().setSubject(email);
	        Date now = new Date();
	        String token = Jwts.builder()
	                .setClaims(claims)
	                .setIssuedAt(now)
	                .setExpiration(new Date(now.getTime() + exp))
	                .signWith(secretKey, SignatureAlgorithm.HS256)
	                .compact();
	        logger.info("Token created successfully");
	        return token;
	    }

	    public Authentication getAuthentication(String token) {
	        logger.info("Getting authentication for token");
	        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getEmail(token));
	        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	    }

	    public String getEmail(String token) {
	        logger.info("Getting email from token");
	        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
	    }

	    public String resolveToken(HttpServletRequest request) {
	        logger.info("Resolving token from request");
	        return request.getHeader("Authorization");
	    }

	    public boolean validateToken(String token) {
	        try {
	            logger.info("Validating token");
	            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	            return !claims.getExpiration().before(new Date());
	        } catch (Exception e) {
	            logger.error("Token validation error", e);
	            return false;
	    }
	}
}