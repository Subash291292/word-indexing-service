# Stage 1: Build the application using Gradle
FROM gradle:8.10.2-jdk17 AS build
WORKDIR /app

# Copy Gradle wrapper and build files first (to leverage cache)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Download dependencies
RUN ./gradlew dependencies --no-daemon || return 0

# Copy the source code and build the JAR
COPY src ./src
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy JAR from build stage (adjust if you have custom jar name)
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]