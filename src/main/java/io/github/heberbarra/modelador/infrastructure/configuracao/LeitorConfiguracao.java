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

package io.github.heberbarra.modelador.infrastructure.configuracao;

import io.github.heberbarra.modelador.application.logging.JavaLogger;
import io.github.heberbarra.modelador.application.tradutor.TradutorWrapper;
import io.github.heberbarra.modelador.domain.codigo.CodigoSaida;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import org.tomlj.Toml;
import org.tomlj.TomlTable;

/**
 * Lê os arquivos de configuração e a configuração padrão do programa.
 *
 * @since v0.0.2-SNAPSHOT
 */
public class LeitorConfiguracao {

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

    /**
     * Pega o valor de um atributo específico, sendo necessário indicar o nome, a tabela e o tipo do atributo
     *
     * @param categoria a tabela/categoria na qual se encontra o atributo
     * @param atributo  o nome do atributo desejado
     * @param tipo      o tipo do atributo, devendo ser de um dos seguintes tipos: {@code String}, {@code long}, {@code double} ou {@code boolean}
     * @return o valor do atributo requirido ou {@code null} caso valor não tenha sido encontrado.
     */
    @SuppressWarnings("unchecked")
    public <T> T pegarValorConfiguracao(String categoria, String atributo, Class<T> tipo) {
        TomlTable tabelaCategoria = informacoesConfiguracoes.getTable(categoria);

        if (tabelaCategoria == null) {
            logger.warning(TradutorWrapper.tradutor
                    .traduzirMensagem("error.config.category.notfound")
                    .formatted(categoria));
            return null;
        }

        if (tipo == long.class) {
            return (T) tabelaCategoria.getLong(atributo);
        } else if (tipo == double.class) {
            return (T) tabelaCategoria.getDouble(atributo);
        } else if (tipo == boolean.class) {
            return (T) tabelaCategoria.getBoolean(atributo);
        } else if (tipo == String.class) {
            return (T) tabelaCategoria.getString(atributo);
        } else {
            logger.warning(TradutorWrapper.tradutor.traduzirMensagem("error.config.attribute.get.invalid.type"));
            return null;
        }
    }

    /**
     * Pega o código hexadecimal da cor específica da paleta.
     *
     * @param nomeVariavel o nome da variável na paleta de cores
     * @return o código hexadecimal da cor
     */
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

    /**
     * Lê um arquivo TOML e retorna as informações lidas como uma tabela TOML
     * Caso ocorra um erro durante a leitura do arquivo, o programa será encerrado com o seguinte código de saída: {@link CodigoSaida#ERRO_LEITURA_ARQUIVO}
     *
     * @param nomeArquivo o nome do arquivo que deve ser lido
     * @return as informações lidas como uma tabela TOML
     */
    private TomlTable lerArquivo(String nomeArquivo) {
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

    public TomlTable lerArquivoConfiguracoesSemSalvar() {
        return lerArquivo(arquivoConfiguracoes);
    }

    private void lerArquivoPaleta() {
        informacoesPaleta = lerArquivo(arquivoPaleta);
    }

    public TomlTable lerArquivoPaletaSemSalvar() {
        return lerArquivo(arquivoPaleta);
    }

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
