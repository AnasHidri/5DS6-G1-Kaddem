pipeline {
    environment {
        RELEASE_VERSION = "0.0.7"
        registry = "wajdiouaili/kaddem"
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
                git branch: 'Si-wajdi', url: 'https://github.com/AnasHidri/5DS6-G1-Kaddem.git'
                
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
                sh "mvn sonar:sonar -Dsonar.login=squ_1fbee3959d7b0a7daed827080bcde342dc6aef54"
        }
        }
        stage('Deploy') {
            steps {
                // Commande pour déployer avec l'option de skipper les tests
                sh 'mvn deploy  -DskipTests=true'
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
            echo 'Build finished successfully!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
