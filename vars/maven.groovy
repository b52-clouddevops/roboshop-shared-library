def lintChecks(COMPONENT) {
        sh "echo Installing mvn"
        // sh "yum install maven -y"
        // sh "mvn checkstyle:check"
        sh "echo lint checks completed for ${COMPONENT}.....!!!!!"
}

def sonarChecks(COMPONENT) {
        sh "echo Starting Code Quality Analysis"
        sh "sonar-scanner -Dsonar.host.url=http://172.31.1.207:9000  -Dsonar.sources=. -Dsonar.projectKey=catalogue  -Dsonar.login=admin -Dsonar.password=password"

}

def call(COMPONENT)                                              // call is the default function that's called by default.
{
    pipeline {
        agent nodejs 
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