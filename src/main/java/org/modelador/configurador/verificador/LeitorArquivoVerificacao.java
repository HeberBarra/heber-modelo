package org.modelador.configurador.verificador;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.modelador.configurador.verificador.json.JsonVerificador;
import org.modelador.logger.JavaLogger;

/**
 * Responsável por ler e salvar os dados de um modelo de configuração
 * @since v0.0.2-SNAPSHOT
 * */
public class LeitorArquivoVerificacao<T extends JsonVerificador<?>> {

    private final Class<T> tipoVerificador;
    public File arquivoVerificador;
    private static final Logger logger = JavaLogger.obterLogger(LeitorArquivoVerificacao.class.getName());
    private T informacoesJson;

    public LeitorArquivoVerificacao(Class<T> tipoVerificador, File arquivoVerificador) {
        this.tipoVerificador = tipoVerificador;
        this.arquivoVerificador = arquivoVerificador;
    }

    /**
     * O lê o arquivo de verificação e salvas as informações lidas.
     * */
    public void lerArquivo() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            informacoesJson = objectMapper.readValue(arquivoVerificador, tipoVerificador);
        } catch (IOException e) {
            logger.severe("Um erro ocorreu ao tentar ler o arquivo template configuração: %s%n"
                    .formatted(arquivoVerificador));
            logger.severe("Finalizando o programa...");
        }
    }

    public T getInformacoesJson() {
        return informacoesJson;
    }

    public void setInformacoesJson(T informacoesJson) {
        this.informacoesJson = informacoesJson;
    }

    public File getArquivoVerificador() {
        return arquivoVerificador;
    }

    protected void setArquivoVerificador(File arquivoVerificador) {
        this.arquivoVerificador = arquivoVerificador;
    }
}
