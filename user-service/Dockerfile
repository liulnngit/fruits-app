FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app

COPY target/user-servise-1.0-SNAPSHOT.jar /app/user-servise.jar
ENTRYPOINT ["java","-jar","/app/user-servise.jar"]