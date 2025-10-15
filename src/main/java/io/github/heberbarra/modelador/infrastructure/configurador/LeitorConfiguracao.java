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

import io.github.heberbarra.modelador.application.logging.JavaLogger;
import io.github.heberbarra.modelador.application.tradutor.TradutorWrapper;
import io.github.heberbarra.modelador.domain.codigo.CodigoSaida;
import io.github.heberbarra.modelador.domain.configurador.ILeitorConfiguracao;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import org.tomlj.Toml;
import org.tomlj.TomlTable;

/**
 * Lê os arquivos de configuração e a configuração padrão do programa.
 *
 * @since v0.0.2-SNAPSHOT
 */
public class LeitorConfiguracao implements ILeitorConfiguracao {

    private static final Logger logger = JavaLogger.obterLogger(LeitorConfiguracao.class.getName());
    private String pastaConfiguracao;
    private String arquivoConfiguracoes;
    private String arquivoPaleta;
    private TomlTable informacoesConfiguracoes;
    private TomlTable informacoesPaleta;

    public LeitorConfiguracao(String pastaConfiguracao, String arquivoConfiguracoes, String arquivoPaleta) {
        this.pastaConfiguracao = pastaConfiguracao;
        this.arquivoConfiguracoes = arquivoConfiguracoes;
        this.arquivoPaleta = arquivoPaleta;
    }

    /**
     * Lê os arquivos de configuração do programa e salva as informações lidas
     */
    public void lerArquivos() {
        lerArquivoConfiguracoes();
        lerArquivoPaleta();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> pegarValorConfiguracao(String categoria, String atributo, Class<T> tipo) {
        TomlTable tabelaCategoria = informacoesConfiguracoes.getTable(categoria);

        if (tabelaCategoria == null) {
            logger.warning(TradutorWrapper.tradutor
                    .traduzirMensagem("error.config.category.notfound")
                    .formatted(categoria));
            return Optional.empty();
        }

        if (tipo == long.class) {
            return Optional.ofNullable((T) tabelaCategoria.getLong(atributo));
        } else if (tipo == double.class) {
            return Optional.ofNullable((T) tabelaCategoria.getDouble(atributo));
        } else if (tipo == boolean.class) {
            return Optional.ofNullable((T) tabelaCategoria.getBoolean(atributo));
        } else if (tipo == String.class) {
            return Optional.ofNullable((T) tabelaCategoria.getString(atributo));
        } else {
            logger.warning(TradutorWrapper.tradutor.traduzirMensagem("error.config.attribute.get.invalid.type"));
            return Optional.empty();
        }
    }

    @Override
    public String pegarCorPaleta(String nomeVariavel) {
        return Objects.requireNonNull(informacoesPaleta.getTable("paleta")).getString(nomeVariavel);
    }

    /**
     * Pega todas as variáveis da paleta com seus respectivos códigos hexadecimais
     *
     * @return um {@link Map} cuja chave é o nome da variável e o valor é o código hexadecimal
     */
    public Map<String, String> pegarVariaveisPaleta() {
        Map<String, String> informacoes = new LinkedHashMap<>();
        Map<String, Object> valores =
                Objects.requireNonNull(informacoesPaleta.getTable("paleta")).toMap();

        for (String chave : valores.keySet()) {
            informacoes.put(chave, String.valueOf(valores.get(chave)));
        }

        return informacoes;
    }

    @Override
    public TomlTable lerArquivo(String nomeArquivo) {
        try {
            return Toml.parse(Path.of(pastaConfiguracao, nomeArquivo));
        } catch (IOException e) {
            logger.severe(TradutorWrapper.tradutor
                    .traduzirMensagem("error.file.read")
                    .formatted(nomeArquivo, e.getMessage()));
            logger.severe(TradutorWrapper.tradutor.traduzirMensagem("app.end"));
            System.exit(CodigoSaida.ERRO_LEITURA_ARQUIVO.getCodigo());
        }

        return null;
    }

    private void lerArquivoConfiguracoes() {
        informacoesConfiguracoes = lerArquivo(arquivoConfiguracoes);
    }

    @Override
    public TomlTable lerArquivoConfiguracoesSemSalvar() {
        return lerArquivo(arquivoConfiguracoes);
    }

    private void lerArquivoPaleta() {
        informacoesPaleta = lerArquivo(arquivoPaleta);
    }

    @Override
    public TomlTable lerArquivoPaletaSemSalvar() {
        return lerArquivo(arquivoPaleta);
    }

    @Override
    public String[] pegarStringConfiguracao() {
        String tomlConfiguracoes = informacoesConfiguracoes.toToml();
        String tomlPaleta = informacoesPaleta.toToml();

        return new String[] {tomlConfiguracoes, tomlPaleta};
    }

    public String getPastaConfiguracao() {
        return pastaConfiguracao;
    }

    public void setPastaConfiguracao(String pastaConfiguracao) {
        this.pastaConfiguracao = pastaConfiguracao;
    }

    public String getArquivoConfiguracoes() {
        return arquivoConfiguracoes;
    }

    public void setArquivoConfiguracoes(String arquivoConfiguracoes) {
        this.arquivoConfiguracoes = arquivoConfiguracoes;
    }

    public String getArquivoPaleta() {
        return arquivoPaleta;
    }

    public void setArquivoPaleta(String arquivoPaleta) {
        this.arquivoPaleta = arquivoPaleta;
    }

    public TomlTable getInformacoesConfiguracoes() {
        return informacoesConfiguracoes;
    }

    protected void setInformacoesConfiguracoes(TomlTable informacoesConfiguracoes) {
        this.informacoesConfiguracoes = informacoesConfiguracoes;
    }

    public TomlTable getInformacoesPaleta() {
        return informacoesPaleta;
    }

    protected void setInformacoesPaleta(TomlTable informacoesPaleta) {
        this.informacoesPaleta = informacoesPaleta;
    }
}
