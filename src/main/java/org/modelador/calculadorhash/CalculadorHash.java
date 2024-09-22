package org.modelador.calculadorhash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import org.modelador.codigosaida.CodigoSaida;
import org.modelador.logger.JavaLogger;

// https://www.baeldung.com/sha-256-hashing-java

public class CalculadorHash {

    private static final Logger logger = JavaLogger.obterLogger(CalculadorHash.class.getName());

    private String calcularHash(MessageDigest messageDigest, byte[] dados) {
        byte[] mensagemHex = messageDigest.digest(dados);
        StringBuilder hexString = new StringBuilder(mensagemHex.length * 2);

        for (byte hash : mensagemHex) {
            String hex = Integer.toHexString(0xff & hash);

            if (hex.length() == 1) {
                hexString.append("0");
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }

    public String calcularHash256(byte[] dados) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Algoritmo de hash inválido. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...");
            System.exit(CodigoSaida.ALGORITMO_INVALIDO.getCodigo());
            throw new RuntimeException(e);
        }

        return calcularHash(messageDigest, dados);
    }

    public String calcularHash512(byte[] dados) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Algoritmo de hash inválido. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...");
            System.exit(CodigoSaida.ALGORITMO_INVALIDO.getCodigo());
            throw new RuntimeException(e);
        }

        return calcularHash(messageDigest, dados);
    }
}
