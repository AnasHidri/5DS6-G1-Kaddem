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
            emailext(
                subject: "✅ Pipeline succeeded: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    <h2 style="color: green;">✔️ Pipeline Successful</h2>
                    <p>Le pipeline <strong>${env.JOB_NAME}</strong> a été exécuté avec succès.</p>
                    <table style="border: 1px solid #ddd; border-collapse: collapse;">
                        <tr><td style="padding: 8px; border: 1px solid #ddd;"><strong>Job</strong></td><td style="padding: 8px; border: 1px solid #ddd;">${env.JOB_NAME}</td></tr>
                        <tr><td style="padding: 8px; border: 1px solid #ddd;"><strong>Build</strong></td><td style="padding: 8px; border: 1px solid #ddd;">#${env.BUILD_NUMBER}</td></tr>
                        <tr><td style="padding: 8px; border: 1px solid #ddd;"><strong>URL</strong></td><td style="padding: 8px; border: 1px solid #ddd;"><a href="${env.BUILD_URL}">${env.BUILD_URL}</a></td></tr>
                    </table>
                    <p style="color: #555;">Merci et félicitations !</p>
                """,
                mimeType: 'text/html',
                to: 'nour.elghali@esprit.tn' // Remplace le Provider par l'email direct
            )
        }
        failure {
            emailext(
                subject: "❌ Pipeline failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    <h2 style="color: red;">❌ Pipeline Failure</h2>
                    <p>Le pipeline <strong>${env.JOB_NAME}</strong> a échoué.</p>
                    <table style="border: 1px solid #ddd; border-collapse: collapse;">
                        <tr><td style="padding: 8px; border: 1px solid #ddd;"><strong>Job</strong></td><td style="padding: 8px; border: 1px solid #ddd;">${env.JOB_NAME}</td></tr>
                        <tr><td style="padding: 8px; border: 1px solid #ddd;"><strong>Build</strong></td><td style="padding: 8px; border: 1px solid #ddd;">#${env.BUILD_NUMBER}</td></tr>
                        <tr><td style="padding: 8px; border: 1px solid #ddd;"><strong>URL</strong></td><td style="padding: 8px; border: 1px solid #ddd;"><a href="${env.BUILD_URL}">${env.BUILD_URL}</a></td></tr>
                    </table>
                    <p style="color: #555;">Merci de vérifier les logs et résoudre les erreurs.</p>
                """,
                mimeType: 'text/html',
                to: 'nour.elghali@esprit.tn' // Remplace le Provider par l'email direct
            )
        }
    }

    }