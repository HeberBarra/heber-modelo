package org.modelador.configurador.verificador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.modelador.configurador.Recurso;
import org.modelador.configurador.verificador.json.JsonVerificadorConfiguracoes;
import org.modelador.configurador.verificador.json.JsonVerificadorPaleta;
import org.modelador.logger.JavaLogger;
import org.tomlj.TomlTable;

public class VerificadorConfiguracao {

    private static final Logger logger = JavaLogger.obterLogger(VerificadorConfiguracao.class.getName());
    private final List<LeitorArquivoVerificacao<?>> leitores;
    private boolean configuracaoErrada;

    public VerificadorConfiguracao() {
        configuracaoErrada = false;
        leitores = new ArrayList<>();
        leitores.add(new LeitorArquivoVerificacao<>(
                JsonVerificadorConfiguracoes.class, Recurso.pegarArquivoRecurso("config/configuracao.template.json")));
        leitores.add(new LeitorArquivoVerificacao<>(
                JsonVerificadorPaleta.class, Recurso.pegarArquivoRecurso("config/paleta.template.json")));
    }

    public void lerArquivosTemplate() {
        leitores.forEach(LeitorArquivoVerificacao::lerArquivo);
    }

    public void mostrarInformacoes() {
        leitores.forEach(leitor -> System.out.println(leitor.getInformacoesJson()));
    }

    @SuppressWarnings("unchecked")
    public LeitorArquivoVerificacao<JsonVerificadorPaleta> getLeitorPaleta() {
        return (LeitorArquivoVerificacao<JsonVerificadorPaleta>) leitores.get(1);
    }

    @SuppressWarnings("unchecked")
    public LeitorArquivoVerificacao<JsonVerificadorConfiguracoes> getLeitorConfiguracoes() {
        return (LeitorArquivoVerificacao<JsonVerificadorConfiguracoes>) leitores.getFirst();
    }

    public void verificarArquivoConfiguracoes(
            Map<String, List<Map<String, String>>> configuracaoPadrao, TomlTable dados) {
        configuracaoErrada = false;

        for (String categoria : dados.keySet()) {

            if (!configuracaoPadrao.containsKey(categoria)) {
                logger.warning("[ERRO NA CONFIGURAÇÃO] A categoria %s não existe.%n".formatted(categoria));
                continue;
            }

            TomlTable tabelaCategoria = dados.getTable(categoria);
            if (tabelaCategoria == null) {
                logger.warning("[ERRO NA CONFIGURAÇÃO] A categoria %s está vazia.%n".formatted(categoria));
                continue;
            }

            for (String atributo : tabelaCategoria.keySet()) {
                verificarAtributoConfiguracao(
                        configuracaoPadrao.get(categoria), atributo, tabelaCategoria.get(atributo));
            }
        }
    }

    private void verificarAtributoConfiguracao(List<Map<String, String>> atributos, String nomeAtributo, Object valor) {
        int quantidade = 0;

        Map<String, String> atributoPadraoEncontrado = null;
        for (Map<String, String> atributoPadrao : atributos) {

            if (atributoPadrao.get("atributo").equals(nomeAtributo)) {
                atributoPadraoEncontrado = atributoPadrao;
                quantidade++;
            }
        }

        if (quantidade == 0) {
            logger.warning("O atributo %s é inválido.%n".formatted(nomeAtributo));
            return;
        }

        if (quantidade > 1) {
            logger.warning("O atributo %s é repetido %d vezes.%n".formatted(nomeAtributo, quantidade));
        }

        String tipoPadraoAtributo = atributoPadraoEncontrado.get("tipo");
        String tipoAtributo = valor.getClass().getSimpleName();

        if (tipoPadraoAtributo.equals(tipoAtributo)) {
            return;
        }

        logger.warning("O valor do atributo %s é inválido, o tipo requerido é %s.%n"
                .formatted(nomeAtributo, tipoPadraoAtributo));
        configuracaoErrada = true;
    }

    public void verificarArquivoPaleta(Map<String, List<Map<String, String>>> paletaPadrao, TomlTable dados) {
        TomlTable tabelaPaleta = dados.getTable("paleta");
        List<Map<String, String>> variaveisTabelaPadrao = paletaPadrao.get("paleta");

        if (tabelaPaleta == null) {
            logger.warning("O arquivo de configuração da paleta a requer a tabela paleta.");
            configuracaoErrada = true;
            return;
        }

        for (String variavel : tabelaPaleta.keySet()) {
            verificarVariavelPaleta(variaveisTabelaPadrao, variavel, tabelaPaleta.get(variavel));
        }
    }

    private void verificarVariavelPaleta(List<Map<String, String>> variaveis, String nomeVariavel, Object valor) {
        String regexPaleta = "^#(?:[0-9a-fA-F]{3}){1,2}$";
        int quantidade = 0;

        for (Map<String, String> variavelPadrao : variaveis) {

            if (variavelPadrao.get("nomeVariavel").equals(nomeVariavel)) {
                quantidade++;
            }
        }

        if (quantidade == 0) {
            logger.warning("A variavel %s não foi encontrada.%n".formatted(nomeVariavel));
            return;
        }

        if (quantidade > 1) {
            logger.warning("A variavel %s foi repetida %d vezes.%n".formatted(nomeVariavel, quantidade));
        }

        if (valor instanceof String valorVariavel) {
            if (!valorVariavel.matches(regexPaleta)) {
                logger.warning("O valor da variável %s não tem o formatado apropriado de: #000000. %n"
                        .formatted(nomeVariavel));
                configuracaoErrada = true;
            }

            return;
        }

        logger.warning("O valor da variavel %s deve ser do tipo String.%n".formatted(nomeVariavel));
        configuracaoErrada = true;
    }

    public List<LeitorArquivoVerificacao<?>> getLeitores() {
        return leitores;
    }

    public boolean isConfiguracaoErrada() {
        return configuracaoErrada;
    }

    protected void setConfiguracaoErrada(boolean configuracaoErrada) {
        this.configuracaoErrada = configuracaoErrada;
    }
}
