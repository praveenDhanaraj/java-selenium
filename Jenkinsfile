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
	stage('ClamAV') {
	 parallel {
	 stage('Scan') {
	  steps {
	    script {
        FAILED_STAGE=env.STAGE_NAME
		 ClamAV_scan= "${readProb['ClamAV']}"
		    if ("$ClamAV_scan" == "yes"){
       build job: 'demo_clamav', wait: false
       } else
     echo "Skipped"
	     }
	    }
	   }
	  }
    }
    stage ('Build Stage') {
    steps {
            sh 'mvn -f $WORKSPACE/pom.xml clean install'
            }
    }
    
    stage('upload') {
       steps {
          script { 
             def server = Artifactory.server 'artifactory'
             def uploadSpec = """{
                "files": [{
                   "pattern": "${WORKSPACE}/target/newapp-0.0.1-SNAPSHOT.war",
                   "target": "example-repo-local"
                }]
              }"""
              server.upload(uploadSpec) 
              }
            }
        }
    stage('SonarQube analysis') {
	  steps {
	    script {
         scannerHome = tool 'sonarqube';
	     FAILED_STAGE=env.STAGE_NAME
	   	  SonarQube= "${readProb['SonarQube_Analysis']}"
		if ("$SonarQube" == "yes") {
          withSonarQubeEnv('sonarqube') {
          sh "${scannerHome}/bin/sonar-scanner -X -Dsonar.login=admin -Dsonar.password=zippyops -Dsonar.projectKey=demo -Dsonar.projectName=demo -Dsonar.projectVersion=1.0  -Dsonar.sources=${readProb['sonar_sources']} -Dsonar.java.sourceEncoding=ISO-8859-1"
           }
	    }
		else {
		  echo "Skipped"
		  }
		 }
		}
     }
    stage("Sonarqube Quality Gate") {
	   steps {
	     script { 
            FAILED_STAGE=env.STAGE_NAME
			Quality= "${readProb['SonarQube_Quality']}"
		    if ("$Quality" == "yes") {
            sleep(60)
            qg = waitForQualityGate() 
            if (qg.status != 'OK') {
            error "Pipeline aborted due to quality gate failure: ${qg.status}"	
              }
            }
			else {
			echo "skipped"
			}
		   }
         }
     }
    stage('Docker Build') {
      agent any
      steps {
        sh 'address=$(kubectl get svc | grep jfrog-artifactory-nginx | awk -F \' \' \'{print $4}\') && docker build -t $address:80/docker/cicd-dockerimage:$BUILD_NUMBER  /var/jenkins_home/workspace/demo/.'
      }
    }

    stage('Docker Push') {
      agent any
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
          sh 'echo script done'
          sh 'address=$(kubectl get svc | grep jfrog-artifactory-nginx | awk -F \' \' \'{print $4}\') && docker login -u admin -p zippyops $address:80 '
          sh 'address=$(kubectl get svc | grep jfrog-artifactory-nginx | awk -F \' \' \'{print $4}\') && docker push $address:80/docker/cicd-dockerimage:$BUILD_NUMBER'
        }
      }
    }
        stage("Dev Deploy") {
           steps {
           script {
            FAILED_STAGE=env.STAGE_NAME
                  Dev_deploy= "${readProb['Dev_Deploy']}"
                    if ("$Dev_deploy" == "yes") {
                  sh '''address=$(kubectl get svc | grep jfrog-artifactory-nginx | awk -F \' \' \'{print $4}\')
                  sed  -i "s/zippyops01\\/cicd-dockerimage/${address}:80\\/docker\\/cicd-dockerimage/g"  /var/jenkins_home/workspace/demo/deploy.yml
                  echo  $BUILD_NUMBER                  
                  sed -i s/latest/$BUILD_NUMBER/g /var/jenkins_home/workspace/demo/deploy.yml
                  kubectl apply -f /var/jenkins_home/workspace/demo/deploy.yml'''
                  sh 'sleep 55s'
                  sh 'ip=$(kubectl get svc | grep tomcat | tr -s [:space:] \' \' | cut -d \' \' -f 4) && echo http://zippyops:zippyops@$ip:8080/newapp-0.0.1-SNAPSHOT/'
                  }
                  else {
                  echo "Skipped"
                  }
            }
           }
          }      
        stage('OpenVAS') {
           steps {
             sh 'sed -i \'s/#PubkeyAuthentication yes/PubkeyAuthentication yes/g\' /etc/ssh/sshd_config'
             sh 'sudo echo \'ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCsmSiIXvN9k5IW0cDZgw4wnuQx73kosSONahzhGQ7L3qJDVcmOVa6nAdvy3Z6jl8uY02dSWq+BnDtiSZd+XnmR9SU4egwmYc7SLfCwKLYL7lhpe3/K+mAm/zToNcpudtGNbMn7+rgR8lOZ+ZZww+tvtd6zEBq1fLSX64KHs6W7kyQUShNBuF/kxp14qu0BO/WQ5LS568wuJA2j7M0+GnG4QljbgGFDniIBOkuHXWFeoguDzsyG8MTjH0csz2l4BDDxQfckYLfIw2xcQzBZMFKcjv06Fcic0Rdw6bjIJCsVJVBhmNEj8o4r9fnJ6XyhBD9oobS1otfbZlGr2q0iimCP root@openvas.zippyops.com\' > /root/.ssh/authorized_keys'
             sh 'ip=$(kubectl get svc | grep tomcat | tr -s [:space:] \\\' \\\' | cut -d \\\' \\\' -f 4)'
             sh '''ssh -tt root@192.168.2.16 /bin/bash << SSH_EOF
             echo \'open vas server\'
             cd /root/openvas_cli
             nohup ./zippyops1.py $ip &
             sleep 5
             exit
             SSH_EOF '''
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
            reportFiles: 'demo_Dev_ZAP_VULNERABILITY_REPORT.html',
            reportName: 'Dev_owaps'
              ]
	   
            }
        }

}