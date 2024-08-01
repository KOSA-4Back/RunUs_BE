package com.fourback.runus.global.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourback.runus.global.error.errorCode.ResponseCode;
import com.fourback.runus.global.error.response.ErrorResponse;
import com.fourback.runus.global.security.provider.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            log.info("====>>>>>>>>>> doFilterInternal {}", request.getRequestURI());

            String token = getJwtFromRequest(request);
            if (StringUtils.hasText(token)) {
                if (jwtProvider.validateToken(token)) {
                    Long userId = jwtProvider.getUserId(token);
                    String roleString = jwtProvider.getRole(token);
                    List<SimpleGrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority(roleString));

                    // 사용자 정보를 컨텍스트에 설정
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userId, null, roles);

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } else {
                    // 토큰이 유효하지 않으면 401 응답 반환
                    setErrorResponse(response, ResponseCode.TOKEN_INVALID);
                    return;
                }

            } else {
                // 토큰이 없는 경우 401 응답 반환
                setErrorResponse(response, ResponseCode.USER_NOT_FOUND);
                return;
            }

        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
            setErrorResponse(response, ResponseCode.INTERNAL_SERVER_ERROR);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String[] api = {"/api/auth/login", "/api/auth/register",
                "/api/auth/forgot-password", "/api/auth/verify-code",
                "/api/auth/change-password", "/api/auth/check-email",
                "/api/auth/check-nickname" , "/api/member/change-password/{user-id}"};

        String path = request.getRequestURI();
        return Arrays.stream(api).anyMatch(path::startsWith);
    }


    // 토큰 자르기
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 에러처리
    private void setErrorResponse(HttpServletResponse response, ResponseCode responseCode) throws IOException {
        response.setStatus(responseCode.getStatus().value());
        response.setContentType("application/json; charset=UTF-8");
        ErrorResponse errorResponse = ErrorResponse.of(responseCode);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}