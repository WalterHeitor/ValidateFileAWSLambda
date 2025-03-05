package br.com.softwalter.validate_file.aws.client.s3;

import com.amazonaws.services.s3.AmazonS3;

public interface ClientS3 {

    AmazonS3 getS3Client();
}
