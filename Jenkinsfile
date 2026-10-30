pipeline {
    agent any

    options {
        timeout(time: 30, unit: 'MINUTES')
        disableConcurrentBuilds()
    }

    environment {
        STUDENT_NAME = 'Akbota Zhanserikkyzy'
        STUDENT_ID   = '37106'
        IMAGE_NAME   = 'zhanserikkyzy-akbota-student-api'
        IMAGE_TAG    = '1.1-37106'
        IMAGE_REF    = 'zhanserikkyzy-akbota-student-api:1.1-37106'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Student: ${STUDENT_NAME} (ID ${STUDENT_ID}, group IT2-2308)"
                checkout scm
                sh 'git log -1 --oneline'
            }
        }

        stage('Build') {
            steps {
                sh 'java -version'
                sh 'chmod +x gradlew'
                sh './gradlew clean bootJar --no-daemon'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test --no-daemon'
            }
            post {
                always {
                    junit 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t ${IMAGE_REF} .'
            }
        }

        stage('Verify') {
            steps {
                sh "docker image inspect ${IMAGE_REF} --format 'Image: {{.RepoTags}} Size: {{.Size}} bytes Created: {{.Created}}'"
                sh 'docker images ${IMAGE_NAME}'
                echo "Verified: image ${IMAGE_REF} exists (student ${STUDENT_NAME})"
            }
        }
    }

    post {
        success {
            echo "BUILD SUCCESS for ${STUDENT_NAME}"
        }
        failure {
            echo "BUILD FAILED for ${STUDENT_NAME}"
        }
    }
}
