#!/bin/bash
set -e
echo "Конфигурирование postgresdb ..."
kubectl apply -f k8s/postgresdb-secret.yml
kubectl apply -f k8s/postgresdb-configmap.yml
echo "Развертывание postgresdb ..."
kubectl apply -f k8s/postgresdb-deployment.yml
while [ "$(kubectl get pods -l app=postgresdb | grep -c '1/1')" -eq 0 ]
do
  sleep 5s
  echo -ne "\b=>"
done
echo -e "\nПод postgresdb:"
kubectl get pods -l app=postgresdb
echo -e "\nЛог postgresdb:"
kubectl logs -l app=postgresdb
echo -e "\nРазвертывание spring-boot ..."
kubectl apply -f k8s/spring-boot-deployment.yml
while [ "$(kubectl get pods -l app=spring-boot | grep -c '1/1')" -eq 0 ]
do
  sleep 5s
  echo -ne "\b=>"
done
sleep 3s
echo -e "\nСостояние сервисов k8s:"
kubectl get services
echo -e "\nЛог spring-boot:"
kubectl logs -l app=spring-boot
printf "\nАдрес сервиса spring-boot: \n%s\n" $(minikube service --url spring-boot-service)
exit 0