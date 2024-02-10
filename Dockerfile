FROM openjdk:22-slim

COPY target/message_producer_assignment_2-0.0.1-SNAPSHOT.jar ./app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]