package com.fourback.runus.global.security.config;

import com.fourback.runus.global.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RedisLogoutHandler redisLogoutHandler;
    private final RedisLogoutSuccessHandler redisLogoutSuccessHandler;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("===>>>>>>>>>>>>>>> securityFilterChain");

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/auth/login", "/api/auth/register", 
                    		"/api/auth/forgot-password", "/api/auth/verify-code",
                    		"/api/auth/change-password").permitAll()
                .anyRequest().authenticated()
            )
                .logout(logout -> logout.logoutUrl("/api/auth/logout") // 레디스에서 토큰 파기를 위한 로그아웃 필터 구현 by 영훈
                        .addLogoutHandler(redisLogoutHandler)
                        .logoutSuccessHandler(redisLogoutSuccessHandler)) // 성공하면 토큰 정보 파기
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 암호화
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //TODO AuthenticationManager 조금 더 공부한 뒤에 나중에 구현 !
}
