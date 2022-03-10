def call(Map config=[:], Closure body) {

stage("Kubernetes Deployment"){
  steps {
	  script {
	  if( (env.GIT_BRANCH.contains("test")) || (env.GIT_BRANCH.contains("develop")) || (env.GIT_BRANCH == "main") ) {
		  sh """ docker login -u $DOCKER_USER -p $DOCKER_PASS """
	  if(env.GIT_BRANCH=="main"){
		   sh """
				export IMAGE_NAME=bala2805/nodejs:main-${env.BUILD_ID}
				export NAMESPACE=${env.GIT_BRANCH}
				cat deploy.yml | envsubst > deployment.yml
			"""
		 }
	   if(env.GIT_BRANCH.contains("develop")){
		   sh """
				docker pull bala2805/nodejs:dev-${env.BUILD_ID}
				export IMAGE_NAME=bala2805/nodejs:dev-${env.BUILD_ID}
				export NAMESPACE=${env.GIT_BRANCH}
				cat deploy.yml | envsubst > deployment.yml
			"""
		 }
		 if(env.GIT_BRANCH.contains("test")){
		   sh """
				docker pull bala2805/nodejs:test-${env.BUILD_ID}
				export IMAGE_NAME=bala2805/nodejs:test-${env.BUILD_ID}
				export NAMESPACE=${env.GIT_BRANCH}
				cat deploy.yml | envsubst > deployment.yml
			"""
		 }
		step([
			$class: 'KubernetesEngineBuilder',
			projectId: env.PROJECT_ID,
			clusterName: env.CLUSTER_NAME,
			location: env.LOCATION,
			manifestPattern: 'deployment.yml',
			credentialsId: env.CREDENTIALS_ID,
			verifyDeployments: false])
		}
	 }
  }
}
   
        body()
}
