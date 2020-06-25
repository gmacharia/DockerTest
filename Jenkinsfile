pipeline {
    agent any
    tools {
        maven 'Maven_3.5.2' 
    }

   environment {
     //get instance of a pom file to get properties
     def pom = readMavenPom()

     // fetch version and application name
     def VERSION = readMavenPom().getVersion()
     def APPLICATION_NAME = readMavenPom().getArtifactId()
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