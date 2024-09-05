package org.modelador.configurador.verificador;

import java.util.ArrayList;
import java.util.List;
import org.modelador.configurador.Recurso;
import org.modelador.configurador.verificador.json.JsonVerificadorConfiguracoes;
import org.modelador.configurador.verificador.json.JsonVerificadorPaleta;

public class VerificadorConfiguracao {

    private final List<LeitorArquivoVerificacao<?>> leitores;

    public VerificadorConfiguracao() {
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

    public List<LeitorArquivoVerificacao<?>> getLeitores() {
        return leitores;
    }
}
