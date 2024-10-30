pipeline {
        environment {
        RELEASE_VERSION = "0.0.1"
        registry = "skander/kaddem"
        registryCredential = 'dockerhub_id'
        dockerImage = ''
    }
    agent any

    triggers {
        
        pollSCM('H/5 * * * *') 
    }
    stages {
        stage('Checkout') {
            steps {checkout([                     $class: 'GitSCM',                     branches: [[name: '*/skander']],
                    // Remplacez 'main' par la branche correcte
                      userRemoteConfigs: [[url: 'https://github.com/AnasHidri/5DS6-G1-Kaddem.git']]                 ])

        }}



        stage('Build') {
            steps {
               sh 'mvn clean install -DskipTests'
            }
        }

        stage('MVN Sonarqube') {
            steps {
                sh "mvn sonar:sonar -Dsonar.login=squ_72b9110e30030af808658c5223d0da89409e21cd"
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

