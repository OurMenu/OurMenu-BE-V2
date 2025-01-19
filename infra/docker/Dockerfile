FROM gradle:8.2.1-jdk17 AS builder

WORKDIR /app

COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle

COPY src ./src

RUN chmod +x gradlew
RUN ./gradlew clean build -x test

FROM openjdk:17-jdk-slim

ENV APP_HOME=/app
ENV JAR_NAME=app.jar

WORKDIR ${APP_HOME}

COPY --from=builder /app/build/libs/*.jar ${JAR_NAME}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
