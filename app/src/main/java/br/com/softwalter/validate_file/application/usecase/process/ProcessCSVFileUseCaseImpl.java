package br.com.softwalter.validate_file.application.usecase.process;

import br.com.softwalter.validate_file.adapter.service.S3ServicePortImpl;
import br.com.softwalter.validate_file.application.usecase.publish.PublishToSQSUseCase;
import br.com.softwalter.validate_file.application.usecase.publish.PublishToSQSUseCaseImpl;
import br.com.softwalter.validate_file.application.usecase.validate.ValidateFileUsecase;
import br.com.softwalter.validate_file.domain.entity.Person;
import br.com.softwalter.validate_file.domain.ports.input.S3ServicePort;
import com.amazonaws.services.s3.model.S3Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessCSVFileUseCaseImpl implements ProcessCSVFileUseCase {

    private static final Logger logger = LoggerFactory.getLogger(ProcessCSVFileUseCaseImpl.class);
    private final S3ServicePort s3ServicePort = new S3ServicePortImpl();
    private final PublishToSQSUseCase publishToSQSUseCase = new PublishToSQSUseCaseImpl();

    @Override
    public void execute(String bucketName, String objectKey) {

        logger.info("Iniciando processamento do arquivo CSV. Bucket: {}, Key: {}", bucketName, objectKey);

        try {
            S3Object objectContent = s3ServicePort.getObjectContent(bucketName, objectKey);
            logger.info("Arquivo obtido com sucesso do S3.");
            ValidateFileUsecase.validarExtensao(objectContent);
            logger.info("Extensão do arquivo validada.");
            InputStream objectData = objectContent.getObjectContent();
            BufferedReader reader;
            reader = getBufferedReader(objectData);
            String headerLine = null;
            headerLine = reader.readLine();
            ValidateFileUsecase.validarCabecalho(headerLine);
            logger.info("Cabeçalho do arquivo validado: {}", headerLine);
            List<Person> personList = parseCSV(reader);
            logger.info("Arquivo processado. Total de registros: {}", personList.size());
            publishToSQSUseCase.sendToSQS(personList);
            logger.info("Mensagens enviadas para a fila SQS.");
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } catch (Exception e) {
            logger.error("Erro ao processar o arquivo CSV. Bucket: {}, Key: {}, Erro: {}", bucketName, objectKey, e.getMessage(), e);
            throw new RuntimeException("Erro no processamento do arquivo CSV", e);
        }
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
                logger.error("Erro ao ler linha do CSV: {}", e.getMessage(), e);
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
