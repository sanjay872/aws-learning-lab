# Monitoring and Tracing

Enable CloudWatch Logs, Metrics, and Alarms

Trace your Lambda function’s execution flow using AWS X-Ray

Use dashboards and logs to monitor AWS services like EC2 and Lambda

## Task 1 - Enable CloudWatch Logs for Lambda

- Create a Lambda function with tigger of api gateway or Dynamo db.
- It this automatically add a cloudwatch log and it can viewed in the cloudwatch log groups or from monitor tab of lambda function.

## Task 2 - Add Custom Log Messages in Lambda

- Add more print statements in lambda function to more info in cloudwatch.

```python
def lambda_handler(event, context):
    # TODO implement
    print("🔔 Lambda triggered")
    print(f"📨 Event received: {json.dumps(event)}")

    # Simulate some logic
    message = "Hello from Lambda!"
    print(f"✅ Responding with message: {message}")
    return {
        'statusCode': 200,
        'body': json.dumps('Hello from Lambda!')
    }

```

- check the logs.

## Task 3 - X ray traces

- In lambda function, open configuration -> monitoring and operation tools  -> enable x-ray.
- Redo lambda function.
- check the log for application signals, it would give a detailed info on the executed function.

## Task 4 - CloudWatch Alarm

- It to know if some error happen while executing function.
- Open cloudWatch -> Alarm -> create alarm.
    - Select metrics (what to monitor? (error or behaviour), select the service and its metric)
    - Define the threshold
    - Create SNS for getting notification.
    - give name for the alarm, create.

## Task 5 - Log Tracing and testing alarm

- Add the following code in the lambda.
```python
import time

def lambda_handler(event, context):
    print("Starting slow operation")
    time.sleep(2)
    raise Exception("Test failure")
```
- Ensure whether subscription is confirmed for email notification.
- check the x- ray live trace and also see whether email is triggred for this.