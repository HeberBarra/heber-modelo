package io.github.heberbarra.modelador.token;

import io.github.heberbarra.modelador.calculadorhash.CalculadorHash;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class GeradorToken {

    private String token;
    private final CalculadorHash calculadorHash;

    public GeradorToken() {
        calculadorHash = new CalculadorHash();
    }

    public void gerarToken() {
        String dataAtual = LocalDateTime.now().toString();
        token = calculadorHash.calcularHash512(dataAtual.getBytes(StandardCharsets.UTF_8));
        token = token.toUpperCase();
    }

    public String getToken() {
        return token;
    }
}
