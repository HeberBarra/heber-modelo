package org.modelador.configurador;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum PastaConfiguracao {
    MAC(Paths.get(System.getProperty("user.home") + "/Library/Application Support/der-modelador/")),
    UNIX(Paths.get(System.getProperty("user.home") + "/.config/der-modelador/")),
    WINDOWS(Paths.get(System.getenv("APPDATA") + "/der-modelador/"));

    private final Path caminhoPasta;

    PastaConfiguracao(Path caminhoPasta) {
        this.caminhoPasta = caminhoPasta;
    }

    public Path getCaminhoPasta() {
        return caminhoPasta;
    }
}
