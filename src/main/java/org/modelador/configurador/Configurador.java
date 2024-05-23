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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modelador.configurador.paleta.Paleta;
import org.modelador.exploraradorarquivos.Recurso;
import org.modelador.logger.JavaLogger;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

public class Configurador {

    private static final Logger logger = JavaLogger.obterLogger(Configurador.class.getName());
    protected static Recurso recurso = new Recurso();
    public static final PastaConfiguracao PASTA_CONFIGURACAO = detectarSistemaOperacional();
    public static final Path TEMPLATE_CONFIGURACOES = recurso.pegarCaminhoRecurso("template.toml");
    public static final Path TEMPLATE_PALETA = recurso.pegarCaminhoRecurso("template_paleta.toml");
    public static final String ARQUIVO_CONFIGURACOES = "configuracoes.toml";
    public static final String ARQUIVO_PALETA = "paleta.toml";
    public static TomlTable configuracoes = lerConfiguracoes();
    public static TomlTable paleta = lerPaleta();

    public static void recarregarConfiguracoes() {
        JavaLogger.desativarLogger(pegarValorConfiguracao("logger", "desativar", boolean.class), Logger.getLogger(""));
        atualizarConfiguracoes(ARQUIVO_CONFIGURACOES, TEMPLATE_CONFIGURACOES, lerConfiguracoes());
        configuracoes = lerConfiguracoes();
        paleta = lerPaleta();
        Paleta.setTemplatePaleta(lerTemplateConfiguracoes(TEMPLATE_PALETA));
        Paleta.setInformacoesPaleta(paleta);
        Paleta.limparCache();
    }

    public static @NotNull PastaConfiguracao detectarSistemaOperacional() {
        String nomeSistema = System.getProperty("os.name").toLowerCase();

        if (nomeSistema.contains("windows")) {
            return PastaConfiguracao.WINDOWS;
        } else if (nomeSistema.contains("mac") || nomeSistema.contains("darwin")) {
            return PastaConfiguracao.MAC;
        } else {
            return PastaConfiguracao.UNIX;
        }
    }

    public static @Nullable TomlParseResult lerTemplateConfiguracoes(@NotNull Path template) {
        try {
            return Toml.parse(template);
        } catch (IOException e) {
            logger.warning("Falha ao ler template das configurações: %n%s".formatted(e.getMessage()));
        }

        return null;
    }

    protected static @NotNull TomlParseResult lerArquivo(@NotNull String arquivo) {
        if (!arquivo.startsWith("/")) {
            arquivo = "/" + arquivo;
        }

        Path caminhoArquivo = Path.of(PASTA_CONFIGURACAO.getCaminhoPasta() + arquivo);

        try {
            return Toml.parse(caminhoArquivo);
        } catch (FileNotFoundException e) {
            logger.severe("Arquivo %s não encontrado".formatted(caminhoArquivo));
        } catch (NoSuchFileException e) {
            logger.severe("Arquivo: %s não existe".formatted(caminhoArquivo));
        } catch (IOException e) {
            logger.severe("Erro ao ler arquivo: %s. %n%s".formatted(arquivo, e.getMessage()));
        }

        logger.severe("Erro crítico ao tentar ler arquivo %s, portanto o programa será encerrado.".formatted(arquivo));
        System.exit(1);
        return null;
    }

    public static @NotNull TomlTable lerConfiguracoes() {
        criarArquivoConfiguracoes();
        return lerArquivo(ARQUIVO_CONFIGURACOES);
    }

    public static @NotNull TomlTable lerPaleta() {
        criarArquivoPaleta();
        return lerArquivo(ARQUIVO_PALETA);
    }

    @SuppressWarnings("unchecked")
    public static @NotNull <T> T pegarValorConfiguracao(
            @NotNull String nomeTabela, @NotNull String chave, @NotNull Class<T> tipoValor) {
        TomlTable tabela = configuracoes.getTableOrEmpty(nomeTabela);
        var valor = Objects.requireNonNull(tabela.get(chave));

        if (tipoValor == int.class) {
            valor = Math.toIntExact((long) valor);
        }

        return (T) valor;
    }

    public static @NotNull TomlParseResult combinarConfiguracoes(
            @NotNull TomlTable templateConfiguracoes, @NotNull TomlTable configuracoes) {
        Map<String, Map<String, Object>> combinacaoConfiguracoes = new LinkedHashMap<>();

        for (String nomeTabela : templateConfiguracoes.keySet()) {
            TomlTable templateTabela = Objects.requireNonNull(templateConfiguracoes.getTable(nomeTabela));
            TomlTable tabelaConfiguracoes = configuracoes.getTable(nomeTabela);
            Map<String, Object> mapTabela = new LinkedHashMap<>();

            if (tabelaConfiguracoes == null) {
                combinacaoConfiguracoes.put(nomeTabela, templateTabela.toMap());
                continue;
            }

            for (String chaveTabela : templateTabela.keySet()) {
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

    protected static void criarArquivo(@NotNull String arquivo, @NotNull Path template) {
        File pastaConfiguracoes = PASTA_CONFIGURACAO.getCaminhoPasta().toFile();
        File arquivoConfiguracoes = new File(PASTA_CONFIGURACAO.getCaminhoPasta() + "/" + arquivo);

        try {
            logger.fine(String.valueOf(pastaConfiguracoes.mkdir()));
            logger.fine(String.valueOf(arquivoConfiguracoes.createNewFile()));
        } catch (IOException e) {
            logger.warning("Erro ao criar o arquivo de configurações: %s. %n%s"
                    .formatted(arquivoConfiguracoes, e.getMessage()));
        }

        if (arquivoConfiguracoes.length() != 0) {
            return;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(arquivoConfiguracoes))) {
            bufferedWriter.write(
                    Objects.requireNonNull(lerTemplateConfiguracoes(template)).toToml());
        } catch (IOException e) {
            logger.warning("Erro ao tentar ler o arquivo de configuracões: %s%n%s"
                    .formatted(arquivoConfiguracoes, e.getMessage()));
        } catch (NullPointerException e) {
            logger.severe("Arquivo de configurações está vazio e houve um problema na leitura da template: %n%s"
                    .formatted(e.getMessage()));
            logger.severe("Encerrando execução do programa...");
            System.exit(1);
        }
    }

    public static void criarArquivoConfiguracoes() {
        criarArquivo(ARQUIVO_CONFIGURACOES, TEMPLATE_CONFIGURACOES);
    }

    public static void criarArquivoPaleta() {
        criarArquivo(ARQUIVO_PALETA, TEMPLATE_PALETA);
    }

    public static void atualizarConfiguracoes(
            @NotNull String arquivo, @NotNull Path template, @NotNull TomlTable informacoes) {
        TomlTable resultadoCombinacao =
                combinarConfiguracoes(Objects.requireNonNull(lerTemplateConfiguracoes(template)), informacoes);
        File arquivoConfiguracoes = new File(PASTA_CONFIGURACAO.getCaminhoPasta() + "/" + arquivo);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(arquivoConfiguracoes))) {
            bufferedWriter.write(resultadoCombinacao.toToml());
        } catch (IOException e) {
            logger.warning(
                    "Falha ao tentar atualizar o arquivo: %s. %n%s".formatted(arquivoConfiguracoes, e.getMessage()));
        }
    }
}
