def lintChecks(COMPONENT) {
        sh "echo Installing mvn"
        // sh "yum install maven -y"
        // sh "mvn checkstyle:check"
        sh "echo lint checks completed for ${COMPONENT}.....!!!!!"
}

def call(COMPONENT)                                              // call is the default function that's called by default.
{
    pipeline {
        agent any 
        environment {
            SONAR = credentials('SONAR')
            SONAR_URL = "172.31.1.207"
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
                        sh "mvn clean compile"
                        env.ARGS="-Dsonar.java.binaries=target/"
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

            stage('Downloading the dependencies') {
                when { 
                    expression { env.TAG_NAME != null } 
                    }
                steps {
                    sh "mvn clean package"
                }
            }

            stage('Uploading the artifact') {
                when { 
                    expression { env.TAG_NAME != null } 
                    }
                steps {
                    sh "echo uploading artifact to nexus"
                }
            }
        } // End of Stages
    }
}