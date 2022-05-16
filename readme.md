[![Build Status](https://app.travis-ci.com/velesov7493/job4j_url-shortcut.svg?branch=master)](https://app.travis-ci.com/velesov7493/job4j_url-shortcut)
## Описание ##

Это учебный проект REST-сервиса по сокращению ссылок.

#### API ####
**1. Регистрация сайта**

Запрос:
```
curl \
--location \
--request POST 'localhost/registration' \ 
--header 'Content-Type: application/json' \
--data-raw '{
    "site": "job4j.ru"
}'
```
Ответ: 
```
{"registration" : true/false, "login": "УНИКАЛЬНЫЙ_КОД", "password" : "УНИКАЛЬНЫЙ_КОД"}
```

**2. Авторизация**

Запрос:
```
curl \
   --location --request POST 'localhost/login' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "site": "НАИМЕНОВАНИЕ_САЙТА",
       "password": "ПАРОЛЬ"
   }'
```

Ответ: JWT token в заголовке Authorization 

**3. Получить сокращенную ссылку**

Запрос:
```
curl --location --request POST 'localhost/convert' \
--header 'Authorization: ВАШ_ТОКЕН' \
--header 'Content-Type: application/json' \
--data-raw '{
    "url": "https://kubernetes.io"
}'
```
Ответ: `{"code": "УНИКАЛЬНЫЙ_КОД"}`

**4. Переход по сокращенной ссылке (лучше выполнять в браузере)**

```
localhost/redirect/УНИКАЛЬНЫЙ_КОД
```

Ответ: перенаправление на исходный URL

**5. Статистика переходов для авторизованного сайта**

Запрос:
```
curl --location --request GET 'http://localhost/statistic' \
--header 'Authorization: ВАШ_ТОКЕН
```

Ответ: `[{"url" : "ДЛИННАЯ_ССЫЛКА1", "total" : КОЛИЧЕСТВО_ПЕРЕХОДОВ1}, ..., {"url" : "ДЛИННАЯ_ССЫЛКАN", "total" : КОЛИЧЕСТВО_ПЕРЕХОДОВN}]`

#### Технологии проекта ####
![badge](https://img.shields.io/badge/docker-20.10-random)
![badge](https://img.shields.io/badge/docker/compose-2.5-indigo)
![badge](https://img.shields.io/badge/kubernetes-1.24-lightgreen)
![badge](https://img.shields.io/badge/PostgreSQL-12-blue)
![badge](https://img.shields.io/badge/Java-14-green)
![badge](https://img.shields.io/badge/Maven-3.6-green)
![badge](https://img.shields.io/badge/SpringBot-2.6-yellow)
#### Инструкция по развертыванию приложения в контейнерах ####
[Развертывание приложения в контейнерах при помощи docker и docker compose](https://github.com/velesov7493/job4j_url-shortcut/wiki/Manual:-deploying-an-application-in-containers-using-docker-and-docker-compose)
#### Инструкция по развертыванию приложения в kubernetes ####
[Развертывание приложения в k8s minikube](https://github.com/velesov7493/job4j_url-shortcut/wiki/Manual:-deploying-an-application-in-kubernetes)