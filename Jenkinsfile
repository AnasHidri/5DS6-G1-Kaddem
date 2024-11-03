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
//        githubPush()

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


        stage('Test') {
                           steps {
                               sh 'mvn test'
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
            mail to: 'nada.aissaoui@gmail.com',
                 subject: "Jenkins Job Successful: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
                 body: "Good news Nadou! The job ${env.JOB_NAME} [${env.BUILD_NUMBER}] has finished successfully."
        }
        failure {
            echo 'Build failed!'
            mail to: 'nada.aissaoui@gmail.com',
                 subject: "Jenkins Job Failed: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
                 body: "OOPS it's (skander's or anas's )fault ! Sorry Nadou ,the job ${env.JOB_NAME} [${env.BUILD_NUMBER}] has failed. Please check the Jenkins console output for details."
        }
    }

}
