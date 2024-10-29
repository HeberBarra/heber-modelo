package org.modelador.atualizador;

/**
 * Define o valor de cada um dos sufixos das versões semânticas.
 * @since v0.0.3-SNAPSHOT
 * */
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
