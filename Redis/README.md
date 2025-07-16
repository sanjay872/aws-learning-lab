# Redis

✅ Objective

Launch an ElastiCache Redis cluster.

Connect to it from an EC2 instance.

Store and retrieve data.

Understand caching patterns.

#  Task 1 - Launch an ElastiCache Redis Cluster

- Service > ElastiCache
    - Type
        - Redis OSS (The original Redis you know (key-value, pub/sub, caching).	Most common choice for caching, queues, session storage.)
        - Valkey (A Redis-compatible fork AWS is starting to support. Similar to Redis OSS (early days).)
        - Memcached (Simple memory key-value store (no persistence, no rich types).	Lightweight caching (not as feature-rich).)
    - Create it custom and ensure that the cache and Ec2 are in same network.

# Task 2 -  Install redis-cli on EC2

- Make a SSH connection with EC2.
- Install redis
```bash
sudo yum install -y docker
sudo service docker start
sudo usermod -a -G docker ec2-user
sudo docker run -it --rm redis redis-cli -h <your-redis-endpoint>
```
- Few thing to keep in mind
    - EC2 and cache are in same VPC.
    - EC2 and cache can be in same or different security group and subset.
    - EC2 must has inbound rule of SSH for connectivity.
    - Cache must have Custom TCP with the source of EC2 secuity group.

# Task 3 - 