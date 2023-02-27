def call() {
    properties(
        [
            parameters([
                    choice(name: 'ENV', choices: 'dev\nprod' , description: 'Chose an environment' ),
                    choice(name: 'ACTION', choices: 'apply\ndestroy' , description: 'Chose action to be apply or destroy')
            ])   
        ])
    node {
        ansiColor('xterm') {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/b52-clouddevops/${REPONAME}.git"
        
        stage('Terraform Init') {
                sh ''' 
                       terrafile -f env-${ENV}/Terrafile
                       terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars
                   '''            
            }

        stage('Terraform Plan') {
                sh ''' 
                       terraform plan -var-file=env-${ENV}/${ENV}.tfvars
                   '''            
            }

        stage('Terraform Action') {
                sh ''' 
                        terraform ${ACTION} -var-file=env-${ENV}/${ENV}.tfvars -auto-approve
                   '''            
            }            
        }
    }
}

// pipeline {
//     agent any 

//         parameters {
//              choice(name: 'ENV', choices: ['dev', 'prod'], description: 'Chose an environment')
//              choice(name: 'ACTION', choices: ['apply', 'destroy'], description: 'Chose action to be apply or destroy')
//         }
        
//         options {
//             ansiColor('xterm')
//         }

//     stages {
//         stage('Terraform Init') {
//             steps {
//                 sh "terrafile -f env-${ENV}/Terrafile"
//                 sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars"
//             }
//         }

//         stage('Terraform Plan') {
//             steps {
//                 sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"
//             }
//         }

//         stage('Terraform Action') {
//             steps {
//                 sh "terraform ${ACTION} -var-file=env-${ENV}/${ENV}.tfvars -auto-approve"
//             }
//         }
//     }
// }