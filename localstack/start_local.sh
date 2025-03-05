
# comando /bin/sh localstack/start_local.sh
echo "Iniciando execução do script localstack/start_local.sh"

echo "Criando bucket S3 'my-test-bucket' no LocalStack..."
aws --profile localstack --endpoint-url=http://localhost:4566 s3 mb s3://my-test-bucket

echo "Criando fila SQS 'my-test-queue' no LocalStack..."
aws --profile localstack --endpoint-url=http://localhost:4566 sqs create-queue --queue-name my-test-queue

# subindo arquivo para execucao da lambda
echo "Enviando arquivo 'mock_data.csv' para o bucket S3 'my-test-bucket'..."
aws --profile localstack --endpoint-url=http://localhost:4566 s3  cp ./localstack/mock_data.csv s3://my-test-bucket