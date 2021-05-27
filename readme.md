#Assumptions:

##Taking unique name as input for catererInputModel.
#Pre-Requsites are :  Docker, Minikube, JDK
## How to run

#### clone the project.

Copy application.properties file from resources folder and place at following path /opt/company/conf/resource-management

#### Cd into project repository.

#### Run following commands

mvn package

Once image is created then we have to run locally through kubernetes.

To run the image with minikube run following command

eval $(minikube docker-env)

docker build -t assignment/resource-management:1.0.0 .

kubectl create -f ./k8/redis/
kubectl create -f ./k8/kafka/
kubectl create -f ./k8/mongodb/

Now check once all deployments are running properly Then run following command to created the deployment of application.

kubectl create -f ./k8/resource-management/

To access swagger ui:   http://{Place ip of rms-service}:30008/resource-management/v1/swagger-ui.html

###Swagger file is placed in resources folder.

To access 







### Extra command
kubectl create configmap rms-config --from-file=/opt/company/conf/resource-management
kubectl get configmap rms-config -o yaml     -- created a rms-config file and placed all data
