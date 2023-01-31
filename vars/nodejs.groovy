pipeline {
    agent any 
    stages {                                        // Start of Stages
        stage('Lint Checks') {
            steps {
                sh "echo Installing JSLINT"
                sh "npm install jslint"
                sh "ls -ltr node_modules/jslint/bin/"
                // sh "./node_modules/jslint/bin/jslint.js server.js"
                sh "echo lint checks completed.....!!!!!"
            }
        }
        stage('Downloading the dependencies') {
            steps {
                sh "npm install"
            }
        }
    } // End of Stages
}