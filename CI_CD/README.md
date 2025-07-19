# CI/CD

Creating code pipline in AWS

## Task 1 - Creating Ec2 Instance

- Creating apache to run a web server.
```bash
sudo yum update -y
sudo yum install -y httpd
sudo systemctl start httpd
sudo systemctl enable httpd
```
- Adding CodeDeploy agent
```bash
sudo yum install ruby -y
sudo yum install wget -y
cd /home/ec2-user
wget https://bucket-name.s3.region-code.amazonaws.com/latest/install # getting the code deploy agent
chmod +x ./install # giving executable permission to install script
sudo ./install auto # install everything with default settings
```
- In the command replace, bucket-name and region-code by refering (https://docs.aws.amazon.com/codedeploy/latest/userguide/resource-kit.html#resource-kit-bucket-names).

- Verify it’s running.
```bash
    sudo service codedeploy-agent status
```
## Task 2 - Create and Attach IAM Roles

This allows your EC2 to communicate with CodeDeploy.

- Go to IAM > Roles > Create Role

    - Trusted entity: AWS service → EC2

    - Attach policy: AmazonEC2RoleforAWSCodeDeploy

- Create role

- Attach Role to EC2
    -  Go to EC2 Dashboard
    -  Select your instance → Actions > Security > Modify IAM Role
    -  Attach the new EC2CodeDeployRole

- IAM Role for CodeDeploy Application
    - This lets CodeDeploy manage deployment lifecycle events.
    - Go to IAM > Roles > Create Role
    - Trusted entity: AWS service → CodeDeploy
    - Attach policy: AWSCodeDeployRole
    - Create role

## Task 3 – GitHub Repo Setup + CodeDeploy Config

- create a repo (private or public)
- add index.html file.
```html
<!DOCTYPE html>
<html>
<head>
  <title>Deployed via CodeDeploy</title>
</head>
<body>
  <h1>Hello from CodeDeploy!</h1>
</body>
</html>
```
- add appspec.yml.
```yml
version: 0.0
os: linux
files:
  - source: /
    destination: /var/www/html
```

- Enable the codedeploy to access the github
    - open CodePipeline > Settings > GitHub connections
    - create new github connection
    - add the repo

## Task 4 – Create CodeDeploy Application & Deployment Group

- Create the application from the application tab.
    - Name.
    - Compute platform Ec2/on-premises.
- In application tab, create deployment group
    - Name.
    - Service role (Add created code deploy role).
    - deployment type (inplace).
    - environment config (select the tag and value of the ec2 instance created)
    - Install CodeDeploy, select no if ec2 already has the codedeploy running.
    - deployment settings
    - Load balancer (as per requirement)
    - create.

## Task 5 - Create a pipeline
- Create a custom pipeline
- Skip the build and test stage
- In deploy stage select the created application.
- create.