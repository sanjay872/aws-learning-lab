# Kinesis
Create a Kinesis data stream, write sample data into it, and process the records using a Lambda function.

## Task 1 - Create a Kinesis Stream

- Open the service kinesis.
- create kinesis data stream.
    - Name.
    - Capacity mode (on-demand(dynamic), Provisioned(fixed))
    - If Provisioned is selected,
        - Add total number of streams.
    - Click create.

## Task 2 - Create a Lambda function

- Open lambda service.
- Create function.

## Task 3 - add Permission
- add KinesisReadOnly permission to lambda function.

## Task 4 – Configure the Kinesis Trigger
- In Lambda, Configuration > Triggers.
- Click add triggers.
- Select the created kinesis.
- batch process (number of data sent at a time)
- starting position (from where to read - Latest, trim horizon, timestamp)
    -   Latest (From recent added data)
    -   Trim Horizon (All the data)
    -   Timestamp (with timestamp)

## Task 5 – Write Lambda Code to Process Records
```python
import base64

def lambda_handler(event, context):
    for record in event['Records']:
        decoded_bytes = base64.b64decode(record['kinesis']['data'])
        try:
            payload = decoded_bytes.decode('utf-8')
        except UnicodeDecodeError:
            payload = str(decoded_bytes)
        print("Decoded payload:", payload)
    return {
        'statusCode': 200,
        'body': 'Processed {} records.'.format(len(event['Records']))
    }
```

## Task 6 – Put Records into Kinesis

Make sure your AWS CLI is configured (aws configure) with credentials that have permission to write to Kinesis.

- create a new user to access kinesis.
- use IAM and generate key access.
- do aws configure and add the user.
- Add record into kinesis.
```bash
    aws kinesis put-record --stream-name TodoStream --partition-key "partitionKey1" --data "Hello from Kinesis!"
```
- it gives a response of created shardId and sequence number.

## Task 7 – Verify Lambda Logs in CloudWatch

- Open service cloud watch.
- By default, lambda will create is log service in cloud watch.
- Open Logs -> Log group.
- 