/**
 * Copyright (C) 2025 Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 * <p>
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 * <p>
 * https://choosealicense.com/licenses/mit/
 * <p>
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 */
package io.github.heberbarra.modelador.infrastructure.configuracao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.tomlj.TomlTable;

/**
 * Responsável por combinar uma configuração existente com novas opções de configuração.
 *
 * @since v0.0.2-SNAPSHOT
 */
public class CombinadorConfiguracoes {

    /**
     * Combina a configuração padrão do programa com a configuração feita pelo usuário, respeitando os valores modificados.
     *
     * @param configuracaoPadrao a configuração padrão do programa
     * @param configuracao       a configuração do usuário
     * @param nomeAtributo       o nome do atributo no modelo da configuração padrão
     * @param nomeValor          o nome dos valores no modelo da configuração padrão
     * @return um {@link Map} com o resultado da combinação das configurações
     */
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
