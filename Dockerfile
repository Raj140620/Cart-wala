# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
# Make sure to replace 'your-application.jar' with the actual JAR file name
COPY target/*.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Set environment variables for database connection (these can also be set in docker-compose)

ENV DB_PORT=3306
ENV DB_USER=raj
ENV DB_PASSWORD=123

# Run the application
CMD ["java", "-jar", "app.jar"]
