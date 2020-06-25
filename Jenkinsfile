def label = "worker-${UUID.randomUUID().toString()}"
def ARCHITECTURE = ""

// define the pod templates
podTemplate(label: label, containers: [
        containerTemplate(name: 'maven', image: 'maven:3.6.1-jdk-11-slim', command: 'cat', ttyEnabled: true),
        containerTemplate(name: 'docker', image: 'docker:19.03', command: 'cat', ttyEnabled: true),
],
        volumes: [
                hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
        ]) {
    node(label) {
        //checkout the code
        repository = checkout scm

        // source control variables
        ARCHITECTURE = "amd64"
        def COMMIT_ID = repository.GIT_COMMIT
        def BRANCH = repository.GIT_BRANCH
        def SHORT_COMMIT_ID = "${COMMIT_ID[0..5]}"

        //get instance of a pom file to get properties
        def pom = readMavenPom()

        //extract image properties
        def DOCKER_AMD_BASE_IMAGE = pom.properties['docker.image.amd.base']
        def DOCKER_REPOSITORY_NAME = pom.properties['docker.image.repository']

        // fetch version and application name
        def VERSION = readMavenPom().getVersion()
        def APPLICATION_NAME = readMavenPom().getArtifactId()

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
}
