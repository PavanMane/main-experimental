- Follow instruction in blog: 
	http://cloudmark.github.io/Kubernetes/
	
- Go the root folder, where you have build.gradle

- create docker image
	*** docker build -t <app>_<microservice_name>_v<version major.minor.buildnumber> . 
		The '.' in the end is important

- check if the image is created
	*** docker images

- check if the container is runs fine
	*** docker run -P <app>_<microservice_name>_v<version major.minor.buildnumber>

- check the status
	*** docker ps
	*** docker ps -a

- Use any cloud provider. For this example i will be using Google cloud

- Create project and container cluster in google cloud

- Install "Google cloud SDK"

- Check your googlecloud installation
	*** gcloud config list

- Install "Kubectl"
	*** gcloud components update kubectl

- build image and push in the private repository of the project that you created in google cloud
	*** docker build -t <zone>/<projectid>/<docker_image_name> .
		Example:- docker build -t us.gcr.io/p1-springboot-mongodb/test_usermrsvc_v0.1.1 .
	*** gcloud docker push <zone>/<projectid>/<docker_image_name>
		Example:- gcloud docker push us.gcr.io/p1-springboot-mongodb/test_usermrsvc_v0.1.1

- *** gcloud config set container/cluster kub-cluster-spring-data

- *** gcloud container clusters get-credentials kub-cluster-spring-data

- *** kubectl run p1-springboot-mongo-v1 --image us.gcr.io/p1-springboot-mongodb/test_usermrsvc_v0.1.1

- *** kubectl expose deployment p1-springboot-mongo-v1 --port=80 --target-port=8080 --name=p1-springboot-mongo-service --type=LoadBalancer

- For Rolling update
	*** kubectl rolling-update p1-springboot-mongo-v1 --image us.gcr.io/p1-springboot-mongodb/test_usermrsvc_v0.1.2

- Get the EXTERNAL-IP and the PORT of the service created above to check the application
	*** kubectl get service p1-springboot-mongo-service

- ACCESS THE URL http://EXTERNAL-IP:PORT/<contextRoot>/....
	Example: http://23.236.61.207/demo/v1/user/p1

Ensure the mongodb points to the mongodb service running on cloud.
Check /deploy/database/Readme.txt

