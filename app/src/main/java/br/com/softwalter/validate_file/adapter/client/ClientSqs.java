package br.com.softwalter.validate_file.adapter.client;

import com.amazonaws.services.sqs.AmazonSQS;

public interface ClientSqs {

    AmazonSQS getSqsClient();
}
