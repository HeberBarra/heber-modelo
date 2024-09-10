package org.modelador.configurador;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modelador.logger.JavaLogger;
import org.tomlj.Toml;
import org.tomlj.TomlTable;

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

    public void lerArquivos() {
        lerArquivoConfiguracoes();
        lerArquivoPaleta();
    }

    @SuppressWarnings("unchecked")
    public <T> @Nullable T pegarValorConfiguracao(String categoria, String atributo, Class<T> tipo) {
        TomlTable tabelaCategoria = informacoesConfiguracoes.getTable(categoria);

        if (tabelaCategoria == null) {
            logger.warning("A categoria %s não foi encontrada.%n".formatted(categoria));
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
            logger.warning("Tipo de atributo inválido.\n");
            return null;
        }
    }

    public @Nullable String pegarCorPaleta(String nomeVariavel) {
        return Objects.requireNonNull(informacoesPaleta.getTable("paleta")).getString(nomeVariavel);
    }

    private @NotNull TomlTable lerArquivo(String nomeArquivo) {
        try {
            return Toml.parse(Path.of(pastaConfiguracao, nomeArquivo));
        } catch (IOException e) {
            logger.severe("Falha ao tentar ler o arquivo %s. Erro: %s%n".formatted(nomeArquivo, e.getMessage()));
            logger.severe("Devido a uma falha grave, o program será encerrado");
            System.exit(0);
        }

        return null;
    }

    private void lerArquivoConfiguracoes() {
        informacoesConfiguracoes = lerArquivo(arquivoConfiguracoes);
    }

    private void lerArquivoPaleta() {
        informacoesPaleta = lerArquivo(arquivoPaleta);
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
