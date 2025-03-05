package br.com.softwalter.validate_file.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.AmazonSQS;

public interface AwsService {

    AmazonS3 getS3Client();
    AmazonSQS getSqsClient();
    String getQueueUrl();
}
