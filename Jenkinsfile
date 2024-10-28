pipeline {
    agent any

    triggers {
        
        pollSCM('H/5 * * * *') 
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/AnasHidri/5DS6-G1-Kaddem.git',branch:'Si-wajdi'
                
            }
        }//compile
        //test nvm sonar:sonar config -port
        stage('Nettoyage du projet avec Maven') {
            steps {
                cleanWs()
                sh 'mvn clean'
            }
        }

        stage('Compilation du projet avec Maven') {
            steps {
                sh 'mvn compile'
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
                sh 'mvn deploy -Durl=http://192.168.1.50/repository/maven-releases/ -DskipTests=true'
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
