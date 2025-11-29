package com.openwallet.config; // 패키지명은 본인 프로젝트에 맞게 수정

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 1. 이 어노테이션이 있어야 설정 파일로 인식하고 자동 적용됨
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")              // 모든 URL 경로에 대해
                .allowedOrigins("*")            // 모든 출처(Origin) 허용
                .allowedMethods("*")            // 모든 HTTP 메서드(GET, POST 등) 허용
                .allowedHeaders("*")            // 모든 헤더 허용
                .maxAge(3600);                  // Preflight 요청 캐싱 시간 (초 단위)

        // 주의: .allowCredentials(true)는 절대 넣으면 안 됨 (allowedOrigins("*")와 충돌남)
    }
}