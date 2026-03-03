# start with a base image containing Java runtime
#FROM openjdk:17-jdk-slim
FROM openjdk:17-ea-slim-buster
#Information around who maintains the image
#MAINTAINER koho.org
LABEL maintainer="koho.org"

#Add the application's jar to the image
COPY target/account-service-0.0.1-SNAPSHOT.jar account-service-0.0.1-SNAPSHOT.jar

#Execute the application
ENTRYPOINT ["java","-jar","account-service-0.0.1-SNAPSHOT.jar"]

#command to build docker image file
#docker build . -t kohodocker/account-service:v1