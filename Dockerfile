FROM openjdk:21
EXPOSE 5500
COPY target/moneyTransferService-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]