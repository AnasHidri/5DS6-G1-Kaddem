pipeline {
    agent any

    triggers {
        
        pollSCM('H/5 * * * *') 
    }
    stages {
        stage('Checkout') {
            steps {
git branch: 'skander', url: 'https://github.com/AnasHidri/5DS6-G1-Kaddem.git'            }
        }//compile
        //test nvm sonar:sonar config -port


        stage('Build') {
            steps {
               sh 'mvn clean install -DskipTests'
            }
        }

        stage('MVN Sonarqube') {
            steps {
                sh "mvn sonar:sonar -Dsonar.login=squ_fa40c2d1d06efaab4703e54ed1250be63c41e7bf"
        }
        }


        /*stage('Test') {
            steps {
                sh 'mvn test'
            }
        }*/
    
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
