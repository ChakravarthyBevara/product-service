# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

RUN apt-get update && apt-get install -y curl

# Copy the JAR into the image
COPY target/product-service-0.0.1-SNAPSHOT.jar app.jar

# Expose app port
EXPOSE 8081

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
