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
import io.github.heberbarra.modelador.domain.model.json.AtributoJsonConfiguracao;
import io.github.heberbarra.modelador.domain.model.json.AtributoJsonPaleta;
import io.github.heberbarra.modelador.infrastructure.conversor.IConversorTOMLString;
import io.github.heberbarra.modelador.infrastructure.convesor.ConversorTomlPrograma;
import io.github.heberbarra.modelador.infrastructure.verificador.JsonVerificadorConfiguracoes;
import io.github.heberbarra.modelador.infrastructure.verificador.JsonVerificadorPaleta;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Responsável por criar os arquivos de configuração com as opções padrões se ainda não existirem.
 * Atualiza os arquivos com novas opções de configuração.
 *
 * @since v0.0.2-SNAPSHOT
 */
public class CriadorConfiguracoes {

    private static final Logger logger = JavaLogger.obterLogger(CriadorConfiguracoes.class.getName());
    private final IConversorTOMLString conversorToml;
    private final Map<String, List<Map<String, String>>> configuracaoPadrao;
    private final Map<String, List<Map<String, String>>> paletaPadrao;

    public CriadorConfiguracoes() {
        conversorToml = new ConversorTomlPrograma();
        configuracaoPadrao = new LinkedHashMap<>();
        paletaPadrao = new LinkedHashMap<>();
        paletaPadrao.put("paleta", new ArrayList<>());
    }

    /**
     * Cria, se necessário, a pasta de configuração e as pastas anteriores.
     *
     * @param pastaConfiguracao o caminho da pasta a ser criada
     */
    public void criarPastaConfiguracao(String pastaConfiguracao) {
        File pasta = new File(pastaConfiguracao);
        if (pasta.mkdirs()) {
            logger.info(TradutorWrapper.tradutor
                    .traduzirMensagem("file.dir.creation.success")
                    .formatted(pastaConfiguracao));
        }
    }

    /**
     * Coleta e salva a configuração padrão do programa.
     *
     * @param verificadorConfiguracoes verificador que contém as informações da configuração padrão
     */
    public void pegarConfiguracaoPadrao(JsonVerificadorConfiguracoes verificadorConfiguracoes) {
        for (AtributoJsonConfiguracao atributo : verificadorConfiguracoes.getAtributos()) {
            if (!configuracaoPadrao.containsKey(atributo.getCategoria())) {
                configuracaoPadrao.put(atributo.getCategoria(), new ArrayList<>());
            }

            configuracaoPadrao.get(atributo.getCategoria()).add(atributo.converterParaMap());
        }
    }

    /**
     * Coleta e salva a paleta padrão do programa.
     *
     * @param verificadorPaleta verificador que contém as informações da paleta padrão
     */
    public void pegarPaletaPadrao(JsonVerificadorPaleta verificadorPaleta) {
        List<Map<String, String>> atributos = paletaPadrao.get("paleta");

        for (AtributoJsonPaleta atributo : verificadorPaleta.getAtributos()) {
            atributos.add(atributo.converterParaMap());
        }
    }

    /**
     * Cria o arquivo de configuração, com as configurações padrões, mas não sobrescreve um arquivo existente.
     *
     * @param pastaConfiguracao    a pasta na qual o arquivo deve ser criado
     * @param arquivoConfiguracoes o nome do arquivo de configuração
     * @see CriadorConfiguracoes#sobrescreverArquivoConfiguracoes(String, String, String)
     */
    public void criarArquivoConfiguracoes(String pastaConfiguracao, String arquivoConfiguracoes) {
        String dadosToml = conversorToml.converterMapConfiguracaoParaStringTOML(configuracaoPadrao);
        criarArquivo(dadosToml, pastaConfiguracao + "/" + arquivoConfiguracoes);
    }

    /**
     * Cria o arquivo de paleta, com a configuração padrão, mas não sobrescreve um arquivo existente.
     *
     * @param pastaConfiguracao a pasta na qual o arquivo deve ser criado
     * @param arquivoPaleta     o nome do arquivo de paleta
     * @see CriadorConfiguracoes#sobrescreverArquivoPaleta(String, String, String)
     */
    public void criarArquivoPaleta(String pastaConfiguracao, String arquivoPaleta) {
        String dadosToml = conversorToml.converterMapPaletaParaStringTOML(paletaPadrao);
        criarArquivo(dadosToml, pastaConfiguracao + "/" + arquivoPaleta);
    }

    /**
     * Sobrescreve, sem confirmação, um arquivo de configuração com os dados fornecidos.
     *
     * @param pastaConfiguracao    - a pasta na qual o arquivo deve ser criado/sobrescrito
     * @param arquivoConfiguracoes - o nome do arquivo de configurações
     * @param dadosToml            - os dados a serem escritos no arquivo
     */
    public void sobrescreverArquivoConfiguracoes(
            String pastaConfiguracao, String arquivoConfiguracoes, String dadosToml) {
        criarArquivo(dadosToml, pastaConfiguracao + "/" + arquivoConfiguracoes, false);
    }

    /**
     * Sobrescreve, sem confirmação, um arquivo de paleta com os dados fornecidos.
     *
     * @param pastaConfiguracao - a pasta na qual o arquivo deve ser criado/sobrescrito
     * @param arquivoPaleta     - o nome do arquivo de paleta
     * @param dadosToml         - os dados a serem escritos no arquivo
     * @see CriadorConfiguracoes#criarArquivoPaleta(String, String)
     */
    public void sobrescreverArquivoPaleta(String pastaConfiguracao, String arquivoPaleta, String dadosToml) {
        criarArquivo(dadosToml, pastaConfiguracao + "/" + arquivoPaleta, false);
    }

    /**
     * Cria o arquivo com os dados fornecidos sem sobrescrever um arquivo existente.
     *
     * @param dadosToml      os dados a serem escritos
     * @param caminhoArquivo o caminho até o arquivo
     */
    private void criarArquivo(String dadosToml, String caminhoArquivo) {
        criarArquivo(dadosToml, caminhoArquivo, true);
    }

    /**
     * Cria o arquivo com os dados fornecidos, com a opção de sobreescrever um arquivo existente.
     *
     * @param dadosToml        os dados a serem escritos
     * @param caminhoArquivo   o caminho até o arquivo
     * @param naoSobreescrever se um arquivo existente deve ou não ser sobrescrito
     */
    private void criarArquivo(String dadosToml, String caminhoArquivo, boolean naoSobreescrever) {
        File arquivo = new File(caminhoArquivo);

        if (naoSobreescrever && arquivo.length() != 0) {
            return;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bufferedWriter.write(dadosToml);
        } catch (IOException e) {
            logger.severe(TradutorWrapper.tradutor
                    .traduzirMensagem("error.file.create")
                    .formatted(caminhoArquivo, e.getMessage()));
            logger.severe(TradutorWrapper.tradutor.traduzirMensagem("app.end"));
            System.exit(CodigoSaida.ERRO_CRIACAO_CONFIG.getCodigo());
        }
    }

    public Map<String, List<Map<String, String>>> getConfiguracaoPadrao() {
        return configuracaoPadrao;
    }

    public Map<String, List<Map<String, String>>> getPaletaPadrao() {
        return paletaPadrao;
    }
}
