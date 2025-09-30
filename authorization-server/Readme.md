Start


### VSCode


## Add Tasks .vscode/tasks.json
```json
{
	"version": "2.0.0",
	"tasks": [
		{
            "label": "gradle: bootRun",
            "type": "shell",
            "command": "./gradlew",
            "options": {
                "env": {
                    "AWS_ACCESS_KEY_ID": "",
                    "AWS_SECRET_ACCESS_KEY": "",
                    "AWS_SESSION_TOKEN": ""
                }
            },
            "group": {
                "kind": "build",
                "isDefault": true
              }
        },
		 {
            "label": "gradle: bootTestRun",
            "type": "shell",
            "command": "./gradlew",
            "options": {
                "env": {
                }
            }
        },
        {
            "label": "Run My Command",
            "type": "shell",
            "command": "echo 'Hello World!'"
          }
	]
}
```

## Add Settings .vscode/settings.json
```
{
  "java.debug.settings.onBuildFailureProceed": true,
  "java.debug.settings.enableRunDebugCodeLens": true,
  "gradle.debug":true,
  "java.jdt.ls.java.home": "C:\\Program Files\\Java\\jdk-17",
  "java.configuration.updateBuildConfiguration": "automatic",
  "java.compile.nullAnalysis.mode": "automatic",
  "java.import.gradle.java.home": "C:\\Program Files\\Java\\jdk-17"
}
```

## Add Launch .vscode/launch.json

```
{
  "version": "0.2.0",
  "configurations": [

    {
      "type": "java",
      "name": "Gradle SpringBoot BootRun",
      "request": "launch",
      "cwd": "${workspaceFolder}",
      "mainClass": "${file}",
      "projectName": "keplara",
      "preLaunchTask": "gradle: bootRun",
    },
    {
      "type": "java",
      "name": "Debug (Gradle) SpringBoot BootTestRun",
      "request": "attach",
      "hostName": "localhost",
      "port": 5006,
      "projectName": "keplara",
      "preLaunchTask": "gradle: bootTestRun"
    }
  ]
}
```

## Developer Role 
```
aws configure

aws sts assume-role --role-arn arn:aws:iam::010928192513:role/Developer_Role --role-session-name client-test          
```


```
export REDIS_USER=Grant \
export REDIS_PASSWORD=Grant \
export MONGO_URI=mongodb://localhost:27017/development \
export JWT_KEY=Test \
export JWT_PAYLOAD_KEY=Test \
export PORT=8084

```


https://medium.com/@welcometoreality2808/building-a-fullstack-application-6109cd262f8b

# Generate TrustStore
keytool -importcert \
  -file certs/ca.pem \
  -alias mongoCA \
  -keystore certs/truststore.jks \
  -storepass testpassword123 \
  -noprompt

  openssl pkcs12 -export \
  -in combined.pem \
  -out keystore.p12 \
  -name mongo-client \
  -password pass:testpassword123

  keytool -importkeystore \
  -destkeystore keystore.jks \
  -deststorepass testpassword123 \
  -srckeystore keystore.p12 \
  -srcstoretype PKCS12 \
  -srcstorepass testpassword123 \
  -alias mongo-client