# S3 storage

## Hosting static website
- create a s3 bucket.
    - bucket name
    - bucket type (ACL enabled or ACL disabled)
        - ACL is Access Control List and though this we can set authorization.
    - bucket public access
        - Access by ACL
        - Access is open without ACL
        - block cross account access
        - block creating new bucket or object
    - bucket versioning
    - tags
    - encryption
    - bucket key
- upload index.html file

```html
<html>
  <head><title>My S3 Website</title></head>
  <body>
    <h1>Hello from S3!</h1>
  </body>
</html>
```

- upload the file
- open static web hosting and enable it for index.html file.
    - it is within the properties of the bucket.
- make the file public, because by default all file are private
    - add bucket policy to make it public.
```json
{
    "Version": "2012-10-17", // language policy version
    "Statement": [ // for organizing policies
        {
            "Sid": "PublicReadGetObject", // unique id for each policy
            "Effect": "Allow", // Kind of permission (Allow or deny)
            "Principal": "*", // to who this policy is applicable (* - everyone on the internet)
            "Action": [ // what operation
                "s3:GetObject" // to read a object
            ],
            "Resource": [
                "arn:aws:s3:::Bucket-Name/*" // path to the object
            ]
        }
    ]
}
```
- open the url in the properties of static web hosting.