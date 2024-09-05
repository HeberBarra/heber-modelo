package org.modelador.configurador;

import java.util.List;
import java.util.Map;

public class ConversorToml {

    public static String converterMapPaletaParaStringToml(Map<String, List<Map<String, String>>> dados) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[paleta]\n");

        for (Map<String, String> variavelPaleta : dados.get("paleta")) {
            stringBuilder.append("%s=".formatted(variavelPaleta.get("nomeVariavel")));
            stringBuilder.append("%s%n".formatted(variavelPaleta.get("valorPadraoVariavel")));
        }

        return stringBuilder.toString();
    }

    public static String converterMapConfiguracoesParaStringToml(Map<String, List<Map<String, String>>> dados) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String categoria : dados.keySet()) {
            stringBuilder.append("[%s]%n".formatted(categoria));

            for (Map<String, String> informacoesAtributo : dados.get(categoria)) {
                stringBuilder.append("%s=".formatted(informacoesAtributo.get("atributo")));

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
