# ---------- Stage 1: build the JAR with Gradle ----------
FROM gradle:9-jdk21 AS build
USER root
WORKDIR /app

# Copy the Gradle wrapper and build files first (better layer caching)
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle

# Copy the application source code and build the JAR
COPY src ./src
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon

# ---------- Stage 2: small runtime image ----------
FROM eclipse-temurin:21-jre

LABEL maintainer="Akbota Zhanserikkyzy" \
      student.id="37106" \
      student.group="IT2-2308"

WORKDIR /app

# Run the application as a non-root user
RUN groupadd --system app && useradd --system --gid app app

COPY --from=build --chown=app:app /app/build/libs/student-api.jar /app/student-api.jar
USER app

EXPOSE 8080

CMD ["java", "-jar", "/app/student-api.jar"]
