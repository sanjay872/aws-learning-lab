# Identity and Access Management (IAM)

## Task 1 - Creating a new IAM user
- IAM -> User -> create User
    - user name
        - programmatic access (to access aws without console) is created after creating user.
    - permissions
        - adding to group (user groups)
        - adding policy (buildin or custom)
        - copy permissions (using existing permissions)
    - Tags (Optionals)
    - create
- Download access key, user info -> create access key -> download csv

## Task 2 - Create a custom policy.
- IAM > Policies > Create policy.
    - use visual editor
        - select service
        - action allowed
            - read
            - write
            - list
            - permission management
        - resources (add resource path with ARNs)
    - policy name
    - attach the policy to created user
    - user info -> permissions -> add permissions -> add the policy.

## Task 3 - Install and Configure AWS CLI

- Install AWS CLI.
- Configure the CLI with Your New User.
    - open cmd
    ```bash
    aws configure
    ```
    - give access key id and secret access key from the downloaded access key (cvs file), add region as per the console and give output as JSON.
    - To see all buckets for which use has access.
    ```bash
    aws s3 ls
    ```
    - Test uploading file.
    Creating file.
    ```bash
    echo "Hello from AWS CLI" > hello.txt
    ```
    Uploading file.
    ```bash
    aws s3 cp hello.txt s3://sanjaysakthivel-static-website/
    ```
    - verify if the file exist from console.

## Task 4 -  Create an IAM Role for EC2 to Access S3

- IAM > Roles > Create role.
    - Trusted Entity 
        - Service (AWS Service)
        - Use Case (EC2)
    - Permissions
        - AmazonS3ReadOnlyAccess (Example)
    - Role name and review policies.
    - create.
- Attach the Role to an EC2 Instance
    - Open EC2
    - Select the instance you used in Milestone 1 (or launch a new one).
    - Actions > Security > Modify IAM Role.
    - Choose created role.
    - Save.
- Verify Access from the EC2 Instance
    - SSH into your EC2 instance.
    - Run this command to list your bucket:

        ```bash
        aws s3 ls s3://sanjaysakthivel-static-website/
        ```
        ```yaml
        2024-07-06 00:00:00     14 hello.txt Try to upload a file:
        ```

        ```bash
        echo "Test write" > test.txt
        aws s3 cp test.txt s3://sanjaysakthivel-static-website/
        ```

        ✅ This should fail with Access Denied—because you only attached read-only permissions.