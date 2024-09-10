package org.modelador.configurador;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.modelador.logger.JavaLogger;
import org.tomlj.TomlTable;

public class CombinadorConfiguracoes {

    private static final Logger logger = JavaLogger.obterLogger(CombinadorConfiguracoes.class.getName());

    public Map<String, List<Map<String, String>>> combinarConfiguracoes(
            Map<String, List<Map<String, String>>> configuracaoPadrao,
            TomlTable configuracao,
            String nomeAtributo,
            String nomeValor) {
        Map<String, List<Map<String, String>>> novaConfiguracao = new LinkedHashMap<>();

        for (String categoria : configuracaoPadrao.keySet()) {
            List<Map<String, String>> atributos = new ArrayList<>();
            List<Map<String, String>> tabelaPadraoCategoria = configuracaoPadrao.get(categoria);
            TomlTable tabelaCategoria = configuracao.getTable(categoria);

            if (tabelaCategoria == null) {
                novaConfiguracao.put(categoria, tabelaPadraoCategoria);
                continue;
            }

            for (Map<String, String> atributo : tabelaPadraoCategoria) {
                String atributoUsuario = null;
                Object valorAtributo = tabelaCategoria.get(atributo.get(nomeAtributo));

                if (valorAtributo != null) {
                    atributoUsuario = String.valueOf(valorAtributo);
                }

                if (atributoUsuario != null) {
                    atributo.replace(nomeValor, atributoUsuario);
                }

                atributos.add(atributo);
            }

            novaConfiguracao.put(categoria, atributos);
        }

        return novaConfiguracao;
    }
}
