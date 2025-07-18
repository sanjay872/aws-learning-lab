# Load Balancer

##  Task 1: Launch 2 EC2 Instances
- Create a 2 ec2 instance
    - install web service on both.
        ```bash
            sudo yum install -y httpd
            sudo systemctl start httpd
            sudo systemctl enable httpd
        ```
    - create a web page
        - Server 1
        ```bash
            echo "<h1>Server 1</h1>" | sudo tee /var/www/html/index.html
        ```
        - Server 2
        ```bash
            echo "<h1>Server 2</h1>" | sudo tee /var/www/html/index.html
        ```

## Task 2 - Create a Security Group
- For both the server open http port 80 and set it anywhere.

## Task 3 - Create an Application Load Balancer
-  EC2 > Load Balancers > Create ALB (Application Load Balancer)
    - Name
    - Scheme (Internet facing (To handle internet traffic), Internal(To handle internal service traffic))
    - Address Type (IPV4, Dualstack, Dualstack without public IP)
    - VPC (Select the VPC used by both the server)
    - Avaliability Zone (check the zone of both servers. There should be atleast 2 subset selected)
    - Security Group (Select default or create new)
    - Listener and routing (The port that load balancer must listen, our case is http 80)
        - Create Target group
            - Target type (Instance (EC2), IP address, Lambda function)
            - Name
            - add protocol as Http and port as 80
            - Address type (IPV4, IPv6)
            - vpc (select the vpc as of load balancer and server)
            - heath check (default - / , to check the health of server at intervals)
            - add both Server instances
            - Register target (add both the servers)
            - create
        - add the created target group.
        - move the target group into pending.
    - create load balancer
    - Ensure that the secuity group of load balancer has http 80 enabled.

## Task 4 - Test ALB
- Get the DNS name of the ALB.
- Try the url in multiple browser and check the response.