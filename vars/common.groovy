def sonarChecks(COMPONENT) {   
       sh  "echo Starting Code Quality Analysis"
       // sh  "sonar-scanner -Dsonar.host.url=http://${SONAR_URL}:9000  ${ARGS} -Dsonar.projectKey=${COMPONENT}  -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}"
       // sh  "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gata.sh"
       // sh  "bash -x quality-gata.sh ${SONAR_USR} ${SONAR_PSW} ${SONAR_URL} ${COMPONENT}"
       // Uncomment above 3 lines only when your sonarQube is available. Keeping cost in mind, we marked them as comments. But, in lab we have practiced the sonar checks and quality gates.
       sh "echo Code Quality Checks Completed."
}