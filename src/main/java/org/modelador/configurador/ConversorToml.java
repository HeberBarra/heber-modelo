package org.modelador.configurador;

import java.util.Map;
import org.tomlj.Toml;
import org.tomlj.TomlTable;

public class ConversorToml {

    protected static final int NUMERO_ESPACOS = 2;
    protected static final String INDENTACAO = " ".repeat(NUMERO_ESPACOS);

    public static TomlTable converterStringParaToml(String tomlString) {
        // TODO: Implementar conversor
        return Toml.parse("");
    }

    public static String converterMapParaString(Map<String, Map<String, Object>> dados) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String chave : dados.keySet()) {
            stringBuilder.append("[%s]\n".formatted(chave));
            Map<String, Object> tabela = dados.get(chave);

            for (String chaveTabela : tabela.keySet()) {
                stringBuilder
                        .append("%s%s=%s"
                                .formatted(
                                        INDENTACAO,
                                        chaveTabela,
                                        tabela.get(chaveTabela).toString()))
                        .append("\n");
            }
        }

        return stringBuilder.toString();
    }
}
