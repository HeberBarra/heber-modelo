package io.github.heberbarra.modelador.calculadorhash;

import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.logger.JavaLogger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Baseado em: <a href="https://www.baeldung.com/sha-256-hashing-java">SHA-256 Java</a>
 * <p>
 * Utilizado para calcular o hash utilizando vários algoritmos, veja os métodos disponíveis para descobrir os algoritmos disponíveis
 * @since v0.0.3-SNAPSHOT
 * */
public class CalculadorHash {

    private static final Logger logger = JavaLogger.obterLogger(CalculadorHash.class.getName());

    /**
     * Calcula o hash de um array dados utilizando um algoritmo específico
     * @param messageDigest especifica o algoritmo que será utilizado para o cálculo do hash
     * @param dados os dados a serem processados pelo algoritmo
     * @return a string do hash calculado
     * */
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

    /**
     * Calcula o hash sum de um dado array de bytes utilizando o algoritmo SHA-256
     * @param dados os dados que serão processados
     * @return a string do hash calculado
     * */
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

    /**
     * Calcula o hash sum de um dado array de bytes utilizando o algoritmo SHA-512
     * @param dados os dados que serão processados
     * @return a string do hash calculado
     * */
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
