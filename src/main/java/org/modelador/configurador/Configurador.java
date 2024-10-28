package org.modelador.configurador;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.modelador.codigosaida.CodigoSaida;
import org.modelador.configurador.verificador.VerificadorConfiguracao;
import org.modelador.logger.JavaLogger;

/**
 * Gerencia as configurações do programa, e permite acesso fácil às mesmas.
 * <p>
 * Utiliza o anti pattern {@code Singleton} para garantir o compartilhamento das informações entre os utilizadores.
 * @since v0.0.1-SNAPSHOT
 * */
public final class Configurador {

    public static final String ARQUIVO_PALETA = "paleta.toml";
    public static final String ARQUIVO_CONFIGURACOES = "config.toml";
    private static final Logger logger = JavaLogger.obterLogger(Configurador.class.getName());
    private static volatile Configurador configurador;
    private final CriadorConfiguracoes criadorConfiguracoes;
    private final VerificadorConfiguracao verificadorConfiguracao;
    private final CombinadorConfiguracoes combinadorConfiguracoes;
    private final LeitorConfiguracao leitorConfiguracao;

    private Configurador() {
        criadorConfiguracoes = new CriadorConfiguracoes();
        verificadorConfiguracao = new VerificadorConfiguracao();
        leitorConfiguracao =
                new LeitorConfiguracao(PastaConfiguracao.PASTA_CONFIGURACAO, ARQUIVO_CONFIGURACOES, ARQUIVO_PALETA);
        combinadorConfiguracoes = new CombinadorConfiguracoes();
    }

    public static synchronized Configurador getInstance() {
        if (configurador == null) {
            configurador = new Configurador();
        }

        return configurador;
    }

    /**
     * Lê as configurações padrões do programa, para permitir a geração dos arquivos caso não existam ainda
     * @see CriadorConfiguracoes
     * @see Configurador#criarArquivos()
     * */
    private void lerConfiguracaoPadrao() {
        verificadorConfiguracao.lerArquivosTemplate();
        criadorConfiguracoes.pegarConfiguracaoPadrao(
                verificadorConfiguracao.getLeitorConfiguracoes().getInformacoesJson());
        criadorConfiguracoes.pegarPaletaPadrao(
                verificadorConfiguracao.getLeitorPaleta().getInformacoesJson());
    }

    /**
     * Cria a pasta e os arquivos de configuração se não existirem.
     * @see CriadorConfiguracoes
     * */
    public void criarArquivos() {
        criadorConfiguracoes.criarPastaConfiguracao(PastaConfiguracao.PASTA_CONFIGURACAO);
        lerConfiguracaoPadrao();
        criadorConfiguracoes.criarArquivoConfiguracoes(PastaConfiguracao.PASTA_CONFIGURACAO, ARQUIVO_CONFIGURACOES);
        criadorConfiguracoes.criarArquivoPaleta(PastaConfiguracao.PASTA_CONFIGURACAO, ARQUIVO_PALETA);
    }

    /**
     * Lê as configurações do programa.
     * @see LeitorConfiguracao
     * */
    public void lerConfiguracoes() {
        leitorConfiguracao.lerArquivos();
    }

    /**
     * Verifica se há algum erro na configuração do programa, caso haja um erro o grave o programa é encerrado com o código de saída: {@link CodigoSaida#ERRO_CONFIGURACOES}.
     * @see VerificadorConfiguracao
     * */
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

    /**
     * Mostra as configurações atuais do programa
     * @see LeitorConfiguracao
     * */
    public void mostrarConfiguracoes() {
        String[] informacoesConfiguracao = leitorConfiguracao.pegarStringConfiguracao();

        for (String informacao : informacoesConfiguracao) {
            System.out.println(informacao);
        }
    }

    /**
     * Atualiza as configurações do programa, mantendo os valores, com novas opções, se houverem, de configuração
     * @see CombinadorConfiguracoes
     * */
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

    /**
     * Pega o código hexadecimal de uma cor específica da paleta.
     * @param nomeVariavel o nome da variável na paleta de cores
     * @return o código hexadecimal da cor
     * @see LeitorConfiguracao
     * */
    public String pegarCorPaleta(String nomeVariavel) {
        return leitorConfiguracao.pegarCorPaleta(nomeVariavel);
    }

    /**
     * Pega todas as variáveis da paleta com seus respectivos códigos hexadecimais
     * @return um {@link Map} contendo as informações da paleta de cores
     * @see LeitorConfiguracao
     * */
    public Map<String, String> pegarInformacoesPaleta() {
        return leitorConfiguracao.pegarVariaveisPaleta();
    }

    /**
     * Pega o valor de um atributo específico, sendo necessário indicar o nome, a tabela e o tipo do atributo.
     * @param categoria a tabela/categoria na qual se encontra o atributo desejado
     * @param atributo o nome do atributo desejado
     * @param tipo o tipo do atributo, devendo ser de um dos seguintes tipos: {@code String}, {@code long}, {@code double} ou {@code boolean}
     * @return o valor do atributo requirido ou {@code null} caso o valor não tenha sido encontrado.
     * @see LeitorConfiguracao
     * */
    public <T> T pegarValorConfiguracao(String categoria, String atributo, Class<T> tipo) {
        return leitorConfiguracao.pegarValorConfiguracao(categoria, atributo, tipo);
    }
}
