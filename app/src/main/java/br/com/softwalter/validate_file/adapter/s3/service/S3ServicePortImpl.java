package br.com.softwalter.validate_file.adapter.s3.service;

import br.com.softwalter.validate_file.adapter.s3.client.ClientS3;
import br.com.softwalter.validate_file.adapter.s3.client.ClientS3Imp;
import br.com.softwalter.validate_file.domain.ports.input.S3ServicePort;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class S3ServicePortImpl implements S3ServicePort {
    private static final Logger logger = LoggerFactory.getLogger(S3ServicePortImpl.class);
    private static final ClientS3 clientS3 = new ClientS3Imp();
    private static final AmazonS3 s3Client = clientS3.getS3Client();

    @Override
    public S3Object getObjectContent(String bucketName, String objectKey) {
        logger.debug("Entrada - getObjectContent | bucketName: {}, objectKey: {}", bucketName, objectKey);

        S3Object object = null;
        try {
            object = s3Client.getObject(bucketName, objectKey);
            logger.debug("Saída - getObjectContent | bucketName: {}, objectKey: {} - Sucesso", bucketName, objectKey);
        } catch (AmazonServiceException e) {
            logger.error("Erro ao acessar o S3 | bucketName: {}, objectKey: {} | Código de status: {} | Mensagem: {}",
                    bucketName, objectKey, e.getStatusCode(), e.getMessage(), e);
        } catch (SdkClientException e) {
            logger.error("Erro no cliente SDK ao acessar o S3 | bucketName: {}, objectKey: {} | Mensagem: {}",
                    bucketName, objectKey, e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Erro inesperado ao acessar o S3 | bucketName: {}, objectKey: {} | Mensagem: {}",
                    bucketName, objectKey, e.getMessage(), e);
        }

        return object;
    }
}
