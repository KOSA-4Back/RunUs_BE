package com.fourback.runus.global.security.provider;

import com.fourback.runus.global.error.exception.JwtValidationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static com.fourback.runus.global.error.errorCode.ResponseCode.TOKEN_INVALID;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String salt;

    private Key secretKey;

    @Value("${jwt.expiration}")
    private long exp;


    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }


    // 토큰 생성
    public String createToken(String email, Long userId, String role) {
        log.info("====>>>>>>>>>> createToken {}, {}, {}", email, userId, role);

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", userId);
        claims.put("role", role);// 리스트로 저장

        Date now = new Date();
        Date validity = new Date(now.getTime() + exp);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256);

        // 헤더 설정
        builder.setHeaderParam(JwsHeader.TYPE, JwsHeader.JWT_TYPE);

        return builder.compact();
    }

    
    // 유저의 이메일 꺼내기
    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 유저의 아이디 꺼내기
    public Long getUserId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.get("userId", Long.class);
    }

    // 유저의 role 꺼내기
    public String getRole(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.get("role", String.class);
    }

    // 토큰 유효기간
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            if (claims.getExpiration().before(new Date())) {
                throw new JwtValidationException(TOKEN_INVALID);
            }

            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {    // 잘못된 서명
            throw new JwtValidationException(TOKEN_INVALID);

        } catch (ExpiredJwtException e) {   // 만료
            throw new JwtValidationException(TOKEN_INVALID);

        } catch (UnsupportedJwtException e) {   // 지원 되지 않는 JWT
            throw new JwtValidationException(TOKEN_INVALID);

        } catch (IllegalArgumentException e) {  // 잘못된 토큰
            throw new JwtValidationException(TOKEN_INVALID);
        }
    }
}