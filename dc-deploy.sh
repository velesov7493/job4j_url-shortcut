#!/bin/bash
set -e
mvn clean ;
mvn package -Dmaven.test.skip=true ;
docker build -t shortcut . ;
docker compose up ;
exit 0