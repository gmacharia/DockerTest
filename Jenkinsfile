pipeline {
    agent any

    stages {
        stage ('Compile Stage') {

            steps {
               container('maven') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage ('Testing Stage') {

            steps {
                container('maven') {
                    sh 'mvn test'
                }
            }
        }


        stage ('Deployment Stage') {
            steps {
                container('maven') {
                    sh 'mvn deploy'
                }
            }
        }
    }
}
