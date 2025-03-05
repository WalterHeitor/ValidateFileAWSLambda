package br.com.softwalter.validate_file.aws.client.sqs;

import com.amazonaws.services.sqs.AmazonSQS;

public interface ClientSqs {

    AmazonSQS getSqsClient();
}
