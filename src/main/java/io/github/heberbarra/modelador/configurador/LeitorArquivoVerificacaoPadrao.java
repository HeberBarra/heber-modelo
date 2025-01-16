/**
 * Copyright (C) 2025 Heber Ferreira Barra, João Gabriel de Cristo, Matheus Jun Alves Matuda.
 * <p>
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 * <p>
 * https://choosealicense.com/licenses/mit/
 * <p>
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 */
package io.github.heberbarra.modelador.configurador;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.heberbarra.modelador.configurador.json.JsonVerificador;
import io.github.heberbarra.modelador.logger.JavaLogger;
import io.github.heberbarra.modelador.tradutor.TradutorWrapper;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Responsável por ler e salvar os dados de um modelo de configuração
 *
 * @since v0.0.2-SNAPSHOT
 */
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
            logger.severe(TradutorWrapper.tradutor
                    .traduzirMensagem("error.file.read.template")
                    .formatted(arquivoVerificador));
            logger.severe(TradutorWrapper.tradutor.traduzirMensagem("app.end"));
        }
    }
}
