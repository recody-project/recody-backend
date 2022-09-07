#!/bin/sh

JAR_FILE=build/libs/recody-backend-0.0.1-SNAPSHOT.jar

gradle_build()
{
  ./gradlew clean build
}

jar_run()
{
  java -Dspring.profiles.active=dev -jar ${JAR_FILE}
}

# jar 빌드 및 실행
gradle_build; echo "=====   gradle build 성공   =====\n" && echo "===== jar 실행 중... =====\n"; jar_run