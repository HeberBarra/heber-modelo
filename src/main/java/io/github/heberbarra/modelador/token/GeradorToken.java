/**
 * Copyright (C) 2025 Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 * <p>
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 * <p>
 * https://choosealicense.com/licenses/mit/
 * <p>
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 */
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
