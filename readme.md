[![Build Status](https://app.travis-ci.com/velesov7493/job4j_url-shortcut.svg?branch=master)](https://app.travis-ci.com/velesov7493/job4j_url-shortcut)
## Описание ##

Это учебный проект REST-сервиса по сокращению ссылок.

#### API ####
**1. Регистрация сайта**

`POST /registration`

Запрос: `{"site": "НАИМЕНОВАНИЕ_САЙТА"}`

Ответ: `{"registration" : true/false, "login": "УНИКАЛЬНЫЙ_КОД", "password" : "УНИКАЛЬНЫЙ_КОД"}`

**2. Авторизация**

`POST /login`

Запрос: `{"site": "НАИМЕНОВАНИЕ_САЙТА", "password": "ПАРОЛЬ"}`

Ответ: JWT token в заголовке Authorization 

**3. Получить сокращенную ссылку**

`POST /convert`

Запрос:
+ JWT token в заголовке Authorization
+ `{url: "ДЛИННАЯ_ССЫЛКА"}`

Ответ: `{"code": "УНИКАЛЬНЫЙ_КОД"}`

**4. Переход по сокращенной ссылке**

`GET /redirect/УНИКАЛЬНЫЙ_КОД`

Ответ: перенаправление на исходный URL

**5. Статистика переходов для авторизованного сайта**

`GET /statistic`

Запрос: JWT token в заголовке Authorization

Ответ: `[{"url" : "ДЛИННАЯ_ССЫЛКА1", "total" : КОЛИЧЕСТВО_ПЕРЕХОДОВ1}, ..., {"url" : "ДЛИННАЯ_ССЫЛКАN", "total" : КОЛИЧЕСТВО_ПЕРЕХОДОВN}]`

#### Технологии проекта ####
![badge](https://img.shields.io/badge/docker-20.10-random)
![badge](https://img.shields.io/badge/docker/compose-2.5-green)
![badge](https://img.shields.io/badge/PostgreSQL-12-blue)
![badge](https://img.shields.io/badge/Java-14-green)
![badge](https://img.shields.io/badge/Maven-3.6-green)
![badge](https://img.shields.io/badge/SpringBot-2.6-yellow)
#### Инструкция по развертыванию приложения в контейнерах ####
[Развертывание приложения в контейнерах при помощи docker и docker compose](https://github.com/velesov7493/job4j_url-shortcut/wiki/Manual:-deploying-an-application-in-containers-using-docker-and-docker-compose)