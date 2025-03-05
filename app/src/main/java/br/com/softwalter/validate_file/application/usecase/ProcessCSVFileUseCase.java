package br.com.softwalter.validate_file.application.usecase;

public interface ProcessCSVFileUseCase {

    void execute(String bucketName, String objectKey);
}
