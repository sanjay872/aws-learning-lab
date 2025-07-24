# AI Services
Explore AWS’s powerful AI services without needing to build or train models yourself.

## Task 1 - Amazon Rekognition 
Use Amazon Rekognition to analyze an image stored in S3 and detect labels (like "Car", "Tree", "Person").

- create a s3 bucket with a image.
- create a lambda function, which will access the image and used ai model to identify the image.
```python
import boto3

def lambda_handler(event, context):
    client = boto3.client('rekognition')

    response = client.detect_labels(
        Image={
            'S3Object': {
                'Bucket': 'your-bucket-name',
                'Name': 'your-image.jpg'
            }
        },
        MaxLabels=10,
        MinConfidence=70
    )

    for label in response['Labels']:
        print(f"{label['Name']} : {label['Confidence']:.2f}%")
    return response
```

## Task 2 - Use Amazon Comprehend for Text Analysis 

- create a lambda function.
```python
import boto3

client = boto3.client('comprehend')

text = "Amazon Web Services, based in Seattle, is the leading cloud provider. Jeff Bezos founded the company in 2006."

# Detect Language
lang_response = client.detect_dominant_language(Text=text)
language = lang_response['Languages'][0]['LanguageCode']

# Detect Sentiment
sentiment = client.detect_sentiment(Text=text, LanguageCode=language)

# Detect Entities
entities = client.detect_entities(Text=text, LanguageCode=language)

print("Sentiment:", sentiment['Sentiment'])
print("\nEntities:")
for entity in entities['Entities']:
    print(f"{entity['Type']}: {entity['Text']}")
```

- add permission to run the analysis.
```json
{
  "Effect": "Allow",
  "Action": [
    "comprehend:DetectSentiment",
    "comprehend:DetectEntities",
    "comprehend:DetectLanguage"
  ],
  "Resource": "*"
}
```

## Task 3 - Convert Text to Speech using Amazon Polly
- create lambda function.
```python
import boto3

polly = boto3.client('polly')

response = polly.synthesize_speech(
    Text="Hello Sanjay! You're doing a great job learning AWS.",
    OutputFormat='mp3',
    VoiceId='Joanna'  # You can try others like 'Matthew', 'Amy', 'Brian'
)

# Save audio to file
with open('speech.mp3', 'wb') as file:
    file.write(response['AudioStream'].read())

```

- add permission.
```json
{
  "Effect": "Allow",
  "Action": [
    "polly:SynthesizeSpeech"
  ],
  "Resource": "*"
}
```

## Task 4 - combining all 3 services
- create lambda function.

```python
import boto3

def lambda_handler(event, context):
    bucket = "your-bucket"
    image_key = "image.jpg"
    text_key = "text.txt"

    s3 = boto3.client('s3')
    rekognition = boto3.client('rekognition')
    comprehend = boto3.client('comprehend')
    polly = boto3.client('polly')

    # Get text from file
    text_obj = s3.get_object(Bucket=bucket, Key=text_key)
    text = text_obj['Body'].read().decode('utf-8')

    # Rekognition
    rekog_resp = rekognition.detect_labels(
        Image={'S3Object': {'Bucket': bucket, 'Name': image_key}},
        MaxLabels=5
    )
    labels = [label['Name'] for label in rekog_resp['Labels']]

    # Comprehend
    lang_resp = comprehend.detect_dominant_language(Text=text)
    lang = lang_resp['Languages'][0]['LanguageCode']
    sentiment_resp = comprehend.detect_sentiment(Text=text, LanguageCode=lang)

    # Build summary
    summary = f"The image contains: {', '.join(labels)}. " \
              f"The text sentiment is {sentiment_resp['Sentiment']}."

    # Polly narration
    polly_resp = polly.synthesize_speech(
        Text=summary,
        OutputFormat='mp3',
        VoiceId='Joanna'
    )

    # Save result to S3
    s3.put_object(
        Bucket=bucket,
        Key="summary.mp3",
        Body=polly_resp['AudioStream'].read()
    )

    return {
        'statusCode': 200,
        'body': summary
    }

```

- keep all the permissions.

## Task 5 - 