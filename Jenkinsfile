pipeline {
  environment {
        RELEASE_VERSION = "0.0.1"
        registry = "nourghali/kaddem"
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

        stage('Test') {
                    steps {
                        sh 'mvn test'
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

        /*stage('Test') {
            steps {
                sh 'mvn test'
            }
        }*/

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
            echo '🎉 Build finished successfully!'
            mail to: 'nourghali2001@gmail.com',
                 subject: "✅ Jenkins Job Successful: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
                 body: """Hello Nour,

    Good news! The job '${env.JOB_NAME}' [#${env.BUILD_NUMBER}] has completed successfully.

    Best regards,
    Jenkins 🎉"""
        }
        failure {
            echo '❌ Build failed!'
            mail to: 'nourghali2001@gmail.com',
                 subject: "❗ Jenkins Job Failed: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
                 body: """Hello Nour,

    Unfortunately, the job '${env.JOB_NAME}' [#${env.BUILD_NUMBER}] has failed.

    Please check the Jenkins console output for more details and troubleshooting.

    Best regards,
    Jenkins ❌"""
        }
    }

    }