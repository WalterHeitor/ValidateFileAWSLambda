package br.com.softwalter.validate_file.application.usecase.publish;

import br.com.softwalter.validate_file.domain.entity.Person;

import java.util.List;

public interface PublishToSQSUseCase {

    void sendToSQS(List<Person> personList);
}
