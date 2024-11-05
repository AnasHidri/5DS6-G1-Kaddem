pipeline {
        environment {
        RELEASE_VERSION = "0.0.1"
        registry = "skander/kaddem"
        registryCredential = 'dockerhub_id'
        dockerImage = ''
        SONAR_HOST_URL = 'http://192.168.0.14:9000/'

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

                stage('Test') {
                    steps {
                        sh 'mvn test'
                    }
                }

        stage('MVN Sonarqube') {
            steps {

                sh 'mvn test jacoco:report'
                sh "mvn sonar:sonar -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=squ_51ab882c903d276337dc9d5b0b32bd6de2e7046b"
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

         stage('Deploy with Docker Compose') {
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
                }


                   stage('Grafana') {
                                steps {
                                    script {
                                       sh 'docker start prometheus'
                                       sh 'docker start grafana'
                                    }
                                }


                        }



    
    }

   post {
        success {
            echo 'Build finished successfully!'
            mail to: 'skandergharbi.contact@gmail.com',
                 subject: "Jenkins Job Successful: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
                 body: "Good news Nadou! The job ${env.JOB_NAME} [${env.BUILD_NUMBER}] has finished successfully."
        }
        failure {
            echo 'Build failed!'
            mail to: 'skandergharbi.contact@gmail.com',
                 subject: "Jenkins Job Failed: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
                 body: "OOPS it's (skander's or anas's )fault ! Sorry Nadou, the job ${env.JOB_NAME} [${env.BUILD_NUMBER}] has failed. Please check the Jenkins console output for details."
        }
    }
}

