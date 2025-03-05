package br.com.softwalter.validate_file.adapter.client;

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
                tempSqsClient = AmazonSQSClientBuilder.standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                                "http://localhost:4566", "us-east-1"))
                        .withCredentials(new ProfileCredentialsProvider("localstack"))
                        .build();
            } else {

                tempSqsClient = AmazonSQSClientBuilder.standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                                "http://localhost:4566", "us-east-1"))
                        .withCredentials(new DefaultAWSCredentialsProviderChain())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sqsClient = tempSqsClient;
        return sqsClient;
    }
}
