pipeline {
    agent {
        // Используем ваш Dockerfile для запуска тестов в изолированном контейнере
        dockerfile {
            filename 'Dockerfile'
            // Пробрасываем кэш Maven, чтобы тесты запускались быстро
            additionalBuildArgs  '--build-arg MAVEN_CONFIG=/root/.m2'
            args '-v maven_repo:/root/.m2'
        }
    }

    environment {
        // Указываем путь к результатам Allure (совпадает с pom.xml)
        ALLURE_RESULTS = 'target/allure-results'
    }

    stages {
        stage('Подготовка') {
            steps {
                echo '=== Даем права на выполнение скрипту ==='
                sh 'chmod +x run-allure.sh'
            }
        }

        stage('Прогон тестов и генерация Allure') {
            steps {
                echo '=== Запуск тестов и обработка истории (аналог вашего .ps1) ==='
                // Запускаем наш адаптированный bash-скрипт
                sh './run-allure.sh'
            }
        }
    }

    post {
        always {
            echo '=== Публикация Allure отчета в Jenkins ==='
            // Этот шаг требует установленного плагина "Allure Jenkins Plugin"
            // Он автоматически подхватит историю и тренды
            script {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: "${env.ALLURE_RESULTS}"]]
                ])
            }
        }
        
        success {
            echo '=== Тесты пройдены успешно! ==='
        }
        
        failure {
            echo '=== Есть упавшие тесты или ошибка сборки! ==='
        }
    }
}
