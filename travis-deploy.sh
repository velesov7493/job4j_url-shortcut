#!/bin/sh
set -e
IMAGE="$DOCKER_USERNAME/job4j_shortcut"
mvn package -Dmaven.test.skip=true ;
docker build -t ${IMAGE} . ;
echo "${DOCKER_PASSWORD}" | docker login -u "${DOCKER_USERNAME}" --password-stdin ;
docker push ${IMAGE} ;
exit 0