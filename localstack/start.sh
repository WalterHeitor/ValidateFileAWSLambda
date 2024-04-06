

aws --endpoint-url=http://localhost:4566 s3 mb s3://my-test-bucket
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name my-test-queue

# O Lambda precisa de um IAM Role para a sua execução
aws --endpoint http://localhost:4566 --profile localstack \
  iam create-role \
  --role-name lambda-execution \
  --assume-role-policy-document "{\"Version\": \"2012-10-17\",\"Statement\": [{ \"Effect\": \"Allow\", \"Principal\": {\"Service\": \"lambda.amazonaws.com\"}, \"Action\": \"sts:AssumeRole\"}]}"

  # Com a IAM Role criada, vamos incluir a policy de execução:
aws --endpoint http://localhost:4566 --profile localstack \
  iam attach-role-policy \
  --role-name lambda-execution \
  --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole

# Da acesso para lambda ler o arquivo do S3
aws --endpoint http://localhost:4566 --profile localstack \
  iam attach-role-policy \
  --role-name lambda-execution \
  --policy-arn arn:aws:iam::aws:policy/AmazonS3FullAccess

#  implantação do Lambda no LocalStack com o seguinte comando:
aws --endpoint http://localhost:4566 --profile localstack \
  --region us-east-1 lambda create-function \
  --function-name validacao \
  --runtime java17 \
  --handler org.example.App \
  --memory-size 128 \
  --zip-file fileb://app/build/libs/app.jar \
  --role arn:aws:iam::000000000000:role/lambda-execution --timeout 30000

# Configuração do gatilho S3 para acionar a função Lambda
aws --endpoint-url=http://localhost:4566 lambda add-permission \
  --function-name validacao \
  --statement-id s3-event \
  --action "lambda:InvokeFunction" \
  --principal s3.amazonaws.com \
  --source-arn "arn:aws:s3:::my-test-bucket" \
  --source-account "000000000000" \
  --region us-east-1

aws --endpoint-url=http://localhost:4566 s3api put-bucket-notification-configuration \
  --bucket my-test-bucket \
  --notification-configuration '{"LambdaFunctionConfigurations":[{"LambdaFunctionArn":"arn:aws:lambda:us-east-1:000000000000:function:validacao","Events":["s3:ObjectCreated:*"]}]}'

# subindo arquivo para execucao da lambda
aws --endpoint-url=http://localhost:4566 s3  cp ./localstack/mock_data.csv s3://my-test-bucket



DATABASE_USERNAME=postgres;DATABASE_PASSWORD=postgres;DATABASE_NAME=postgres;DATABASE_SCHEMA=public;DATABASE_PORT=5432;DATABASE_HOST=localhost;

