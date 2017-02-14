- Follow the instructions under the following blog:
	https://kubernetes.io/docs/tutorials/stateful-application/run-stateful-application/

- First create a disk
	gcloud compute disks create --size=10GB mongo-disk

- Change all 'mysql' string to 'mongo' in all the *.yaml file in the blog's *.yaml files
	For example check mongoDeployment.yaml
	- Ensure the disk name created matches the one in the PersistentVolume name.

- Create the mongodb deployment with an headless service
	kubectl create -f mongoDeployment.yaml

- Expose the headless service
	kubectl expose service mongo --port=27017 --target-port=27017 --name=mongo-lb-service --type=LoadBalancer
		- This is the most critical step else your mongodb will not be availabe to the outside. Useful when you want to access mongodb deployed on Google Cloud cluster from your laptop.

- Verify the deployment, ensure the pods are in ready state and deployments have atleast 1 available replica.
	kubectl describe service mongo-lb-service
	kubectl describe service mongo
	kubectl describe deployment mongo
	kubectl describe pvc mongo-pv-claim
	kubectl describe pv mongo-pv


- check if you can access it externally
	- get external_ip for the loadbalancer service
		kubectl get service mongo-lb-service
	mongo --host <external_ip for the loadbalancer service>

- Delete 
	kubectl delete service mongo-lb-service
	kubectl delete deployment,svc mongo
	kubectl delete pvc mongo-pv-claim
	kubectl delete pv mongo-pv
	gcloud compute disks delete mongo-disk
