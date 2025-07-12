# SNS (Simple Notification Service)

## Task 1 - Create SNS Topic

- Open the service SNS.
- Create topic.
    - Topic name.
    - Type (standard, FIFO)
 
 ## Task 2 - Create SNS queue (consumer)

 - Open the service SQS.
 - Create queue.
    - Name.
    - Type (standard, FIFO)
    - Message config.
    - Access Policy (who can access it).
    - other many optionals.

## Task 3 – Subscribe the SQS Queue to the SNS Topic

- Open SNS.
- In the created SNS, click subscription.
    -   Select protocol as SQS.
    -   Select endpoint of the created SQS.
    -   Enable raw message delivery (so, that message will be delivery without any overlaying json object)

## Task 4 – Publish a Test Message to SNS (Produce message in topic)

- Open SNS.
- In the created SNS.
- Click publish message.
    - set Subject and Body.


## Task 5 - Verify the Message in SQS
- check if message is available in queue.
    - Open SQS
        - click sent and receive messages.
        - Do poll for messages, it will get all the messages from the topic.
        - Once the message is received, stop polling.

