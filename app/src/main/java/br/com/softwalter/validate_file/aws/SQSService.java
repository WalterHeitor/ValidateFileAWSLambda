package br.com.softwalter.validate_file.aws;

import br.com.softwalter.validate_file.entity.Person;

import java.util.List;

public interface SQSService {
    void sendToSQS(List<Person> personList);
}
