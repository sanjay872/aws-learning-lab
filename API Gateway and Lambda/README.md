# API Gateway and Lambda

Creating a Http endpoint that triggers Lambda function.

# API Gateway

API Gateway acts as the managed HTTPS endpoint that:

Handles authentication (e.g., API keys, IAM auth).

Validates requests.

Throttles and protects your backend.

Transforms HTTP requests into Lambda event data.

Scales automatically.

✅ So API Gateway is the public front door, and Lambda is the private backend logic.

## Task 1 - creating api gateway

- Services > Api Gateway
- Select API type (Rest, Http, websocket, Rest private)
    - Name
    - Endpoint type (Regional, Edge optimized, private)
    - Ip address type (ipv4, dualstack)
    - create

## Task 2 - create a resource and method

- click Actions > Create Resource.
    - Name (It is the path)
    - create
- select the path
    - create method
        - select request type
        - select lambda fucntion
        - create

## Task 3 - deploy api

- Click deploy api
    - Select new stage
    - Give stage name
    - create
- The invoke url on the side bar is the base url.


### Stage

API Gateway stages let you manage different environments for your API, like dev, test, and prod.
Each stage can have:

Its own deployment version

Separate configuration (logging, throttling)

Unique endpoint URL

✅ So you can develop and test safely without affecting production users.