package br.com.softwalter.validate_file.application.usecase;

import br.com.softwalter.validate_file.aws.S3Service;
import br.com.softwalter.validate_file.aws.S3ServiceImpl;
import br.com.softwalter.validate_file.aws.SQSService;
import br.com.softwalter.validate_file.aws.SQSServiceImpl;
import br.com.softwalter.validate_file.entity.Person;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessCSVFileUseCaseImpl implements ProcessCSVFileUseCase {

    private final S3Service s3Service = new S3ServiceImpl();
    private final SQSService sqsService = new SQSServiceImpl();


    @Override
    public void execute(String bucketName, String objectKey) {

        S3Object objectContent = s3Service.getObjectContent(bucketName, objectKey);
        ValidateFileUsecase.validarExtensao(objectContent);
        InputStream objectData = objectContent.getObjectContent();
        BufferedReader reader;
        reader = getBufferedReader(objectData);
        String headerLine = null;
        try {
            headerLine = reader.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        ValidateFileUsecase.validarCabecalho(headerLine);
        List<Person> personList = parseCSV(reader);

        sqsService.sendToSQS(personList);
    }

    private static BufferedReader getBufferedReader(InputStream objectData) {
        BufferedReader reader;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(objectData);
            reader = new BufferedReader(inputStreamReader);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao carregar buffer" + e);
        }
        return reader;

    }

    public List<Person> parseCSV(BufferedReader reader) {

        List<Person> personList = new ArrayList<>();
        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
            String[] parts = line.split(",");
            if (parts.length == 6) {
                Person person = new Person(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5]);
                personList.add(person);
            }
        }
        return personList;
    }

}
