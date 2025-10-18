# ===== Build stage =====
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy pom and source code
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

# Package the app
RUN mvn clean package -DskipTests

# ===== Runtime stage =====
FROM eclipse-temurin:21-jre

# Create non-root user for safety
RUN useradd -ms /bin/bash appuser
USER appuser

WORKDIR /app

# Copy JAR from builder
COPY --from=builder /app/target/*.jar app.jar

# Expose backend port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
