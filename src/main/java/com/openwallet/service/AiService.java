package com.openwallet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openwallet.dto.AiRequest;
import com.openwallet.dto.AiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    // Config에서 등록한 빈 이름과 변수명을 일치시켜 주입받음
    private final RestClient aiRestClient;
    private final ObjectMapper objectMapper;


    public AiResponse.Ocr ocrReceipt(AiRequest.Ocr request) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", request.getFile().getResource());
        return postToAiServer(
                "/ocr-receipt",               // 경로
                body,                         // Body (Map 객체)
                MediaType.MULTIPART_FORM_DATA,// 타입
                AiResponse.Ocr.class          // 리턴받을 타입
        );
    }

    public AiResponse.Summary getTrendSummary(AiRequest.Summary request) {
        return postToAiServer(
                "/trends/summary",
                request,
                MediaType.APPLICATION_JSON,
                AiResponse.Summary.class
        );
    }


    public AiResponse.Report getReport(AiRequest.Report request) {
        return postToAiServer(
                "/report",
                request,
                MediaType.APPLICATION_JSON,
                AiResponse.Report.class
        );
    }

    private <T> T postToAiServer(String path, Object body, MediaType mediaType, Class<T> responseType) {
        log.info("AI 서버로 요청 전송: path={}, type={}", path, mediaType);

        return aiRestClient.post()
                .uri(path)
                .contentType(mediaType) // 동적으로 Content-Type 설정
                .body(body)             // Object 타입이라 무엇이든 들어감
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    // 아까 만든 정교한 에러 핸들링 로직 유지
                    try {
                        AiResponse.AiErrorResponse error = objectMapper.readValue(res.getBody(), AiResponse.AiErrorResponse.class);
                        log.error("AI 요청 실패: {}", error.getDetail());
                        throw new RuntimeException("AI 오류: " + error.getDetail());
                    } catch (IOException e) {
                        log.error("AI 에러 파싱 실패", e);
                        throw new RuntimeException("AI 서버 통신 오류 (Status: " + res.getStatusCode() + ")");
                    }
                })
                .body(responseType); // 원하는 클래스 타입으로 변환해서 리턴
    }

}