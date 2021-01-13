def readProb;
def FAILED_STAGE
pipeline {
agent { label 'master'}
tools {
  maven 'maven-3'
  git 'Default'
}
stages {
    stage('Preperation'){
    steps {
        script {
        readProb = readProperties  file:'config.properties'
        FAILED_STAGE=env.STAGE_NAME
        Preperation= "${readProb['Preperation']}"
		if ("$Preperation" == "yes") {
	    sh "git config --global user.email admin@example.com"
        sh "git config --global user.name Administrator"
        sh 'git config --global credential.helper cache'
        sh 'git config --global credential.helper cache'
        sh "if [ -d ${readProb['Project_name']} ]; then rm -Rf ${readProb['Project_name']}; fi"
        sh "if [ -d ${readProb['PMD_result']} ]; then rm -Rf ${readProb['PMD_result']};  fi"        
        sh "mkdir ${readProb['PMD_result']}"
		}
		else {
		 echo "Skipped"
		}
		}
		}
    }
    stage('Git Pull'){
    steps {	dir("${readProb['Project_name']}"){
	    git branch: "${readProb['branch']}", credentialsId: "${readProb['credentials']}", url: "${readProb['Bitbucket.url']}"    
	      }
		}
	 }
	stage("Validate NOPMD Usage") {
	steps {
        script {
        FAILED_STAGE=env.STAGE_NAME
        NOPMD= "${readProb['NOPMD']}"
		if ("$NOPMD" == "yes") {
	    sh (
	       script: '''
	    cd demo
	    echo $PWD
        nosonar="$(grep -ir nosonar  | wc -l)"
        nopmd="$(grep -ir nopmd  | wc -l)"
        nosquid="$(grep -ir squid  | wc -l)"
        if [ "${nosonar}" == 0 ] && [ "${nopmd}" == 0 ] && [ "${nosquid}" == 0 ]; then
        exit 1
        else
        grep -ir nopmd    > ${WORKSPACE}/${readProb['PMD_result']}/nopmd.report
        grep -ir nosonar  > ${WORKSPACE}/${readProb['PMD_result']}/nosonar.report
        grep -ir squid    > ${WORKSPACE}/${readProb['PMD_result']}/nosquid.report 
        echo "No Sonar was used ${nosonar} times in the code" 
        echo "No PMD was used ${nopmd} times in the code"
        echo "squid supress was used ${nosquid} times in the code"
        exit 0
        fi '''
        )
		}
		else {
		  echo "skipped"
		 }
	    }
	   }
	 }

    stage ('Build Stage') {
    steps {
            sh 'mvn -f $WORKSPACE/pom.xml clean install'
            }
    }
    

      

}

  post {
      success {
      publishHTML target: [
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: '/var/jenkins_home/jobs/demo/builds/archive/out',
            reportFiles: 'index.html',
            reportName: 'Dev_speedtest'
              ]
            }
        }

}
