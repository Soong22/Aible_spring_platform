# Eclipse Temurin JDK 17을 기반 이미지로 사용
FROM eclipse-temurin:17-jdk

# 애플리케이션 JAR 파일을 컨테이너로 복사
COPY build/libs/platform-0.0.1-SNAPSHOT.jar app.jar

# (선택 사항) 프로파일 기반 실행을 위한 주석
# 기본적으로 "application.properties"를 사용할 것이지만,
# -Dspring.profiles.active=prod 또는 -Dspring.profiles.active=dev 등의 프로파일 설정을 활성화하려면 아래 명령어를 수정하십시오.

# EXPOSE 명령어로 포트를 노출
EXPOSE 8080

# 기본 실행 명령어 설정 (개발 단계에서는 기본 프로파일 사용)
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 주석 예시:
# ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/app.jar"]
