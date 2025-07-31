/*
 * Copyright (c) 2025. Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
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

package io.github.heberbarra.modelador.domain.model;

import io.github.heberbarra.modelador.Principal;
import io.github.heberbarra.modelador.application.diagrama.ListadorTiposDiagrama;
import io.github.heberbarra.modelador.application.tradutor.TradutorWrapper;
import io.github.heberbarra.modelador.infrastructure.configuracao.ConfiguradorPrograma;
import io.github.heberbarra.modelador.infrastructure.configuracao.LeitorConfiguracao;
import org.springframework.stereotype.Service;
import org.tomlj.TomlTable;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebMapAtributos extends HashMap<String, Object> {

    private final ConfiguradorPrograma configurador = ConfiguradorPrograma.getInstance();

    public WebMapAtributos() {
        super();
        injetarPaleta();
    }

    public void injetarBindings() {
        LeitorConfiguracao leitorConfiguracao = configurador.getLeitorConfiguracao();
        TomlTable tabelaBindings = leitorConfiguracao.getInformacoesConfiguracoes().getTable("bindings");

        if (tabelaBindings == null) return;

        for (String nomeBinding : tabelaBindings.dottedKeySet()) {
            this.put(nomeBinding, tabelaBindings.get(nomeBinding));
        }
    }

    public void injetarInfoDiagramas(NovoDiagramaDTO novoDiagramaDTO) {
        this.put("novoDiagramaDTO", novoDiagramaDTO);
        this.put("diagramasUML", ListadorTiposDiagrama.pegarDiagramasUML());
        this.put("diagramasBD", ListadorTiposDiagrama.pegarDiagramasBancoDados());
        this.put("diagramasOutros", ListadorTiposDiagrama.pegarDiagramasOutros());
    }

    public void injetarTokenDesligar(String token) {
        if (configurador.pegarValorConfiguracao("programa", "desativar_botao_desligar", boolean.class)) return;

        this.put("desligar", token);
    }

    private void injetarPaleta() {
        Map<String, String> variaveisPaleta = ConfiguradorPrograma.getInstance().pegarInformacoesPaleta();
        StringBuilder stringBuilder = new StringBuilder(":root {%n".formatted());

        for (String variavel : variaveisPaleta.keySet()) {
            stringBuilder.append("    --%s: %s;%n".formatted(variavel.replace("_", "-"), variaveisPaleta.get(variavel)));
        }

        stringBuilder.append("  }%n".formatted());
        this.put("paleta", stringBuilder.toString());
    }

    public void adicionarTituloPagina(String chaveTitulo) {
        String titulo = TradutorWrapper.tradutor.traduzirMensagem(chaveTitulo);

        this.put("programa", Principal.NOME_PROGRAMA.replace("-", " ") + (titulo.isBlank() ? "" : " - " + titulo));
    }

}
