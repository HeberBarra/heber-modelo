package io.github.heberbarra.modelador.configurador;

import io.github.heberbarra.modelador.configurador.json.JsonVerificadorConfiguracoes;
import io.github.heberbarra.modelador.configurador.json.JsonVerificadorPaleta;
import io.github.heberbarra.modelador.logger.JavaLogger;
import io.github.heberbarra.modelador.recurso.AcessadorRecursos;
import io.github.heberbarra.modelador.tradutor.Tradutor;
import io.github.heberbarra.modelador.tradutor.TradutorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.tomlj.TomlTable;

/**
 * Analisa a configuração do usuário, e reporta os erros encontrados.
 * A variável {@link VerificadorConfiguracaoPrograma#configuracaoErrada} é definida como {@code true} caso haja algum erro grave.
 * @since v0.0.2-SNAPSHOT
 * */
public class VerificadorConfiguracaoPrograma implements VerificadorConfiguracao {

    private static final Logger logger = JavaLogger.obterLogger(VerificadorConfiguracaoPrograma.class.getName());
    private final List<LeitorArquivoVerificacao<?>> leitores;
    private final Tradutor tradutor;
    private boolean configuracaoErrada;

    public VerificadorConfiguracaoPrograma() {
        AcessadorRecursos acessadorRecursos = new AcessadorRecursos();
        configuracaoErrada = false;
        leitores = new ArrayList<>();
        leitores.add(new LeitorArquivoVerificacaoPadrao<>(
                JsonVerificadorConfiguracoes.class,
                acessadorRecursos.pegarArquivoRecurso("config/configuracao.template.json")));
        leitores.add(new LeitorArquivoVerificacaoPadrao<>(
                JsonVerificadorPaleta.class, acessadorRecursos.pegarArquivoRecurso("config/paleta.template.json")));
        TradutorFactory tradutorFactory = new TradutorFactory();
        tradutor = tradutorFactory.criarObjeto();
    }

    /**
     * Lê os arquivos de modelo de configuração.
     * @see LeitorArquivoVerificacaoPadrao
     * */
    public void lerArquivosTemplate() {
        leitores.forEach(LeitorArquivoVerificacao::lerArquivo);
    }

    @Override
    public boolean configuracoesContemErrosGraves() {
        return configuracaoErrada;
    }

    @Override
    public void registrarLeitor(LeitorArquivoVerificacao leitorArquivoVerificacao) {
        leitores.add(leitorArquivoVerificacao);
    }

    /**
     * Mostra na tela as informações de todos os leitores disponíveis.
     * @see LeitorArquivoVerificacaoPadrao
     * */
    public void mostrarInformacoes() {
        leitores.forEach(leitor -> System.out.println(leitor.getInformacoesJson()));
    }

    @SuppressWarnings("unchecked")
    public LeitorArquivoVerificacaoPadrao<JsonVerificadorPaleta> getLeitorPaleta() {
        return (LeitorArquivoVerificacaoPadrao<JsonVerificadorPaleta>) leitores.get(1);
    }

    @SuppressWarnings("unchecked")
    public LeitorArquivoVerificacaoPadrao<JsonVerificadorConfiguracoes> getLeitorConfiguracoes() {
        return (LeitorArquivoVerificacaoPadrao<JsonVerificadorConfiguracoes>) leitores.getFirst();
    }

    @Override
    public void verificarArquivoConfiguracao(
            Map<String, List<Map<String, String>>> configuracaoPadrao, TomlTable dados) {
        configuracaoErrada = false;

        for (String categoria : dados.keySet()) {

            if (!configuracaoPadrao.containsKey(categoria)) {
                logger.warning(tradutor.traduzirMensagem("error.config.category.notfound")
                        .formatted(categoria));
                continue;
            }

            TomlTable tabelaCategoria = dados.getTable(categoria);
            if (tabelaCategoria == null) {
                logger.warning(
                        tradutor.traduzirMensagem("error.config.category.empty").formatted(categoria));
                continue;
            }

            for (String atributo : tabelaCategoria.keySet()) {
                verificarAtributoConfiguracao(
                        configuracaoPadrao.get(categoria), atributo, tabelaCategoria.get(atributo));
            }
        }
    }

    @Override
    public void verificarAtributoConfiguracao(List<Map<String, String>> atributos, String nomeAtributo, Object valor) {
        int quantidade = 0;

        Map<String, String> atributoPadraoEncontrado = null;
        for (Map<String, String> atributoPadrao : atributos) {

            if (atributoPadrao.get("atributo").equals(nomeAtributo)) {
                atributoPadraoEncontrado = atributoPadrao;
                quantidade++;
            }
        }

        if (quantidade == 0) {
            logger.warning(
                    tradutor.traduzirMensagem("error.config.attribute.invalid").formatted(nomeAtributo));
            return;
        }

        if (quantidade > 1) {
            logger.warning(tradutor.traduzirMensagem("error.config.attribute.multiple.occurrences")
                    .formatted(nomeAtributo, quantidade));
        }

        String tipoPadraoAtributo = atributoPadraoEncontrado.get("tipo");
        String tipoAtributo = valor.getClass().getSimpleName();

        if (tipoPadraoAtributo.equals(tipoAtributo)) {
            return;
        }

        logger.warning(tradutor.traduzirMensagem("error.config.attribute.invalid.type")
                .formatted(nomeAtributo, tipoPadraoAtributo));
        configuracaoErrada = true;
    }

    @Override
    public void verificarArquivoPaleta(Map<String, List<Map<String, String>>> paletaPadrao, TomlTable dados) {
        TomlTable tabelaPaleta = dados.getTable("paleta");
        List<Map<String, String>> variaveisTabelaPadrao = paletaPadrao.get("paleta");

        if (tabelaPaleta == null) {
            logger.warning(tradutor.traduzirMensagem("error.paleta.notfound"));
            configuracaoErrada = true;
            return;
        }

        for (String variavel : tabelaPaleta.keySet()) {
            verificarVariavelPaleta(variaveisTabelaPadrao, variavel, tabelaPaleta.get(variavel));
        }
    }

    @Override
    public void verificarVariavelPaleta(List<Map<String, String>> variaveis, String nomeVariavel, Object valor) {
        String regexPaleta = "^#(?:[0-9a-fA-F]{3}){1,2}$";
        int quantidade = 0;

        for (Map<String, String> variavelPadrao : variaveis) {

            if (variavelPadrao.get("nomeVariavel").equals(nomeVariavel)) {
                quantidade++;
            }
        }

        if (quantidade == 0) {
            logger.warning(
                    tradutor.traduzirMensagem("error.paleta.variable.notfound").formatted(nomeVariavel));
            return;
        }

        if (quantidade > 1) {
            logger.warning(tradutor.traduzirMensagem("error.paleta.variable.multiple.occurrences")
                    .formatted(nomeVariavel, quantidade));
        }

        if (valor instanceof String valorVariavel) {
            if (!valorVariavel.matches(regexPaleta)) {
                logger.warning(tradutor.traduzirMensagem("error.paleta.variable.invalid.format")
                        .formatted(nomeVariavel));
                configuracaoErrada = true;
            }

            return;
        }

        logger.warning(
                tradutor.traduzirMensagem("error.paleta.variable.invalid.type").formatted(nomeVariavel));
        configuracaoErrada = true;
    }

    public List<LeitorArquivoVerificacao<?>> getLeitores() {
        return leitores;
    }
}
