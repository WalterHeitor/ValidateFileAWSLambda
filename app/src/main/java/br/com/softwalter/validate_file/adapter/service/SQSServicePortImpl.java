package br.com.softwalter.validate_file.adapter.service;

import br.com.softwalter.validate_file.adapter.client.ClientSqs;
import br.com.softwalter.validate_file.adapter.client.ClientSqsImpl;
import br.com.softwalter.validate_file.domain.entity.Person;
import br.com.softwalter.validate_file.domain.ports.output.SQSServicePort;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SQSServicePortImpl implements SQSServicePort {

    private static final Logger logger = LoggerFactory.getLogger(SQSServicePortImpl.class);
    private final ClientSqs clientSqs = new ClientSqsImpl();
    private final AmazonSQS sqsClient = clientSqs.getSqsClient();
    private static final String QUEUE_URL = "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-test-queue";


    @Override
    public void sendToSQS(List<Person> personList) {
        for (Person person : personList) {
            String messageBody = person.toString();
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(QUEUE_URL)
                    .withMessageBody(messageBody);

            SendMessageResult sendMessageResult = sqsClient.sendMessage(sendMessageRequest);
            logger.debug("Mensagem enviada para SQS. Message ID: {}, Body: {}",
                    sendMessageResult.getMessageId(), messageBody);
        }
    }
}
