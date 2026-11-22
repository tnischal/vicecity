pipeline {
    agent any

    tools {
        maven 'MAVEN'
        jdk 'JDK'
    }

    environment {
        IMAGE_NAME     = 'vicecity-app'
        IMAGE_TAG      = "${env.BUILD_NUMBER}"
        CONTAINER_NAME = 'vicecity'
        HOST_PORT      = '8081'          // Jenkins already owns 8080
        SMOKE_URL      = "http://host.docker.internal:8081"
    }

    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Unit Test') {
            steps {
                sh 'mvn -B clean verify'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Checkstyle') {
            steps {
                sh 'mvn -B checkstyle:checkstyle'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                scannerHome = tool 'SONAR'
            }
            steps {
                withSonarQubeEnv('sonarcodeserver') {
                    sh '''
                        ${scannerHome}/bin/sonar-scanner \
                          -Dsonar.host.url=${SONAR_HOST_URL} \
                          -Dsonar.token=${SONAR_AUTH_TOKEN}
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        // Dependency, secret and config scan of the source tree (needs no Docker)
        stage('Trivy FS Scan') {
            steps {
                sh '''
                    trivy fs . \
                      --scanners vuln,secret,misconfig \
                      --severity HIGH,CRITICAL \
                      --ignore-unfixed \
                      --exit-code 1 \
                      --skip-dirs target
                '''
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} -t ${IMAGE_NAME}:latest .'
            }
        }

        stage('Trivy Image Scan') {
            steps {
                sh '''
                    trivy image \
                      --severity HIGH,CRITICAL \
                      --ignore-unfixed \
                      --exit-code 1 \
                      ${IMAGE_NAME}:${IMAGE_TAG}
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    docker rm -f ${CONTAINER_NAME} || true
                    docker run -d --name ${CONTAINER_NAME} \
                      --restart unless-stopped \
                      -p ${HOST_PORT}:8080 \
                      ${IMAGE_NAME}:${IMAGE_TAG}
                '''
            }
        }

        stage('Smoke Test') {
            steps {
                sh '''
                    for i in $(seq 1 20); do
                      if curl -sf ${SMOKE_URL}/actuator/health | grep -q '"status":"UP"'; then
                        echo "App is healthy"
                        curl -sf ${SMOKE_URL}/ | grep -q "VICE CITY NIGHTS"
                        exit 0
                      fi
                      echo "Waiting for app... ($i/20)"
                      sleep 3
                    done
                    echo "App did not become healthy"
                    docker logs ${CONTAINER_NAME} || true
                    exit 1
                '''
            }
        }
    }

    post {
        success {
            echo "Deployed ${IMAGE_NAME}:${IMAGE_TAG} -> http://localhost:${HOST_PORT}"
        }
        failure {
            echo 'Pipeline failed - check the stage logs above.'
        }
    }
}
