def readProb;
def FAILED_STAGE
pipeline {
agent { label 'node'}
tools {
  maven 'maven-3'
  git 'Default'
}
stages {
    stage('build') {
    steps {
        sh 'python3 test.py'
    }
}
    }
}