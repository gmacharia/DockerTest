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

         stage('Building the jar file') {
        
           steps {
            print "Building branch: ${BRANCH}"
            print "Using GIT commitID: ${COMMIT_ID}"
            print "Application name:  ${APPLICATION_NAME}"
            print "Version: ${VERSION}"
            sh "mvn clean package -DskipTests package"
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
