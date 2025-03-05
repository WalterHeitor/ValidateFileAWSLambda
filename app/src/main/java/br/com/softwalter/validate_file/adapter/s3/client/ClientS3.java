package br.com.softwalter.validate_file.adapter.s3.client;

import com.amazonaws.services.s3.AmazonS3;

public interface ClientS3 {

    AmazonS3 getS3Client();
}
