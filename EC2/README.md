# EC2 Web Server Setup

## Task 1 - Launch and Configure the EC2 Instance

- Create EC2 Instance
    - Name
    - Select image (Amazon linux, Ubuntu, Redhat...)
    - Select instance (t2.micro, t3.micro....)
    - generate keypair
    - permission
        - open SSH for my IP
        - Open HTTP for anywhere (so, http be request can be made from anywhere)
    - create
- Update the file permission for keypair as per OS.
- connect to the server,
```bash
ssh -i "path/to/your-key.pem" ec2-user@<Public-IP>
```
- Update server packages, if needed.

## Task 2 - Install and Config Web Server

- Install web server
```bash
sudo yum install -y httpd
``` 
- start server
```bash
sudo systemctl start httpd
sudo systemctl enable httpd
```

- We can hit server by, http://<PUBLIC_IP_OF_SERVER>

## Task 3 - Updating Server HTML file

- Open /var/www/html in cmd.
- Remove the existing html file.
```bash
sudo rm -f index.html
```
- Create new index.html file
```bash
sudo nano index.html
```
- Reload the page http://<PUBLIC_IP_OF_SERVER>

## Optional

- Login aws cli in the server.
- Access S3.
- Copy and paste the file from S3 into web server. 
```bash
aws s3 cp s3://<BUCKET_NAME>/index.html .
sudo mv index.html /var/www/html/index.html
```