def lintChecks(COMPONENT) {
        sh "echo Installing JSLINT"
        sh "npm install jslint"
        sh "ls -ltr node_modules/jslint/bin/"
        // sh "./node_modules/jslint/bin/jslint.js server.js"
        sh "echo lint checks completed for ${COMPONENT}.....!!!!!"
}

def call(COMPONENT)                                              // call is the default function that's called by default.
{
    pipeline {
        agent any 
        environment {
            SONAR = credentials('SONAR')
            NEXUS = credentials('NEXUS')
            SONAR_URL = "172.31.1.207"
            NEXUS_URL = "172.31.4.26"
        }
        stages {                                        // Start of Stages
            stage('Lint Checks') {
                steps {
                    script {
                        lintChecks(COMPONENT)                    // If the function is in the same file, no need to call the function with the fileName as prefix.
                    }
                }
            }

            stage('Sonar Checks') {
                steps {
                    script {
                        env.ARGS="-Dsonar.sources=."
                        common.sonarChecks(COMPONENT)                    
                    }
                }
            }

            stage('Test Cases') {
                    parallel {
                        stage('Unit Tests'){
                            steps {
                                sh "echo Unit Testing ......."
                            }
                        }

                        stage('Integration Tests'){
                            steps {
                                sh "echo Integration Testing ......."
                            }
                        }

                        stage('Functional Tests'){
                            steps {
                                sh "echo Functional Testing ......."
                            }
                        }
                    }
            }

            stage('Artifact Validation On Nexus') {
                steps {
                    sh "echo checking whether artifact exists of not. If it doesnt exist then only proceed with Preparation and Upload"
                    script {
                         env.STATUS_CODE=sh(returnStdout: true, script: 'curl -L -s http://172.31.4.26:8081/service/rest/${COMPONENT}/browse/${COMPONENT} | grep ${COMPONENT}-${TAG_NAME}.zip')
                         print STATUS_CODE
                    }
                }
            }

            stage('Preparing the artifact') {
                when { 
                    expression { env.TAG_NAME != null } 
                    }
                steps {
                    sh "npm install"
                    sh "zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js"
                    sh "ls -ltr"
                }
            }
            stage('Uploading the artifact') {
                when { 
                    expression { env.TAG_NAME != null } 
                    }
                steps {
                    sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
                }
            }
        } // End of Stages
    }
}


// Jenkins WS Should be operated with t3.medium for the jobs to be processed quickly.
// Outstanding from my end.
