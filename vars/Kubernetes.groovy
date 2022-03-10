def call(Map config=[:], Closure body) {

stage("Kubernetes Deployment"){
  steps {
	  script {
	  if( (config.branch.contains("test")) || (config.branch.contains("develop")) || (config.branch == "main") ) {
		  sh """ docker login -u $DOCKER_USER -p $DOCKER_PASS """
	  if(config.branch =="main"){
		   sh """       docker pull ${config.image}:main-${env.BUILD_ID}
				export IMAGE_NAME=${config.image}:main-${env.BUILD_ID}
				export NAMESPACE=${config.branch}
				cat deploy.yml | envsubst > ${config.manifestfile}
			"""
		 }
	   if(config.branch.contains("develop")){
		   sh """
				docker pull ${config.image}:dev-${env.BUILD_ID}
				export IMAGE_NAME=${config.image}:dev-${env.BUILD_ID}
				export NAMESPACE=${config.branch}
				cat deploy.yml | envsubst > ${config.manifestfile}
			"""
		 }
		 if(config.branch.contains("test")){
		   sh """
				docker pull ${config.image}:test-${env.BUILD_ID}
				export IMAGE_NAME=${config.image}:test-${env.BUILD_ID}
				export NAMESPACE=${config.branch}
				cat deploy.yml | envsubst > ${config.manifestfile}
			"""
		 }
		step([
			$class: 'KubernetesEngineBuilder',
			projectId: config.project_id,
			clusterName: config.cluster_name,
			location: config.location,
			manifestPattern: 'deployment.yml',
			credentialsId: config.credential_id,
			verifyDeployments: false])
		}
	 }
  }
}
   
        body()
}
# branch,image,manifestfile,project_id,cluster_name,location,credential_id
