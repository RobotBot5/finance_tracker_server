FROM openjdk:21-jdk
WORKDIR /app
COPY target/finance_tracker_server-0.0.1-SNAPSHOT.jar /app/finance_tracker_server-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/finance_tracker_server-0.0.1-SNAPSHOT.jar"]