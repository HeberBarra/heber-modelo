package org.modelador.configurador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import org.modelador.logger.JavaLogger;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

public class Configurador {

    private static final Logger logger = JavaLogger.obterLogger(Configurador.class.getName());
    public static final Path PASTA_TEMPLATES = Path.of("src/main/resources");
    public static final PastaConfiguracao PASTA_CONFIGURACAO = detectarSistemaOperacional();
    public static final Path TEMPLATE_CONFIGURACOES = Path.of(PASTA_TEMPLATES + "/template.toml");
    public static final Path TEMPLATE_PALETA = Path.of(PASTA_TEMPLATES + "/template_paleta.toml");
    public static final String ARQUIVO_CONFIGURACOES = "configuracoes.toml";
    public static final String ARQUIVO_PALETA = "paleta.toml";
    public static TomlParseResult configuracoes = lerConfiguracoes(ARQUIVO_CONFIGURACOES);

    static {
        JavaLogger.desativarLogger(
                pegarValorConfiguracao("logger", "desativar", boolean.class), logger);
        atualizarArquivoConfiguracoes();
    }

    public static String pegarInformacoesConfiguracoes(TomlParseResult configuracoes) {
        StringBuilder informacoesConfiguracoes = new StringBuilder();

        for (var nomeTabela : configuracoes.keySet()) {
            informacoesConfiguracoes.append(String.format("[%s]\n", nomeTabela));

            TomlTable tabela = configuracoes.getTable(nomeTabela);
            assert tabela != null;
            for (var chave : tabela.keySet()) {
                informacoesConfiguracoes.append(
                        String.format("%s = %s\n", chave, tabela.get(chave)));
            }
        }

        return informacoesConfiguracoes.toString();
    }

    public static void recarregarConfiguracoes() {
        atualizarArquivoConfiguracoes();
        configuracoes = lerConfiguracoes(ARQUIVO_CONFIGURACOES);
    }

    public static PastaConfiguracao detectarSistemaOperacional() {
        String nomeSistema = System.getProperty("os.name").toLowerCase();

        if (nomeSistema.contains("windows")) {
            return PastaConfiguracao.WINDOWS;
        } else if (nomeSistema.contains("mac") || nomeSistema.contains("darwin")) {
            return PastaConfiguracao.MAC;
        } else {
            return PastaConfiguracao.UNIX;
        }
    }

    public static TomlParseResult lerTemplateConfiguracoes() {
        try {
            return Toml.parse(TEMPLATE_CONFIGURACOES);
        } catch (IOException e) {
            logger.warning(
                    String.format("Falha ao ler template das configurações: %s", e.getMessage()));
        }

        return null;
    }

    public static TomlParseResult lerConfiguracoes(String arquivo) {
        criarArquivoConfiguracoes();
        if (!arquivo.startsWith("/")) {
            arquivo = "/" + arquivo;
        }

        String caminhoArquivoConfiguracoes = PASTA_CONFIGURACAO.getCaminhoPasta() + arquivo;

        try {
            Path caminhoArquivo = Path.of(caminhoArquivoConfiguracoes);
            return Toml.parse(caminhoArquivo);
        } catch (FileNotFoundException e) {
            logger.warning(
                    String.format(
                            "Arquivo de configurações %s não encontrado",
                            caminhoArquivoConfiguracoes));
        } catch (NoSuchFileException e) {
            logger.warning(String.format("Arquivo: %s não existe", caminhoArquivoConfiguracoes));
        } catch (IOException e) {
            logger.warning(
                    String.format(
                            "Erro ao ler arquivo de configuracoes: %s - %s",
                            arquivo, e.getMessage()));
        }

        return null;
    }

    public static <T> T pegarValorConfiguracao(
            String nomeTabela, String chave, Class<T> tipoValor) {
        TomlTable tabela = configuracoes.getTableOrEmpty(nomeTabela);
        var valor = tabela.get(chave);

        if (tipoValor == int.class && valor != null) {
            valor = Math.toIntExact((long) valor);
        }

        return (T) valor;
    }

    public static TomlParseResult combinarConfiguracoes(
            TomlTable templateConfiguracoes, TomlTable configuracoes) {
        Map<String, Map<String, Object>> combinacaoConfiguracoes = new LinkedHashMap<>();

        for (String nomeTabela : templateConfiguracoes.keySet()) {
            TomlTable templateTabela = templateConfiguracoes.getTable(nomeTabela);
            Map<String, Object> mapTabela = new LinkedHashMap<>();

            for (String chaveTabela : Objects.requireNonNull(templateTabela).keySet()) {
                TomlTable tabelaConfiguracoes = configuracoes.getTable(chaveTabela);

                if (tabelaConfiguracoes == null) {
                    mapTabela.put(chaveTabela, templateTabela.get(chaveTabela));
                    continue;
                }

                Object valor = tabelaConfiguracoes.get(chaveTabela);

                if (valor == null) {
                    valor = templateTabela.get(chaveTabela);
                }

                mapTabela.put(chaveTabela, valor);
            }

            combinacaoConfiguracoes.put(nomeTabela, mapTabela);
        }

        String resultadoCombinacao = ConversorToml.converterMapParaString(combinacaoConfiguracoes);
        return Toml.parse(resultadoCombinacao);
    }

    public static void criarArquivoConfiguracoes() {
        File pastaConfiguracoes = PASTA_CONFIGURACAO.getCaminhoPasta().toFile();
        File arquivoConfiguracoes =
                new File(PASTA_CONFIGURACAO.getCaminhoPasta() + "/" + ARQUIVO_CONFIGURACOES);

        try {
            logger.fine(String.valueOf(pastaConfiguracoes.mkdir()));
            logger.fine(String.valueOf(arquivoConfiguracoes.createNewFile()));
        } catch (IOException e) {
            logger.warning(
                    String.format(
                            "Erro ao criar o arquivo de configurações: %s - %s",
                            arquivoConfiguracoes, e.getMessage()));
        }

        if (arquivoConfiguracoes.length() != 0) {
            return;
        }

        try (BufferedWriter bufferedWriter =
                new BufferedWriter(new FileWriter(arquivoConfiguracoes))) {
            bufferedWriter.write(Objects.requireNonNull(lerTemplateConfiguracoes()).toToml());
        } catch (IOException e) {
            logger.warning(
                    String.format(
                            "Erro ao tentar ler o arquivo de configuracões: %s - %s",
                            arquivoConfiguracoes, e.getMessage()));
        } catch (NullPointerException e) {
            logger.severe(
                    String.format(
                            "Arquivo de configurações está vazio e houve um problema na leitura da template: %s",
                            e.getMessage()));
            logger.severe("Encerrando execução do programa...");
            System.exit(1);
        }
    }

    public static void atualizarArquivoConfiguracoes() {
        TomlParseResult resultadoCombinacao =
                combinarConfiguracoes(
                        Objects.requireNonNull(lerTemplateConfiguracoes()), configuracoes);
        File arquivoConfiguracoes =
                new File(PASTA_CONFIGURACAO.getCaminhoPasta() + "/" + ARQUIVO_CONFIGURACOES);

        try (BufferedWriter bufferedWriter =
                new BufferedWriter(new FileWriter(arquivoConfiguracoes))) {
            bufferedWriter.write(resultadoCombinacao.toToml());
        } catch (IOException e) {
            logger.warning(
                    String.format(
                            "Erro ao tentar ler o arquivo de configuracões: %s - %s",
                            arquivoConfiguracoes, e.getMessage()));
        }
    }
}
