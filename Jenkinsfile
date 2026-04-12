pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile'
            additionalBuildArgs  '--build-arg MAVEN_CONFIG=/root/.m2'
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
            echo '=== Публикация Allure отчета в Jenkins (Автоматическая история и тренды) ==='
            script {
                // Имя "allure" должно совпадать с именем в init-allure.groovy
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    // Важно: путь берется от корня воркспейса
                    results: [[path: "target/allure-results"]]
                ])
            }
        }
    }
}
