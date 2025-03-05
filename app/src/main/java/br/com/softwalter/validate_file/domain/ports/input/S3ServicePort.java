package br.com.softwalter.validate_file.domain.ports.input;

import com.amazonaws.services.s3.model.S3Object;

public interface S3ServicePort {
    S3Object getObjectContent(String bucketName, String objectKey);
}
