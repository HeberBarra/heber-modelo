package io.github.heberbarra.modelador.configurador.toml;

import java.util.List;
import java.util.Map;

/**
 * Converte uma entrada dados para uma string de tabela TOML
 * @since v0.0.1-SNAPSHOT
 * */
public class ConversorTomlPrograma implements ConversorToml {

    @Override
    public String converterMapPaletaParaStringToml(Map<String, List<Map<String, String>>> dados) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[paleta]\n");

        for (Map<String, String> variavelPaleta : dados.get("paleta")) {
            stringBuilder.append("%s%s=".formatted(" ".repeat(INDENTACAO), variavelPaleta.get("nomeVariavel")));
            stringBuilder.append("\"%s\"%n".formatted(variavelPaleta.get("valorPadraoVariavel")));
        }

        return stringBuilder.toString();
    }

    @Override
    public String converterMapConfiguracaoParaStringToml(Map<String, List<Map<String, String>>> dados) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String categoria : dados.keySet()) {
            stringBuilder.append("[%s]%n".formatted(categoria));

            for (Map<String, String> informacoesAtributo : dados.get(categoria)) {
                stringBuilder.append("%s%s=".formatted(" ".repeat(INDENTACAO), informacoesAtributo.get("atributo")));

                if (informacoesAtributo.get("tipo").equals("String")) {
                    stringBuilder.append("\"%s\"%n".formatted(informacoesAtributo.get("valorPadrao")));
                    continue;
                }

                stringBuilder.append("%s%n".formatted(informacoesAtributo.get("valorPadrao")));
            }
        }

        return stringBuilder.toString();
    }
}
