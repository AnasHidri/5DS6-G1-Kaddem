pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/AnasHidri/5DS6-G1-Kaddem.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

    
    }

    post {
        success {
            echo 'Build finished successfully!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
