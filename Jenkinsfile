pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run API tests + Allure') {
            steps {
                sh 'pwsh -File ./run-allure.ps1 -SkipServe -Ci'
            }
            post {
                always {
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                    archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
                    archiveArtifacts artifacts: 'target/site/allure-maven-plugin/**', allowEmptyArchive: true
                    archiveArtifacts artifacts: 'allure-history/**', allowEmptyArchive: true
                }
            }
        }
    }

    post {
        always {
            echo 'Allure-логика переиспользует run-allure.ps1 (CI режим: -SkipServe -Ci).'
        }
    }
}
