package io.github.heberbarra.modelador.test.calculadorhash;

import java.nio.charset.StandardCharsets;

public record EntradaCalculador(String entrada, String saida) {

    public byte[] getEntradaBytes() {
        return entrada.getBytes(StandardCharsets.UTF_8);
    }
}
