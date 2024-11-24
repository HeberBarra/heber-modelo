package io.github.heberbarra.modelador.configurador;

import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.configurador.toml.ConversorToml;
import io.github.heberbarra.modelador.configurador.toml.ConversorTomlPrograma;
import io.github.heberbarra.modelador.logger.JavaLogger;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Gerencia as configurações do programa, e permite acesso fácil às mesmas.
 * <p>
 * Utiliza o anti pattern {@code Singleton} para garantir o compartilhamento das informações entre os utilizadores.
 * @since v0.0.1-SNAPSHOT
 * */
public final class ConfiguradorPrograma implements Configurador {

    public static final String ARQUIVO_PALETA = "paleta.toml";
    public static final String ARQUIVO_CONFIGURACOES = "config.toml";
    private static final Logger logger = JavaLogger.obterLogger(ConfiguradorPrograma.class.getName());
    private static volatile ConfiguradorPrograma configurador;
    private final ConversorToml conversorToml;
    private final PastaConfiguracaoPrograma pastaConfiguracao;
    private final CriadorConfiguracoes criadorConfiguracoes;
    private final VerificadorConfiguracaoPrograma verificadorConfiguracao;
    private final CombinadorConfiguracoes combinadorConfiguracoes;
    private final LeitorConfiguracao leitorConfiguracao;

    private ConfiguradorPrograma() {
        conversorToml = new ConversorTomlPrograma();
        pastaConfiguracao = new PastaConfiguracaoPrograma();
        criadorConfiguracoes = new CriadorConfiguracoes();
        verificadorConfiguracao = new VerificadorConfiguracaoPrograma();
        leitorConfiguracao =
                new LeitorConfiguracao(pastaConfiguracao.getPasta(), ARQUIVO_CONFIGURACOES, ARQUIVO_PALETA);
        combinadorConfiguracoes = new CombinadorConfiguracoes();
    }

    public static synchronized ConfiguradorPrograma getInstance() {
        if (configurador == null) {
            configurador = new ConfiguradorPrograma();
        }

        return configurador;
    }

    /**
     * Lê as configurações padrões do programa, para permitir a geração dos arquivos caso não existam ainda
     * @see CriadorConfiguracoes
     * @see ConfiguradorPrograma#criarArquivos()
     * */
    @Override
    public void lerConfiguracaoPadrao() {
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
    @Override
    public void criarArquivos() {
        criadorConfiguracoes.criarPastaConfiguracao(pastaConfiguracao.getPasta());
        lerConfiguracaoPadrao();
        criadorConfiguracoes.criarArquivoConfiguracoes(pastaConfiguracao.getPasta(), ARQUIVO_CONFIGURACOES);
        criadorConfiguracoes.criarArquivoPaleta(pastaConfiguracao.getPasta(), ARQUIVO_PALETA);
    }

    /**
     * Lê as configurações do programa.
     * @see LeitorConfiguracao
     * */
    @Override
    public void lerConfiguracao() {
        leitorConfiguracao.lerArquivos();
    }

    /**
     * Verifica se há algum erro na configuração do programa, caso haja um erro o grave o programa é encerrado com o código de saída: {@link CodigoSaida#ERRO_CONFIGURACOES}.
     * @see VerificadorConfiguracaoPrograma
     * */
    public void verificarConfiguracoes() {
        verificadorConfiguracao.verificarArquivoConfiguracao(
                criadorConfiguracoes.getConfiguracaoPadrao(), leitorConfiguracao.getInformacoesConfiguracoes());
        verificadorConfiguracao.verificarArquivoPaleta(
                criadorConfiguracoes.getPaletaPadrao(), leitorConfiguracao.getInformacoesPaleta());

        if (verificadorConfiguracao.configuracoesContemErrosGraves()) {
            logger.severe("A configuração contém erros graves. Para evitar bugs no programa, ele será encerrado");
            System.exit(CodigoSaida.ERRO_CONFIGURACOES.getCodigo());
        }
    }

    /**
     * Mostra as configurações atuais do programa
     * @see LeitorConfiguracao
     * */
    @Override
    public void mostrarConfiguracao() {
        String[] informacoesConfiguracao = leitorConfiguracao.pegarStringConfiguracao();

        for (String informacao : informacoesConfiguracao) {
            System.out.println(informacao);
        }
    }

    /**
     * Atualiza as configurações do programa, mantendo os valores, com novas opções, se houverem, de configuração
     * @see CombinadorConfiguracoes
     * */
    @Override
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

        String dadosConfiguracoesToml = conversorToml.converterMapConfiguracaoParaStringToml(dadosConfiguracoes);
        String dadosPaletaToml = conversorToml.converterMapPaletaParaStringToml(dadosPaleta);

        criadorConfiguracoes.sobrescreverArquivoConfiguracoes(
                pastaConfiguracao.getPasta(), ARQUIVO_CONFIGURACOES, dadosConfiguracoesToml);
        criadorConfiguracoes.sobrescreverArquivoPaleta(pastaConfiguracao.getPasta(), ARQUIVO_PALETA, dadosPaletaToml);
        this.lerConfiguracao();
    }

    /**
     * Pega o código hexadecimal de uma cor específica da paleta.
     * @param nomeVariavel o nome da variável na paleta de cores
     * @return o código hexadecimal da cor
     * @see LeitorConfiguracao
     * */
    @Override
    public String pegarCorPaleta(String nomeVariavel) {
        return leitorConfiguracao.pegarCorPaleta(nomeVariavel);
    }

    /**
     * Pega todas as variáveis da paleta com seus respectivos códigos hexadecimais
     * @return um {@link Map} contendo as informações da paleta de cores
     * @see LeitorConfiguracao
     * */
    @Override
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
    @Override
    public <T> T pegarValorConfiguracao(String categoria, String atributo, Class<T> tipo) {
        return leitorConfiguracao.pegarValorConfiguracao(categoria, atributo, tipo);
    }
}