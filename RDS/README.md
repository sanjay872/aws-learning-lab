# RDS (Relational Database System)

 Interact with both Amazon RDS and Amazon Aurora, and understand when and why to use each.

 ## Task 1 - Create MYSql RDS instance

 - Open RDS and aurora service.
 - Create Database

## Task 2 - Connect Ec2 to RDS

- Create Ec2 instance.
- Ensure that RDS security group has inbound for port 3306.
- Add mysql cli in Ec2 instance.
```bash
sudo dnf install mariadb105
```
- Connect to rds.
```bash
mysql -h your-rds-endpoint.amazonaws.com -u admin -p
```

- Try show queues to check the connectivity.

## Task 3 - Upload Sample Schema and Data

- Login in to sql server from ec2.
```sql
CREATE DATABASE demo_db;
USE demo_db;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);
INSERT INTO users (name, email) VALUES
('Alice', 'alice@example.com'),
('Bob', 'bob@example.com');
SELECT * FROM users;
```

## Task 4 - Create an Aurora MySQL-Compatible Cluster

- Create Aurora mysql-compatible db.
- follow the options.

## Task 5 - Connect to Aurora and Run the Same SQL

- Follow task 3 steps, but use the endpoint of Aurora.