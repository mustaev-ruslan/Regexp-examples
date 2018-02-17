pipeline {
    agent any
    tools {
        gradle 'Gradle 4.5'
    }
    stages {
        stage('Git') {
            steps {
                git 'https://github.com/mustaev-ruslan/Regexp-examples.git'
            }
        }
        stage('Clean') {
            steps {
                bat(/gradle clean/)
            }
        }
        stage('Assemble') {
            steps {
                bat(/gradle assemble/)
            }
        }
        stage('Test') {
            steps {
                bat(/gradle test/)
            }
        }
        stage('Archive jar') {
            steps {
                archiveArtifacts 'build/libs/*.jar'
            }
        }
    }
    post {
        always {
            junit 'build/test-results/test/*.xml'
            publishHTML([
                    allowMissing         : false,
                    alwaysLinkToLastBuild: true,
                    keepAll              : false,
                    reportDir            : 'build/reports/tests/test/',
                    reportFiles          : 'index.html',
                    reportName           : 'HTML Report',
                    reportTitles         : 'Regexp tests'
            ])
        }
    }
}