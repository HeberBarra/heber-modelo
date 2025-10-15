/*
 * Copyright (C) 2025 Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 *
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 *
 *   https://choosealicense.com/licenses/mit/
 *
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 *
 */

package io.github.heberbarra.modelador.infrastructure.configurador;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import io.github.heberbarra.modelador.domain.configurador.ICombinadorConfiguracoes;
import org.tomlj.TomlTable;

public class CombinadorConfiguracoes implements ICombinadorConfiguracoes {

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
