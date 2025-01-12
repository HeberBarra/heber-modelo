package io.github.heberbarra.modelador.tradutor;

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
