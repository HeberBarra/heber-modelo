/*
 * Copyright (c) 2025. Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 *
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 *
 *   https://choosealicense.com/licenses/mit/
 *
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 *
 */

package io.github.heberbarra.modelador.infrastructure.controller;

import io.github.heberbarra.modelador.application.logging.JavaLogger;
import io.github.heberbarra.modelador.application.tradutor.TradutorWrapper;
import io.github.heberbarra.modelador.application.usecase.gerar.GeradorToken;
import io.github.heberbarra.modelador.domain.codigo.CodigoSaida;
import io.github.heberbarra.modelador.domain.configuracao.IConfigurador;
import io.github.heberbarra.modelador.domain.model.RequestTokenDesligar;
import io.github.heberbarra.modelador.infrastructure.configuracao.ConfiguradorPrograma;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ControladorDesligar {

    public static final String TOKEN_SECRETO;
    private static final Logger logger = JavaLogger.obterLogger(ControladorDesligar.class.getName());
    private final IConfigurador configurador;

    public ControladorDesligar() {
        this.configurador = ConfiguradorPrograma.getInstance();
    }

    static {
        GeradorToken geradorToken = new GeradorToken();
        geradorToken.gerarToken();
        TOKEN_SECRETO = geradorToken.getToken();
    }

    @PostMapping("/desligar")
    public void desligar(@RequestBody RequestTokenDesligar request) {
        if (configurador.pegarValorConfiguracao("programa", "desativar_botao_desligar", boolean.class)) return;

        if (TOKEN_SECRETO.equals(request.getToken())) {
            logger.info(TradutorWrapper.tradutor.traduzirMensagem("app.end"));
            System.exit(CodigoSaida.OK.getCodigo());
        }

        logger.severe(TradutorWrapper.tradutor.traduzirMensagem("error.shutdown.invalid.token"));
    }
}
