def call() {
    node {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/b52-clouddevops/${COMPONENT}.git"
        env.APP = "angularjs"
        common.lintChecks()
        env.ARGS="-Dsonar.sources=."
        common.sonarChecks()
        common.testCases()   
        common.artifacts()
    }
}
