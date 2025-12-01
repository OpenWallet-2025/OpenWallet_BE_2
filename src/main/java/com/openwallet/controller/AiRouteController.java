package com.openwallet.controller;

import com.openwallet.dto.AiRequest;
import com.openwallet.dto.AiResponse;
import com.openwallet.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AiRouteController {

    private final AiService aiService;

    // ==================================================================
    // 1. OCR 영수증 분석 (파일 업로드)
    // ==================================================================
    @PostMapping(value = "/ocr-receipt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AiResponse.Ocr> ocrReceipt(@ModelAttribute AiRequest.Ocr request) {
        log.info("API Request: OCR Receipt Analysis");
        return ResponseEntity.ok(aiService.ocrReceipt(request));
    }

    // ==================================================================
    // 5. 트렌드 뉴스 요약
    // ==================================================================
    @PostMapping("/trends/summary")
    public ResponseEntity<AiResponse.Summary> getTrendSummary(@RequestBody AiRequest.Summary request) {
        log.info("API Request: Trend News Summary - Keywords: {}", request.getKeywords());
        return ResponseEntity.ok(aiService.getTrendSummary(request));
    }

    // ==================================================================
    // 6. LLM 자연어 리포트 생성
    // ==================================================================
    @PostMapping("/report")
    public ResponseEntity<AiResponse.Report> getReport(@RequestBody AiRequest.Report request) {
        log.info("API Request: LLM Report Generation - User: {}", request.getUserId());
        return ResponseEntity.ok(aiService.getReport(request));
    }

    // ==================================================================
    // [Error Handler] AI Service 예외 처리
    // Service에서 throw new RuntimeException("...") 발생 시 실행됨
    // ==================================================================
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleAiServiceException(RuntimeException e) {
        log.error("AI Service Error Caught in Controller: {}", e.getMessage());

        // 에러 메시지에 "AI 서버 통신 오류" 등이 포함되어 있으면 502(Bad Gateway)나 500 리턴
        // 프론트엔드가 알기 쉽게 JSON 포맷으로 에러 메시지 반환
        return ResponseEntity
                .status(502) // 502 Bad Gateway (외부 AI 서버 문제임을 명시)
                .body(Map.of(
                        "error", "AI_SERVICE_ERROR",
                        "message", e.getMessage() // "AI 서버 통신 오류 (Status: 500)" 등의 메시지가 전달됨
                ));
    }
}