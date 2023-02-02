def lintChecks(COMPONENT) {
        sh "echo Installing mvn"
        // sh "yum install maven -y"
        // sh "mvn checkstyle:check"
        sh "echo lint checks completed for ${COMPONENT}.....!!!!!"
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



            stage('Downloading the dependencies') {
                steps {
                    sh "npm install"
                }
            }
        } // End of Stages
    }
}