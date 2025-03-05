package br.com.softwalter.validate_file;

import br.com.softwalter.validate_file.application.usecase.ProcessCSVFileUseCase;
import br.com.softwalter.validate_file.application.usecase.ProcessCSVFileUseCaseImpl;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AppProcessCSVFileHandler implements RequestHandler<S3Event, Void> {

    private static final Logger logger = LoggerFactory.getLogger(AppProcessCSVFileHandler.class);

    private final ProcessCSVFileUseCase processCSVFileUseCase = new ProcessCSVFileUseCaseImpl();


    @Override
    public Void handleRequest(S3Event s3Event, Context context) {
        try {
            logger.info("Iniciando processamento!");
            for (S3EventNotification.S3EventNotificationRecord s3record : s3Event.getRecords()) {
                String bucketName = s3record.getS3().getBucket().getName();
                String objectKey = s3record.getS3().getObject().getKey();

                processCSVFileUseCase.execute(bucketName, objectKey);
                logger.info("Fim do processamento!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        String bucketName = "my-test-bucket";
        String key = "mock_data.csv";

        S3EventNotification.S3BucketEntity s3Bucket =
                new S3EventNotification.S3BucketEntity(bucketName, null, null);
        S3EventNotification.S3ObjectEntity s3ObjectEntity =
                new S3EventNotification.S3ObjectEntity(key, null, null, null, null);
        S3EventNotification.S3Entity s3Entity =
                new S3EventNotification.S3Entity(null, s3Bucket, s3ObjectEntity, null);
        S3EventNotification.S3EventNotificationRecord s3EventNotificationRecord =
                new S3EventNotification.S3EventNotificationRecord(
                        null, null, null, null, null, null, null, s3Entity, null);
        List<S3EventNotification.S3EventNotificationRecord> s3EventNotificationRecords = List.of(s3EventNotificationRecord);
        S3Event s3Event = new S3Event(s3EventNotificationRecords);

        new AppProcessCSVFileHandler().handleRequest(s3Event, null);
    }

}
