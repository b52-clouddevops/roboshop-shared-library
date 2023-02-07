def call() {
    node {
        git branch: 'main', url: "https://github.com/b52-clouddevops/${COMPONENT}.git"
        env.APP = "python"
        common.lintChecks()
        env.ARGS="-Dsonar.sources=."
        common.sonarChecks()  
        common.testCases() 
        common.artifacts()
    }
}

// def call(COMPONENT)                                              // call is the default function that's called by default.
// {
//     pipeline {
//         agent any 
//         environment {
//             SONAR = credentials('SONAR')
//             NEXUS = credentials('NEXUS')
//             SONAR_URL = "172.31.1.207"
//             NEXUS_URL = "172.31.4.26"
//         }
//         stages {                                        // Start of Stages
//             stage('Lint Checks') {
//                 steps {
//                     script {
//                         lintChecks(COMPONENT)                    // If the function is in the same file, no need to call the function with the fileName as prefix.
//                     }
//                 }
//             }

//             stage('Sonar Checks') {
//                 steps {
//                     script {
//                         env.ARGS="-Dsonar.sources=."
//                         common.sonarChecks(COMPONENT)                    
//                     }
//                 }
//             }

//             stage('Test Cases') {
//                     parallel {
//                         stage('Unit Tests'){
//                             steps {
//                                 sh "echo Unit Testing ......."
//                             }
//                         }

//                         stage('Integration Tests'){
//                             steps {
//                                 sh "echo Integration Testing ......."
//                             }
//                         }

//                         stage('Functional Tests'){
//                             steps {
//                                 sh "echo Functional Testing ......."
//                             }
//                         }
//                     }
//             }

//             stage('Artifact Validation On Nexus') {
//                 when { 
//                     expression { env.TAG_NAME != null } 
//                     }
//                 steps {
//                     sh "echo checking whether artifact exists of not. If it doesnt exist then only proceed with Preparation and Upload"
//                     script {
//                         env.UPLOAD_STATUS=sh(returnStdout: true, script: "curl -L -s http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT} | grep ${COMPONENT}-${TAG_NAME}.zip || true" )
//                         print UPLOAD_STATUS
//                     }
//                 }
//             }

//             stage('Preparing the artifact') {
//                 when { 
//                     expression { env.TAG_NAME != null } 
//                     expression { env.UPLOAD_STATUS == "" }
//                     }
//                 steps {

//                     sh "zip -r ${COMPONENT}-${TAG_NAME}.zip *.py *.ini requirements.txt"
//                     sh "ls -ltr"
//                 }
//             }
//             stage('Uploading the artifact') {
//                 when { 
//                     expression { env.TAG_NAME != null } 
//                     expression { env.UPLOAD_STATUS == "" }
//                     }
//                 steps {
//                     sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
//                 }
//             }
//         } // End of Stages
//     }
// }


// Jenkins WS Should be operated with t3.medium for the jobs to be processed quickly.
// Outstanding from my end.
