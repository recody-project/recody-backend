#!/bin/sh

application_name="recody-backend"
port=8140
container_name=${application_name}-"container"

gradle_build()
{
  ./gradlew clean build
}

docker_build()
{
  docker build --tag ${application_name} .
}

docker_run()
{
  docker run --name ${container_name} -p ${port}:${port} ${application_name}
}

# jar 빌드 및 docker 이미지 빌드
gradle_build; echo "=====   gradle build 성공   =====\n" && docker_build; echo "=====   docker 이미지 빌드 성공   =====\n"

# 실행중인 컨테이너를 찾는다.
CONTAINER_ID=$(docker container ps -a -f "name=${container_name}" -q)

# 실행중인 컨테이너가 있으면 삭제한다. 없으면 이미지를 실행한다.
if [ -z "${CONTAINER_ID}" ] # 검색된 컨테이너 id가 없으면
then
  echo "> 현재 구동중인 컨테이너가 없습니다. 서버를 실행합니다."
else
  echo "> sudo docker stop ${CONTAINER_ID}"
  sudo docker stop "${CONTAINER_ID}"
  echo "> sudo docker rm ${CONTAINER_ID}"
  sudo docker rm "${CONTAINER_ID}"
fi

echo "컨테이너 실행중..."; docker_run || echo "실행 중단"