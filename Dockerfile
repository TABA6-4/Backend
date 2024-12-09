# Dockerfile
FROM eclipse-temurin:17-jdk-alpine

# 작업 디렉터리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/focus-0.0.1-SNAPSHOT.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
