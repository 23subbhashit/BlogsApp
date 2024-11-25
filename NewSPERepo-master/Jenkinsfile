pipeline {
    agent any

    environment {
        FRONTEND_IMAGE = 'nikhilguptaiiitb/frontend-app'
        BACKEND_IMAGE = 'nikhilguptaiiitb/backend-app'
        KUBECONFIG = '/var/lib/jenkins/.kube/config'
    }

    stages {
        stage('Clone Repository') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [],
                    submoduleCfg: [],
                    userRemoteConfigs: [[url: 'https://github.com/nikhilgupta221b/NewSPERepo.git']]
                ])
            }
        }

        stage('Docker Cleanup') {
            steps {
                script {
                    // Clean up Docker containers, volumes, and networks to ensure a fresh start
                    sh 'docker container prune -f'
                    sh 'docker volume prune -f'
                    sh 'docker network prune -f'
                    sh 'docker image prune -f'
                }
            }
        }

        stage('Build Frontend Docker Image') {
            steps {
                script {
                    docker.build("$FRONTEND_IMAGE", "./front")
                }
            }
        }

        stage('Build Backend Docker Image') {
            steps {
                script {
                    docker.build("$BACKEND_IMAGE", "./back")
                }
            }
        }

        stage('Push Docker Images to DockerHub') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'DockerhubCred') {
                        docker.image("$FRONTEND_IMAGE").push('latest')
                        docker.image("$BACKEND_IMAGE").push('latest')
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                dir('/var/lib/jenkins/workspace/NewSPEAgain/') {
                    script {
                        // Use kubectl to apply the Kubernetes configurations with verbose output
                        sh "kubectl apply -f k8s/persistent_volume.yaml"
                        sh "kubectl apply -f k8s/mysql-service.yaml"
                        sh "kubectl apply -f k8s/mysql-deployment.yaml"
                        sh "kubectl apply -f k8s/backend-service.yaml"
                        sh "kubectl apply -f k8s/backend-deployment.yaml"
                        sh "kubectl apply -f k8s/frontend-deployment.yaml"
                        sh "kubectl apply -f k8s/frontend-service.yaml"
                        sh "kubectl apply -f k8s/ingress.yaml"
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}

