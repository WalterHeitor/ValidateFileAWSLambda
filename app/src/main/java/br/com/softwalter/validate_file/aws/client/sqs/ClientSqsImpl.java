package br.com.softwalter.validate_file.aws.client.sqs;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class ClientSqsImpl implements ClientSqs {

    static String localhost = System.getenv("LOCALHOST");

    @Override
    public AmazonSQS getSqsClient() {
        AmazonSQS sqsClient;
        AmazonSQS tempSqsClient = null;
        try {
            if (localhost != null) {
//                tempSqsClient = AmazonSQSClientBuilder.defaultClient();
                tempSqsClient = AmazonSQSClientBuilder.standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                                "http://localhost:4566", "us-east-1")) // Endpoint LocalStack
                        .withCredentials(new ProfileCredentialsProvider("localstack")) // Usa o perfil localstack
                        .build();
            } else {

                tempSqsClient = AmazonSQSClientBuilder.standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                                "http://localhost:4566", "us-east-1")) // Endpoint LocalStack
                        .withCredentials(new DefaultAWSCredentialsProviderChain()) // Usa o perfil localstack
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sqsClient = tempSqsClient;
        return sqsClient;
    }
}
