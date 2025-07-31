/*
 * Copyright (C) 2025 Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
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

package io.github.heberbarra.modelador.infrastructure.configuracao;

import io.github.heberbarra.modelador.Principal;
import io.github.heberbarra.modelador.application.logging.JavaLogger;
import io.github.heberbarra.modelador.application.tradutor.TradutorWrapper;
import io.github.heberbarra.modelador.domain.configuracao.IPastaConfiguracao;
import io.github.heberbarra.modelador.infrastructure.acessador.AcessadorRecursos;
import java.io.File;
import java.util.logging.Logger;

/**
 * Determina em qual pasta os arquivos de configuração serão criados.
 * <p>
 * A escolha é determinada com base no sistema operacional e se for providenciada pela variável de ambiente {@code HEBER_MODELO_CONFIG_DIR}
 * <p>
 * A pasta respectiva de cada sistema operacional é a seguinte:
 * <li>GNU/Linux: .config/Heber-Modelo/
 * <li>MacOS: $HOME/Library/Preferences/Heber-Modelo/
 * <li>Windows: %AppData%/Heber-Modelo/
 * <li>Outro: .config/Heber-Modelo/
 * <p>
 * A variável de ambiente {@code XDG_CONFIG_HOME}, caso presente, é utilizada para decidir o local das configurações
 *
 * @since v0.0.1-SNAPSHOT
 */
public class PastaConfiguracaoPrograma implements IPastaConfiguracao {

    private static final Logger logger = JavaLogger.obterLogger(PastaConfiguracaoPrograma.class.getName());
    private final String pasta = decidirPastaConfiguracao();
    private final AcessadorRecursos acessadorRecursos;

    public PastaConfiguracaoPrograma() {
        acessadorRecursos = new AcessadorRecursos();
    }

    public void criarPastaConfiguracao() {
        if (new File(pasta).mkdir()) {
            logger.info(TradutorWrapper.tradutor
                    .traduzirMensagem("file.dir.creation.success")
                    .formatted(pasta));
        }
    }

    public String decidirPastaConfiguracao() {
        String nomeSistema = System.getProperty("os.name").toLowerCase();
        String pastaConfiguracao = acessadorRecursos != null
                ? acessadorRecursos.pegarValorVariavelAmbiente("%s_CONFIG_DIR"
                        .formatted(Principal.NOME_PROGRAMA.toUpperCase().replace("-", "_")))
                : null;
        String xdgConfigHome = System.getenv("XDG_CONFIG_HOME");

        if (pastaConfiguracao != null) {
            if (pastaConfiguracao.startsWith("~")) {
                pastaConfiguracao = pastaConfiguracao.replace("~", System.getProperty("user.home"));
            }

            if (pastaConfiguracao.startsWith("$HOME")) {
                pastaConfiguracao = pastaConfiguracao.replace("$HOME", System.getProperty("user.home"));
            }

            return pastaConfiguracao.endsWith("/") ? pastaConfiguracao : pastaConfiguracao + "/";
        }

        if (xdgConfigHome != null) {
            return "%s/%s/".formatted(xdgConfigHome, Principal.NOME_PROGRAMA);
        }

        if (nomeSistema.contains("windows")) {
            return "%s/%s/".formatted(System.getenv("APPDATA"), Principal.NOME_PROGRAMA);
        }
        if (nomeSistema.contains("mac") || nomeSistema.contains("darwin")) {
            return "%s/Library/Preferences/%s/".formatted(System.getProperty("user.home"), Principal.NOME_PROGRAMA);
        } else {
            return "%s/.config/%s".formatted(System.getProperty("user.home"), Principal.NOME_PROGRAMA);
        }
    }

    public String getPasta() {
        return pasta;
    }
}
