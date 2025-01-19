#!/bin/bash

echo "서버 중지 및 삭제"
docker-compose -f infra/docker/docker-compose.yml down --rmi local --volumes

echo "최신 버전 pull"
git pull origin main

#echo "서버 빌드"
#docker-compose -f infra/docker/docker-compose.yml build --no-cache

echo "서버 실행"
docker-compose -f infra/docker/docker-compose.yml up --build -d

echo "배포 완료"