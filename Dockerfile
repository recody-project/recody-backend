FROM openjdk:11

LABEL maintainer="recodyarecody@outlook.com"

EXPOSE 8140

ARG JAR_FILE=build/libs/recody-backend-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} /recody-backend.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar","recody-backend.jar"]