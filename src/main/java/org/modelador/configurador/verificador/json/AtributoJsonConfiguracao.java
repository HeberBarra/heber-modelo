package org.modelador.configurador.verificador.json;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonPropertyOrder({"categoria", "atributo", "tipo", "obrigatorio", "valorPadrao"})
public class AtributoJsonConfiguracao extends AtributoJson {

    private String categoria;
    private String atributo;
    private String tipo;
    private Boolean obrigatorio;
    private String valorPadrao;

    @Override
    public Map<String, String> converterParaMap() {
        Map<String, String> informacoesAtributo = new LinkedHashMap<>();

        informacoesAtributo.put("categoria", categoria);
        informacoesAtributo.put("atributo", atributo);
        informacoesAtributo.put("tipo", tipo);

        if (obrigatorio == null || !obrigatorio) {
            informacoesAtributo.put("obrigatorio", "false");
        } else {
            informacoesAtributo.put("obrigatorio", "true");
        }

        informacoesAtributo.put("valorPadrao", valorPadrao);

        return informacoesAtributo;
    }

    @Override
    public String toString() {
        String indentacaoAtributos = " ".repeat(TAMANHO_INDENTACAO * NIVEL_INDENTACAO);
        String indentacaoChaves = " ".repeat(TAMANHO_INDENTACAO * (NIVEL_INDENTACAO - 1));

        return ("%s{%n".formatted(indentacaoChaves) + "%s\"categoria\": \"%s\",%n")
                        .formatted(indentacaoAtributos, categoria)
                + "%s\"atributo\": \"%s\",%n".formatted(indentacaoAtributos, atributo)
                + "%s\"tipo\": \"%s\",%n".formatted(indentacaoAtributos, tipo)
                + "%s\"obrigatorio\" %s,%n".formatted(indentacaoAtributos, !(obrigatorio == null))
                + "%s\"valorPadrao\": %s%n".formatted(indentacaoAtributos, pegarStringFormatadaValorPadrao())
                + "%s}".formatted(indentacaoChaves);
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
