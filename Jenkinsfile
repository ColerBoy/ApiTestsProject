pipeline {
    // Весь пайплайн теперь бежит прямо на мастере Jenkins
    // Где мы уже установили Java, Maven и Allure
    agent any 

    stages {
        stage('Прогон тестов') {
            steps {
                echo '=== Шаг 1: Запуск API тестов (Maven) ==='
                // Просто запускаем команду. Код проекта уже в воркспейсе.
                sh 'chmod +x run-allure.sh && ./run-allure.sh'
            }
        }
    }

    post {
        always {
            echo '=== Шаг 2: Генерация и публикация Allure отчета ==='
            script {
                // Мы используем плагин Allure. Он сам подхватит результаты из target/allure-results
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
}
