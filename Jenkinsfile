pipeline {
    agent none // Не привязываемся к агенту глобально

    stages {
        stage('Прогон тестов') {
            agent {
                // Тесты бегут в вашем Docker-контейнере
                dockerfile {
                    filename 'Dockerfile'
                    additionalBuildArgs  '--build-arg MAVEN_CONFIG=/root/.m2'
                    args '-v maven_repo:/root/.m2'
                }
            }
            steps {
                echo '=== Запуск тестов в изолированном Docker-контейнере ==='
                sh 'chmod +x run-allure.sh && ./run-allure.sh'
            }
        }
    }

    post {
        always {
            // Публикация отчета происходит на мастере Jenkins, где установлен Allure CLI
            // В новых версиях Jenkins мастер называется 'built-in'
            node('built-in') { 
                echo '=== Публикация Allure отчета на мастере ==='
                allure([
                    commandline: 'allure',
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: "target/allure-results"]]
                ])
            }
        }
    }
}
