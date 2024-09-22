package org.modelador.configurador;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.modelador.codigosaida.CodigoSaida;
import org.modelador.configurador.verificador.VerificadorConfiguracao;
import org.modelador.logger.JavaLogger;

public class Configurador {

    public static final String ARQUIVO_PALETA = "paleta.toml";
    public static final String ARQUIVO_CONFIGURACOES = "config.toml";
    private static final Logger logger = JavaLogger.obterLogger(Configurador.class.getName());
    private final CriadorConfiguracoes criadorConfiguracoes;
    private final VerificadorConfiguracao verificadorConfiguracao;
    private final CombinadorConfiguracoes combinadorConfiguracoes;
    private final LeitorConfiguracao leitorConfiguracao;

    public Configurador() {
        criadorConfiguracoes = new CriadorConfiguracoes();
        verificadorConfiguracao = new VerificadorConfiguracao();
        leitorConfiguracao =
                new LeitorConfiguracao(PastaConfiguracao.PASTA_CONFIGURACAO, ARQUIVO_CONFIGURACOES, ARQUIVO_PALETA);
        combinadorConfiguracoes = new CombinadorConfiguracoes();
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
            System.exit(CodigoSaida.ERRO_CONFIGURACOES.getCodigo());
        }
    }

    public void mostrarConfiguracoes() {
        String[] informacoesConfiguracao = leitorConfiguracao.pegarStringConfiguracao();

        for (String informacao : informacoesConfiguracao) {
            System.out.println(informacao);
        }
    }

    public void combinarConfiguracoes() {
        Map<String, List<Map<String, String>>> dadosConfiguracoes = combinadorConfiguracoes.combinarConfiguracoes(
                criadorConfiguracoes.getConfiguracaoPadrao(),
                leitorConfiguracao.getInformacoesConfiguracoes(),
                "atributo",
                "valorPadrao");
        Map<String, List<Map<String, String>>> dadosPaleta = combinadorConfiguracoes.combinarConfiguracoes(
                criadorConfiguracoes.getPaletaPadrao(),
                leitorConfiguracao.getInformacoesPaleta(),
                "nomeVariavel",
                "valorPadraoVariavel");

        String dadosConfiguracoesToml = ConversorToml.converterMapConfiguracoesParaStringToml(dadosConfiguracoes);
        String dadosPaletaToml = ConversorToml.converterMapPaletaParaStringToml(dadosPaleta);

        criadorConfiguracoes.sobrescreverArquivoConfiguracoes(
                PastaConfiguracao.PASTA_CONFIGURACAO, ARQUIVO_CONFIGURACOES, dadosConfiguracoesToml);
        criadorConfiguracoes.sobrescreverArquivoPaleta(
                PastaConfiguracao.PASTA_CONFIGURACAO, ARQUIVO_PALETA, dadosPaletaToml);
        this.lerConfiguracoes();
    }

    public String pegarCorPaleta(String nomeVariavel) {
        return leitorConfiguracao.pegarCorPaleta(nomeVariavel);
    }

    public <T> T pegarValorConfiguracao(String categoria, String atributo, Class<T> tipo) {
        return leitorConfiguracao.pegarValorConfiguracao(categoria, atributo, tipo);
    }
}
