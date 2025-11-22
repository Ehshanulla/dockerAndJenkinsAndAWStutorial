pipeline {
    agent any

    environment {
        DOCKERHUB_USER = "ehshanulla"
        IMAGE_NAME = "my-spring-boot"
        VERSION = "v1-${env.BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                dir('SpringBootDockeDemo') {
                    sh "mvn clean package -DskipTests"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('SpringBootDockeDemo') {
                    sh """
                    docker build -t ${DOCKERHUB_USER}/${IMAGE_NAME}:${VERSION} .
                    docker tag ${DOCKERHUB_USER}/${IMAGE_NAME}:${VERSION} ${DOCKERHUB_USER}/${IMAGE_NAME}:latest
                    """
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-creds',
                    usernameVariable: 'USER',
                    passwordVariable: 'PASS'
                )]) {
                    sh """
                    echo "$PASS" | docker login -u "$USER" --password-stdin
                    docker push ${DOCKERHUB_USER}/${IMAGE_NAME}:${VERSION}
                    docker push ${DOCKERHUB_USER}/${IMAGE_NAME}:latest
                    docker logout
                    """
                }
            }
        }
    }
}
