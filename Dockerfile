# Use Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy the Maven build files first
COPY pom.xml .
COPY src ./src

# Install Maven and build the app
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests

# Run the jar file
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/employee-management-0.0.1-SNAPSHOT.jar"]