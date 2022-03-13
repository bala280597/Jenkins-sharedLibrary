pipeline{
    agent any
    environment {
        PROJECT_NAME = 'Hello_world'
    }
    stages{
        stage('Checkout'){
          steps {
           sh "echo bala"
          }
        }
    }
    post { 
        always { 
            sh """ 
                   python Build.py Bala@123 bala2805/${env.PROJECT_NAME} ${JOB_NAME} ${BUILD_NUMBER} ${currentBuild.durationString.replace(' and counting', '')} build
                   """
        }
    }
  
}
