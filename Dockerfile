# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the application's JAR file to the container
# Replace 'your-app.jar' with the actual name of your JAR file
COPY target/cart-wala.jar app.jar

# Expose the port that your application listens on
# Replace 8080 with your application's actual port if different
EXPOSE 8080
EXPOSE 3306

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
