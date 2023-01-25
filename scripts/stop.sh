#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)  # profile.sh의 경로를 찾기 위해 현재 stop.sh가 속해있는 경로를 찾음
source ${ABSDIR}/profile.sh # import 구문, 이 코드로 인해 stop.sh에서도 profile.sh의 함수들을 사용할 수 있게 됨

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi