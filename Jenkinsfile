pipeline {
    agent any
    tools {
        maven 'Maven_3.5.2' 
    }

   environment {

     // fetch version and application name
      VERSION = readMavenPom().getVersion()
      APPLICATION_NAME = readMavenPom().getArtifactId()
    }

    stages {
        stage ('Compile Stage') {

            steps {
                    sh 'mvn clean compile'
            }
        }

         stage('Building the jar file') {
        
           steps {
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