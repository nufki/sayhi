# Running it locally via docker

## Build and run locally
Build:
````
docker build -t sayhi .   
````

Run application using docker:
````
docker run -p 9090:8080 sayhi
   
````


# AWS Todos:

## Push the Image to Amazon Elastic Container Registry (ECR) 
Create an ECR Repository
````
aws ecr create-repository --repository-name sayhi   
````

Authenticate Docker with ECR
````
aws ecr get-login-password --region eu-west-1 | docker login --username AWS --password-stdin 4527-5475-1479.dkr.ecr.eu-west-1.amazonaws.com   
````

````
docker push AWS_ACCOUNT_ID.dkr.ecr.eu-west-1.amazonaws.com/sayhi:latest   
````

## Create a new ECS cluster 
````
aws ecs create-cluster --cluster-name sayhi-cluster   
````


## Create a new ECS service inside the cluster 

Fetch routes
````
aws ec2 describe-route-tables --region eu-west-1 --query 'RouteTables[*].Routes[*]'
````

Fetch subnets
````
aws ec2 describe-subnets --filters "Name=vpc-id,Values=vpc-3c62ba45" --region eu-west-1 --query 'Subnets[*].[SubnetId,Tags]' --output table
````

Fetch security group:
````
aws ec2 describe-security-groups --region eu-west-1 --query 'SecurityGroups[*].[GroupId,GroupName]' --output table
````

Create service:
````
aws ecs create-service --cluster sayhi-cluster \
--service-name sayhi-service \
--task-definition sayhi-task-definition \
--desired-count 1 \
--launch-type FARGATE \
--network-configuration "awsvpcConfiguration={subnets=[subnet-072f795d],securityGroups=[sg-742e1329],assignPublicIp=ENABLED}"
````



## Enable routing in the Security Group
Go to AWS Console → EC2 → Security Groups

Find the Security Group attached to your ECS task.
Click "Inbound Rules".
Ensure there is a rule allowing HTTP traffic on port 8080:
Type: Custom TCP
Protocol: TCP
Port Range: 8080
Source: 0.0.0.0/0 (for public access)


## Test the endpoint
e.g. curl http://3.250.212.26:8080/api/hello


## Cleanup
````
aws ecs update-service --cluster sayhi-cluster --service sayhi-service --desired-count 0
````