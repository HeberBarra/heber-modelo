package io.github.heberbarra.modelador.configurador;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.heberbarra.modelador.configurador.json.JsonVerificador;
import io.github.heberbarra.modelador.logger.JavaLogger;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Responsável por ler e salvar os dados de um modelo de configuração
 * @since v0.0.2-SNAPSHOT
 * */
public class LeitorArquivoVerificacaoPadrao<T extends JsonVerificador<?>> extends LeitorArquivoVerificacao<T> {

    private static final Logger logger = JavaLogger.obterLogger(LeitorArquivoVerificacaoPadrao.class.getName());

    public LeitorArquivoVerificacaoPadrao(Class<T> tipoVerificador, File arquivoVerificador) {
        super(tipoVerificador, arquivoVerificador);
    }

    @Override
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
}
