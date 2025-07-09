# Lambda

## Task 1 - What is Lambda function?

Lambda is a serverless compute service that runs your code in response to events.
Unlike EC2, you don’t provision or manage servers—Lambda automatically scales and executes code only when triggered, then shuts down.

🎯Core idea: event-driven, no always-on server, auto-scaling.

## Task 2 - Create a lambda function.

- Open lambda service -> create function.
    - Function name
    - Runtime (Nodejs, java, python.....)
    - Architecture (arm, x86)
    - Permissions
    - create functions

## Task 3 - Edit default function.

```python
import json

def lambda_handler(event, context): # event is the input data from event and context is the meta data
    # TODO implement
    return {
        'statusCode': 200,
        'body': json.dumps('Hello from Lambda!')
    }
```

- event parameter:

```text
    The event parameter contains all the input data passed to the Lambda function by the triggering service.
    For example:

    If triggered by an API Gateway request, event contains the HTTP request data.

    If triggered by an S3 event, it contains bucket and object info.

    If triggered by DynamoDB Streams, it contains record changes.
```

- deploy the code (ctr + shift + u)

## Task 4 - invoking lambda function

- Test the lambda function
- open the test tab
- edit the json, if needed. (it has the input)
- click save.
- To test, click test.

