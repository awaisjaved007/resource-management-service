#Assumptions:

##Taking unique name as input for catererInputModel.

## How to run

#### clone the project.

Copy application.properties file from resources folder and place at following path /opt/company/conf/resource-management

#### Cd into project repository.

#### Run following commands

Once image is created then we have to run locally through kubernetes.

To run the image with minikube run following command

eval $(minikube docker-env)

docker build -t assignment/resource-management:1.0.0 .









### Extra command
kubectl create configmap rms-config --from-file=/opt/company/conf/resource-management
kubectl get configmap rms-config -o yaml     -- created a rms-config file and placed all data
