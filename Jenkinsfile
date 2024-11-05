pipeline {
        environment {
        RELEASE_VERSION = "0.0.1"
        registry = "anashidri/kaddem"
        registryCredential = 'dockerhub_id'
        dockerImage = ''
    }
    agent any

    triggers {
        githubPush() 
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/AnasHidri/5DS6-G1-Kaddem.git'
            }
        }//compile
        //test nvm sonar:sonar config -port


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
                sh "mvn sonar:sonar -Dsonar.login=squ_178b6ec7175e6bf53c9b0d1c03fa8a2086e5e2f2 " 
        }
        }

   
      /*      stage('Deploy') {
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
        }*/
    
    }

  post {
        success {
            echo 'Build finished successfully!'
            mail to: 'anashidri36@gmail.com',
                 subject: "Jenkins Job Successful: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
                 body: "Good news Si Anas! The job ${env.JOB_NAME} [${env.BUILD_NUMBER}] has finished successfully."
        }
        failure {
            echo 'Build failed!'
            mail to: 'anashidri36@gmail.com',
                 subject: "Jenkins Job Failed: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
                 body: "Sorry Si anas ,the job ${env.JOB_NAME} [${env.BUILD_NUMBER}] has failed. Please check the Jenkins console output for details."
        }
    }
}
