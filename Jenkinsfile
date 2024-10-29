pipeline {
        environment {
                        RELEASE_VERSION = "0.0.1"
                        registry = "nadaaissaoui/kaddem"
                        registryCredential = 'dockerhub_id'
                        dockerImage = ''
        }
        agent any

    triggers {
        
        pollSCM('H/5 * * * *') 
    }
    stages {

        stage('Checkout') {
            steps {
                git branch: 'nadou', url: 'https://github.com/AnasHidri/5DS6-G1-Kaddem.git'
            }
        }


        stage('Build') {
            steps {
               sh 'mvn clean install -DskipTests'
            }
        }

        stage('MVN Sonarqube') {
       steps {
           sh "mvn sonar:sonar -Dsonar.host.url=http://192.168.1.22:9000 -Dsonar.login=squ_ba3705efb7ebf90d320df79fcfab3367e9322dd2"
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
        /* stage('Deploy with Docker Compose') {
                    steps {
                        script {
                            // Stop existing containers
                            sh 'docker-compose down || true'

                            // Start the applications
                            sh 'docker-compose up -d'


                            sh 'sleep 30'

                            // Verify deployment
                            sh 'docker-compose ps'
                        }
                    }
                } */


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
