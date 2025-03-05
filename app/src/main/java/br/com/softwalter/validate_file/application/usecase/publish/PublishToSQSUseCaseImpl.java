package br.com.softwalter.validate_file.application.usecase.publish;

import br.com.softwalter.validate_file.adapter.service.SQSServicePortImpl;
import br.com.softwalter.validate_file.domain.entity.Person;
import br.com.softwalter.validate_file.domain.ports.output.SQSServicePort;

import java.util.List;

public class PublishToSQSUseCaseImpl implements PublishToSQSUseCase{

    private final SQSServicePort sqsServicePort = new SQSServicePortImpl();

    @Override
    public void sendToSQS(List<Person> personList) {
        sqsServicePort.sendToSQS(personList);
    }
}
