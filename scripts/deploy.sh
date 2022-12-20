#!/bin/bash
PROJECT_NAME=sleact
CURRENT_PID=$(pgrep -f $PROJECT_NAME)

if [ -z $CURRENT_PID ]; then
  echo "> not running app."
else
  echo "> kill -9 $CURRENT_PID"
  kill -9 $CURRENT_PID
  sleep 3
fi

echo "> deploy new app"

cd /deploy/backend
JAR_NAME=$(ls | grep $PROJECT_NAME | tail -n 1)

nohup java -jar $JAR_NAME --spring.profiles.active=dev 1>nohup/stdout.txt 2>nohup/stderr.txt &
sleep 2
