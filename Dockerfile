#############################################
# 1️⃣ Build Stage  (JDK 17)
#############################################
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Gradle wrapper 및 build config 복사
COPY gradlew gradlew.bat ./
COPY gradle gradle
COPY build.gradle settings.gradle ./

# 🔥 gradlew 실행 권한 부여 (필수!)
RUN chmod +x gradlew

# 의존성 캐시 레이어
RUN ./gradlew dependencies --no-daemon || true

# 실제 소스 복사
COPY src src

# Boot JAR 빌드
RUN ./gradlew bootJar --no-daemon

#############################################
# 2️⃣ Runtime Stage  (JRE 17)
#############################################
FROM eclipse-temurin:17-jre

WORKDIR /app

# builder에서 만들어진 bootJar만 가져오기
COPY --from=builder /app/build/libs/*.jar app.jar

# 컨테이너 포트
EXPOSE 8080

# 실행 명령
ENTRYPOINT ["java","-jar","/app/app.jar"]
