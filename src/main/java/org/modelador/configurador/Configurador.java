package org.modelador.configurador;

import java.util.logging.Logger;
import org.modelador.configurador.verificador.VerificadorConfiguracao;
import org.modelador.logger.JavaLogger;

public class Configurador {

    public static final String ARQUIVO_PALETA = "paleta.toml";
    public static final String ARQUIVO_CONFIGURACOES = "config.toml";
    private static final Logger logger = JavaLogger.obterLogger(Configurador.class.getName());
    private final CriadorConfiguracoes criadorConfiguracoes;
    private final VerificadorConfiguracao verificadorConfiguracao;

    public Configurador() {
        criadorConfiguracoes = new CriadorConfiguracoes();
        verificadorConfiguracao = new VerificadorConfiguracao();
    }

    private void lerConfiguracaoPadrao() {
        verificadorConfiguracao.lerArquivosTemplate();
        criadorConfiguracoes.pegarConfiguracaoPadrao(
                verificadorConfiguracao.getLeitorConfiguracoes().getInformacoesJson());
        criadorConfiguracoes.pegarPaletaPadrao(
                verificadorConfiguracao.getLeitorPaleta().getInformacoesJson());
    }

    public void criarArquivos() {
        criadorConfiguracoes.criarPastaConfiguracao(PastaConfiguracao.PASTA_CONFIGURACAO);
        lerConfiguracaoPadrao();
        criadorConfiguracoes.criarArquivoConfiguracoes(PastaConfiguracao.PASTA_CONFIGURACAO, ARQUIVO_CONFIGURACOES);
        criadorConfiguracoes.criarArquivoPaleta(PastaConfiguracao.PASTA_CONFIGURACAO, ARQUIVO_PALETA);
    }
}
