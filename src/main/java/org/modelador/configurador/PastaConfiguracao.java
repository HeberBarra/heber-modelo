package org.modelador.configurador;

import org.jetbrains.annotations.NotNull;
import org.modelador.Principal;
import org.modelador.logger.JavaLogger;
import java.io.File;
import java.util.logging.Logger;

public class PastaConfiguracao {

    private static final Logger logger = JavaLogger.obterLogger(PastaConfiguracao.class.getName());
    public static final String PASTA_CONFIGURACAO = decidirPastaConfiguracao();

    static {
        criarPastaConfiguracao();
    }

    private static void criarPastaConfiguracao() {
       if (new File(PASTA_CONFIGURACAO).mkdir()) {
           logger.info("Pasta %s criada com sucesso".formatted(PASTA_CONFIGURACAO));
       }
    }

    private static @NotNull String decidirPastaConfiguracao() {
        String nomeSistema = System.getProperty("os.name").toLowerCase();
        String pastaConfiguracao = System.getenv("%s_CONFIG_DIR".formatted(Principal.NOME_PROGRAMA.toUpperCase()));
        String xdgConfigHome = System.getenv("XDG_CONFIG_HOME");

        if (pastaConfiguracao != null) {
            return pastaConfiguracao.endsWith("/") ? pastaConfiguracao : pastaConfiguracao + "/";
        }

        if (xdgConfigHome != null) {
            return "%s/%s/".formatted(xdgConfigHome, Principal.NOME_PROGRAMA);
        }

        if (nomeSistema.contains("windows")) {
            return "%s/%s/".formatted(System.getenv("APPDATA"), Principal.NOME_PROGRAMA);
        } if (nomeSistema.contains("mac") || nomeSistema.contains("darwin")) {
            return "%s/Library/Preferences/%s/".formatted(System.getProperty("user.home"), Principal.NOME_PROGRAMA);
        } else {
            return "%s/.config/%s".formatted(System.getProperty("user.home"), Principal.NOME_PROGRAMA);
        }
    }

}
