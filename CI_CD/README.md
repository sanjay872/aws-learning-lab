
# EC2 CodeDeploy Pipeline using GitHub and CodePipeline

This guide sets up a full CI/CD pipeline using AWS EC2, CodeDeploy, CodePipeline, and GitHub.

---

## ✅ Task 1 - Launch EC2 Instance and Setup Apache

1. **Update and install Apache (web server):**
```bash
sudo yum update -y
sudo yum install -y httpd
sudo systemctl start httpd
sudo systemctl enable httpd
```

2. **Tag your instance** (e.g., `Name = CodeDeployTarget`) in the EC2 dashboard under "Tags".

3. **Install the CodeDeploy Agent:**
```bash
sudo yum install ruby -y
sudo yum install wget -y
cd $HOME
wget https://bucket-name.s3.region-code.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto
```

> Replace `bucket-name` and `region-code` with values from: https://docs.aws.amazon.com/codedeploy/latest/userguide/resource-kit.html#resource-kit-bucket-names

4. **Verify CodeDeploy agent is running:**
```bash
sudo service codedeploy-agent status
```

5. **Ensure IMDSv2 access:**
Use this to verify credentials:
```bash
TOKEN=`curl -X PUT "http://169.254.169.254/latest/api/token" -H "X-aws-ec2-metadata-token-ttl-seconds: 21600"`
curl -H "X-aws-ec2-metadata-token: $TOKEN" http://169.254.169.254/latest/meta-data/iam/security-credentials/
```

---

## ✅ Task 2 - Create IAM Roles

### A. IAM Role for EC2 Instance
1. **Go to IAM > Roles > Create Role**
2. **Trusted entity:** EC2
3. **Attach policies:**
   - `AmazonEC2RoleforAWSCodeDeploy`
   - `AmazonS3ReadOnlyAccess` (for accessing CodePipeline-managed S3 artifact)
4. **Create the role and attach it to your EC2 instance** via:
   - EC2 > Actions > Security > Modify IAM Role

### B. IAM Role for CodeDeploy
1. **Go to IAM > Roles > Create Role**
2. **Trusted entity:** CodeDeploy
3. **Attach policy:** `AWSCodeDeployRole`
4. **Create and save this role**

---

## ✅ Task 3 – GitHub Repository Setup

1. Create a GitHub repo and add the following:

### `index.html`
```html
<!DOCTYPE html>
<html>
<head><title>Deployed via CodeDeploy</title></head>
<body><h1>Hello from CodeDeploy!</h1></body>
</html>
```

### `appspec.yml`
```yml
version: 0.0
os: linux
files:
  - source: index.html
    destination: /var/www/html
```

2. **Connect GitHub to CodePipeline:**
   - Go to CodePipeline > Settings > GitHub connections
   - Create a connection and authorize access
   - Select your repo when creating the pipeline

---

## ✅ Task 4 – Create CodeDeploy Application & Deployment Group

1. **Create Application:**
   - Name: `MyApp`
   - Compute Platform: EC2/On-premises

2. **Create Deployment Group:**
   - Name: `MyDeploymentGroup`
   - Service Role: (CodeDeploy IAM role created earlier)
   - Deployment type: In-place
   - Environment: EC2 tag key = `Name`, value = `CodeDeployTarget`
   - Install Agent: No (already installed manually)
   - Load balancer: optional

---

## ✅ Task 5 – Create CodePipeline

1. Go to **CodePipeline > Create Pipeline**
2. **Source stage:**
   - Provider: GitHub
   - Repo & branch: Select your repo/branch

3. **Build stage:**
   - Skip or disable

4. **Deploy stage:**
   - Provider: AWS CodeDeploy
   - Application name: `MyApp`
   - Deployment group: `MyDeploymentGroup`

5. **Create pipeline**

---

## ✅ Deployment Verification

1. Visit the EC2 Public IP in browser → you should see `Hello from CodeDeploy!`
2. Check logs via:
```bash
sudo tail -f /var/log/aws/codedeploy-agent/codedeploy-agent.log
```

3. Artifacts and logs stored under:
```bash
/opt/codedeploy-agent/deployment-root/
```

---

## ✅ Troubleshooting Tips

- Ensure EC2 IAM role has both `AmazonEC2RoleforAWSCodeDeploy` and `AmazonS3ReadOnlyAccess`
- Ensure CodeDeploy deployment group targets EC2 instance via tags
- Confirm `codedeploy-agent` is running and polling
- Use IMDSv2-compatible curl for verifying credentials

---

✅ You now have a fully working EC2/CodeDeploy/GitHub/CodePipeline setup!
