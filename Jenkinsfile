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
      SHORT_COMMIT_ID = "${COMMIT_ID[0..5]}"
      DOCKER_AMD_BASE_IMAGE = readMavenPom().getProperties().get('docker.image.amd.base')
      DOCKER_REPOSITORY_NAME = readMavenPom().getProperties().getProperty('docker.image.repository')
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


       // build the docker images
        stage('Build docker image') {          
           steps {
            print "Building docker image."
            print "Base image: ${DOCKER_AMD_BASE_IMAGE}."
            print "Image architecture: ${ARCHITECTURE}."
            container('docker') {
                // docker build command
                sh("docker build --build-arg BASE_IMAGE=${DOCKER_AMD_BASE_IMAGE} " +
                        " --build-arg APPLICATION_NAME=${APPLICATION_NAME}-${VERSION} " +
                        " -t ${DOCKER_REPOSITORY_NAME}/${APPLICATION_NAME}:v${VERSION}-${SHORT_COMMIT_ID}-${ARCHITECTURE} .")
            }
          }
        }

        // push image to docker hub repo
        stage('Push image to the repository') {
         steps {
            print "Pushing image into docker repository"
            print "Image name: cellulant/${APPLICATION_NAME}:v${VERSION}-${SHORT_COMMIT_ID}-${ARCHITECTURE}"

            container('docker') {
                withCredentials([[$class          : 'UsernamePasswordMultiBinding',
                                  credentialsId   : '1747d824-3e68-4669-91e5-fc0933824aa8',
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