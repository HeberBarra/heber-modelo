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
package io.github.heberbarra.modelador.test.application.usecase.hash;

import java.nio.charset.StandardCharsets;

public record EntradaCalculador(String entrada, String saida) {

    public byte[] getEntradaBytes() {
        return entrada.getBytes(StandardCharsets.UTF_8);
    }
}
