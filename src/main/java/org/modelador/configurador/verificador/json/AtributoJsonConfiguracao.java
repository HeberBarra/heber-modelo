package org.modelador.configurador.verificador.json;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"categoria", "atributo", "tipo", "obrigatorio", "valorPadrao"})
public class AtributoJsonConfiguracao extends AtributoJson {

    private String categoria;
    private String atributo;
    private String tipo;
    private Boolean obrigatorio;
    private String valorPadrao;

    @Override
    public String toString() {
        String indentacao = " ".repeat(QUANTIDADE_INDENTACAO);

        return ("%s{%n".formatted(" ".repeat(QUANTIDADE_INDENTACAO - 2)) + "%s\"categoria\": \"%s\",%n")
                        .formatted(indentacao, categoria)
                + "%s\"atributo\": \"%s\",%n".formatted(indentacao, atributo)
                + "%s\"tipo\": \"%s\",%n".formatted(indentacao, tipo)
                + "%s\"obrigatorio\" %s,%n".formatted(indentacao, !(obrigatorio == null))
                + "%s\"valorPadrao\": %s%n".formatted(indentacao, pegarStringFormatadaValorPadrao())
                + "%s}".formatted(" ".repeat(QUANTIDADE_INDENTACAO - 2));
    }

    public String pegarStringFormatadaValorPadrao() {
        if (tipo.equals("String")) {
            return "\"%s\"".formatted(valorPadrao);
        }

        return valorPadrao;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getAtributo() {
        return atributo;
    }

    public String getTipo() {
        return tipo;
    }

    public Boolean getObrigatorio() {
        return obrigatorio;
    }

    public String getValorPadrao() {
        return valorPadrao;
    }
}
