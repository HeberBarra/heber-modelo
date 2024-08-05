package org.modelador.configurador;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.modelador.Principal;

public enum PastaConfiguracao {
    MAC(Paths.get(
            "%s/Library/Application Support/%s/".formatted(System.getProperty("user.home"), Principal.NOME_PROGRAMA))),
    UNIX(Paths.get("%s/.config/%s".formatted(System.getProperty("user.home"), Principal.NOME_PROGRAMA))),
    WINDOWS(Paths.get("%s/%s".formatted(System.getenv("APPDATA"), Principal.NOME_PROGRAMA)));

    private final Path caminhoPasta;

    PastaConfiguracao(Path caminhoPasta) {
        this.caminhoPasta = caminhoPasta;
    }

    public Path getCaminhoPasta() {
        return caminhoPasta;
    }
}
