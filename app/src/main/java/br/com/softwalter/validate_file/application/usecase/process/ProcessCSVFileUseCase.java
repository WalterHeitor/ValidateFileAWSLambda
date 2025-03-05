package br.com.softwalter.validate_file.application.usecase.process;

public interface ProcessCSVFileUseCase {

    void execute(String bucketName, String objectKey);
}
