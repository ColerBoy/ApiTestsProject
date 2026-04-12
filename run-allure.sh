#!/bin/bash

# Скрипт для прогона тестов и генерации отчетов Allure (аналог run-allure.ps1)
# Убедитесь, что у файла есть права на выполнение: chmod +x run-allure.sh

PROJECT_ROOT=$(pwd)
TARGET_FOLDER="$PROJECT_ROOT/target"
ALLURE_RESULTS_FOLDER="$TARGET_FOLDER/allure-results"
ALLURE_REPORT_FOLDER="$TARGET_FOLDER/site/allure-maven-plugin"
HISTORY_BACKUP_FOLDER="$PROJECT_ROOT/allure-history"

echo "=== Шаг 1: Запуск тестов через Maven ==="
mvn clean verify

echo "=== Шаг 2: Подготовка истории для Allure ==="
if [ -d "$HISTORY_BACKUP_FOLDER/history" ]; then
    echo "Копируем старую историю в результаты для преемственности трендов..."
    mkdir -p "$ALLURE_RESULTS_FOLDER"
    cp -r "$HISTORY_BACKUP_FOLDER/history" "$ALLURE_RESULTS_FOLDER/"
else
    echo "Предыдущая история не найдена. Начинаем с чистого листа."
fi

echo "=== Шаг 3: Генерация отчета Allure ==="
mvn allure:report

echo "=== Шаг 4: Обновление бэкапа истории ==="
if [ -d "$ALLURE_REPORT_FOLDER/history" ]; then
    echo "Обновляем бэкап истории новыми данными..."
    mkdir -p "$HISTORY_BACKUP_FOLDER"
    cp -r "$ALLURE_REPORT_FOLDER/history" "$HISTORY_BACKUP_FOLDER/"
else
    echo "Новые данные истории не найдены в отчете."
fi

echo "=== Завершено! Отчет доступен в $ALLURE_REPORT_FOLDER ==="
