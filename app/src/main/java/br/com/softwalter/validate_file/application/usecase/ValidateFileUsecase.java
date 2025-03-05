package br.com.softwalter.validate_file.application.usecase;

import com.amazonaws.services.s3.model.S3Object;

public class ValidateFileUsecase {

    public static void validarExtensao(S3Object object) {
        if (!object.getKey().toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("O arquivo não é um arquivo CSV.");
        }
    }

    public static void validarCabecalho(String headerLine) {

        if (headerLine == null) {
            throw new IllegalArgumentException("O arquivo CSV está vazio.");
        }

        String[] columns = headerLine.split(",");

        if (columns.length != 6 ||
                !columns[0].equals("id") ||
                !columns[1].equals("first_name") ||
                !columns[2].equals("last_name") ||
                !columns[3].equals("email") ||
                !columns[4].equals("gender") ||
                !columns[5].equals("ip_address")) {
            throw new IllegalArgumentException("O arquivo CSV não tem o formato esperado.");
        }
    }
}
