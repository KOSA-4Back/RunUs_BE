package com.fourback.runus.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
 * packageName    : com.fourback.runus.global.security.config
 * fileName       : CorsConfig
 * author         : 김은정
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        김은정            최초 생성
 * 2024-07-29        김민정            cors 프론트 포트 설정
 * 2024-07-30        김은정            cors ngrock 주소 설정
 */
@Configuration
public class CorsConfig {

    // cors 설정
    // 시큐리티 6은 CorsConfigurationSource 로 구현해야함
    @Primary
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("http://localhost:3000"); //TODO 나중에 수정해야함
        configuration.addAllowedOriginPattern("https://4b2d-115-93-148-232.ngrok-free.app");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    
}
