#!/bin/bash

# Скрипт для прогона тестов
# Если запускается в Jenkins - он просто прогоняет тесты
# Если запускается локально - можно раскомментировать генерацию отчета

PROJECT_ROOT=$(pwd)

echo "=== Запуск тестов через Maven ==="
# Мы используем clean verify, чтобы сгенерировать allure-results
mvn clean verify

echo "=== Готово! Результаты тестов в target/allure-results ==="
# Jenkins Allure Plugin сам подхватит историю и результаты из этой папки
