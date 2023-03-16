def call() {
    node {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/b52-clouddevops/${COMPONENT}.git"

        stage('Docker Build') {
            sh "docker build ."
        }

    }
}