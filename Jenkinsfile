pipeline {
    agent any

    stages {
        stage('Build & Test') {
            steps {
                sh 'docker build -t freemoz-unittest -f ./Dockerfile.unittest .'
            }
        }
    }
}
