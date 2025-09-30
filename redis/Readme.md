# Redis
Redis server


Requirements:

* IAM Account
* Permission to use AWS Services
* Access Key, talk to Admin on generating an access key
* Docker

this docker container will grab the certs from AWS ACM using CLI commands. Follow the procedure below on how to get the ACM cert using AWS CLI. To run redis, just run docker-compose.yml script. User and password are set by enviorment vars. 

Deployment

#### Configure SSL & AWS for Redis

### install AWS CLI 
https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html#getting-started-install-instructions

Windows install 

`https://awscli.amazonaws.com/AWSCLIV2.msi`

`msiexec.exe /i https://awscli.amazonaws.com/AWSCLIV2.msi`

`aws --version`

`aws acm get-certificate --region {region} --certificate-arn arn:aws:acm:us-east-1:number:certificate/number`


# Getting started

## Production

1. Create a env file in the root directory of this project.

```
 export REDIS_USER=Grant
 export REDIS_PASSWORD=Grant

```

2. Run `Docker compose up -d redis --build` 


3. 

```bash 
    aws sts assume-role \
  --role-arn arn:aws:iam::010928192513:role/Developer_Role \
  --role-session-name DevSession
```


Build image
`docker build --rm -f ./Dockerfile.dev -t 010928192513.dkr.ecr.us-east-1.amazonaws.com/keplara/redis:dev .`

Run image locally
```bash
   docker run \
  -e AWS_SESSION_TOKEN=token \
  -e AWS_ACCESS_KEY_ID=your-access-key \
  -e AWS_SECRET_ACCESS_KEY=your-secret-key \
  -e AWS_DEFAULT_REGION=us-east-1 \
  010928192513.dkr.ecr.us-east-1.amazonaws.com/keplara/redis:dev
```

Run 

ECR login before image push
`aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 010928192513.dkr.ecr.us-east-1.amazonaws.com`

Push image
`docker push 010928192513.dkr.ecr.us-east-1.amazonaws.com/keplara/redis:staging`


# Assume the role and capture the credentials
creds=$(aws sts assume-role \
  --role-arn arn:aws:iam::010928192513:role/Developer_Role \
  --role-session-name DevSession)

# Export the temporary credentials
export AWS_ACCESS_KEY_ID=$(echo $creds | jq -r .Credentials.AccessKeyId)
export AWS_SECRET_ACCESS_KEY=$(echo $creds | jq -r .Credentials.SecretAccessKey)
export AWS_SESSION_TOKEN=$(echo $creds | jq -r .Credentials.SessionToken)