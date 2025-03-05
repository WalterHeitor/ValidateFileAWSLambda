package br.com.softwalter.validate_file.aws;

import com.amazonaws.services.s3.model.S3Object;

public interface S3Service {
    S3Object getObjectContent(String bucketName, String objectKey);
}
