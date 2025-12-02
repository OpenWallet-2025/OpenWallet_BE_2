package com.openwallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

public class AiResponse {

    // [OCR 응답]
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Ocr {

        private String merchant;  // "스타벅스"

        private Long amount;      // 5800 (금액은 Long 권장)

        private String date;      // "2025-11-24", 일단 스트링으로 받음

        private String category;  // "카페/음료"

        // JSON의 "raw_text"를 자바의 "rawText"로 매핑, 파이썬과 자바의 기본 작성법이 발라서 맴핑변환. 값은 똑같고 속성 이름만 바꿈
        @JsonProperty("raw_text")
        private String rawText;

        // items 배열을 담을 리스트
        private List<Item> items;

        // items 내부의 객체 구조 정의 (Nested Class)
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Item {
            private String name;  // "콜드브루"
            private Long price;   // 4800
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AiErrorResponse {
        private String detail;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Summary {

        @JsonProperty("period_start")
        private String periodStart; // "2025-11-19"

        @JsonProperty("period_end")
        private String periodEnd;   // "2025-11-26"

        private List<String> keywords; // 요청했던 키워드 다시 반환됨

        private List<String> bullets;  // 요약 내용

        @JsonProperty("key_stats")
        private List<String> keyStats; // 주요 통계

        private List<String> risks;    // 위험 요소

        private List<String> opportunities; // 기회 요소

        private List<String> sources;  // 뉴스 출처 URL

        private String model;          // 사용된 모델명
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Report {

        // AI가 생성한 핵심 결과물 (긴 텍스트)
        private String report;

        @JsonProperty("start_date")
        private String startDate;

        @JsonProperty("end_date")
        private String endDate;

        // 분석에 사용된 거래 내역 건수
        @JsonProperty("transaction_count")
        private Integer transactionCount;
    }

}

