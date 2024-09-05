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
    private final LeitorConfiguracao leitorConfiguracao;

    public Configurador() {
        criadorConfiguracoes = new CriadorConfiguracoes();
        verificadorConfiguracao = new VerificadorConfiguracao();
        leitorConfiguracao =
                new LeitorConfiguracao(PastaConfiguracao.PASTA_CONFIGURACAO, ARQUIVO_CONFIGURACOES, ARQUIVO_PALETA);
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

    public void lerConfiguracoes() {
        leitorConfiguracao.lerArquivos();
    }

    public void verificarConfiguracoes() {
        verificadorConfiguracao.verificarArquivoConfiguracoes(
                criadorConfiguracoes.getConfiguracaoPadrao(), leitorConfiguracao.getInformacoesConfiguracoes());
        verificadorConfiguracao.verificarArquivoPaleta(
                criadorConfiguracoes.getPaletaPadrao(), leitorConfiguracao.getInformacoesPaleta());

        if (verificadorConfiguracao.isConfiguracaoErrada()) {
            logger.severe("A configuração contém erros graves. Para evitar bugs no programa, ele será encerrado");
            System.exit(1);
        }
    }

    public String pegarCorPaleta(String nomeVariavel) {
        return leitorConfiguracao.pegarCorPaleta(nomeVariavel);
    }

    public <T> T pegarValorConfiguracao(String categoria, String atributo, Class<T> tipo) {
        return leitorConfiguracao.pegarValorConfiguracao(categoria, atributo, tipo);
    }
}
