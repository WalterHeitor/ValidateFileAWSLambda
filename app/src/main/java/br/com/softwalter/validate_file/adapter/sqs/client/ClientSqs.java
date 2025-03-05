package br.com.softwalter.validate_file.adapter.sqs.client;

import com.amazonaws.services.sqs.AmazonSQS;

public interface ClientSqs {

    AmazonSQS getSqsClient();
}
