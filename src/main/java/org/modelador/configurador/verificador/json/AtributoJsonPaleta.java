package org.modelador.configurador.verificador.json;

public class AtributoJsonPaleta extends AtributoJson {

    private String nomeVariavel;
    private String valorPadraoVariavel;

    @Override
    public String toString() {
        String indentacao = " ".repeat(QUANTIDADE_INDENTACAO);

        return ("%s{%n".formatted(" ".repeat(QUANTIDADE_INDENTACAO - 2))
                + "%s\"nomeVariavel\": \"%s\",%n".formatted(indentacao, nomeVariavel)
                + "%s\"valorPadraoVariavel\": \"%s\"%n".formatted(indentacao, valorPadraoVariavel)
                + "%s}".formatted(" ".repeat(QUANTIDADE_INDENTACAO - 2)));
    }

    public String getNomeVariavel() {
        return nomeVariavel;
    }

    public String getValorPadraoVariavel() {
        return valorPadraoVariavel;
    }
}
