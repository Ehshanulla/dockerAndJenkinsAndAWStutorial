pipeline {
    agent any

    environment {
        DOCKERHUB_USER = "ehshanulla"
        IMAGE_NAME = "my-spring-boot"
        VERSION = "v1-${env.BUILD_NUMBER}"
        FULL_IMAGE = "${env.DOCKERHUB_USER}/${env.IMAGE_NAME}:${env.VERSION}"
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
                    // Build your Spring Boot jar
                    bat "mvn clean package -DskipTests"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('SpringBootDockeDemo') {
                    // Build Docker image and tag latest
                    bat "docker build -t ${env.FULL_IMAGE} ."
                    bat "docker tag ${env.FULL_IMAGE} ${env.DOCKERHUB_USER}/${env.IMAGE_NAME}:latest"
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
                    // Login and push Docker image
                    bat """
                    echo %PASS% | docker login -u %USER% --password-stdin
                    docker push ${env.FULL_IMAGE}
                    docker push ${env.DOCKERHUB_USER}/${env.IMAGE_NAME}:latest
                    docker logout
                    """
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully! Docker image: ${env.FULL_IMAGE}"
        }
        failure {
            echo "Pipeline failed. Check the logs for errors."
        }
    }
}
