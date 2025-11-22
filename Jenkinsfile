pipeline {
    agent any

    environment {
        DOCKERHUB_USER = "ehshanulla"
        IMAGE_NAME = "my-spring-boot"
        VERSION = "v1-${env.BUILD_NUMBER}"
        FULL_IMAGE = "%DOCKERHUB_USER%/%IMAGE_NAME%:%VERSION%"
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
                    bat "mvn clean package -DskipTests"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('SpringBootDockeDemo') {
                    bat """
                    docker build -t %FULL_IMAGE% .
                    docker tag %FULL_IMAGE% %DOCKERHUB_USER%/%IMAGE_NAME%:latest
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
                    bat """
                    echo %PASS% | docker login -u %USER% --password-stdin
                    docker push %FULL_IMAGE%
                    docker push %DOCKERHUB_USER%/%IMAGE_NAME%:latest
                    docker logout
                    """
                }
            }
        }
    }
}
