package br.com.softwalter.validate_file.adapter.client;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;


public class ClientS3Imp implements ClientS3 {

    static String localhost = System.getenv("LOCALHOST");

    @Override
    public AmazonS3 getS3Client() {
        AmazonS3 s3Client;
        AmazonS3 tempS3Client = null;
        try {
            if (localhost != null) {
                tempS3Client = AmazonS3ClientBuilder
                        .standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                                "http://s3.us-east-1.localhost.localstack.cloud:4566", "us-east-1"))
                        .withCredentials(new ProfileCredentialsProvider("localstack")) // Usando o perfil localstack
                        .withPathStyleAccessEnabled(true)
                        .build();

            } else {
                tempS3Client = AmazonS3ClientBuilder
                        .standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                                "http://s3.us-east-1.localhost.localstack.cloud:4566", "us-east-1"))
                        .withCredentials(new DefaultAWSCredentialsProviderChain())
                        .withPathStyleAccessEnabled(true)
                        .build();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        s3Client = tempS3Client;
        return s3Client;
    }
}
