package br.com.softwalter.validate_file.aws.service;

import br.com.softwalter.validate_file.aws.service.AwsService;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class AwsServiceImpl implements AwsService {
    private static final AmazonS3 s3Client;
    private static final AmazonSQS sqsClient;
    private static final String QUEUE_URL = "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-test-queue";

    static String localhost = System.getenv("LOCALHOST");

    static {
        AmazonS3 tempS3Client = null;
        AmazonSQS tempSqsClient = null;
        try {
            if (localhost != null) {
                tempS3Client = AmazonS3ClientBuilder
                        .standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                                "http://s3.us-east-1.localhost.localstack.cloud:4566", "us-east-1"))
                        .withCredentials(new ProfileCredentialsProvider("localstack")) // Usando o perfil localstack
                        .withPathStyleAccessEnabled(true)
                        .build();
                tempSqsClient = AmazonSQSClientBuilder.defaultClient();
            } else {
                tempS3Client = AmazonS3ClientBuilder
                        .standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                                "http://s3.us-east-1.localhost.localstack.cloud:4566", "us-east-1"))
                        .withCredentials(new DefaultAWSCredentialsProviderChain())
                        .withPathStyleAccessEnabled(true)
                        .build();

//                tempSqsClient = AmazonSQSClientBuilder.defaultClient();
                tempSqsClient =  AmazonSQSClientBuilder.standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                                "http://localhost:4566 ", "us-east-1")) // Endpoint LocalStack
                        .withCredentials(new ProfileCredentialsProvider("localstack")) // Usa o perfil localstack
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        s3Client = tempS3Client;
        sqsClient = tempSqsClient;
    }

    @Override
    public AmazonS3 getS3Client() {
        return s3Client;
    }

    @Override
    public AmazonSQS getSqsClient() {
        return sqsClient;
    }

    @Override
    public String getQueueUrl() {
        return QUEUE_URL;
    }
}
