pipeline {
    agent any

    stages {
        stage('GIT') {
            steps {
                echo "Getting Project from Git";
                checkout scm
            }
        }

        stage('Maven Clean') {
                    steps {
                        echo "Running Maven Clean"
                        sh 'mvn clean'
                    }
                }

                stage('Maven Compile') {
                    steps {
                        echo "Compiling the project with Maven"
                        sh 'mvn compile'
                    }
                }

        stage('MVN SONARQUBE') {
            steps {
                script {
                    // Provide your SonarQube server URL and credentials
                    withSonarQubeEnv('sonarqube') { // This assumes you have a SonarQube server configured in Jenkins
                        sh 'mvn sonar:sonar -Dsonar.login=squ_6557b3271174c410170f1d59869d5e7e5cd49a99' // Replace with your actual token
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                        error "Pipeline failed due to quality gate failure: ${qg.status}"
                    }
                }
            }
        }
    }
}
