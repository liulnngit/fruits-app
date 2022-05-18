FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app

COPY target/discovery-service-1.0-SNAPSHOT.jar /app/discovery-service.jar
ENTRYPOINT ["java","-jar","/app/discovery-service.jar"]