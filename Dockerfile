# Use Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "target/hirepro-0.0.1-SNAPSHOT.jar"]
