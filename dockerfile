# Use an official Maven image as the base image
FROM maven:3.9.6-eclipse-temurin-11 AS build

# Set the working directory in the container
WORKDIR /wine_prediction_app


# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use an official OpenJDK image as the base image
FROM eclipse-temurin:11-jdk-jammy

# Set the working directory in the container
WORKDIR /wine_prediction_app

# Copy the built JAR file from the previous stage to the container
COPY --from=build /wine_prediction_app/target/wine_prediction_app-1.0-SNAPSHOT.jar .

# Set the command to run the application
CMD ["java", "-jar", "wine_prediction_app-1.0-SNAPSHOT.jar"]