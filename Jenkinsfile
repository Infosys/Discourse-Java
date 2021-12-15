pipeline {
	agent any
	stages {
		stage('Build Docker Image') {
			steps {
				sh './mvnw package -Pprod verify -DskipTests jib:dockerBuild'
			}
			post {
				success {
					echo "Docker Image has been built"
				}
			}
		}
   
		stage('Publish Docker Image to Azure Container Registry') {
                        steps {
                                withCredentials([string(credentialsId: 'EKS-Region', variable: 'REGION'), string(credentialsId: 'Registry-Name', variable: 'REGISTRY_NAME')]) {
					sh 'docker login -u AWS -p $(aws ecr get-login-password --region $REGION) $REGISTRY_NAME'
					sh 'echo $REGISTRY_NAME"/makeathon/discourse:${BUILD_NUMBER}"'
					sh 'docker tag discourse:latest  $REGISTRY_NAME"/makeathon/discourse:${BUILD_NUMBER}"'
					sh 'docker push $REGISTRY_NAME"/makeathon/discourse:${BUILD_NUMBER}"'
				}
                        }
                        post {
                                success {
                                        echo "Published Docker Image to Azure Container Registry"
                                }
                        }
                }
 
                stage('Deploy Application in AKS') {
                        steps {
				sh 'kubectl apply -f kubernetes/deployment.yml'
                                sh 'kubectl apply -f kubernetes/service.yml'
				sh 'kubectl apply -f kubernetes/ingress.yml'
				sh 'kubectl apply -f kubernetes/autoscale.yml'
				withCredentials([string(credentialsId: 'Registry-Name', variable: 'REGISTRY_NAME')]) {
                                        sh 'kubectl set image deployment/discourse  discourse=$REGISTRY_NAME"/makethon/discourse:${BUILD_NUMBER}" -n makethon'
                                }
                        }
                        post {
                                success {
                                        echo "Deployed to AKS"
                                }
                        }
                }
		
	}
}