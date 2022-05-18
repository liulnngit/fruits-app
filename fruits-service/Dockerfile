FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app

COPY target/fruits-service-1.0-SNAPSHOT.jar /app/fruits-service.jar
ENTRYPOINT ["java","-jar","/app/fruits-service.jar"]