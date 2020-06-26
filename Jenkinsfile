pipeline {
    agent any
    tools {
        maven 'Maven_3.5.2' 
    }

   environment {

     // fetch version and application name
      VERSION = readMavenPom().getVersion()
      APPLICATION_NAME = readMavenPom().getArtifactId()

      ARCHITECTURE = "amd64";
      DOCKER_AMD_BASE_IMAGE = readMavenPom().getProperties().get('docker.image.amd.base')
      DOCKER_REPOSITORY_NAME = readMavenPom().getProperties().getProperty('docker.image.repository')
   }

    stages {

         checkout scm

            script {
                COMMIT_ID = GIT_COMMIT
                BRANCH = GIT_BRANCH
                SHORT_COMMIT_ID = "${COMMIT_ID[0..5]}"

            }

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


       // build the docker images
        stage('Build docker image') {          
           steps {
            print "Building docker image."
            print "Base image: ${DOCKER_AMD_BASE_IMAGE}."
            print "Image architecture: ${ARCHITECTURE}."
                // docker build command
                sh("docker build --build-arg BASE_IMAGE=${DOCKER_AMD_BASE_IMAGE} " +
                        " --build-arg APPLICATION_NAME=${APPLICATION_NAME}-${VERSION} " +
                        " -t ${DOCKER_REPOSITORY_NAME}/${APPLICATION_NAME}:v${VERSION}-${SHORT_COMMIT_ID}-${ARCHITECTURE} .")
          }
        }

        // push image to docker hub repo
        stage('Push image to the repository') {
         steps {

            print "Pushing image into docker repository"
            print "Image name: cellulant/${APPLICATION_NAME}:v${VERSION}-${SHORT_COMMIT_ID}-${ARCHITECTURE}"

            container('docker') {
                withCredentials([[$class          : 'UsernamePasswordMultiBinding',
                                  credentialsId   : 'b4c0bd02-cd21-4bc8-9e8b-e6a30b0ad5ae',
                                  usernameVariable: 'DOCKER_HUB_USER',
                                  passwordVariable: 'DOCKER_HUB_PASSWORD']]) {
                    sh """
                        docker login -u ${DOCKER_HUB_USER} -p ${DOCKER_HUB_PASSWORD}
                        docker push cellulant/${APPLICATION_NAME}:v${VERSION}-${SHORT_COMMIT_ID}-${ARCHITECTURE}
                        """
                }
            }
          }
        }
    }
}