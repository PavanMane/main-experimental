- Follow instruction in blog: 
	http://cloudmark.github.io/Kubernetes/
	
- Go the root folder, where you have build.gradle

- ./gradlew eclipse

- *** open sts the host name must be the IP of the machine

- start mongodb

- Run build
	*** ./gradlew build && java -jar deploy/application/dist/moi-user-v0.1.0.jar (Check revision)

- check if images are already present
	*** docker images / docker ps -a

- create docker image
	*** docker build -t <app>-<microservice_name>-v<version major.minor.buildnumber> . 
	Exampel: docker build -t us.gcr.io/p1-springboot-mongodb/moi-user-v0.1.0 .
		The '.' in the end is important

- check if the image is created
	*** docker images

- check if the container is runs fine
	*** docker run - P us.gcr.io/p1-springboot-mongodb/moi-user-v0.1.0
	*** docker run --rm -it -p 8080:8080 <app>-<microservice_name>-v<version major.minor.buildnumber>
		Example: docker run --rm -it -p 8080:8080 us.gcr.io/p1-springboot-mongodb/moi-user-v0.1.0
	access application: http://localhost:8080/...

	if in the above docker run the -rm -it -p 8080:8080 opts were not used, the do 
		docker ps
		check the ports column, example: 0.0.0.0:<someportnumber>->8080/tcp
		you can then access the application via
		http://localhost:<someportnumber>/...

	-----------
	 Debugging
	-----------
		check the jar file name is correct in DockerFile
		ensure application.properties has network ip not 127.0.0.1, else the container cannot talk to host machines mongodb.
		ensure mongodb is running 

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

- run the commands from springboot-mongo folder

- Push Google Cloud
	*** gcloud docker push <zone>/<projectid>/<docker_image_name>
		Example: gcloud docker push us.gcr.io/p1-springboot-mongodb/moi-user-v0.1.0

- Set the cluster to use
	*** gcloud config set container/cluster kub-cluster-spring-data

- Get the credentials
	*** gcloud container clusters get-credentials kub-cluster-spring-data

- Run the deployment locally
	*** kubectl run p1-springboot-mongo-v1 --image us.gcr.io/p1-springboot-mongodb/moi-user-v0.1.0

	Debugging
		- if not able to execute change the wifi connection to NOVOPAY TECH-OPS[X]

- Expose the deployment over an load balancer
	*** kubectl expose deployment p1-springboot-mongo-v1 --port=80 --target-port=8080 --name=p1-springboot-mongo-service --type=LoadBalancer

- For Rolling update
	*** kubectl rolling-update p1-springboot-mongo-v1 --image us.gcr.io/p1-springboot-mongodb/test_usermrsvc_v0.1.2

- Get the EXTERNAL-IP and the PORT of the service created above to check the application
	*** kubectl get service p1-springboot-mongo-service

- ACCESS THE URL http://EXTERNAL-IP:PORT/<contextRoot>/....
	Example: http://23.236.61.207/demo/v1/user/p1

Ensure the mongodb points to the mongodb service running on cloud.
Check /deploy/database/Readme.txt

- Delete 
	docker rm $(docker ps -a -q)
	docker rm $(docker ps -q)
	docker rmi -f $(docker images -q)
	*** kubectl delete service p1-springboot-mongo-service
	kubectl delete deployment p1-springboot-mongo-v1
