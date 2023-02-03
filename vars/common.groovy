def sonarChecks(COMPONENT) {   
       sh  "echo Starting Code Quality Analysis"
       sh  "sonar-scanner -Dsonar.host.url=http://${SONAR_URL}:9000  ${ARGS} -Dsonar.projectKey=${COMPONENT}  -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}"
       sh  "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gata.sh"
       sh  "bash -x quality-gata.sh ${SONAR_USR} ${SONAR_PSW} ${SONAR_URL} ${COMPONENT}"
}