def lintChecks() {
        sh "echo Installing JSLINT"
        sh "npm install jslint"
        sh "ls -ltr node_modules/jslint/bin/"
        // sh "./node_modules/jslint/bin/jslint.js server.js"
        sh "echo lint checks completed.....!!!!!"
}

def call()                                              // call is the default function that's called by default.
{
    pipeline {
        agent any 
        stages {                                        // Start of Stages
            stage('Lint Checks') {
                steps {
                    script {
                        lintChecks()                    // If the function is in the same file, no need to call the function with the fileName as prefix.
                    }
                }
            }
            stage('Downloading the dependencies') {
                steps {
                    sh "npm install"
                }
            }
        } // End of Stages
    }
}