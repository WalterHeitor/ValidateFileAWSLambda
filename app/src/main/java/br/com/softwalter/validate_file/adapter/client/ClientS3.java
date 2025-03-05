package br.com.softwalter.validate_file.adapter.client;

import com.amazonaws.services.s3.AmazonS3;

public interface ClientS3 {

    AmazonS3 getS3Client();
}
