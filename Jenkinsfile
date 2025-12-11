pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/akumaraeis/TimeSheet_Application.git'
            }
        }

        stage('Setup Maven') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Publish TestNG Report') {
            steps {
                publishTestNGResult testResultsPattern: '**/test-output/testng-results.xml'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/test-output/*.html', fingerprint: true
        }
        failure {
            mail to: 'team@example.com',
                 subject: "Build Failed: ${env.JOB_NAME}",
                 body: "Test execution failed. Check Jenkins for details."
        }
    }
}
