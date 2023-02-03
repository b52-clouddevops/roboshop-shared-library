def lintChecks(COMPONENT) {
        sh "echo Installing JSLINT"
        sh "npm install jslint"
        sh "ls -ltr node_modules/jslint/bin/"
        // sh "./node_modules/jslint/bin/jslint.js server.js"
        sh "echo lint checks completed for ${COMPONENT}.....!!!!!"
}

def sonarChecks(COMPONENT) {   
       sh  "echo Starting Code Quality Analysis"
       sh  "sonar-scanner -Dsonar.host.url=http://${SONAR_URL}:9000  -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT}  -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}"
       sh  "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gata.sh"
       sh  "bash -x quality-gata.sh ${SONAR_USR} ${SONAR_PSW} ${SONAR_URL} ${COMPONENT}"
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
                        sonarChecks(COMPONENT)                    
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


// Jenkins WS Should be operated with t3.medium for the jobs to be processed quickly.
// Outstanding from my end.
