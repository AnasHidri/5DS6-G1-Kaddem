pipeline {
  environment {
        RELEASE_VERSION = "0.0.1"
        registry = "nourghali/kaddem"
        registryCredential = 'dockerhub_id'
        dockerImage = ''
    agent any

    triggers {
        
        pollSCM('H/5 * * * *') 
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'Nour', url: 'https://github.com/AnasHidri/5DS6-G1-Kaddem.git'
            }
        }//compile
        //test nvm sonar:sonar config -port


        stage('Build') {
            steps {
               sh 'mvn clean install -DskipTests'
            }
        }

        stage('MVN Sonarqube') {
            steps {
                sh "mvn sonar:sonar -Dsonar.login=squ_93579bd9fc8bb68995c067e8a7a60400edfed1ab"
        }
        }
        stage('Deploy') {
                    steps {
                        // Commande pour déployer avec l'option de skipper les tests
                        sh 'mvn deploy -DskipTests=true'
                    }
                }


  stage('Building our image') {
            steps {
                script {
                    dockerImage = docker.build "${registry}:${RELEASE_VERSION}"

                }
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