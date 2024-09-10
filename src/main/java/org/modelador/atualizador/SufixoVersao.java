package org.modelador.atualizador;

public enum SufixoVersao {
    SNAPSHOT(0),
    ALPHA(1),
    BETA(2),
    PRE_RELEASE(3),
    RELEASE(4);

    public final int valor;

    SufixoVersao(int valor) {
        this.valor = valor;
    }
}
