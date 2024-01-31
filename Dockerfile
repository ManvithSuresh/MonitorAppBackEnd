# Use the official OpenJDK image with JDK 8 as base image
FROM openjdk:8-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the Maven target directory into the container
COPY target/MonitorApp-0.0.1-SNAPSHOT.war /app/MonitorApp.war

# Expose the port that the application will run on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "MonitorApp.war"]
