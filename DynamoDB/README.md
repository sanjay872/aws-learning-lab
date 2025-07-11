# DynamoDB

Create a DynamoDB table, give Lambda permission to access it, and write a Lambda function that saves and retrieves data.

## Task 1 - Create the DynamoDB table

- Services > DynamoDB
- create table
    - table name
    - Partition key (primary key)
    - Sort key (optional)

### Partition Key

The partition key in DynamoDB is the primary key used to uniquely identify each item in the table.
It also determines how data is distributed and partitioned across storage nodes for performance and scalability.

✅ That’s the essential idea:

Uniqueness: Every item must have a unique partition key value.

Distribution: DynamoDB uses it to spread data across partitions.

## Task 2 - Grant Permission to Lambda function

By default, your Lambda function cannot read or write any DynamoDB table.
You must attach permissions.

- Open service -> IAM -> Roles
- Search for lambda, there will be role for the created lambda funcion.
- By default role will be auto created for each lambda.
- we need to add policies on the role to make it access DynamoDB.

- Open the role
    - options -> attach policy
    - Search AmazonDynamoDBFullAccess
    - Select it and attach policy.

## Task 3 - Lambda function to write into DynamoDB

-   Open lambda function.

```python
import json
import boto3  # AWS SDK for accessing resources

dynamodb= boto3.resource('dynamodb')
table = dynamodb.Table('Todos') # getting the table

def lambda_handler(event, context):
   
    item_id = event.get('id') # getting item id from event triggered
    task = event.get('task') # getting task name from event triggered

    if not item_id or not task: # input validation
        return {
            'statusCode': 400,
            'body': json.dumps('Missing id or task')
        }
    
    table.put_item( # to insert record and if same record exist it will be overwritten
        Item={
            'id': item_id,
            'task': task
        }
    )

    # response
    return {
        'statusCode': 200,
        'body': f'Item {item_id} added successfully'
    }
```

## Task 4 - Test Inserting an Item

- create new test event
- add JSON
```json
{
  "id": "todo1",
  "task": "Learn DynamoDB integration"
}
```
- click test

## Task 5 - check the item in DynamoDb

- Services > DynamoDB > Tables > Todos > Explore Table Items
- check the table for data.

## Task 6 - create table data from lambda function

- update the code to read and write data
```python
import json
import boto3 

dynamodb= boto3.resource('dynamodb')
table = dynamodb.Table('Todos')

def lambda_handler(event, context):
   
    item_id = event.get('id')
    task = event.get('task')

    if not item_id:
        return {
            'statusCode': 400,
            'body': json.dumps('Missing id')
        }
    
    if task:
        table.put_item(
            Item={
                'id': item_id,
                'task': task
            }
        )
        return {
            'statusCode': 200,
            'body': f'Item {item_id} added successfully'
        }
    else:
        response = table.get_item(
            Key={
                'id': item_id
            }
        )
        item = response.get('Item')
        if item:
            return {
                'statusCode': 200,
                'body': item
            }
        else:
            return {
                'statusCode': 404,
                'body': f'Item {item_id} not found'
            }

```

## Task 7 - Test Retrieving an Item

- create a test
```json
{
  "id": "todo1"
}
```

## Task 8 - Lambda Function with Java
AWS editor doesn't support Java. So, a java project with lambda function must be create and the generated jar file will be added in the cloud.

- Create a java project (lambda-dynamodb-example).
- Add pom with all required dependencies and plugin for jar generation.
- Add the code lambda.
- run
```bash
mvn clean package
```
- upload the jar file in the lambda function.
- ensure that runtime has the correct function name and the lambda function has policy access attached.
- Test the api.