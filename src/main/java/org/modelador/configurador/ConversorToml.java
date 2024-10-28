package org.modelador.configurador;

import java.util.List;
import java.util.Map;

/**
 * Converte uma entrada dados para uma string de tabela TOML
 * @since v0.0.1-SNAPSHOT
 * */
public class ConversorToml {

    private static final int INDENTACAO = 2;

    /**
     * Converte um {@link Map} que representa a paleta de cores do programa para a seguinte tabela:
     * <p>
     * [paleta]<br>
     * &nbsp;&nbsp;variavel="#xxxxxx"
     *
     * @param dados os dados a serem convertidos numa tabela
     * @return uma string que contém a tabela TOML criada a partir dos dados.
     * */
    public static String converterMapPaletaParaStringToml(Map<String, List<Map<String, String>>> dados) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[paleta]\n");

        for (Map<String, String> variavelPaleta : dados.get("paleta")) {
            stringBuilder.append("%s%s=".formatted(" ".repeat(INDENTACAO), variavelPaleta.get("nomeVariavel")));
            stringBuilder.append("\"%s\"%n".formatted(variavelPaleta.get("valorPadraoVariavel")));
        }

        return stringBuilder.toString();
    }

    /**
     * Converte um {@link Map} que representa as tabelas do arquivo de configuração em uma tabela TOML
     * @param dados os dados a serem convertidos numa tabela
     * @return uma string que contém a tabela TOML
     * */
    public static String converterMapConfiguracoesParaStringToml(Map<String, List<Map<String, String>>> dados) {
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
