pipeline {
    agent any

    triggers {
        
        pollSCM('H/5 * * * *') 
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/AnasHidri/5DS6-G1-Kaddem.git'
            }
        }

        stage('Build') {
            steps {
               sh 'mvn clean install -DskipTests'
            }
        }

        /*stage('Test') {
            steps {
                sh 'mvn test'
            }
        }*/

        stage('Show Date') {
            steps {
                script {
                    def currentDate = new Date()
                    echo "Current Date and Time: ${currentDate}"
                }
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
