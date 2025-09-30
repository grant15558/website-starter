# Mongo

Requirements:

* IAM Account
* Permission to use AWS Services
* Access Key, talk to Admin on generating an access key
* Docker

this docker container will grab the certs from AWS ACM using CLI commands. Follow the procedure below on how to get the ACM cert using AWS CLI. To run redis, just run docker-compose.yml script. User and password are set by enviorment vars. 

Deployment

#### AWS for MongoDB

### install AWS CLI 
https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html#getting-started-install-instructions

Windows install 

`https://awscli.amazonaws.com/AWSCLIV2.msi`

`msiexec.exe /i https://awscli.amazonaws.com/AWSCLIV2.msi`

`aws --version`

# Getting started

## Production

1. Create a env file in the root directory of this project.

```
 export MONGO_USER=Grant
 export MONGO_PASSWORD=Grant

```
Run `Docker compose up -d redis --build` 

Build image
`docker build --rm -f ./Dockerfile.dev -t 010928192513.dkr.ecr.us-east-1.amazonaws.com/mysite/mongodb:dev .`

Run image locally
```bash
  docker run \
  -p 27018:27018 \
  010928192513.dkr.ecr.us-east-1.amazonaws.com/mysite/mongodb:dev
```

Run 

ECR login before image push
`aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 010928192513.dkr.ecr.us-east-1.amazonaws.com`

Push image
`docker push 010928192513.dkr.ecr.us-east-1.amazonaws.com/mysite/redis:staging`


# Assume the role and capture the credentials
creds=$(aws sts assume-role \
  --role-arn arn:aws:iam::010928192513:role/Developer_Role \
  --role-session-name DevSession)

# Export the temporary credentials
export AWS_ACCESS_KEY_ID=$(echo $creds | jq -r .Credentials.AccessKeyId)
export AWS_SECRET_ACCESS_KEY=$(echo $creds | jq -r .Credentials.SecretAccessKey)
export AWS_SESSION_TOKEN=$(echo $creds | jq -r .Credentials.SessionToken)


### Optional
db.createUser(
  {
    user: "GrantMitchell",
    pwd:  passwordPrompt(),
    roles: [ { role: "readWrite", db: "development" }]
  }
)

### Only for Root user and or authorized personel
export MONGOPEM=$( aws secretsmanager get-secret-value --secret-id mongodbpem --query SecretString --output text)

docker compose up -d --build mongo-staging

### Checksum
openssl sha256 -hex -out checksum.sha256 secretfile.txt
openssl aes-256-cbc -in secretfile.txt -out secretfile.enc -e


  tls:
    mode: requireTLS
    certificateKeyFile: /etc/ssl/mongodb/mongodb.pem
    allowConnectionsWithoutCertificates: true
    AllowInvalidCertificates: true