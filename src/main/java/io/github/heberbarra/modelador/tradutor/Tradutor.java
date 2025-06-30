/**
 * Copyright (C) 2025 Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 * <p>
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 * <p>
 * https://choosealicense.com/licenses/mit/
 * <p>
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 */
package io.github.heberbarra.modelador.tradutor;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class Tradutor {

    private final MessageSource messageSource;

    public Tradutor(@Autowired MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String traduzirMensagem(String codigoMensagem) {
        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(codigoMensagem, null, locale);
    }
}
