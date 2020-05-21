pipeline {
    agent any
    tools {
        maven 'Maven_3.5.2' 
    }

    stages {
        stage ('Compile Stage') {

            steps {
                    sh 'mvn clean compile'
            }
        }

        stage ('Testing Stage') {

            steps {
                    sh 'mvn test'
            }
        }


        stage ('Deployment Stage') {
            steps {
                sh 'mvn deploy'
            }
        }
    }
}
