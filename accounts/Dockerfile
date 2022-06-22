#start with a base image containing Java runtime
FROM openjdk:11-slim as build

#Information around who maintain the image
MAINTAINER nishthasoft

#Add the application's jar to the container
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#Exceute the application
ENTRYPOINT ["java" , "-jar","/accounts-0.0.1-SNAPSHOT.jar"]
