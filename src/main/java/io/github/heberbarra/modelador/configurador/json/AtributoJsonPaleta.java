package io.github.heberbarra.modelador.configurador.json;

import java.util.LinkedHashMap;
import java.util.Map;

public class AtributoJsonPaleta extends AtributoJson {

    private String nomeVariavel;
    private String valorPadraoVariavel;

    @Override
    public Map<String, String> converterParaMap() {
        Map<String, String> informacoesAtributo = new LinkedHashMap<>();

        informacoesAtributo.put("nomeVariavel", nomeVariavel);
        informacoesAtributo.put("valorPadraoVariavel", valorPadraoVariavel);

        return informacoesAtributo;
    }

    @Override
    public String toString() {
        String indentacaoAtributos = " ".repeat(indentacao * nivelIndentacao);
        String indentacaoChaves = " ".repeat(indentacao * (nivelIndentacao - 1));

        return ("%s{%n".formatted(indentacaoChaves)
                + "%s\"nomeVariavel\": \"%s\",%n".formatted(indentacaoAtributos, nomeVariavel)
                + "%s\"valorPadraoVariavel\": \"%s\"%n".formatted(indentacaoAtributos, valorPadraoVariavel)
                + "%s}".formatted(indentacaoChaves));
    }

    public String getNomeVariavel() {
        return nomeVariavel;
    }

    public String getValorPadraoVariavel() {
        return valorPadraoVariavel;
    }
}
