pipeline {
        agent any

        tools {
                // Utilisation de Maven 3.6.3 et JDK 17 configurés dans Jenkins
                maven 'Maven 3.6.3'
                jdk 'JDK 17'
            }

        environment {
                RELEASE_VERSION = "0.0.1"
                registry = "nadou/kaddem"
                registryCredential = 'dockerhub_id'
                dockerImage = ''
            }

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

        stage('MVN Sonarqube') {
            steps {
                sh "mvn sonar:sonar -Dsonar.login=squ_076929bb198ea9de082c55c5eb1af2997a409941"
             }
        }
        stage('Deploy') {
                    steps {
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
