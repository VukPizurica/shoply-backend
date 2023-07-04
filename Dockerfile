FROM adoptopenjdk/openjdk11:latest
WORKDIR /app
COPY target/shoply-backend.jar .
EXPOSE 8080
CMD ["java", "-jar", "shoply-backend.jar"]
