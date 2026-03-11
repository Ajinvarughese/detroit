FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/detroit-0.0.1.jar detroit.jar

EXPOSE 8080

CMD [ "java", "-jar", "detroit.jar" ]