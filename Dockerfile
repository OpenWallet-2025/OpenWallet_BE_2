# --------------------------------------------------------
# 1. Builder Stage: Gradle로 빌드하는 단계
# --------------------------------------------------------
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Gradle 빌드에 필요한 파일만 먼저 복사 (Layer Caching 활용)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN apk add --no-cache dos2unix && dos2unix gradlew
# 실행 권한 부여 (Windows에서 작성 시 권한 문제 방지)
RUN chmod +x gradlew

# 의존성 설치 (소스코드 복사 전에 실행하여 캐싱 효과 극대화)
# 이 단계는 build.gradle이 변경되지 않는 한 재사용됨
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사
COPY src src

# 프로젝트 빌드 (테스트는 CI/CD에서 수행한다고 가정하고 생략 -x test)
RUN ./gradlew clean build -x test --no-daemon

# --------------------------------------------------------
# 2. Runtime Stage: 실제로 실행할 가벼운 이미지 생성 단계
# --------------------------------------------------------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Builder 단계에서 생성된 JAR 파일만 복사
# (build/libs/ 안에 생성된 -SNAPSHOT.jar 파일을 app.jar로 복사)
COPY --from=builder /app/build/libs/*.jar app.jar

# 보안을 위해 nobody 유저로 실행 (선택 사항이지만 쿠버네티스 보안 권장)
# 만약 파일 쓰기 권한이 필요하다면 root를 써야 할 수도 있음
USER nobody

# 실행 포트 노출 (문서화 용도)
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]