package org.modelador.calculadorhash;

import java.nio.charset.StandardCharsets;

public record EntradaCalculador(String entrada, String saida) {

    public byte[] getEntradaBytes() {
        return entrada.getBytes(StandardCharsets.UTF_8);
    }
}
