FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app

COPY target/emoji-service-1.0-SNAPSHOT.jar /app/emoji-service.jar
ENTRYPOINT ["java","-jar","/app/emoji-service.jar"]