package br.com.softwalter.validate_file.aws;

import br.com.softwalter.validate_file.aws.client.sqs.ClientSqs;
import br.com.softwalter.validate_file.aws.client.sqs.ClientSqsImpl;
import br.com.softwalter.validate_file.entity.Person;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class SQSServiceImpl implements SQSService {

    private static final Logger logger = LoggerFactory.getLogger(SQSServiceImpl.class);
    ClientSqs clientSqs = new ClientSqsImpl();
    private final AmazonSQS sqsClient = clientSqs.getSqsClient();

    private static final String QUEUE_URL = "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-test-queue";


    @Override
    public void sendToSQS(List<Person> personList) {
        for (Person person : personList) {
            String messageBody = person.toString();
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(QUEUE_URL)
                    .withMessageBody(messageBody);

            sqsClient.sendMessage(sendMessageRequest);
            logger.debug("Message sent successfully. Message ID: {}", sendMessageRequest.getMessageBody());
        }
    }
}

