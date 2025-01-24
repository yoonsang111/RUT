#!/bin/bash

echo "> 도커 컨테이너 찾아서 삭제: $JAR_NAME" >> /home/ubuntu/backend/logs/deploy.log
docker ps -a | grep tour-stream-backend:latest | awk '{print$1}' | xargs -t -I % docker rm -f % | true

echo "> 도커 이미지 찾아서 삭제: $JAR_NAME" >> /home/ubuntu/backend/logs/deploy.log
docker image ls | grep tour-stream-backend | awk '{print$3}' | xargs -I % docker rmi % | true

echo "> 도커 이미지 로드" >> /home/ubuntu/backend/logs/deploy.log
docker load < /home/ubuntu/action/tour-stream-backend.tar

echo "> 도커 컨테이너 실행" >> /home/ubuntu/backend/logs/deploy.log
docker run -d -p 8080:8080 -v /home/ubuntu/backend/logs:/app/logs --name tour-stream-backend tour-stream-backend:latest

