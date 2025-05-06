# Analytic Service

Так как приложение состоит из двух сервисов, запускать их необходимо в порядке
1. Analytic Service
2. Search Service

## Описание
Это демонстрационное приложение написанное на Spring Boot, предназначено для хранения и агрегации данных.
Приложение в фоновом режиме выгружает данные в Clickhouse и позволяет получать аналитические данные с помощью API.

## Архитектура

1. Spring Boot является основой приложения, позволяя использовать все преимущества Spring.
2. PostgreSQL используется для хранения данных и последующих запросах.
3. Kafka используется для получения данных по использованию запросов из Search Service.
4. ClickHouse используется для сбора и выдачи аналитическиз данных

## Стек:

1. JDK 23
2. Spring Boot
3. Spring JDBC
4. Spring Web
5. Scheduled
6. Docker (для запуска зависимостей)
7. PostgreSQL
8. Clickhouse
9. Kafka

## Установка

1. ```git clone https://github.com/MilkevichEgor/search-metrics-analytic-service.git```
2. ```cd search-metrics-analytic-service```
3. ```docker compose up -d```