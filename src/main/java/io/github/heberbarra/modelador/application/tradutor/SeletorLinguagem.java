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
package io.github.heberbarra.modelador.application.tradutor;

import java.util.Locale;
import org.springframework.lang.NonNull;

public class SeletorLinguagem {

    public static void selecionarLinguagem(@NonNull String[] args) {
        for (String arg : args) {
            if (arg.equals("--language-english")) {
                Locale.setDefault(Locale.ENGLISH);
                return;
            }
        }

        Locale.setDefault(Locale.of("pt", "br"));
    }
}
