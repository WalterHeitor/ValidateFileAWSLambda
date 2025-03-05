package br.com.softwalter.validate_file.domain.ports.output;

import br.com.softwalter.validate_file.domain.entity.Person;

import java.util.List;

public interface SQSServicePort {
    void sendToSQS(List<Person> personList);
}
