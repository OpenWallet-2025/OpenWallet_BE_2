package com.openwallet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Configuration
public class RestClientConfig {

    // --- 1. AI 서버용 설정 ---
    @Value("${ai.server.url}")
    private String aiServerUrl;

    @Bean(name = "aiRestClient") // 빈 이름을 명시적으로 지정
    public RestClient aiRestClient(RestClient.Builder builder) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(600000); // AI는 오래 걸리니까 30초

        return builder
                .baseUrl(aiServerUrl)
                .requestFactory(factory)
                .build();
    }
}
