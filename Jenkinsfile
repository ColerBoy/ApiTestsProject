pipeline {
    // Весь пайплайн будет бежать в вашем Docker-контейнере с тестами
    agent {
        dockerfile {
            filename 'Dockerfile'
            // Кэшируем Maven, чтобы тесты не качали пол-интернета каждый раз
            args '-v maven_repo:/root/.m2'
        }
    }

    stages {
        stage('Прогон тестов') {
            steps {
                echo '=== Запуск тестов через скрипт ==='
                sh 'chmod +x run-allure.sh && ./run-allure.sh'
            }
        }
    }

    post {
        always {
            echo '=== Публикация Allure отчета ==='
            // Ссылка на отчет (будет видна в сайдбаре джобы)
            allure includeProperties: false, results: [[path: 'target/allure-results']]
        }
    }
}
